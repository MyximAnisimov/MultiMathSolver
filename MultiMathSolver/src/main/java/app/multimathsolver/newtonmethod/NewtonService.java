package app.multimathsolver.newtonmethod;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.tan;
    /*
     * How to use this function:
     *    List<Function<Double, List<Double>> funcs = SNAEFunctions.get_functions(4);
     *    funcs[0].apply(List.of(0.0001, 0.002));
     */
@Service
class NewtonService {
    private static final double EPSILON = 1e-5;

    public List<Double> solve_by_fixed_point_iterations(int system_id, int number_of_unknowns, List<Double> initial_approximations) {
        List<Function<List<Double>, Double>> functions = SNAEFunctions.get_functions(system_id);

        List<List<Double>> jacobianMatrix;
        List<Double> fValues;
        List<Double> deltaX = new ArrayList<>(Collections.nCopies(number_of_unknowns, 1.0)); // Initialize deltaX with dummy values

        while (deltaX.stream().mapToDouble(Double::doubleValue).anyMatch(val -> Math.abs(val) > EPSILON)) {
            jacobianMatrix = calculateJacobianMatrix(functions, initial_approximations);
            fValues = calculateFunctionValues(functions, initial_approximations);
            deltaX = solveLinearSystem(jacobianMatrix, fValues);

            for (int i = 0; i < number_of_unknowns; i++) {
                initial_approximations.set(i, initial_approximations.get(i) - deltaX.get(i));
            }
        }

        return initial_approximations;
    }

    private List<List<Double>> calculateJacobianMatrix(List<Function<List<Double>, Double>> functions, List<Double> args) {
        int n = args.size();
        List<List<Double>> jacobianMatrix = new ArrayList<>();

        for (int i = 0; i < n; i++) {List<Double> rowValues = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                double delta = 1e-6;

                List<Double> newArgsPlus = new ArrayList<>(args);
                newArgsPlus.set(j, args.get(j) + delta);
                double fxPlus = functions.get(i).apply(newArgsPlus);

                List<Double> newArgsMinus = new ArrayList<>(args);
                newArgsMinus.set(j, args.get(j) - delta);
                double fxMinus = functions.get(i).apply(newArgsMinus);

                double partialDerivative = (fxPlus - fxMinus) / (2 * delta);
                rowValues.add(partialDerivative);
            }
            jacobianMatrix.add(rowValues);
        }

        return jacobianMatrix;
    }

    private List<Double> calculateFunctionValues(List<Function<List<Double>, Double>> functions, List<Double> args) {
        List<Double> fValues = new ArrayList<>();
        for (Function<List<Double>, Double> function : functions) {
            fValues.add(function.apply(args));
        }
        return fValues;
    }

    private List<Double> solveLinearSystem(List<List<Double>> matrixA, List<Double> vectorB) {
        int n = vectorB.size();
        List<List<Double>> augMatrix = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>(matrixA.get(i));
            row.add(vectorB.get(i));
            augMatrix.add(row);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double factor = augMatrix.get(j).get(i) / augMatrix.get(i).get(i);
                for (int k = i; k < n + 1; k++) {
                    double newValue = augMatrix.get(j).get(k) - (factor * augMatrix.get(i).get(k));
                    augMatrix.get(j).set(k, newValue);
                }
            }
        }

        List<Double> solution = new ArrayList<>(Collections.nCopies(n, 0.0));
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += augMatrix.get(i).get(j) * solution.get(j);
            }
            solution.set(i, (augMatrix.get(i).get(n) - sum) / augMatrix.get(i).get(i));
        }

        return solution;
    }
}
