package test.recommendation;

import java.util.HashMap;
import java.util.Map;

import recommender.system.pr.s1.WeightedSlopeOne;
import recommender.system.userbasedcf.CosineRecommendation;

public class PearsonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
		CosineRecommendation<String, String> pr = new CosineRecommendation<String, String>(dataset);
		pr.execute();
		System.out.println(pr.getPredictions());
		
		WeightedSlopeOne<String, String> pw = new WeightedSlopeOne<String, String>(wikidataset);
		System.out.println(pw.computePredictions());
	}

}
