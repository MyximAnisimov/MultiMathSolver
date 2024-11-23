package app.multimathsolver.improvedeulermethod;

import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

import static java.lang.StrictMath.sin;

@Service
class ImprovedEulerMethodService {

    private static double first_function(double x, double y) {
        return sin(x);
    }

    private static double second_function(double x, double y) {
        return (x * y)/2;
    }

    private static double third_function(double x, double y) {
        return y - (2 * x)/y;
    }

    private static double fourth_function(double x, double y) {
        return x + y;
    }

    private static double default_function(double x, double y) {
        return 0.0;
    }

    private static BiFunction<Double, Double, Double> get_function(int n) {
        switch (n) {
            case (1):
                return ImprovedEulerMethodService::first_function;
            case (2):
                return ImprovedEulerMethodService::second_function;
            case (3):
                return ImprovedEulerMethodService::third_function;
            case (4):
                return ImprovedEulerMethodService::fourth_function;
            default:
                return ImprovedEulerMethodService::default_function;
        }
    }

    public double solveByEulerImproved(int f, double epsilon, double a,  double y_a, double b) {
        double h = 0.1;
        double y = y_a;
        double x = a;

        BiFunction<Double, Double, Double> func = get_function(f);

        while (x < b) {
            double y_next = y + h * func.apply(x, y);

            double y_mid = y + h/2 * (func.apply(x, y) + func.apply(x + h, y_next));
            double y_next_improved = y + h * func.apply(x + h/2, y_mid);

            double error = Math.abs(y_next_improved - y_next);
            h = h * Math.sqrt(epsilon / (2 * error));

            y = y_next_improved;
            x += h;
        }

        return y;
    }

}