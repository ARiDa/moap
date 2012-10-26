package arida.ufc.br.moap.rec.function.similarity;

import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author igobrilhante
 */
public class PearsonSimilarity extends SimilarityFunction {

//    private double[][] similarities;
    private double[][] matrix;
    private double[] averages;
    private RecommendationSet dataset;
//    private Map<Object, Integer> indices;
    private Map<Object, Integer> itemIndices;

    /**
     * 
     * @param dataset
     */
    public PearsonSimilarity(RecommendationSet dataset) {
        super();
        this.dataset = dataset;
    }

    private void initialize() {
        int userCount = 0;
        int itemCount = 0;
        
        this.itemIndices = new HashMap<Object, Integer>();
        
        for (Object u : dataset.getUsers()) {
            indices.put(u, userCount);
            Set<Object> items = this.dataset.getItems(u);
            for (Object item : items) {
                if (!itemIndices.containsKey(item)) {
                    itemIndices.put(item, itemCount);
                    itemCount++;
                }
            }
            userCount++;
        }


        int rows = userCount;
        int cols = itemCount;

        matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.matrix[i][j] = 0.0;
            }
        }

        for (Object u : dataset.getUsers()) {
            int userIndex = indices.get(u);
            Set<Object> items = this.dataset.getItems(u);
            for (Object item : items) {
                int itemIndex = itemIndices.get(item);
                double rating = this.dataset.getRating(u, item);
                this.matrix[userIndex][itemIndex] = rating;
            }
        }
    }

    /**
     * 
     */
    @Override
    public void execute() {
        // TODO Auto-generated method stub

        initialize();

        int rows = matrix.length;
        int cols = matrix[0].length;
        similarities = new double[rows][rows];
        computeAverage();
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {
                double sim = 0.0;
                double dot_product = 0.0;
                double a_value = 0.0;
                double b_value = 0.0;
//				double i_avg = this.averages[i];
//				double j_avg = this.averages[j];
                double[] avg = computeAverage(i, j);
                double i_avg = avg[0];
                double j_avg = avg[1];

                for (int k = 0; k < cols; k++) {
                    if (matrix[i][k] > 0 && matrix[j][k] > 0) {
                        dot_product = dot_product + ((matrix[i][k] - i_avg) * (matrix[j][k] - j_avg));
                        a_value = a_value + (Math.pow((matrix[i][k] - i_avg), 2));
                        b_value = b_value + (Math.pow((matrix[j][k] - j_avg), 2));
                    }
                }

                if (a_value != 0 && b_value != 0) {
                    a_value = Math.sqrt(a_value);
                    b_value = Math.sqrt(b_value);
                    double denominator = a_value * b_value;
                    sim = dot_product / denominator;
                }

                this.similarities[i][j] = sim;
                this.similarities[j][i] = sim;
            }
        }
    }

//    @Override
//    public double[][] getSimilarities() {
//        // TODO Auto-generated method stub
//        return this.similarities;
//    }
//
//    @Override
//    public double getSimilarity(Object o1, Object o2) {
//        // TODO Auto-generated method stub
//        int index1 = indices.get(o1);
//        int index2 = indices.get(o2);
//        return this.similarities[index1][index2];
//    }

    private double[] computeAverage(int i, int j) {
//        System.out.printf("AVG %d and %d\n", i,j);
        double[] avg = new double[2];
        int cols = this.matrix[0].length;
        int count = 0;
        for (int k = 0; k < 2; k++) {
            avg[k] = 0.0;
        }
        for (int k = 0; k < cols; k++) {
            if (this.matrix[i][k] > 0 && this.matrix[j][k] > 0) {
//                System.out.printf("Item %d, %d: %f and %d: %f\n",k, i,this.matrix[i][k],j,this.matrix[j][k]);
                avg[0] = avg[0] + this.matrix[i][k];
                avg[1] = avg[1] + this.matrix[j][k];
                count++;
            }
        }
        if (count > 0) {
            avg[0] = (double) avg[0] / count;
            avg[1] = (double) avg[1] / count;
        }

        return avg;
    }

    private void computeAverage() {
        int rows = this.matrix.length;
        int cols = this.matrix[0].length;
        this.averages = new double[rows];
        for (int i = 0; i < rows; i++) {
            this.averages[i] = 0;
        }

        for (int i = 0; i < rows; i++) {
            int count = 0;
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] > 0) {
                    this.averages[i] = this.averages[i] + matrix[i][j];
                    count++;
                }

            }
            if (count > 0) {
                this.averages[i] = (double) this.averages[i] / count;
            }
//			System.out.println(i +" avg "+ this.averages[i]);
        }
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String toString(){
        return PearsonSimilarity.class.getName();
    }
}
