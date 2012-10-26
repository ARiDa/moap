package test.recommendation;

import location.recommender.beans.RecommendationDataset;
import location.recommender.beans.RecommendationDatasetLoader;


public class DatasetTest {

	
	public static void main(String[] args) {
		String query="select * from stops_trip_location_1200s_150m order by id,start_time";
		RecommendationDataset<String, String> dataset = RecommendationDatasetLoader.loadRecommendationDataset(query);
		System.out.println(dataset.getDataset());
//		fail("Not yet implemented");
		
		
	}

}
