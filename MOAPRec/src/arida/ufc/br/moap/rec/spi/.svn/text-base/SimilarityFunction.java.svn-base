package arida.ufc.br.moap.rec.spi;

import arida.ufc.br.moap.core.algorithm.spi.IAlgorithm;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author igobrilhante
 */
public abstract class SimilarityFunction implements IAlgorithm {
    /**
     * 
     */
    protected Map<Object, Integer> indices;
    /**
     * 
     */
    protected double[][] similarities;
        /**
         * 
         */
        protected double caseAmplification = 1.0; // Default Value
        
        /**
         * 
         */
        public SimilarityFunction(){
            this.indices = new HashMap<Object, Integer>();
        }
        
        /**
         * 
         * @return
         */
        public double[][] getSimilarities(){
            
            // Modify the similarities according to the case amplification
            if(this.caseAmplification > 1.0){
                int rows = similarities.length;
                int cols = similarities[0].length;
            
                for(int i=0;i<rows;i++){
                    for(int j=0;j<cols;j++){
                        similarities[i][j] = similarities[i][j] * 
                                Math.pow(Math.abs(similarities[i][j]),
                                this.caseAmplification-1);
                    }
                }
            }
            
            
            return this.similarities;
        }
        /**
         * 
         * @param o1
         * @param o2
         * @return
         */
        public double getSimilarity(Object o1, Object o2){
            int index1 = indices.get(o1);
            int index2 = indices.get(o2);
            
            double ca = similarities[index1][index2] * 
                    Math.pow(Math.abs(similarities[index1][index2]),
                    this.caseAmplification-1);
            
            return ca;
        }
        
        
        /**
         * 
         * @param value
         */
        public void setCaseAmplification(double value){
            this.caseAmplification = value;
        }
        
        
}	
