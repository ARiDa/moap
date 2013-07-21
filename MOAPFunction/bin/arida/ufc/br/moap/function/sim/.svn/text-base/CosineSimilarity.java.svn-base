package arida.ufc.br.moap.rec.function.similarity;

import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * 
 * @author igobrilhante
 */
public class CosineSimilarity extends SimilarityFunction {

//    private double[][] similarities;
    private Logger logger = Logger.getLogger(Logger.class);
    private double[][] matrix;
    private RecommendationSet dataset;
//    private Map<Object, Integer> indices;
    private Map<Object, Integer> itemIndices;

//	public CosineSimilarity(double[][] matrix){
//		this.matrix = matrix;
//	}
    /**
     * 
     * @param dataset
     */
    public CosineSimilarity(RecommendationSet dataset) {
        super();
        this.dataset = dataset;
        
    }

    private void initialize(){
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
        
        for(int i =0; i<rows;i++){
            for(int j=0;j<cols;j++){
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
        logger.info("Cosine Similarity Execute");

        initialize();
        
        int rows = this.matrix.length;
        int cols = this.matrix[0].length;
        
        this.similarities = new double[rows][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {
                double sim = 0.0;
                double dot_product = 0.0;
                double a_value = 0.0;
                double b_value = 0.0;

                for (int k = 0; k < cols; k++) {
                    dot_product = dot_product + (matrix[i][k] * matrix[j][k]);
                    a_value = a_value + (matrix[i][k] * matrix[i][k]);
                    b_value = b_value + (matrix[j][k] * matrix[j][k]);
                }
                if (a_value > 0 && b_value > 0) {
                    a_value = Math.sqrt(a_value);
                    b_value = Math.sqrt(b_value);

                    sim = dot_product / (a_value * b_value);
                }

                this.similarities[i][j] = sim;
                this.similarities[j][i] = sim;
            }
        }
        
        logger.info("Cosine Similarity Execute End");
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
//        int index1 = userIndices.get(o1);
//        int index2 = userIndices.get(o2);
//        return this.similarities[index1][index2];
//    }
    
    /**
     * 
     * @return
     */
    @Override
    public String toString(){
        return CosineSimilarity.class.getName();
    }
}
