package arida.ufc.br.moap.rec.approaches;

import arida.ufc.br.moap.network.beans.PoiNetwork;
import arida.ufc.br.moap.network.beans.UserNetwork;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


import org.apache.log4j.Logger;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeIterable;
import org.gephi.graph.api.UndirectedGraph;
import org.openide.util.Lookup;


import arida.ufc.br.moap.rec.slopeone.Deviation;

/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class PoiRank<U, I> {
	private Map<I, Map<I, Double>> dev;
	private Map<U, Map<I, Double>> dataset;
	private Map<U, Map<I, Double>> predictions;
	private int[][] setSij;
	private DirectedGraph poiNetwork;
	private double[][] candidate_similarity;
	private Map<Node,Integer> userIndex;
	private Map<I,Integer> itemIndex;
	private UndirectedGraph userNetwork;
	private Map<Node, Map<Node, Integer>> node_distances;
	private AttributeModel attributeModel;
	private Logger logger = Logger.getLogger(PoiRank.class);
	private double dampFactor = 0.1;
	private final int INFINITY = Integer.MIN_VALUE;

        /**
         * 
         * @return
         */
        public double getDampFactor() {
		return dampFactor;
	}

        /**
         * 
         * @param dampFactor
         */
        public void setDampFactor(double dampFactor) {
		this.dampFactor = dampFactor;
	}

        /**
         * 
         * @param network
         * @param userNetwork
         * @param dataset
         */
        public PoiRank(PoiNetwork network, UserNetwork userNetwork,
			RecommendationSet<U, I> dataset) {
		this.poiNetwork = network.getPoiNetwork();
		this.userNetwork = userNetwork.getUserNetwork();
		this.dataset = dataset.getDataset();
		this.attributeModel = network.getAttributeModel();
	}

        /**
         * 
         * @return
         */
        public Map<U, Map<I, Double>> getPredictions() {
		return predictions;
	}

        /**
         * 
         * @param predictions
         */
        public void setPredictions(Map<U, Map<I, Double>> predictions) {
		this.predictions = predictions;
	}

	/**
	 * Recommend to user the item i
	 * 
	 * @param user
	 * @param item_i
	 * @return
	 */
	private double poiRank(U user, I item_i) {
		Map<I, Double> items = this.dataset.get(user);

		double weights = 0.0;
		// Map<I,Double> predictions = new HashMap<I, Double>();
		double prediction = 0.0;
		Map<I, Double> dev_i = dev.get(item_i); // get deviations of item i

		for (I item_j : items.keySet()) {
			if (!item_i.equals(item_j)) {
				// Replace cad for a network linkage measure
				// Node node_j = this.poiNetwork.getNode(item_j.toString());
				// Node node_i = this.poiNetwork.getNode(item_i.toString());
				int d = 0;
				// if(this.node_distances.containsKey(node_j)){
				// if(this.node_distances.get(node_j).containsKey(node_i)){
				// d = this.node_distances.get(node_j).get(node_i);
				// }
				// }
				// double pr_j = 0.0,pr_i=0.0;
				// int outdegree_j = 0,indegree_i=0;
				// double inweight = 0.0,outweight = 0.0;
				// AttributeModel attributeModel =
				// Lookup.getDefault().lookup(AttributeController.class).getModel();
				// AttributeTable nodeTable = attributeModel.getNodeTable();
				// AttributeColumn pageranks = nodeTable.getColumn("pageranks");
				//
				// if(node_j != null){
				// if(pageranks.getType().equals(AttributeType.STRING))
				// pr_j =
				// Double.parseDouble((String)node_j.getNodeData().getAttributes().getValue("pageranks"));
				// // page rank of j
				// else
				// pr_j =
				// (Double)node_j.getNodeData().getAttributes().getValue("pageranks");
				// // outweight =
				// Double.parseDouble((String)node_j.getNodeData().getAttributes().getValue("outweight_as_users"));
				// // outdegree_j =
				// (Integer)node_j.getNodeData().getAttributes().getValue("outdegree");
				// }
				// if(node_i != null){
				// if(pageranks.getType().equals(AttributeType.STRING))
				// pr_i =
				// Double.parseDouble((String)node_i.getNodeData().getAttributes().getValue("pageranks"));
				// // page rank of i
				// else
				// pr_i =
				// (Double)node_i.getNodeData().getAttributes().getValue("pageranks");
				// // inweight =
				// Double.parseDouble((String)node_i.getNodeData().getAttributes().getValue("inweight_as_users"));
				// // indegree_i =
				// (Integer)node_i.getNodeData().getAttributes().getValue("indegree");
				// }

				int cad = setSij(item_i, item_j); // cardinality of set Sij:
													// number of users that
													// visited both i and j
													// without considering order
				double factor = cad;
				// if(d != 0){
				// // factor = factor * (pr_j*dampFactor +
				// ((1-dampFactor)*(pr_i))); // cad plus page rank of i divided
				// by the distance from j to i on the graph
				// factor = factor + (pr_j * pr_i/Math.pow(d,2));
				//
				// }
				// System.out.println("User "+user+" Factor "+item_j+"-"+item_i+" "+factor+" d "+d);
				prediction += (dev_i.get(item_j) + items.get(item_j)) * factor;
				weights += factor;
			}
		}
		if (prediction != 0.0 && weights != 0.0) {
			prediction = (double) prediction / weights;
		}

		return prediction;
	}

	/**
	 * @return Map<U, Map<I,Double>> Set of users and their prediction for items
	 */
	public Map<U, Map<I, Double>> getRecommendation() {
		return predictions;
	}

	private int setSij(I item_i, I item_j) {
		int cad = 0;
		int iIndex = itemIndex.get(item_i);
		int jIndex = itemIndex.get(item_j);
		if(setSij[iIndex][jIndex]==-1){
			for (U user : dataset.keySet()) {
				Set<I> items = dataset.get(user).keySet();
				if (items.contains(item_i) && items.contains(item_j)) {
					cad++;
				}
			}
			setSij[iIndex][jIndex] = cad;
			setSij[jIndex][iIndex] = cad;
		}
		else{
			cad = setSij[iIndex][jIndex];
		}
			
			
//		System.out.println("Cad "+item_i+" "+item_j+" = "+cad);
		return cad;
	}

        /**
         * 
         */
        public void execute() {
		logger.info("Computing prediction");
		Set<I> item_set = new HashSet<I>();
		

		int nodeCount = userNetwork.getNodeCount();
		candidate_similarity = new double[nodeCount][nodeCount];
		userIndex = new HashMap<Node, Integer>();
		int index = 0;
		for(Node node : this.userNetwork.getNodes()){
			userIndex.put(node, index);
			index++;
		}
		for(int i=0;i<nodeCount;i++){
			for(int j=0;j<nodeCount;j++){
				candidate_similarity[i][j] = INFINITY;
			}
		}
		
		logger.info("Selecting item set");
		for (U u : this.dataset.keySet()) {
			Map<I, Double> user_items = dataset.get(u);
			for (I item : user_items.keySet()) {
				item_set.add(item);
			}
		}
		
		int c =0;
		itemIndex = new HashMap<I, Integer>();
		for(I item : item_set){
			itemIndex.put(item, c);
			c++;
		}
		
		int itemCount = item_set.size();
		this.setSij = new int[itemCount][itemCount];
		for(int i=0;i<itemCount;i++){
			for(int j=0;j<itemCount;j++){
				this.setSij[i][j] = -1;
			}
		}

		logger.info("Node Distances");
//		BreadthFirstSearch graphSearch = new BreadthFirstSearch(this.poiNetwork);
//		graphSearch.execute();
//		this.node_distances = graphSearch.getDistances();
		// System.out.println(node_distances);

		if(this.predictions==null){
			predictions = new HashMap<U, Map<I, Double>>();
			logger.info("Computing deviations");
			Deviation<U, I> deviations = new Deviation<U, I>(null);
			this.dev = deviations.computeDeviation();

			logger.info("Prediction for each user u_i: "
					+ this.dataset.keySet().size() + " users");
			for (U user : dataset.keySet()) {
				// logger.info("User: "+user);
				Set<I> user_items = dataset.get(user).keySet();
				Map<I, Double> user_prediction = new HashMap<I, Double>();
				for (I item : item_set) {
					if (!user_items.contains(item)) {
						double pred = poiRank(user, item);
						if (pred > 0.0)
							user_prediction.put(item, pred);
					}
				}
				predictions.put(user, user_prediction);
			}
		}

//		System.out.println(this.setSij);
		logger.info("Serendipitous Recommendation");
		for (U user : dataset.keySet()) { // serendipitous recommendation for
											// each user
			// get serendipitous recommendation for the user
			Map<String, Double> sr = getSerendipitousRecommendation(user); // compare
																			// this
																			// users
																			// with
																			// friends
																			// of
																			// his
																			// friends
			Map<I, Double> aux = new HashMap<I, Double>();
			Map<I, Double> aux2 = new HashMap<I, Double>();
//			System.out.println("User " + user);
			for (String candidate_user : sr.keySet()) { // candidate users
				Map<I, Double> candidate_items = dataset.get(candidate_user);
//				System.out.println("Candidate User: " + candidate_user);
				for (I candidate_item : candidate_items.keySet()) { // candidate
																	// items

					double pagerank = 0.0;
					if(this.poiNetwork.getNode(candidate_item.toString())!= null){
						pagerank = (Double) this.poiNetwork
							.getNode(candidate_item.toString()).getNodeData()
							.getAttributes().getValue("pagerank");
					}
//					pagerank = 1.0;
					double sim = sr.get(candidate_user);
					double r_candidate = candidate_items.get(candidate_item);
					double rec = r_candidate* sim*pagerank;
//					double rec = pagerank;

//					logger.info("\nCandidate item: " + candidate_item + " = "
//							+ r_candidate + "\n" + "Sim " + user + " and "
//							+ candidate_user + ": " + sim + "\n"
//							+ "Page of the item " + candidate_item + ": "
//							+ pagerank + " rec " + rec);

					// aux to get the weighted average of rec by the user
					// similarity
					if (aux.containsKey(candidate_item)) {
						double value = aux.get(candidate_item);
						value = value + rec;
						aux.put(candidate_item, value);
						double total = aux2.get(candidate_item);
//						total = total +(sim);
						total = total +1;
						aux2.put(candidate_item, total);
					} else {
						aux.put(candidate_item, rec);
						aux2.put(candidate_item, 1.0);
					}
					//
					// if(predictions.get(user).containsKey(candidate_item)){
					// double value = predictions.get(user).get(candidate_item);
					// value = value + rec;
					// predictions.get(user).put(candidate_item, value);
					// }
					// else{
					// predictions.get(user).put(candidate_item, rec);
					// }
				}

				// Add the new items
			}

			for (I candidate_item : aux.keySet()) {
				double rec = (double) aux.get(candidate_item)
						/ aux2.get(candidate_item);
//				System.out.println("Avg " + user + " " + candidate_item + " "
//						+ rec);
				if (predictions.get(user).containsKey(candidate_item)) {
					double value = predictions.get(user).get(candidate_item);
					value = value * rec;
					predictions.get(user).put(candidate_item, value);
				} else {
					predictions.get(user).put(candidate_item, rec);
				}
			}
			
//			
		}
		normalization();
	}

	private Map<String, Double> getSerendipitousRecommendation(U user) {

//		Queue<String> queue = new LinkedList<String>();
		Node node = this.userNetwork.getNode(user.toString());
		Map<String, Double> node_neighbors = new HashMap<String, Double>();

		Map<String, Double> candidates = new HashMap<String, Double>();
		Map<String, Double> candidate_neighbors;
		
		if (node != null) {
			NodeIterable nodeiter = this.userNetwork.getNeighbors(node);
			for (Node neighbor : nodeiter) {
				Edge e = this.userNetwork.getEdge(node, neighbor);
				node_neighbors.put(neighbor.getNodeData().getLabel(),
						(Double) e.getEdgeData().getAttributes().getValue(UserNetwork.POI_MATCH));
			}
			nodeiter = this.userNetwork.getNeighbors(node);
			int nodeIndex = userIndex.get(node);
			
			for (Node neighbor : nodeiter) {
				NodeIterable neighbor_iter = this.userNetwork
						.getNeighbors(neighbor);
				for (Node candidate : neighbor_iter) {
					if (!candidate.equals(node)) {
						if (!this.userNetwork.isAdjacent(node, candidate)) { // node not adjacent to the candidate
							int candidateIndex = userIndex.get(candidate);
							double sim = 0.0;
							if(candidate_similarity[nodeIndex][candidateIndex]== INFINITY){
								NodeIterable neighbor_candidate_iter = this.userNetwork
										.getNeighbors(candidate);
								
								candidate_neighbors = new HashMap<String, Double>();
								for (Node candidate_neighbor : neighbor_candidate_iter) {
									Edge e = this.userNetwork.getEdge(
											candidate, candidate_neighbor);
									candidate_neighbors.put(
											candidate_neighbor.getNodeData()
													.getLabel(),
											(Double) e.getEdgeData().getAttributes().getValue(UserNetwork.POI_MATCH));

								}
								
								
								sim = TanimotoSimilarity(node_neighbors,
										candidate_neighbors);
//								sim = cosineSimilarity(node_neighbors,
//										candidate_neighbors);
								candidate_similarity[nodeIndex][candidateIndex] = sim;
								candidate_similarity[candidateIndex][nodeIndex] = sim;
								
							}
							else{
								sim = candidate_similarity[nodeIndex][candidateIndex];
							}
							
							candidates.put(candidate.getNodeData().getLabel(),
									sim);
							
						}
					}
				}
			}
		}

		return candidates;

	}
	
	private double cosineSimilarity(Map<String, Double> a, Map<String, Double> b) {
		double sim = 0.0;

		double dot_product = 0.0;
		double a_value = 0.0;
		double b_value = 0.0;
		Set<String> intersection = new HashSet<String>(a.keySet());
		intersection.retainAll(b.keySet());

		for (String s : intersection) {
			double a_i = a.get(s);
			double b_i = b.get(s);
			dot_product = dot_product + (a_i * b_i);
		}

		// a value
		for (Double a_i : a.values()) {
			a_value = a_value + (a_i * a_i);
		}
		a_value = Math.sqrt(a_value);

		// b value
		for (Double b_i : b.values()) {
			b_value = b_value + (b_i * b_i);
		}
		b_value = Math.sqrt(b_value);

		// System.out.println("dot "+dot_product);
		// System.out.println("a "+a_value);
		// System.out.println("b "+b_value);
		sim = dot_product / (a_value * b_value);

		return sim;
	}
	
	private double TanimotoSimilarity(Map<String, Double> a, Map<String, Double> b) {
		double sim = 0.0;

		double dot_product = 0.0;
		double a_value = 0.0;
		double b_value = 0.0;
		Set<String> intersection = new HashSet<String>(a.keySet());
		intersection.retainAll(b.keySet());

		for (String s : intersection) {
			double a_i = a.get(s);
			double b_i = b.get(s);
			dot_product = dot_product + (a_i * b_i);
		}

		// a value
		for (Double a_i : a.values()) {
			a_value = a_value + (a_i * a_i);
		}
//		a_value = Math.sqrt(a_value);

		// b value
		for (Double b_i : b.values()) {
			b_value = b_value + (b_i * b_i);
		}
//		b_value = Math.sqrt(b_value);

		// System.out.println("dot "+dot_product);
		// System.out.println("a "+a_value);
		// System.out.println("b "+b_value);
		sim = dot_product / (a_value + b_value - dot_product);

		return sim;
	}
	
	private void normalization(){
		double max = 0.0;
		
		for(U user : this.predictions.keySet()){
			Map<I,Double> pois = this.predictions.get(user);
			for(Double ratings : pois.values()){
				if(max < ratings)
					max = ratings;
			}
		}
		
		for(U user : this.predictions.keySet()){
			Map<I,Double> pois = this.predictions.get(user);
			for(I poi : pois.keySet()){
				double value = pois.get(poi);
				
				pois.put(poi, value/max);
			}
		}
		
	}
	
}
