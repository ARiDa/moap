/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.beans;

import arida.ufc.br.moap.core.beans.Interval;
import arida.ufc.br.moap.core.beans.MovingObject;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.project.api.ProjectController;
import org.openide.util.Lookup;

/**
 *
 * @author igobrilhante
 */
public class UserNetwork implements NetworkBuilder {
	private final Logger logger = Logger.getLogger(UserNetwork.class); 
	private List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects;
        /**
         * 
         */
        public static final String VISITED_POIS = "visited_pois";
        /**
         * 
         */
        public static final String COUNT_VISITED_POIS = "count_visited_pois";
        /**
         * 
         */
        public static final String AVG_STOP_TIME = "avg_stop_time";
        /**
         * 
         */
        public static final String POI_MATCH = "POI_MATCH";
	private Map<String,Integer> userIndices;
	private Map<String,Integer> poiIndices;
	private UndirectedGraph userNetwork;
	private AttributeModel attributeModel;
	private double[][] avg_stop_time_poi;

        /**
         * 
         * @param movingObjects
         */
        public UserNetwork(List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects){
		this.movingObjects = movingObjects;
	}

        /**
         * 
         * @return
         */
        public UndirectedGraph getUserNetwork(){
		return this.userNetwork;
	}

        /**
         * 
         * @return
         */
        public AttributeModel getAttributeModel(){
		return this.attributeModel;
	}

