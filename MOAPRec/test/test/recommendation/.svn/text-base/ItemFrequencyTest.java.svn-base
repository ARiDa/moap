package test.recommendation;

import java.util.Map;
import location.recommender.beans.RecommendationDataset;
import location.recommender.beans.RecommendationDatasetLoader;

import org.junit.Test;

import arida.ufc.br.locationrecommender.algorithms.ItemFrequency;

public class ItemFrequencyTest {

	@Test
	public void test(){
		String query = "select * from stops_trip_location_1200s_150m order by id,start_time";
		RecommendationDataset<String, String> datasetMobility = RecommendationDatasetLoader.loadRecommendationDataset(query);
		
		Map<String, Integer> f = ItemFrequency.getItemFrequency(datasetMobility);
		System.out.println(f.size());
		System.out.println(f);
		
		
		
	}
}
