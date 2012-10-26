/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.approaches;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.IRecommenderSystem;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Node;

/**
 *
 * @param <U> 
 * @param <I> 
 * @author igobrilhante
 *
 * This method aims at re-raking a recommended list provided by a Collaborative
 * Filtering method. The list of each user is re-ordered with respect to the
 * PageRank of the PoIs/Locations
 */
public class CFPageRank<U, I> implements IRecommenderSystem<U, I> {

    private final Logger logger = Logger.getLogger(CFPageRank.class);
    private RecommendationSet<U,I> cfList;
    private RecommendationSet<U,I> recommendations;
    private DirectedGraph network;
    private double alpha = 0.999999999;

    /**
     * 
     * @param cfList
     * @param network
     */
    public CFPageRank(RecommendationSet<U,I> cfList, DirectedGraph network) {
        this.cfList = cfList;
        this.network = network;
    }


    /**
     * 
     * @return
     */
    @Override
    public RecommendationSet<U, I> getRecommendation() {
        return this.recommendations;
    }

    // Execute the recommendation process
    /**
     * 
     */
    @Override
    public void execute() {
        logger.info("CFPageRank Execution");
        
        // Normalized Ratings
//        normalizeRatings();
        
        Map<U,Map<I,Double>> map = new HashMap<U, Map<I, Double>>(cfList.getDataset());
        this.recommendations = new RecommendationSet<U, I>(map);
        
        for(U user : this.recommendations.getUsers()){
            Set<I> items = this.recommendations.getItems(user);
            for(I item : items){
                Node node = this.network.getNode(item.toString());
                double rating = this.recommendations.getRating(user, item);
                double pagerank = 0.0;
                if(node != null){
                    pagerank = (Double)this.network.getNode(item.toString()).getNodeData().getAttributes().getValue("pagerank");
                }
                // Update the rating according to the PageRank
//                double new_rating = (alpha*pagerank)+((1-alpha)* this.recommendations.getRating(user, location));
                double new_rating = pagerank;
                this.recommendations.setRating(user, item, new_rating);
            }
        }
        logger.info("CFPageRank Execution Complete");
        
        
    }
    
    private void normalizeRatings(){
        double max =0.0;
        
        Set<U> users = this.cfList.getUsers();
        
        for(U user : users){
            Collection<Double> values = this.cfList.getItemRatings(user).values();
            for(Double value : values){
                if(value > max){
                    max = value;
                }
            }
        }
        
        for(U user : users){
            Set<I> user_items = this.cfList.getItems(user);
            for(I item : user_items){
                double rating = this.cfList.getRating(user, item);
                this.cfList.addUserItem(user, item, rating/max);
            }
        }
    }
}
