/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.evaluation;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author igobrilhante
 * 
 * 
 * References:
 * 
 * Zhou, T., Kuscsik, Z., Liu, J.-G., Medo, M., Wakeling, J. R., & Zhang, Y.-C. (2010). Solving the apparent diversity-accuracy dilemma of recommender systems. 
 * Proceedings of the National Academy of Sciences of the United States of America, 107(10), 4511–5. doi:10.1073/pnas.1000488107
 * 
 * Zhang, Y., & Séaghdha, D. (2012). Auralist: introducing serendipity into music recommendation. 
 * Proceedings of the fifth …. Retrieved from http://dl.acm.org/citation.cfm?id=2124300
 */
public class Novelty {
    
    private double[] popularity;
    private RecommendationSet dataset;
    private RecommendationSet recs;
    private Map<Object,Integer> itemIndices;
    private Logger logger = Logger.getLogger(Novelty.class);
    
    /**
     * 
     * @param dataset
     * @param recs
     */
    public Novelty(RecommendationSet dataset,RecommendationSet recs){
       
        this.dataset = dataset;
        this.recs = recs;
        
    }
    
    /**
     * 
     * @return
     */
    public double getNolvety(){
        logger.info("Novelty");
        initialize();
        
        double eval = 0.0;
        int userCount = this.recs.getUserCount();
        
        
        for(Object user : this.recs.getUsers()){
//            System.out.println("User: "+user);
            double userEval = 0.0;
            int count = 0;
            Set userItems = this.recs.getItems(user);
            for(Object item : userItems){
                int idx = this.itemIndices.get(item);
//                System.out.println("Item["+this.popularity[idx]+"]: "+item);
                userEval = userEval + (Math.log(userCount/this.popularity[idx])/Math.log(2));
//                System.out.println(item+": "+Math.log(this.popularity[idx])/Math.log(2));
                count++;
            }
            userEval = (double)userEval / count;
            
            eval = eval + userEval;
            
//            System.out.println("Novelty for "+user+": "+userEval);
        }
        
        eval = (double) eval / userCount;
        
        
        return eval;
    }
    
    // Initialize some variables and compute item popularity
    private void initialize(){
        this.itemIndices = new HashMap<Object, Integer>();
        int index = 0;
        for(Object item : this.dataset.getItems()){
            this.itemIndices.put(item, index);
            index++;
        }
        
        computePopularity();
        
    }
    
    // Compute item popularity: fraction of users that have selected an item
    private void computePopularity(){
        int itemCount = this.dataset.getItemCount();
        int userCount = this.dataset.getUserCount();
        
        popularity = new double[itemCount];
        Arrays.fill(popularity, 0);
        
        for(Object user : this.dataset.getUsers()){
            Set userItems = this.dataset.getItems(user);
            for(Object item : userItems){
                int itemIndex = this.itemIndices.get(item);
                this.popularity[itemIndex] = this.popularity[itemIndex] +1;
            }
        }
        
//        for(int i=0;i<itemCount;i++){
//            this.popularity[i] = (double)this.popularity[i] / userCount;
//        }
        
    }
    
    
}
