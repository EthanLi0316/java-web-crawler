import java.util.ArrayList;
import java.util.List;

public class Matmult {

    public static double[][] mult_scalar(double[][] matrix, double scale) {
        double[][] result = new double[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new double[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[i][j] * scale;
            }
        }
        return result;
    }

    public static double[][] mult_matrix(double[][] a, double[][] b) {
        int rows_a = a.length;
        int cols_a = a[0].length;
        int rows_b = b.length;
        int cols_b = b[0].length;

        if (cols_a != rows_b) {
            throw new IllegalArgumentException("Invalid dimensions for matrix multiplication");
        }

        double[][] product_matrix = new double[rows_a][cols_b];
        for (int i = 0; i < rows_a; i++) {
            for (int j = 0; j < cols_b; j++) {
                double sum_elements = 0;
                for (int k = 0; k < cols_a; k++) {
                    sum_elements += a[i][k] * b[k][j];
                }
                product_matrix[i][j] = sum_elements;
            }
        }
        return product_matrix;
    }

    public static double euclidean_dist(double[][] a, double[][] b) {
        double finalEuclideanValue = 0;
        for (int colIndex = 0; colIndex < a[0].length; colIndex++) {
            double eachEuclideanValue = Math.pow(a[0][colIndex] - b[0][colIndex], 2);
            finalEuclideanValue += eachEuclideanValue;
        }
        finalEuclideanValue = Math.sqrt(finalEuclideanValue);
        return finalEuclideanValue;
    }

    public static double vectorSquare(ArrayList<Double> A) {
        double total = 0;
        for (double num : A) {
            total += Math.pow(num, 2);
        }
        return total;
    }

    public static ArrayList<Double> tfIdfVector(ArrayList<Double> tf, ArrayList<Double> idf) {
        if (tf.size() != idf.size()) {
            return null;
        }
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < tf.size(); i++) {
            result.add(Math.log(tf.get(i) + 1) / Math.log(2) * idf.get(i));
        }
        return result;
    }

    public static Double calculateCosineSimilarity(ArrayList<Double> docVector, ArrayList<Double> searchWordsVector) {
        double numerator = sumTwoVectors(searchWordsVector, docVector);
        double denominator = Math.sqrt(vectorSquare(docVector) * vectorSquare(searchWordsVector));
        return numerator/denominator;
    }

    private static double sumTwoVectors(List<Double> vector1, List<Double> vector2) {
        double sum = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            sum += vector1.get(i) * vector2.get(i);
        }
        return sum;
    }


}