        /**
         * 
         */
        @Override
	public void buildNetwork(){
		logger.info("User Network");
		// Creating new Project for managing the network: GEPHI API
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		pc.getCurrentWorkspace();
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		this.userNetwork = graphModel.getUndirectedGraph();
		this.attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
		AttributeTable nodeTable = this.attributeModel.getNodeTable();
		nodeTable.addColumn(VISITED_POIS, VISITED_POIS, AttributeType.STRING, AttributeOrigin.DATA, "");
		nodeTable.addColumn(COUNT_VISITED_POIS, COUNT_VISITED_POIS, AttributeType.INT, AttributeOrigin.DATA, 0);
		
		// Multidimension network
		AttributeTable edgeTable = this.attributeModel.getEdgeTable();
		// Dimension 1:
		edgeTable.addColumn(POI_MATCH, POI_MATCH, AttributeType.DOUBLE, AttributeOrigin.DATA, 0.0);
		// Dimension 2:
		edgeTable.addColumn(AVG_STOP_TIME, AVG_STOP_TIME, AttributeType.DOUBLE, AttributeOrigin.DATA, 0.0);
		
		Map<String,Map<String,Double>> ratings = new HashMap<String,Map<String,Double>>();
		Map<String,Integer> visits = new HashMap<String,Integer>();
		Map<String,String> pois = new HashMap<String,String>();
		this.poiIndices = new HashMap<String, Integer>();
		this.userIndices = new HashMap<String, Integer>();
		int poiIndex = 0;
		// Array of moving objects
		int size = movingObjects.size();
		for(int i = 0 ; i< size; i ++){
			MovingObject<List<String>, Interval<Timestamp>> m_i = movingObjects.get(i);
			this.userIndices.put(m_i.getId(), i);
			Map<String,Double> rating_i = new HashMap<String, Double>();
			List<List<String>> list = m_i.getTrajectory(0).getPoints();
			String list_pois = "";
			int count = 0;
			for(List<String> sl : list){
				for(String s : sl){
					if(rating_i.containsKey(s)){
						Double d = rating_i.get(s);
						rating_i.put(s, d+1);
					}
					else{
						// Inserting new data to the node
						rating_i.put(s, 1.0);
					}
					if(list_pois.equals("")){
						list_pois = s;
					}
					else{
						list_pois = list_pois +","+s;
					}
					if(!this.poiIndices.containsKey(s)){
						this.poiIndices.put(s, poiIndex);
						poiIndex++;
					}
					
				}
				count ++;
				
			}
			pois.put(m_i.getId(), list_pois);
			visits.put(m_i.getId(), count);
			ratings.put(m_i.getId(), rating_i);
		}

		
		this.avg_stop_time_poi = new double[this.userIndices.keySet().size()][this.poiIndices.keySet().size()];
		double[][] count_poi_visits = new double[this.userIndices.keySet().size()][this.poiIndices.keySet().size()];
		
		// Creating indices for users and PoIs
		for(int i = 0; i< this.userIndices.keySet().size(); i++){
			for(int j=0;j<this.poiIndices.keySet().size();j++){
				this.avg_stop_time_poi[i][j] = 0.0;
				count_poi_visits[i][j] = 0.0;
			}
		}
		
		// Get duration for each PoI of each user's stop
		for(int i = 0 ; i< size; i ++){
			MovingObject<List<String>, Interval<Timestamp>> m_i = movingObjects.get(i);
			int user_index = userIndices.get(m_i.getId());
			int aux = m_i.getTrajectory(0).getPoints().size();
			for(int j = 0; j < aux;j++){
				List<String> sl = m_i.getTrajectory(0).getPoints().get(j);
				Interval<Timestamp> interval = m_i.getTrajectory(0).getTimes().get(j);
				double duration = interval.getEnd().getTime()-interval.getBegin().getTime();
				for(String s : sl){
					int poi_index = poiIndices.get(s);
					
					this.avg_stop_time_poi[user_index][poi_index] = this.avg_stop_time_poi[user_index][poi_index] + duration;
					count_poi_visits[user_index][poi_index] = count_poi_visits[user_index][poi_index] + 1;
					
				}
				
			}
		}
		// Computing Average Stop Time of each User for each PoI
		for(int i = 0; i< this.userIndices.keySet().size(); i++){
			for(int j=0;j<this.poiIndices.keySet().size();j++){
				if(count_poi_visits[i][j] > 0){
					this.avg_stop_time_poi[i][j] = this.avg_stop_time_poi[i][j] / count_poi_visits[i][j];
				}
			}
		}

		for(int i = 0 ; i< size; i ++){ // moving object i
			logger.info(i+"/"+size);
			MovingObject<List<String>, Interval<Timestamp>> m_i = movingObjects.get(i);
			Map<String,Double> rating_i = ratings.get(m_i.getId());
			int user1_index = this.userIndices.get(m_i.getId());
//			List<List<String>> list = m_i.getTrajectory(0).getPoints();

			for(int j = i+1; j < size ; j++){ // moving object j
				MovingObject<List<String>, Interval<Timestamp>> m_j = movingObjects.get(j);
				Map<String,Double> rating_j = ratings.get(m_j.getId());
				int user2_index = this.userIndices.get(m_j.getId());
//				List<List<String>> list2 = m_j.getTrajectory(0).getPoints();


				// Similarity between the users: cosine similarity
//				float sim = (float)cosineSimilarity(rating_i, rating_j);
				double sim1 = TanimotoSimilarity(rating_i, rating_j); // Compare two users by their visited PoIs without considering temporal information
				//        		System.out.println(m_i.getId()+" "+m_j.getId()+"Sim :"+sim);
				double[] user1_array = new double[this.poiIndices.keySet().size()];
				double[] user2_array = new double[this.poiIndices.keySet().size()];
				
				for(int k = 0; k<this.poiIndices.keySet().size();k++){
					user1_array[k] = this.avg_stop_time_poi[user1_index][k];
					user2_array[k] = this.avg_stop_time_poi[user2_index][k];
				}
				
				double sim2 = TanimotoSimilarity(user1_array, user2_array);
				
				// New edge
				if(sim1 > 0.0){
					
					Node n_i = this.userNetwork.getNode(m_i.getId());
					if(n_i == null){
						n_i = graphModel.factory().newNode(m_i.getId());
						n_i.getNodeData().setLabel(m_i.getId());
						n_i.getNodeData().getAttributes().setValue(COUNT_VISITED_POIS, visits.get(m_i.getId()));
						n_i.getNodeData().getAttributes().setValue(VISITED_POIS, pois.get(m_i.getId()));
						this.userNetwork.addNode(n_i);

					}
					
					Node n_j = this.userNetwork.getNode(m_j.getId());
					if(n_j == null){
						n_j = graphModel.factory().newNode(m_j.getId());
						n_j.getNodeData().setLabel(m_j.getId());
						n_j.getNodeData().getAttributes().setValue(COUNT_VISITED_POIS, visits.get(m_j.getId()));
						n_j.getNodeData().getAttributes().setValue(VISITED_POIS, pois.get(m_j.getId()));
						this.userNetwork.addNode(n_j);

					}
					
					Edge edge = graphModel.factory().newEdge(n_i, n_j);
					edge.setWeight((float)sim1);
					edge.getEdgeData().getAttributes().setValue(POI_MATCH, sim1);
					edge.getEdgeData().getAttributes().setValue(AVG_STOP_TIME, sim2);
					this.userNetwork.addEdge(edge);
				}


			}
		}
		logger.info("End User Network");

		//        try {
		//            
		//            String query_nodes = " SELECT DISTINCT userid as id FROM ("+table_input+") as t";
		//            String query_edges = 
		//                    " select t.user1 as source,t.user2 as target,count(*) as weight "
		//                    + " from "
		//                    + " ("
		//                    + " select distinct user1.userid user1, user2.userid user2, user1.locationid  as locationid "
		//                    + " from ("+table_input+") user1, ("+table_input+") user2 "
		//                    + " where user1.locationid = user2.locationid "
		//                    + " and user1.userid < user2.userid "
		//                    + " ) t "
		//                    + " group by t.user1, t.user2";
		//            
		//            
		//            nc.storeQueryAsNetwork(network, query_nodes,"id", query_edges, "source,target");
		//            logger.info("Network "+network.getName()+" has been built");
		//        }
		//        catch (Exception ex) {
		//            Exceptions.printStackTrace(ex);
		//        }
	}

//	private double cosineSimilarity(Map<String,Double> a, Map<String,Double> b){
//		double sim = 0.0;
//
//		double dot_product = 0.0;
//		double a_value = 0.0;
//		double b_value = 0.0;
//		Set<String> intersection = new HashSet<String>(a.keySet());
//		intersection.retainAll(b.keySet());
//
//		for(String s : intersection){
//			double a_i = a.get(s);
//			double b_i = b.get(s);
//			dot_product = dot_product + (a_i*b_i);
//		}
//
//		// a value
//		for(Double a_i : a.values()){
//			a_value = a_value + (a_i*a_i);
//		}
//		a_value = Math.sqrt(a_value);
//
//		// b value
//		for(Double b_i : b.values()){
//			b_value = b_value + (b_i*b_i);
//		}
//		b_value = Math.sqrt(b_value);
//
//		//    	System.out.println("dot "+dot_product);
//		//    	System.out.println("a "+a_value);
//		//    	System.out.println("b "+b_value);
//		sim = dot_product/(a_value*b_value);
//
//		return sim;
//	}
	
