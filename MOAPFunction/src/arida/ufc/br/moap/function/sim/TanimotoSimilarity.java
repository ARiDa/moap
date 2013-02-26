//package arida.ufc.br.moap.function.sim;
//
//import arida.ufc.br.moap.function.beans.DefaultDataset;
//import arida.ufc.br.moap.functions.spi.SimilarityFunction;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import org.apache.log4j.Logger;
//
///**
// * 
// * @author igobrilhante
// */
//public class TanimotoSimilarity extends SimilarityFunction {
//
////    private double[][] similarities;
//    private double[][] matrix;
//    private DefaultDataset dataset;
////    private Map<Object, Integer> indices;
//    private Map<Object, Integer> itemIndices;
//    private Logger logger = Logger.getLogger(TanimotoSimilarity.class);
//
//    /**
//     * 
//     * @param dataset
//     */
//    public TanimotoSimilarity(DefaultDataset dataset) {
//        super();
//        this.dataset = dataset;
//    }
//    
//    public TanimotoSimilarity(){}
//
//    private void initialize() {
//        int userCount = 0;
//        int itemCount = 0;
//        
//        this.itemIndices = new HashMap<Object, Integer>();
//        
//        for (Object u : dataset.getUsers()) {
//            indices.put(u, userCount);
//            Set<Object> items = this.dataset.getItems(u);
//            for (Object item : items) {
//                if (!itemIndices.containsKey(item)) {
//                    itemIndices.put(item, itemCount);
//                    itemCount++;
//                }
//            }
//            userCount++;
//        }
//
//
//        int rows = userCount;
//        int cols = itemCount;
//
//        matrix = new double[rows][cols];
//
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                this.matrix[i][j] = 0.0;
//            }
//        }
//
//        for (Object u : dataset.getUsers()) {
//            int userIndex = indices.get(u);
//            Set<Object> items = this.dataset.getItems(u);
//            for (Object item : items) {
//                int itemIndex = itemIndices.get(item);
//                double rating = this.dataset.getRating(u, item);
//                this.matrix[userIndex][itemIndex] = rating;
//            }
//        }
//    }
//
//    /**
//     * 
//     */
//    @Override
//    public void execute() {
//        // TODO Auto-generated method stub
//        logger.info("Tanimoto Coefficient Execute");
//        initialize();
//        int rows = matrix.length;
//        int cols = matrix[0].length;
//        similarities = new double[rows][rows];
//        for (int i = 0; i < rows; i++) {
//            for (int j = i + 1; j < rows; j++) {
//                double sim = 0.0;
//                double dot_product = 0.0;
//                double a_value = 0.0;
//                double b_value = 0.0;
//
//                for (int k = 0; k < cols; k++) {
//                    dot_product = dot_product + (matrix[i][k] * matrix[j][k]);
//                    a_value = a_value + (matrix[i][k] * matrix[i][k]);
//                    b_value = b_value + (matrix[j][k] * matrix[j][k]);
//                }
//
//                sim = dot_product / (a_value + b_value - dot_product);
//                this.similarities[i][j] = sim;
//                this.similarities[j][i] = sim;
//            }
//        }
//        logger.info("Tanimoto Coefficient Execute End");
//    }
//
////    @Override
////    public double[][] getSimilarities() {
////        // TODO Auto-generated method stub
////        return this.similarities;
////    }
////
////    @Override
////    public double getSimilarity(Object o1, Object o2) {
////        // TODO Auto-generated method stub
////        int index1 = indices.get(o1);
////        int index2 = indices.get(o2);
////        return this.similarities[index1][index2];
////    }
//    
//    /**
//     * 
//     * @return
//     */
//    @Override
//    public String toString(){
//        return TanimotoSimilarity.class.getName();
//    }
//    
//    public <T> double invoke(Map<T,Double> map1,Map<T,Double> map2){
//        double sim = 0.0;
//        
//        Set<T> union = new HashSet<T>(map1.keySet());
//        union.addAll(map2.keySet());
//        double a = 0.0;
//        double b = 0.0;
//        double c = 0.0;
//        
//        boolean c1 = false;
//        boolean c2 = false;
//        
//        for(T item : union){
//            c1 = map1.containsKey(item);
//            c2 = map2.containsKey(item);
//            if(c1 && c2){
//                a += (map1.get(item)*map2.get(item));
//            }
//            if(c1){
//                b += (map1.get(item)*map1.get(item));
//            }
//            if(c2){
//                 c += (map2.get(item)*map2.get(item));
//            }
//        }
//        
////        System.out.println(String.format("Sim %s/%s+%s-%s = %s", a,b,c,a,b+c - a));
//        if(b+c - a > 0) { sim = (a)/(b+c - a); }
//        
//        return sim;
//    }
//}
