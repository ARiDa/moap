/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.evaluation;

import arida.ufc.br.moap.rec.function.similarity.CosineSimilarity;
import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.utils.RecommendationUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author igobrilhante
 * 
 * References:
 * 
 * Ziegler, C.-N., McNee, S. M., Konstan, J. a., & Lausen, G. (2005). Improving recommendation lists through topic diversification. 
 * Proceedings of the 14th international conference on World Wide Web - WWW  ’05, 22. doi:10.1145/1060745.1060754
 * 
 */
public class IntraListSimilarity {
    
    private RecommendationSet dataset;
    private RecommendationSet recs;
    private SimilarityFunction similarityFunction;
    private Logger logger = Logger.getLogger(IntraListSimilarity.class);
    
    /**
     * 
     * @param dataset
     * @param recs
     */
    public IntraListSimilarity(RecommendationSet dataset, RecommendationSet recs){
        logger.info("Intra-List Similarity");
        this.dataset = dataset;
        this.recs = recs;
    }
    
    /**
     * 
     * @return
     */
    public double getIntraListSimilarity(){
        double eval = 0.0;
        int userCount = this.recs.getUserCount();
        generateSimilarity();
        
        // FOR EACH USER
        for(Object user : recs.getUsers()){
            // FOR EACH ITEM
            double userEval = 0.0;
            
            List userItems = new ArrayList();
            userItems.addAll(this.recs.getItems(user));
            int itemCount = userItems.size();
            
            for(int i =0; i < itemCount; i++){
                for(int j=i+1; j < itemCount;j++){
                    Object itemI = userItems.get(i);
                    Object itemJ = userItems.get(j);
                    double sim = this.similarityFunction.getSimilarity(itemI, itemJ);
                    
                    userEval = userEval + sim;
//                    System.out.println("Item Similarity "+itemI+" - " +itemJ+": "+sim);
                }
            }
//            System.out.println("User "+user+": "+userEval);
            eval = eval + userEval;
        }
        eval = (double) eval / userCount;
        
        return eval;
    }
    
    
    
    // Generate Item-User Matrix:
    // Item is the user
    // User is the item
    private void generateSimilarity(){
        // FOR EACH USER
        RecommendationSet itemSimilarity = RecommendationUtils.getInverseRecommendationDataset(this.dataset);
        
//        System.out.println(itemSimilarity.getDataset());
        if(this.similarityFunction==null){
            this.similarityFunction = new CosineSimilarity(itemSimilarity);
        }
        this.similarityFunction.execute();
        
    }
}
