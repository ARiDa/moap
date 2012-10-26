/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.approaches;

import arida.ufc.br.moap.rec.algorithms.ItemFrequency;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.IRecommenderSystem;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author igobrilhante
 */
public class PopularityBasedRec<U,I> implements IRecommenderSystem<U,I> {

    private RecommendationSet<U,I> result;
    private RecommendationSet<U,I> dataset;
    private Logger logger = Logger.getLogger(PopularityBasedRec.class);
    
    public PopularityBasedRec(RecommendationSet<U,I> dataset){
        this.dataset = dataset;
    }
    
    @Override
    public RecommendationSet<U, I> getRecommendation() {
        return this.result;
    }

    @Override
    public void execute() {
        logger.info("Popularity Based Recommendation Execute");
        Map<I,Integer> item_frequency = ItemFrequency.getItemFrequency(dataset);
        this.result = new RecommendationSet<U, I>();
        Set<U> users = this.dataset.getUsers();
        for(U user : users){
            this.result.addUser(user);
            Set<I> user_items = this.dataset.getItems(user);
            Set<I> items = item_frequency.keySet();
            
            for(I item : items){
                
                if(!user_items.contains(item)){
                    this.result.addUserItem(user,item,(double)item_frequency.get(item));
                }
            }
        }
        logger.info("Popularity Based Recommendation Execute End");
    }
    
}
