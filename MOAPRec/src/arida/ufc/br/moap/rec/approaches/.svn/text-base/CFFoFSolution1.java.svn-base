/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.approaches;

import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import arida.ufc.br.moap.rec.function.similarity.TanimotoSimilarity;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.IRecommenderSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;

/**
 *
 * @param <U> 
 * @param <I> 
 * @author igobrilhante
 */
public class CFFoFSolution1<U, I> implements IRecommenderSystem<U, I> {

    private RecommendationSet<U, I> recCFFoF;
    private RecommendationSet<U, I> recSet;
    private RecommendationSet<U, I> recFoF;
    private RecommendationSet<U, I> recCF;
    private UndirectedGraph network;
    private SimilarityFunction sf;
    private RecommendationSet friendshipSet;
    private RecommendationSet candidateSet;
    private Map<Node, U> indices;
    private Logger logger = Logger.getLogger(CFFoFSolution1.class);

    /**
     * 
     * @param recSet
     * @param cfList
     * @param network
     */
    public CFFoFSolution1(RecommendationSet recSet, RecommendationSet<U, I> cfList, UndirectedGraph network) {
        this.recSet = recSet;
        this.recCF = cfList;
        this.network = network;
    }

    /**
     * 
     * @return
     */
    @Override
    public RecommendationSet<U, I> getRecommendation() {
        return this.recCFFoF;
    }

    /**
     * 
     */
    @Override
    public void execute() {
        logger.info("Collaborative Filtering + Friend of Friend (CF+FoF) Execute");
        initIndices();
        createFriendshipSet();
        this.sf = new TanimotoSimilarity(friendshipSet);
        this.sf.execute();
        computeCandidateSet();

        Set<U> users = this.recSet.getUsers();
        Set<I> items = this.recSet.getItems();

        for (U user : users) {
            this.recFoF.addUser(user);
            for (I item : items) {
                // if that user has not selected the item
                if (!this.recSet.contains(user, item)) {
                    double item_rec = recommendItembyFoFnotF(user, item);
                    if (item_rec > 0) {
                        this.recFoF.addUserItem(user, item, item_rec);
                    }
                }
            }
        }
        // Combine the recommended lists provided by both methods: CF and FoF
        combineCFwithFoF();
    }

    private void combineCFwithFoF() {
        logger.info("Combine CF with FoF");
        Map<U, Map<I, Double>> map = new HashMap<U, Map<I, Double>>(this.recCF.getDataset());
        this.recCFFoF.setDataset(map);

//        System.out.println("RecCF");
//        System.out.println(this.recCF.getDataset());
//        System.out.println("RecFoF");
//        System.out.println(this.recFoF);

        // Combination 1: Multiplication
//        for (U user : this.recFoF.getUsers()) {
//            for (I item : this.recFoF.getItems(user)) {
//                double fof_rec = this.recFoF.getRating(user, item);
//                // If the item is already recommended by CF
//                if (this.recCFFoF.contains(user, item)) {
//                    double new_rec = fof_rec * this.recCFFoF.getRating(user, item);
//                    this.recCFFoF.addUserItem(user, item, new_rec);
//                } else {
//                    this.recCFFoF.addUserItem(user, item, fof_rec);
//                }
//            }
//        }
        // Combination 2: Averaging
        for (U user : this.recFoF.getUsers()) {
            for (I item : this.recFoF.getItems(user)) {
                double fof_rec = this.recFoF.getRating(user, item);
                // If the item is already recommended by CF
                if (this.recCFFoF.contains(user, item)) {
                    double new_rec = (fof_rec + this.recCFFoF.getRating(user, item)) / 2;
                    this.recCFFoF.addUserItem(user, item, new_rec);
                } else {
                    this.recCFFoF.addUserItem(user, item, fof_rec);
                }
            }
        }

    }

    private void initIndices() {
        this.indices = new HashMap<Node, U>();
        Set<U> users = this.recSet.getUsers();
        for (U user : users) {
            Node user_node = this.network.getNode(user.toString());
            if (user_node != null) {
                this.indices.put(user_node, user);
            }
        }
        this.recCFFoF = new RecommendationSet<U, I>();
        this.recFoF = new RecommendationSet<U, I>();
    }

    private double recommendItembyFoFnotF(U user, I item) {

        Map<U, Double> candidates = this.candidateSet.getItemRatings(user);
        double weight = 0.0;
        double rec = 0.0;
        for (U candidate : candidates.keySet()) {
            // If the candidate has selected the item
            if (this.recSet.contains(candidate, item)) {
                double candidate_rating = this.recSet.getRating(candidate, item);
                double similarity = this.sf.getSimilarity(user, candidate);
                weight = weight + Math.abs(similarity);
                rec = rec + (candidate_rating * similarity);
            }
        }
        // Weighted Average
        if (weight > 0.0) {
            rec = rec / weight;
        }

        return rec;
    }

    private void createFriendshipSet() {
        logger.info("Create Friendship Set");
        friendshipSet = new RecommendationSet<U, U>();

        Set<U> users = this.recSet.getUsers();
        for (U user : users) {
            friendshipSet.addUser(user);
            Node user_node = this.network.getNode(user.toString());
            if (user_node != null) {

                for (Node user_neighbor : this.network.getNeighbors(user_node)) {
                    Edge edge = this.network.getEdge(user_node, user_neighbor);
                    double weight = edge.getWeight();

                    friendshipSet.addUserItem(user, this.indices.get(user_neighbor), weight);
                }
            }
        }
//        System.out.println("Friendship: "+friendshipSet.getDataset());


    }

    private void computeCandidateSet() {
        logger.info("Compute Candidate Set");
        // For each node in the network
        this.candidateSet = new RecommendationSet();
        for (Node node : this.network.getNodes()) {
            U user = this.indices.get(node);
            this.candidateSet.addUser(user);
            // For all friend
            for (Node node_neighbor : this.network.getNeighbors(node)) {
                // For all friend of mine
                for (Node node_nn : this.network.getNeighbors(node_neighbor)) {
                    // If friend of mine is not my friend
                    if (!node.equals(node_nn)) {
                        Edge edge = this.network.getEdge(node, node_nn);
                        if (edge == null) {
                            // Insert such user into the candidade set with the respective similarity

                            U user_candidate = this.indices.get(node_nn);
                            this.candidateSet.addUserItem(user, user_candidate,
                                    this.sf.getSimilarity(user, user_candidate));
                        }
                    }
                }
            }
        }
//        System.out.println("Cadidate Set: "+this.candidateSet.getDataset());
    }
}
