package app.multimathsolver.simpsonmethod;
import org.springframework.stereotype.Service;

import java.util.function.*;

import static java.lang.StrictMath.log;
import static java.lang.StrictMath.sin;

@Service
class SimpsonService {
    public static String error_message = "";
    public static boolean has_discontinuity = false;

    private static double firstFunction(double x) {
        return 1 / x;
    }

    private static double secondFunction(double x) {
        return sin(x) / x;
    }

    private static double thirdFunction(double x) {
        return x * x + 2;
    }

    private static double fourthFunction(double x) {
        return 2 * x + 2;
    }

    private static double fiveFunction(double x) {
        return log(x);
    }

    private static Function<Double, Double> getFunction(int n) {
        switch (n) {
            case (1):
                return SimpsonService::firstFunction;
            case (2):
                return SimpsonService::secondFunction;
            case (3):
                return SimpsonService::thirdFunction;
            case (4):
                return SimpsonService::fourthFunction;
            case (5):
                return SimpsonService::fiveFunction;
            default:
                throw new UnsupportedOperationException("Function " + n + " not defined.");
        }
    }

    public double calculateIntegral(double a, double b, int f, double epsilon) {
        SimpsonService.has_discontinuity = false;

        Function<Double, Double> func = getFunction(f);

        if (Double.isNaN(func.apply(a)) || Double.isNaN(func.apply(b))||  Double.isInfinite(func.apply(a)) || Double.isInfinite(func.apply(b))) {
            SimpsonService.has_discontinuity = true;
            SimpsonService.error_message = "Integrated function has discontinuity or does not defined in current interval";
            return 0.0;
        }

        double h = (b - a) / 2;

        double integral = (h / 3) * (func.apply(a) + 4 * func.apply(a + h) + func.apply(b));

        double prev_integral = integral;
        do {
            prev_integral = integral;
            h /= 2;
            double sum = 0;
            for (double x = a + h; x < b; x += 2 * h) {
                if (Double.isNaN(func.apply(x)) || Double.isInfinite(func.apply(x))) {
                    SimpsonService.has_discontinuity = true;
                    SimpsonService.error_message = "Integrated function has discontinuity or does not defined in current interval";
                    return 0.0;
                }
                sum += 4 * func.apply(x);
            }
            for (double x = a + 2 * h; x < b; x += 2 * h) {
                if (Double.isNaN(func.apply(x)) || Double.isInfinite(func.apply(x))) {
                    SimpsonService.has_discontinuity = true;
                    SimpsonService.error_message = "Integrated function has discontinuity or does not defined in current interval";
                    return 0.0;
                }
                sum += 2 * func.apply(x);
            }
            integral = (h / 3) * (func.apply(a) + func.apply(b) + sum);
        } while (((double) 1 /15) *Math.abs(integral - prev_integral) > epsilon);
        if(integral <= 0 && a >b){
            return integral;
        }
        else if((integral > 0 || integral < 0) && a <b){
            return integral;
        }
        else if(integral > 0 && a > b){
            return -integral;
        }
        else return integral;
    }

}