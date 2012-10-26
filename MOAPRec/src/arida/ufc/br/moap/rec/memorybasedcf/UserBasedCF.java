/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.memorybasedcf;

import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.MemoryBasedCF;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @param <U> 
 * @param <I> 
 * @author igobrilhante
 */
public class UserBasedCF<U, I> extends MemoryBasedCF {

    private Map<U, Map<I, Double>> predictions;
    private Map<I,Set<U>> aux_map;
    private SimilarityFunction similarityFunction;
    private double[] avg_ratings;
    private int[] item_frequency;
//    private Map<U, Integer> userIndices;
//    private Map<I, Integer> itemIndices;
    private RecommendationSet<U, I> result;
    private RecommendationSet<U, I> recSet;
    private Logger logger = Logger.getLogger(UserBasedCF.class);

    /**
     * 
     * @param recDataset
     * @param similarityFunction
     */
    public UserBasedCF(RecommendationSet recDataset, SimilarityFunction similarityFunction){
        super(recDataset);
        this.recSet = recDataset;
        this.similarityFunction = similarityFunction;
        this.predictions = new HashMap<U, Map<I, Double>>();
    }
    
    /**
     * 
     * @return
     */
    @Override
    public RecommendationSet<U,I> getRecommendation() {
        return this.result;
    }
//
    /**
     * 
     * @param simFunction
     */
    @Override
    public void setSimilarityFunction(SimilarityFunction simFunction) {
        this.similarityFunction = simFunction;
    }

    /**
     * 
     */
    @Override
    public void execute() {
        logger.info("UBCF Execute");
        Set<I> item_set = this.recSet.getItems();
        logger.info("Selecting item set");
        int c = 0;


        logger.info("Initializing structures");
        int userCount = this.recSet.getUserCount();
        initialize();

        
        logger.info("Executing for each user: " + userCount);
        Set<U> users = this.recSet.getUsers();
        for (U user : users) {
//            logger.info("User: "+user);
            Set<I> user_items = this.recSet.getItems(user);
            Map<I, Double> user_prediction = new HashMap<I, Double>();
            for (I item : item_set) { // for all item in the dataset
                if (!user_items.contains(item)) { // if the user has not had this item yet
//                    logger.info("Item: "+item);
                    double pred = getPrediction(user, item);
//					System.out.println("Pred "+pred+" for "+user +" item "+item);
                    if (pred > 0.0) {
                        user_prediction.put(item, pred);
                    }
                }
            }
            predictions.put(user, user_prediction);
        }

        this.result = new RecommendationSet<U, I>(this.predictions);
//		System.out.println(correlations);
    }

    private double getPrediction(U user, I item) {
//		logger.info("getPrediction");
        double p = 0.0;
        double cor = 0.0;
        double weight = 0.0;
        double v_rating = 0.0;
        double v_avg_r;
//        double user_avg_r = getAverageRating(user,item);
        double user_avg_r =  this.avg_ratings[(Integer)this.userIndices.get(user)];
//        Set<U> users = this.recSet.getUsers();
        Set<U> users = this.aux_map.get(item);
        for (U v : users) {
            if (!v.equals(user)) {
                Map<I, Double> v_items = this.recSet.getItemRatings(v);
                if (v_items.containsKey(item)) {
                    v_rating = v_items.get(item);
//                    v_avg_r = getAverageRating(v, item);
                    v_avg_r = getRelativeAverageRating(v, item);
//                    cor = CosineCorrelation(user, v);
                    cor = this.similarityFunction.getSimilarity(user, v);
//                    System.out.printf("%s: (%f - %f) * %f = %f \n",v, v_rating,v_avg_r,cor,(double) ((v_rating - v_avg_r) * cor) );
                    p = p + (double) ((v_rating - v_avg_r) * cor);
                    weight = weight + Math.abs(cor);
                    
                    
                }
            }
        }


        if (weight != 0.0) {

            p = p / weight;
            p = user_avg_r + p;

//			System.out.println(4);
        }

        return p;

    }


    private double getAverageRating(U user, I i) {
        double avg = 0.0;
//		logger.info("Average Rating");
        Map<I, Double> items = this.recSet.getItemRatings(user);
        int count = 0;
        for (I item : items.keySet()) {
            if (!item.equals(i)) {
                avg = avg + items.get(item);
                count++;
            }
        }
        if (count > 0) {
            avg = (double) avg / count;
        }

        return avg;
    }
    
    private void computeAverage(){
        this.logger.info("Computing Average Ratings");
        this.avg_ratings = new double[this.recSet.getUserCount()];
        Arrays.fill(avg_ratings, 0.0);
        
        for(U user : this.recSet.getUsers()){
            int count =0;
            int user_idx = (Integer)this.userIndices.get(user);
            
            for(I item : this.recSet.getItems(user)){
                this.avg_ratings[user_idx] = this.avg_ratings[user_idx] + this.recSet.getRating(user, item);
                count++;
            }
            this.avg_ratings[user_idx] = (double) this.avg_ratings[user_idx] / count;
        }
        
    }
    
    private double getRelativeAverageRating(U user,I item){
        double result =0.0;
        
        double avg = this.avg_ratings[(Integer)this.userIndices.get(user)];
        double total = this.recSet.getItems(user).size();
        double rating = this.recSet.getRating(user, item);
        if(total-1 > 0)
            result = (avg - rating/total)*total/(total-1);
        
        return result;
    }
    
    private void initialize(){
        this.aux_map = new HashMap<I, Set<U>>();
        for(U user : this.recSet.getUsers()){
            Set<I> user_items = this.recSet.getItems(user);
            for(I item : user_items){
                if(!this.aux_map.containsKey(item)){
                    this.aux_map.put(item, new HashSet<U>());
                }
                this.aux_map.get(item).add(user);
            }
        }
        computeAverage();
    }
}
