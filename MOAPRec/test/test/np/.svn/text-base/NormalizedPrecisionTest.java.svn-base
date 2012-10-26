package test.np;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import location.recommender.beans.RecommendationDataset;
import location.recommender.beans.RecommendationDatasetLoader;

import org.junit.Test;

import recommender.evaluation.NormalizedPrecision;

public class NormalizedPrecisionTest {

	@Test
	public void test() {
//		RecommendationDataset<String, String> relevantSet = RecommendationDatasetLoader.loadRecommendationDataset("select * from crossvalidation_5 order by id,start_time");
		
		String igo ="igo";
		RecommendationDataset<String, String> relevantSet = new RecommendationDataset<String, String>();
		Map<String, Double> items1 = new HashMap<String, Double>();
		items1.put("1", 1.0);
		items1.put("2", 1.0);
		items1.put("3", 1.0);
		items1.put("4", 1.0);
		items1.put("5", 1.0);
		
		Map<String,Map<String,Double>> d1 = new HashMap<String,Map<String,Double>>();
		d1.put(igo, items1);
		relevantSet.setDataset(d1);
		
		RecommendationDataset<String, String> retrievedSet = new RecommendationDataset<String, String>();
		Map<String, Double> items2 = new HashMap<String, Double>();
		items2.put("1", 1.0);
		items2.put("2", 1.0);
		items2.put("3", 1.0);
//		relevantSet.addUser(igo, items2);
		Map<String,Map<String,Double>> d2 = new HashMap<String,Map<String,Double>>();
		d2.put(igo, items2);
		retrievedSet.setDataset(d2);
		System.out.println(retrievedSet.getDataset());
		
		
//		NormalizedPrecision<String, String> np = new NormalizedPrecision<String, String>(d2, d1);
//		double evaluation = np.normalizedPrecisionK(5);
//		System.out.println(evaluation);
	}

}
