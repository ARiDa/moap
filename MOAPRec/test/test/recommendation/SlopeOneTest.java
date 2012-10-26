package test.recommendation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import location.recommender.beans.RecommendationDataset;
import location.recommender.beans.RecommendationDatasetLoader;

import org.apache.log4j.Logger;
import org.gephi.io.importer.api.EdgeDefault;
import org.junit.Test;
import org.openide.util.Exceptions;

import arida.ufc.br.locationrecommender.algorithms.TopK;
import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkImporterImpl;
import arida.ufc.br.networks.PoiNetwork;

import recommender.evaluation.IREvaluation;
import recommender.evaluation.NormalizedPrecision;
import recommender.system.pr.s1.WeightedSlopeOne;

public class SlopeOneTest {
	private Logger logger = Logger.getLogger(SlopeOneTest.class);

	@Test
	public void testComputePredictions() throws Exception {

		/**
		 * DATASET 1
		 */

		String user1 = "user1";
		String user2 = "user2";
		String user3 = "user3";
		String user4 = "user4";
		String user5 = "user5";

		Map<String,Double> user1_items = new HashMap<String, Double>();
		user1_items.put("item1", 4.0);
		user1_items.put("item3", 5.0);
		user1_items.put("item4", 5.0);

		Map<String,Double> user2_items = new HashMap<String, Double>();
		user2_items.put("item1", 4.0);
		user2_items.put("item2", 2.0);
		user2_items.put("item3", 1.0);

		Map<String,Double> user3_items = new HashMap<String, Double>();
		user3_items.put("item1", 3.0);
		user3_items.put("item3", 2.0);
		user3_items.put("item4", 4.0);

		Map<String,Double> user4_items = new HashMap<String, Double>();
		user4_items.put("item1", 4.0);
		user4_items.put("item2", 4.0);

		Map<String,Double> user5_items = new HashMap<String, Double>();
		user5_items.put("item1", 2.0);
		user5_items.put("item2", 1.0);
		user5_items.put("item3", 3.0);
		user5_items.put("item4", 5.0);

		Map<String, Map<String,Double>> dataset = new HashMap<String, Map<String,Double>>();
		dataset.put(user1, user1_items);
		dataset.put(user2, user2_items);
		dataset.put(user3, user3_items);
		dataset.put(user4, user4_items);
		dataset.put(user5, user5_items);

		/**
		 * DATASET 2 FROM WIKIPEDIA
		 */
		String john = "john";
		String mark = "mark";
		String lucy = "lucy";

		Map<String,Double> john_items = new HashMap<String, Double>();
		john_items.put("item1", 5.0);
		john_items.put("item2", 3.0);
		john_items.put("item3", 2.0);

		Map<String,Double> mark_items = new HashMap<String, Double>();
		mark_items.put("item1", 3.0);
		mark_items.put("item2", 4.0);

		Map<String,Double> lucy_items = new HashMap<String, Double>();
		lucy_items.put("item2", 2.0);
		lucy_items.put("item3", 5.0);

		Map<String, Map<String,Double>> wikidataset = new HashMap<String, Map<String,Double>>();
		wikidataset.put(john, john_items);
		wikidataset.put(mark, mark_items);
		wikidataset.put(lucy, lucy_items);



		/**
		 * DATASET 3 FROM MOBILITY
		 */
		/**
		 * DATASET FROM MOBILITY
		 */
		String[] s = new String[5];

		s[0] = "select * from " +
				"((((select * from crossvalidation_1) union " +
				"(select * from crossvalidation_2)) union " +
				"(select * from crossvalidation_3)) union " +
				"(select * from crossvalidation_4))  a " +
				"order by id,start_time";
		s[1] = "select * from " +
				"((((select * from crossvalidation_1) union " +
				"(select * from crossvalidation_2)) union " +
				"(select * from crossvalidation_3)) union " +
				"(select * from crossvalidation_5))  a " +
				"order by id,start_time";
		s[2] = "select * from " +
				"((((select * from crossvalidation_1) union " +
				"(select * from crossvalidation_2)) union " +
				"(select * from crossvalidation_4)) union " +
				"(select * from crossvalidation_5))  a " +
				"order by id,start_time";
		s[3] = "select * from " +
				"((((select * from crossvalidation_1) union " +
				"(select * from crossvalidation_3)) union " +
				"(select * from crossvalidation_4)) union " +
				"(select * from crossvalidation_5))  a " +
				"order by id,start_time";
		s[4] = "select * from " +
				"((((select * from crossvalidation_2) union " +
				"(select * from crossvalidation_3)) union " +
				"(select * from crossvalidation_4)) union " +
				"(select * from crossvalidation_5))  a " +
				"order by id,start_time";

		String[] test = new String[5];
		test[0] = "select * from crossvalidation_5 order by id,start_time";
		test[1] = "select * from crossvalidation_4 order by id,start_time";
		test[2] = "select * from crossvalidation_3 order by id,start_time";
		test[3] = "select * from crossvalidation_2 order by id,start_time";
		test[4] = "select * from crossvalidation_1 order by id,start_time";
		int k = 10;
		double avg_precision = 0.0;
		double avg_recall = 0.0;
		double avg_fmeasure = 0.0;
		int r = 5;
		for(int i=0;i<r;i++){
			System.out.println("###########################");
			logger.info("Query "+i);
			String query= s[i];
			RecommendationDataset<String, String> datasetMobility = RecommendationDatasetLoader.loadRecommendationDataset(query);
			logger.info("Load Recommendation for "+i);
			
			WeightedSlopeOne<String, String> ws1 = new WeightedSlopeOne<String, String>(datasetMobility.getDataset());
			RecommendationDataset<String, String> predictions = new RecommendationDataset<String, String>();
			predictions.setDataset(ws1.computePredictions());
			
			logger.info("Evaluation for "+i);
			
			// EVALUTATION USING NORMALIZED PRECISION
			RecommendationDataset<String, String> relevantSet = RecommendationDatasetLoader.loadRecommendationDataset(test[i]);
			
			for(String u : relevantSet.getUsers()){
				if(datasetMobility.getDataset().containsKey(u)){
					relevantSet.getItems(u).removeAll(datasetMobility.getItems(u));
				}
				
			}
			
//			NormalizedPrecision<String, String> np = new NormalizedPrecision<String, String>(predictions.getDataset(), relevantSet.getDataset());
			
//			double nP = np.normalizedPrecisionK(k);
//			System.out.println("Q "+i+" for NP@"+k+" = "+nP+ "\n");
			
			// PRECISION, RECALL AND F-MEASURE
			IREvaluation<String, String> evaluation = new IREvaluation<String, String>(relevantSet.getDataset(),predictions.getDataset() );
			double precision = evaluation.precisionK(k);
			double recall = evaluation.recallK(k);
			double fmeasure = evaluation.fmeasureK(k);
			
			avg_precision += precision;
			avg_recall += recall;
			avg_fmeasure += fmeasure;
			
			System.out.println("Precision@"+k+": "+precision);
			System.out.println("Recall@"+k+": "+recall);
			System.out.println("F-Measure@"+k+": "+fmeasure);
			Map<String,SortedMap<String,Double>> topPredictions = TopK.getTopK(predictions, k);
			File file = new File("/Users/igobrilhante/ws_k"+k+"_"+i+".csv");
			try {
				OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file));
				output.write("user,item,rating\n");
				for(String user : topPredictions.keySet()){
					SortedMap<String,Double> items = topPredictions.get(user);
					for(Map.Entry item : items.entrySet()){
						if(((Double)item.getValue()) > 0)
							output.write(user+","+ item.getKey()+","+item.getValue()+"\n");
					}
				}
				output.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}	
		avg_precision = (double)avg_precision/r;
		avg_recall = (double)avg_recall/r;
		avg_fmeasure = (double)avg_fmeasure/r;
		
		System.out.println("Avg Precision@"+k+": "+avg_precision);
		System.out.println("Avg Recall@"+k+": "+avg_recall);
		System.out.println("Avg F-Measure@"+k+": "+avg_fmeasure);
		//		fail("Not yet implemented");
	}

}
