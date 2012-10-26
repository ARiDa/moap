/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.evaluation;

import arida.ufc.br.moap.core.beans.Annotation;
import arida.ufc.br.moap.core.beans.Poi;
import arida.ufc.br.moap.rec.function.similarity.CosineSimilarity;
import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.utils.RecommendationUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author igobrilhante Evaluate a recommender systems in terms of serendipity.
 * Distance between the user's history and her recommending list
 *
 * Reference:
 *
 * Vargas, S., & Castells, P. (2011). Rank and relevance in novelty and
 * diversity metrics for recommender systems. Proceedings of the fifth ACM
 * conference on Recommender systems - RecSys ’11, 109.
 * doi:10.1145/2043932.2043955
 *
 * Zhang, Y., & Séaghdha, D. (2012). Auralist: introducing serendipity into
 * music recommendation. Proceedings of the fifth …. Retrieved from
 * http://dl.acm.org/citation.cfm?id=2124300
 */
public class Unserendipity {

    private RecommendationSet dataset;
    private RecommendationSet recs;
    private SimilarityFunction similarityFunction;

    /**
     *
     * @param dataset
     * @param recs
     * @param poiList
     */
    public Unserendipity(RecommendationSet dataset, RecommendationSet recs) {
        this.dataset = dataset;
        this.recs = recs;

    }

    private void initialize() {
        generateSimilarity();
//        this.poiSimilarity = new RecommendationSet();
//        this.poiSimilarity = generateSimilarity();
//        for (Poi poi : poiList) {
//            this.poiSimilarity.addUser(poi.getId());
//            Annotation annot = poi.getAnnotations().getAnnotation("categories");
//
//            Set categories = (Set) annot.getValue();
//            for (Object category : categories) {
//                this.poiSimilarity.addUserItem(poi.getId(), category, 1.0);
//            }
//        }
    }

    /**
     *
     * @return
     */
    public double getUnserendipity() {
        double result = 0.0;

        initialize();
//        System.out.println(this.poiSimilarity.getDataset());
//        SimilarityFunction sf = new CosineSimilarity(this.poiSimilarity);
//        sf.execute();

        //For each user
        Set users = this.dataset.getUsers();
        for (Object user : users) {
            double userEval = 0.0;
            if (this.dataset.containsUser(user) && this.recs.containsUser(user)) {
                Object[] userItems = this.dataset.getItems(user).toArray();
                Object[] userRecs = this.recs.getItems(user).toArray();
                // User's History
                for (int i = 0; i < userItems.length; i++) {
                    double aux = 0.0;

                    // User's Recommending List
                    for (int j = 0; j < userRecs.length; j++) {

                        // Similarity between the items of the two lists
                        double sim = similarityFunction.getSimilarity(userItems[i], userRecs[j]);
//                    System.out.println(String.format("Sim %s - %s: %f", userItems[i], userRecs[j],sim));
                        aux = aux + sim;
                    }
                    // Averaging for each recommendation
                    if (userRecs.length > 0) {
                        aux = (double) aux / userRecs.length;
                    }

                    userEval = userEval + aux;

//                System.out.println(String.format("1. %s and %s: %f", user,userItems[i],aux));
                }

                // Averagin for each item
                if (userItems.length > 0) {
                    userEval = (double) userEval / userItems.length;
                }
//            System.out.println(String.format("2. %s: %f", user,userEval));
                result = result + userEval;
            }
        }

        // Averagin the result
        if (users.size() > 0) {
            result = (double) result / users.size();
        }

        return result;
    }

    private void generateSimilarity() {
        // FOR EACH USER
        // Invert User-Item Matrix to Item-User Matrix
        RecommendationSet itemSimilarity = RecommendationUtils.getInverseRecommendationDataset(this.dataset);

//        System.out.println(itemSimilarity.getDataset());
        if (this.similarityFunction == null) {
            this.similarityFunction = new CosineSimilarity(itemSimilarity);
        }
        this.similarityFunction.execute();

    }

    public SimilarityFunction getSimilarityFunction() {
        return similarityFunction;
    }

    public void setSimilarityFunction(SimilarityFunction similarityFunction) {
        this.similarityFunction = similarityFunction;
    }
}
