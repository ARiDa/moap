/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class JaccardSimilarity extends SimilarityFunction {

//    private double[][] similarities;
    private double[][] matrix;
    private RecommendationSet dataset;
//    private Map<Object, Integer> indices;
    private Map<Object, Integer> itemIndices;

    /**
     * 
     * @param dataset
     */
    public JaccardSimilarity(RecommendationSet dataset) {
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
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {
                double sim = 0.0;
                double intersection_size = 0.0;
                double i_size = 0.0;
                double j_size = 0.0;

                for (int k = 0; k < cols; k++) {
                    if(matrix[i][k] > 0 && matrix[j][k] > 0){
                        intersection_size++;
                        i_size++;
                        j_size++;
                    }
                    else{
                        if(matrix[i][k] > 0){
                            i_size++;
                        }
                        if(matrix[j][k] > 0){
                            j_size++;
                        }
                    }
                    
                }

                if((i_size +j_size  - intersection_size) > 0){
                    sim = intersection_size / (i_size +j_size  - intersection_size);
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
    
    /**
     * 
     * @return
     */
    @Override
    public String toString(){
        return JaccardSimilarity.class.getName();
    }
}