	private double TanimotoSimilarity(Map<String,Double> a, Map<String,Double> b){
		double sim = 0.0;

		double dot_product = 0.0;
		double a_value = 0.0;
		double b_value = 0.0;
		Set<String> intersection = new HashSet<String>(a.keySet());
		intersection.retainAll(b.keySet());

		for(String s : intersection){
			double a_i = a.get(s);
			double b_i = b.get(s);
			dot_product = dot_product + (a_i*b_i);
		}

		// a value
		for(Double a_i : a.values()){
			a_value = a_value + (a_i*a_i);
		}
//		a_value = Math.sqrt(a_value);

		// b value
		for(Double b_i : b.values()){
			b_value = b_value + (b_i*b_i);
		}
//		b_value = Math.sqrt(b_value);

		//    	System.out.println("dot "+dot_product);
		//    	System.out.println("a "+a_value);
		//    	System.out.println("b "+b_value);
		sim = dot_product/(a_value+b_value - dot_product);

		return sim;
	}
	
	private double TanimotoSimilarity(double[] a, double[] b){
		double sim = 0.0;

		double dot_product = 0.0;
		double a_value = 0.0;
		double b_value = 0.0;
		
		int size = a.length;
		
		for(int i=0;i<size;i++){
			dot_product = dot_product + (a[i]*b[i]);
			a_value = a_value + (a[i]*a[i]);
			b_value = b_value + (b[i]*b[i]);
		}
		
		sim = dot_product/(a_value + b_value - dot_product);
		
		return sim;
	}


}
