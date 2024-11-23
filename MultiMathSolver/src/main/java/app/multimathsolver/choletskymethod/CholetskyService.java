package app.multimathsolver.choletskymethod;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Обязательно проверить правильность данных
@Service
class CholetskyService {

    public static boolean isSolutionExists = true;
    public static String errorMessage = "The system has no roots of equations or has an infinite set of them.";

    //        public static boolean isInputMatrixCorrect(int n, List<List<Double>> inputMatrix){
////        boolean isCorrect = true;
//        if(n != inputMatrix.size()){
////            isCorrect = false;
//            isSolutionExists = false;
//            return isSolutionExists;
//        }
//        for(List<Double> oneLine : inputMatrix){
//            if(oneLine.size() != n){
//               isSolutionExists = false;
//                break;
//            }
//        }
//        return isSolutionExists;
//    }
    private boolean isInputMatrixSymmetrical(List<List<Double>> inputMatrix) {
        if (inputMatrix.size() < inputMatrix.get(0).size() - 1) {
            isSolutionExists = false;
            return isSolutionExists;
        }
        return isSolutionExists;
    }

    private Double[] getVectorB(List<List<Double>> inputMatrix) {
        Double[] vectorB = new Double[inputMatrix.size()];

        for (int i = 0; i < inputMatrix.size(); i++) {
            for (int j = 0; j < inputMatrix.get(i).size(); j++) {
                if (j == inputMatrix.get(i).size() - 1) vectorB[i] = inputMatrix.get(i).get(j);
            }
        }
        return vectorB;
    }

    private void fillMatrixes(int n, List<List<Double>> inputMatrix, Double[][] matrixB, Double[][] matrixC){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){

                if(j == 0) {
                    matrixB[i][j] = inputMatrix.get(i).get(j);
                }
                else if(i >= j && j > 0){
                    double sum = 0.0;
                    for(int k=0; k< j; k++){
                        sum = sum + matrixB[i][k] * matrixC[k][j];
                    }
                    matrixB[i][j] = inputMatrix.get(i).get(j) - sum;
                }
                else {
                    matrixB[i][j]= 0.0;
                }

                if(0 < i && i < j){
                    double sum = 0.0;
                    for(int k=0; k<i; k++){
                        sum = sum + matrixB[i][k] * matrixC[k][j];
                    }
                    matrixC[i][j] = (inputMatrix.get(i).get(j)- sum) / matrixB[i][i];
                }
                else if(i == 0){
                    matrixC[i][j] = inputMatrix.get(i).get(j) / matrixB[i][i];
                }
                else if(i==j){
                    matrixC[i][j] = 1.0;
                }
                else {
                    matrixC[i][j] = 0.0;
                }
            }
        }
    }

    private void makeInversionMatrix(Double[][] matrix, int matrixSize) {
        double temp;

        Double[][] identityMatrix = new Double[matrixSize][matrixSize];


        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                identityMatrix[i][j] = 0.0;

                if (i == j) identityMatrix[i][j] = 1.0;
            }
        }

        for (int k = 0; k < matrixSize; k++) {
            temp = matrix[k][k];

            for (int j = 0; j < matrixSize; j++) {
                if (temp == 0.0) {
                    isSolutionExists = false;
                    return;
                }
                else {
                    matrix[k][j] /= temp;
                    identityMatrix[k][j] /= temp;
                }
            }

            for (int i = k + 1; i < matrixSize; i++) {
                temp = matrix[i][k];

                for (int j = 0; j < matrixSize; j++) {
                    matrix[i][j] -= matrix[k][j] * temp;
                    identityMatrix[i][j] -= identityMatrix[k][j] * temp;
                }
            }
        }

        for (int k = matrixSize - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = matrix[i][k];

                for (int j = 0; j < matrixSize; j++) {
                    matrix[i][j] -= matrix[k][j] * temp;
                    identityMatrix[i][j] -= identityMatrix[k][j] * temp;
                }
            }
        }

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) matrix[i][j] = identityMatrix[i][j];
        }

    }
    private Double[] getCalculatedVector(Double[][] matrix, Double[] vectorB) {

        Double[] vectorY = new Double[vectorB.length];
        Double elementValue = 0.0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                elementValue = elementValue + vectorB[j] * matrix[i][j];
            }

            vectorY[i] = elementValue;
            elementValue = 0.0;
        }

        return vectorY;
    }

    public List<Double> solveByCholeskyDecomposition(int n, List<List<Double>> matrix) {
//        if(!isInputMatrixSymmetrical(matrix) || !isInputMatrixCorrect(n, matrix)){
//            return null;
//        }
        Double[] vectorB = getVectorB(matrix);

        Double[][] matrixB = new Double[n][n];
        Double[][] matrixC = new Double[n][n];

        fillMatrixes(n, matrix, matrixB, matrixC);
        makeInversionMatrix(matrixB, n);
        makeInversionMatrix(matrixC, n);

        Double[] vectorY = getCalculatedVector(matrixB, vectorB);
        Double[] vectorX = getCalculatedVector(matrixC, vectorY);

        List<Double> result = new ArrayList<>();
        result.addAll(Arrays.stream(vectorY).toList());
        result.addAll(Arrays.stream(vectorX).toList());
        return result;
    }

}

