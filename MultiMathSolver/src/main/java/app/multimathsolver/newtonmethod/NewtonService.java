package app.multimathsolver.newtonmethod;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Service
class NewtonService {
    private static final double EPSILON = 1e-5;
    private static final int MAX_ITERATIONS = 100; // Максимальное количество итераций

    public List<Double> solve_by_fixed_point_iterations(int system_id, int number_of_unknowns, List<Double> initial_approximations) {
        // Проверка количества приближений
        if (initial_approximations.size() != number_of_unknowns) {
            throw new IllegalArgumentException();
        }

        // Остальная часть кода
        List<Function<List<Double>, Double>> functions = SNAEFunctions.getFunctions(system_id);
        List<List<Double>> jacobianMatrix;
        List<Double> fValues;
        List<Double> deltaX = new ArrayList<>(Collections.nCopies(number_of_unknowns, 1.0));

        int iterationCount = 0; // Счётчик итераций

        while (iterationCount < MAX_ITERATIONS) {
            jacobianMatrix = calculateJacobianMatrix(functions, initial_approximations);
            fValues = calculateFunctionValues(functions, initial_approximations);
            deltaX = solveLinearSystem(jacobianMatrix, fValues);

            // Обновление приближений
            for (int i = 0; i < number_of_unknowns; i++) {
                initial_approximations.set(i, initial_approximations.get(i) - deltaX.get(i));
            }

            // Проверка, завершается ли процесс
            double maxDeltaX = deltaX.stream().mapToDouble(Math::abs).max().orElse(Double.MAX_VALUE);
            if (maxDeltaX < EPSILON) {
                break; // Метод сошелся
            }

            iterationCount++;
        }

        if (iterationCount >= MAX_ITERATIONS) {
            throw new ArithmeticException();
        }

        return initial_approximations;
    }

    private List<List<Double>> calculateJacobianMatrix(List<Function<List<Double>, Double>> functions, List<Double> args) {
        int n = args.size();
        List<List<Double>> jacobianMatrix = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Double> rowValues = new ArrayList<>();
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
            double value = function.apply(args);
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                throw new ArithmeticException();
            }
            fValues.add(value);
        }
        return fValues;
    }

    private List<Double> solveLinearSystem(List<List<Double>> matrixA, List<Double> vectorB) {
        int n = vectorB.size();
        List<List<Double>> augMatrix = new ArrayList<>(n);

        // Создание расширенной матрицы
        for (int i = 0; i < n; i++) {
            List<Double> row = new ArrayList<>(matrixA.get(i));
            row.add(vectorB.get(i));
            augMatrix.add(row);
        }

        // Прямой ход метода Гаусса
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
        // Обратный ход метода Гаусса
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