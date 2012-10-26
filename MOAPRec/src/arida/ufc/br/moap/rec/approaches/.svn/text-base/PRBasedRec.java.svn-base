/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.approaches;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.IRecommenderSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Node;

/**
 *
 * @param <U> 
 * @param <I> 
 * @author igobrilhante
 * 
 * This algorithm sets PageRank as the recommendation, 
 * for which the users receive the same recommended list ordered by the PageRank
 */
public class PRBasedRec<U,I> implements IRecommenderSystem<U, I> {

    private DirectedGraph network;
    private RecommendationSet<U,I> recs;
    private RecommendationSet<U,I> recSet;
    
    
    /**
     * 
     * @param recSet
     * @param network
     */
    public PRBasedRec(RecommendationSet<U,I> recSet,DirectedGraph network){
        this.recSet = recSet;
        this.network = network;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public RecommendationSet<U, I> getRecommendation() {
        return this.recs;
    }

    /**
     * Execute the algorithm with the required inputs
     */
    @Override
    public void execute() {
        this.recs = new RecommendationSet<U, I>();
        
        Map<I,Double> pageranks = pageRankAsRecommendation();
        Set<I> items = this.recSet.getItems();
        Set<U> users = this.recSet.getUsers();
        for(U user : users){
            this.recs.addUser(user);
            for(I item : items){
                // If the user has not selected the item yet
                if(!this.recSet.contains(user, item)){
                    double item_pagerank = pageranks.get(item); // get the item pagerank
                    this.recs.addUserItem(user, item, item_pagerank); // recommend the item
                }
            }
            
        }
    }
    
    // Set PageRank as recommendation
    private Map<I,Double> pageRankAsRecommendation(){
        Map<I,Double> pageranks = new HashMap<I, Double>();
        Set<I> items = this.recSet.getItems();
        
        for(I item : items){
            double pageRank = 0.0;
            Node node = this.network.getNode(item.toString());
            if(node != null){
                pageRank = (Double)node.getNodeData().getAttributes().getValue("pagerank");
            }
            pageranks.put(item, pageRank);
        }
        
        return pageranks;
    }
    
}
