package arida.ufc.br.moap.rec.spi;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.IRecommenderSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public abstract class MemoryBasedCF<U, I> implements IRecommenderSystem<U, I> {
    /**
     * 
     */
    protected double NO_RATING = 0.0;
        /**
         * 
         */
        protected RecommendationSet<U,I> recommendationSet;
        /**
         * 
         */
        protected Map<U,Integer> userIndices;
        /**
         * 
         */
        protected Map<I,Integer> itemIndices;
        /**
         * 
         */
        protected boolean inverseUserFrequency;
        
        /**
         * 
         * @param recDataset
         */
        public MemoryBasedCF(RecommendationSet<U,I> recDataset){
            this.userIndices = new HashMap<U, Integer>();
            this.itemIndices = new HashMap<I, Integer>();
            this.recommendationSet = recDataset;
            initIndices();
        }
        
        /**
         * 
         * @param simFunction
         */
        public abstract void setSimilarityFunction(SimilarityFunction simFunction);
        

        /**
         * 
         * @return
         */
        public boolean isInverseUserFrequency() {
            return inverseUserFrequency;
        }

        /**
         * 
         * @param inverseUserFrequency
         */
        public void setInverseUserFrequency(boolean inverseUserFrequency) {
            this.inverseUserFrequency = inverseUserFrequency;
        }
        
        private void initIndices(){
            int count = 0;
            for(U user : this.recommendationSet.getUsers()){
                this.userIndices.put(user, count);
                count++;
            }
            
            count = 0;
            for(I item : this.recommendationSet.getItems()){
                this.itemIndices.put(item, count);
                count++;
            }
        }
        
        /**
         * 
         * @return
         */
        protected double[][] getMatrix(){
            int users = this.recommendationSet.getUserCount();
            int items = this.recommendationSet.getItemCount();
            
            double[][] matrix = new double[users][items];
            
            for(int i=0;i<users;i++){
                for(int j=0;j<items;j++){
                    matrix[i][j] = NO_RATING;
                }
            }
            
            
            for(U user : this.recommendationSet.getUsers()){
                Set<I> user_items = this.recommendationSet.getItems(user);
                int userIndex = this.userIndices.get(user);
                for(I item : user_items){
                    int itemIndex = this.itemIndices.get(item);
                    matrix[userIndex][itemIndex] = this.recommendationSet.getRating(user, item);
                }
            }
            
            return matrix;
            
        }
}
