/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.utils;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.Set;

/**
 *
 * @author igobrilhante
 */
public class RecommendationUtils {
    
    /**
     * 
     * @param rec
     * @return
     */
    public static RecommendationSet getInverseRecommendationDataset(RecommendationSet rec){
        RecommendationSet inverseRecommendationDataset = new RecommendationSet();
        
        for(Object user : rec.getUsers()){
            // FOR EACH ITEM
            Set<Object> userItems = rec.getItems(user);
            
            for(Object item : userItems){
                double value = rec.getRating(user, item);
                
                if(!inverseRecommendationDataset.containsUser(item)){
                    inverseRecommendationDataset.addUser(item);
                }
                inverseRecommendationDataset.addUserItem(item, user, value);
                
            }
            
        }
        
        return inverseRecommendationDataset;
    }
}
