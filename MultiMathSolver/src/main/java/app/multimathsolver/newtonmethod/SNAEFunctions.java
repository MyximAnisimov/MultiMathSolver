package app.multimathsolver.newtonmethod;

import java.util.List;
import java.util.function.Function;

import static java.lang.StrictMath.*;
import static java.lang.StrictMath.pow;

class SNAEFunctions {
    static double k = 0.4;
    static double a = 0.9;

    private static double firstFunction(List<Double> args) {
        return sin(args.get(0));
    }

    private static double secondFunction(List<Double> args) {
        return (args.get(0) * args.get(1)) / 2;
    }

    private static double thirdFunction(List<Double> args) {
        return tan(args.get(0) * args.get(1) + k) - pow(args.get(0), 2);
    }

    private static double fourthFunction(List<Double> args) {
        return a * pow(args.get(0), 2) + 2 * pow(args.get(1), 2) - 1;
    }

    private static double fifthFunction(List<Double> args) {
        return pow(args.get(0), 2) + pow(args.get(1), 2) + pow(args.get(2), 2) - 1;
    }

    private static double sixFunction(List<Double> args) {
        return 2 * pow(args.get(0), 2) + pow(args.get(1), 2) - 4 * args.get(2);
    }


    private static double sevenFunction(List<Double> args) {
        return 3 * pow(args.get(0), 2) - 4 * args.get(1) + pow(args.get(2), 2);
    }

    private static double defaultFunction(List<Double> args) {
        return 0.0;
    }
    public static List<Function<List<Double>, Double>> getFunctions(int n) {
        switch (n) {
            case (1):
                return List.of(SNAEFunctions::firstFunction, SNAEFunctions::secondFunction);
            case (2):{
                SNAEFunctions.k = 0.4;
                SNAEFunctions.a = 0.9;
                return List.of(SNAEFunctions::thirdFunction, SNAEFunctions::fourthFunction);
            }
            case (3):{
                SNAEFunctions.k = 0.0;
                SNAEFunctions.a = 0.5;
                return List.of(SNAEFunctions::thirdFunction, SNAEFunctions::fourthFunction);
            }
            case (4):
                return List.of(SNAEFunctions::fifthFunction, SNAEFunctions::sixFunction, SNAEFunctions::sevenFunction);
            default:
                return List.of(SNAEFunctions::defaultFunction);
        }
    }
}



