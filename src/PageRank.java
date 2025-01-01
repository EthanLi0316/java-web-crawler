import java.util.ArrayList;
import java.util.HashSet;

public class PageRank {

    private DataStorage dataStorage;
    private ArrayList<String> urls;
    private double[][] probabilityMatrix;
    private double[][] firstPageRankVector;

    public PageRank(DataStorage dataStorage){
        this.dataStorage = dataStorage;
        this.urls = new ArrayList<>(dataStorage.getUrlToPageMap().keySet());
        this.probabilityMatrix = generateProbabilityMatrix();
        firstPageRankVector = initializePageRankVector(probabilityMatrix);
    }

    public void storePageRankValues() {
        // Calculate the final rank vector
        double[][] finalRankVector = calculatePageRank(probabilityMatrix, firstPageRankVector);

        // Assign each PageRank value to its corresponding URL
        for (int i = 0; i < urls.size(); i++) {
            dataStorage.getUrlToPageMap().get(urls.get(i)).setPageRank(finalRankVector[0][i]);
        }
    }



    private double[][] generateProbabilityMatrix() {
        double alpha = 0.1;
        int N = dataStorage.getUrlToPageMap().size();
        double[][] matrix = new double[N][N];

        for (int i = 0; i < N; i++) {
            HashSet<String> outgoingUrlsSet = new HashSet<>(dataStorage.getUrlToPageMap().get(urls.get(i)).getOutgoingUrls());

            ArrayList<String>outgoingUrls = new ArrayList<>();
            for(String url : outgoingUrlsSet){
                outgoingUrls.add(url);
            }
            // Iterate over the range of N
            for (int j = 0; j < N; j++) {
                // Check if urls.get(j) is in get_outgoing_links(urls.get(i))
                if (outgoingUrls.contains(urls.get(j))) {
                    // Append 1 to row
                    matrix[i][j] = 1;
                } else {
                    // Append 0 to row
                    matrix[i][j] = 0;
                }
            }
        }

        // Normalize rows with no 1s and rows with 1s
        for (int i = 0; i < N; i++) {
            int rowSum = 0;

            for (int j = 0; j < N; j++) {
                if (matrix[i][j] == 1) {
                    rowSum++;
                }
            }

            //check if the row has no 1s
            if (rowSum == 0) {
                for (int j = 0; j < N; j++) {
                    //set row[j] to 1/N
                    matrix[i][j] = 1.0 / N;
                }
            }

            //check if the row has any 1s
            if(rowSum > 0) {
                for (int j = 0; j < N; j++) {
                    //check if row[j] is 1
                    if (matrix[i][j] == 1) {
                        //set row[j] to 1/count_ones
                        matrix[i][j] = 1.0 / rowSum;
                    }
                }
            }
        }

        // Multiply the matrix by (1 - 𝝰) and add 𝝰/N to each entry of the matrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = matrix[i][j] * (1 - alpha) + alpha / N;
            }
        }

        // Return the matrix
        return matrix;

    }

    private double[][] initializePageRankVector(double[][] matrix) {
        // Get the number of columns in the matrix
        int N = matrix[0].length;
        // Declare matrix sized of 1*n
        double[][] pageRankVector = new double[1][N];
        double entryValue = 1.0 / N;

        ///initialize matrix with 1/N
        for (int i = 0; i < N; i++) {
            pageRankVector[0][i] = entryValue;
        }
        // Return the page rank vector
        return pageRankVector;
    }

    private double[][] calculatePageRank(double[][] matrix, double[][] rankVector) {
        final double THRESHOLD = 0.0001;
        double[][] previousRankVector = rankVector;
        double[][] result;

        while (true) {
            // Multiply the rank vector with the probability matrix
            double[][] newRankVector = Matmult.mult_matrix(previousRankVector, matrix);

            //if the new and previous rank vectors are the same, break the loop
            if (newRankVector == previousRankVector){
                result = newRankVector;
                break;
            }

            // Calculate the Euclidean distance between the new and previous rank vectors
            double distance = Matmult.euclidean_dist(previousRankVector, newRankVector);

            // If the distance is below the threshold, break the loop
            if (distance <= THRESHOLD) {
                result = newRankVector;
                break;
            }

            // Update the previous rank vector
            previousRankVector = newRankVector;
        }

        // Return the result
        return result;
    }
}