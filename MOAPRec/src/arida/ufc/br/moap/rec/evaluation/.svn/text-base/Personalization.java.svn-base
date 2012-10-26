/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.evaluation;

import arida.ufc.br.moap.rec.function.similarity.CommonItemsSimilarity;
import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;

/**
 *
 * @author igobrilhante
 * 
 * References:
 * 
 * Zhou, T., Kuscsik, Z., Liu, J.-G., Medo, M., Wakeling, J. R., & Zhang, Y.-C. (2010). Solving the apparent diversity-accuracy dilemma of recommender systems. 
 * Proceedings of the National Academy of Sciences of the United States of America, 107(10), 4511–5. doi:10.1073/pnas.1000488107
 */
public class Personalization {
    
    private RecommendationSet recs;
    private double[][] userSimilarity;
    private int k;
    
    /**
     * 
     * @param recs
     * @param k
     */
    public Personalization(RecommendationSet recs,int k){
        this.recs = recs;
        this.k = k;
    }
    
    /**
     * 
     * @return
     */
    public double getPersonalization(){
        double result = 0.0;
        int userCount = this.recs.getUserCount();
        
        // Number of items in common
        SimilarityFunction sf = new CommonItemsSimilarity(recs);
        sf.execute();
        
        this.userSimilarity = sf.getSimilarities();
        
        int count = 0;
        
        for(int i=0;i<userCount;i++){
            for(int j=i+1;j<userCount;j++){
//                System.out.println("Hij "+i+","+j+": "+(1 - this.userSimilarity[i][j]/k));
                result = result + (1 - this.userSimilarity[i][j]/k);
                count++;
            }
        }
        
        if(count > 0){
            result = (double) result / count;
        }
        
        return result;
    }
    
}
