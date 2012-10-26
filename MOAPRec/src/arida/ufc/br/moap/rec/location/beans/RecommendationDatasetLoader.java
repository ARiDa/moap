package arida.ufc.br.moap.rec.location.beans;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


/**
 * 
 * @author igobrilhante
 */
public class RecommendationDatasetLoader {
    private static Logger logger = Logger.getLogger(RecommendationDatasetLoader.class);
    /**
     * 
     * @param query
     * @return
     */
    public static RecommendationSet<String,String> loadRecommendationTrajectoryDataset(String query){
        logger.info("Loading Trajectory Dataset as Recommendation Dataset");
        
		PostgresqlProvider provider = new PostgresqlProvider();
			ResultSet rs = provider.getResultSet(query);
			String user_id = "";
			String poi = "";
			Map<String,Double> items = new HashMap<String, Double>();
			RecommendationSet<String, String> dataset = new RecommendationSet<String, String>();
			int count = 0;
			try {
				while(rs.next()){
					String current_user = rs.getString("userid");
					String current_poi = rs.getString("poiid");
					Timestamp time = rs.getTimestamp("start_time");
					
					if(!current_user.equals(user_id)){
						
						// Store last found user

						user_id = current_user;
						items = new HashMap<String, Double>();
						items.put(current_poi, 1.0);
						dataset.addUser(user_id, items);
					}
					else{
						double v = 1;
						if(dataset.contains(user_id, current_poi)){
							v = dataset.getRating(user_id, current_poi);
							v = v + 1;
							dataset.setRating(user_id, current_poi, v);
							
						}
						else{
							dataset.addUserItem(user_id, current_poi, v);
						}
					}
					
				}
				return dataset;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

	}
}
