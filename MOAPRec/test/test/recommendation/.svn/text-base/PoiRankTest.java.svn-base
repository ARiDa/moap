//package test.recommendation;
//
//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.util.Map;
//import java.util.Set;
//import java.util.SortedMap;
//
//import location.recommender.beans.RecommendationDataset;
//import location.recommender.beans.RecommendationDatasetLoader;
//
//import org.apache.log4j.Logger;
//import org.gephi.graph.api.DirectedGraph;
//import org.gephi.graph.api.GraphController;
//import org.gephi.graph.api.GraphModel;
//import org.gephi.io.importer.api.EdgeDefault;
//import org.junit.Test;
//import org.openide.util.Exceptions;
//import org.openide.util.Lookup;
//
//import arida.ufc.br.locationrecommender.algorithms.TopK;
//import arida.ufc.br.networkmanager.impl.Network;
//import arida.ufc.br.networkmanager.impl.NetworkImporterImpl;
//import arida.ufc.br.networks.PoiNetwork;
//
//import recommender.evaluation.IREvaluation;
//import recommender.evaluation.NormalizedPrecision;
//import recommender.system.poirank.PoiRank;
//
//public class PoiRankTest {
//	private Logger logger = Logger.getLogger(PoiRankTest.class);
//	@Test
//	public void test() throws Exception {
//		//		fail("Not yet implemented");
//		/**
//		 * DATASET FROM MOBILITY
//		 */
//		String[] s = new String[5];
//		
//		s[0] = "select * from " +
//				"((((select * from crossvalidation_1) union " +
//				"(select * from crossvalidation_2)) union " +
//				"(select * from crossvalidation_3)) union " +
//				"(select * from crossvalidation_4))  a " +
//				"order by id,start_time";
//		s[1] = "select * from " +
//				"((((select * from crossvalidation_1) union " +
//				"(select * from crossvalidation_2)) union " +
//				"(select * from crossvalidation_3)) union " +
//				"(select * from crossvalidation_5))  a " +
//				"order by id,start_time";
//		s[2] = "select * from " +
//				"((((select * from crossvalidation_1) union " +
//				"(select * from crossvalidation_2)) union " +
//				"(select * from crossvalidation_4)) union " +
//				"(select * from crossvalidation_5))  a " +
//				"order by id,start_time";
//		s[3] = "select * from " +
//				"((((select * from crossvalidation_1) union " +
//				"(select * from crossvalidation_3)) union " +
//				"(select * from crossvalidation_4)) union " +
//				"(select * from crossvalidation_5))  a " +
//				"order by id,start_time";
//		s[4] = "select * from " +
//				"((((select * from crossvalidation_2) union " +
//				"(select * from crossvalidation_3)) union " +
//				"(select * from crossvalidation_4)) union " +
//				"(select * from crossvalidation_5))  a " +
//				"order by id,start_time";
//		
//		String[] test = new String[5];
//		test[0] = "select * from crossvalidation_5 order by id,start_time";
//		test[1] = "select * from crossvalidation_4 order by id,start_time";
//		test[2] = "select * from crossvalidation_3 order by id,start_time";
//		test[3] = "select * from crossvalidation_2 order by id,start_time";
//		test[4] = "select * from crossvalidation_1 order by id,start_time";
//		int k = 10;
//		double avg_precision = 0.0;
//		double avg_recall = 0.0;
//		double avg_fmeasure = 0.0;
//		int r = 5;
//		for(int i=0;i<r;i++){
//			System.out.println("###########################");
//			logger.info("Query "+i);
//			String query= s[i];
//			RecommendationDataset<String, String> datasetMobility = RecommendationDatasetLoader.loadRecommendationDataset(query);
//			logger.info("Load Recommendation for "+i);
//			Network network = new Network("poi_wd_10h_150m_q"+i, "Network generated from clustered pois. Considering user activity on the poi with max stop of 6h");
//			network.setEdgeType(EdgeDefault.DIRECTED);
//			String table_input = 
//					"(select s.id user_id,s.trip user_trip,s.start_time,s.end_time,s.id2 location_id, "
//					+ " 'cluster_'||s.id2 as location_label,s.time_duration/3600 as time_duration, l.object as location_object"
//					+ " from (" + query + ") s, (select clus,st_convexhull(st_collect(object)) as object from location_cluster$ group by clus) l " +
//					" where l.clus = s.id2 order by s.id,s.start_time)";
//
//			logger.info("Building network for "+i);
//			PoiNetwork instance = new PoiNetwork(network, table_input);
//			instance.buildNetwork();
//			
//			String net = "poi_wd_10h_150m_q"+i;
//			Network n = new Network(net);
//			NetworkImporterImpl importer = null;
//			try {
//				importer = new NetworkImporterImpl(n);
//				importer.importNetwork();
//			}
//			catch (Exception ex) {
//				Exceptions.printStackTrace(ex);
//			}
//
//			logger.info("Poi Rank for "+i);
//			// POI RANK
//			GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
//			DirectedGraph digraph = graphModel.getDirectedGraph();
////			PoiRank<String, String> poiRank = new PoiRank<String, String>(digraph,datasetMobility);
////			poiRank.setDampFactor(0.1);
////			poiRank.execute();
//			// PREDICTIONS
////			RecommendationDataset<String, String> predictions = new RecommendationDataset<String, String>();
////			predictions.setDataset(poiRank.getRecommendation());
//			
//			logger.info("Evaluation for "+i);
//			// EVALUTATION USING NORMALIZED PRECISION
//			RecommendationDataset<String, String> relevantSet = RecommendationDatasetLoader.loadRecommendationDataset(test[i]);
//			for(String u : relevantSet.getUsers()){
//				if(datasetMobility.getDataset().containsKey(u)){
//					relevantSet.getItems(u).removeAll(datasetMobility.getItems(u));
//				}
//				
//			}
//			NormalizedPrecision<String, String> np = new NormalizedPrecision<String, String>(predictions.getDataset(), relevantSet.getDataset());
//			
//			double nP = np.normalizedPrecisionK(k);
//			System.out.println("Q "+i+" for NP@"+k+" = "+nP+ "\n");
//			
//			// PRECISION, RECALL AND F-MEASURE
//			IREvaluation<String, String> evaluation = new IREvaluation<String, String>(relevantSet.getDataset(),predictions.getDataset() );
//			double precision = evaluation.precisionK(k);
//			double recall = evaluation.recallK(k);
//			double fmeasure = evaluation.fmeasureK(k);
//			
//			avg_precision += precision;
//			avg_recall += recall;
//			avg_fmeasure += fmeasure;
//			
//			System.out.println("Precision@"+k+": "+precision);
//			System.out.println("Recall@"+k+": "+recall);
//			System.out.println("F-Measure@"+k+": "+fmeasure);
//			
//			
//			logger.info("Writing recommendation for "+i);
//			// Writing the result onto a file
//			Map<String,SortedMap<String,Double>> topPredictions = TopK.getTopK(predictions, k);
//			File file = new File("/Users/igobrilhante/poirank_k"+k+"_q"+i+".csv");
//			try {
//				OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file));
//				output.write("# Q "+i+" NP@"+k+" = "+nP);
//				output.write("user,item,rating\n");
//				for(String user : topPredictions.keySet()){
//					SortedMap<String,Double> items = topPredictions.get(user);
//					for(Map.Entry item : items.entrySet()){
//						if(((Double)item.getValue()) > 0)
//							output.write(user+","+ item.getKey()+","+item.getValue()+"\n");
//					}
//				}
//				output.close();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		avg_precision = avg_precision/r;
//		avg_recall = avg_recall/r;
//		avg_fmeasure = avg_fmeasure/r;
//		
//		System.out.println("Avg Precision@"+k+": "+avg_precision);
//		System.out.println("Avg Recall@"+k+": "+avg_recall);
//		System.out.println("Avg F-Measure@"+k+": "+avg_fmeasure);
//	}
//
//}
