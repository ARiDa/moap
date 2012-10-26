package arida.ufc.br.moap.rec.database;

import arida.ufc.br.moap.core.beans.Poi;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
import arida.ufc.br.moap.rec.algorithms.TopK;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.log4j.Logger;
//import arida.ufc.br.moap.


/**
 * 
 * @author igobrilhante
 */
public class RecommendationDB {
	private static Logger logger = Logger.getLogger(RecommendationDB.class);
        /**
         * 
         * @param <U>
         * @param <I>
         * @param dataset
         * @param table_name
         * @throws SQLException
         * @throws Exception
         */
        public static synchronized <U, I> void storeRecommendation(RecommendationSet<U, I> dataset,String table_name) throws SQLException, Exception{
		PostgresqlProvider db = new PostgresqlProvider();
		db.createTable(table_name, "userid text, itemid text, rating numeric ");
		PreparedStatement ps = PostgresqlProvider.getConnection().prepareStatement(
				"INSERT INTO "+table_name+" VALUES (?,?,?)");
		
		for(U user : dataset.getUsers()){
			Set<I> items = dataset.getItems(user);
			for(I item : items){
				double rating = dataset.getRating(user, item);
                                
				ps.setString(1, user.toString());
				ps.setString(2, item.toString());
				ps.setDouble(3, rating);
				ps.addBatch();
			}
			int[] r = ps.executeBatch();
			logger.info("Commiting "+r.length);
			
		}
		ps.close();
		
	}
        
        public static synchronized <I extends Comparable> void storeRecommendation(RecommendationSet dataset,int k,String table_name) throws SQLException, Exception{
		PostgresqlProvider db = new PostgresqlProvider();
		db.createTable(table_name, "userid text, itemid text, rating numeric ");
		PreparedStatement ps = PostgresqlProvider.getConnection().prepareStatement(
				"INSERT INTO "+table_name+" VALUES (?,?,?)");
		
		for(Object user : dataset.getUsers()){
			Map<I,Double> items = dataset.getItemRatings(user);
                        SortedMap<I,Double> sorted_items = TopK.getTopK(dataset.getItemRatings(user), k);
                        
                        
			for(Object item : sorted_items.keySet()){
				double rating = dataset.getRating(user, item);
                                
				ps.setString(1, user.toString());
				ps.setString(2, item.toString());
				ps.setDouble(3, rating);
				ps.addBatch();
			}
			int[] r = ps.executeBatch();
			logger.info("Commiting "+r.length);
			
		}
		ps.close();
		
	}
        
        public static synchronized RecommendationSet loadRecommendation(String table_name) throws SQLException{
            logger.info("Load Recommendation");
            PostgresqlProvider db = new PostgresqlProvider();
            ResultSet rs = db.getResultSet(table_name);
            RecommendationSet rec = new RecommendationSet();
            String previous_user = "-1";
            
            while(rs.next()){
                String userid = rs.getString("userid");
                String itemid = rs.getString("itemid");
                Double rating = rs.getDouble("rating");
                
                if(!userid.equals(previous_user)){
                    rec.addUser(userid);
                }
                rec.addUserItem(userid, itemid, rating);
                
                previous_user = userid;
            }
            
            return rec;
        }
        
        public static synchronized RecommendationSet loadRecommendation(int top_k,String table_name) throws SQLException{
            logger.info("Load Recommendation");
            PostgresqlProvider db = new PostgresqlProvider();
            ResultSet rs = db.getResultSet(table_name);
            RecommendationSet rec = new RecommendationSet();
            Map<String,Double> items = null;
            String previous_user = "-1";
            
            while(rs.next()){
                String userid = rs.getString("userid");
                String itemid = rs.getString("itemid");
                Double rating = rs.getDouble("rating");
                
                if(!userid.equals(previous_user)){
                    if(!previous_user.equals("-1")){
                        
                        SortedMap<String,Double> sorted_items = TopK.getTopK(items, top_k);
                        rec.addUser(previous_user, sorted_items);
                    }
                    items = new HashMap<String, Double>();
                }
                items.put(itemid, rating);
                
                previous_user = userid;
            }
            if(!items.isEmpty()){
                SortedMap<String,Double> sorted_items = TopK.getTopK(items, top_k);
                rec.addUser(previous_user, sorted_items);
            }
            
            return rec;
        }
        
        public static synchronized Set<Poi> loadPoiSet(String poi_table) throws SQLException{
            logger.info("Load Poi Set");
            Map<String,Poi> map = new HashMap<String, Poi>();
            Set<Poi> pois = new HashSet<Poi>();
            
            PostgresqlProvider db = new PostgresqlProvider();
            ResultSet rs = db.getResultSet(poi_table);
            Poi poi = null;
            while(rs.next()){
                String poi_id = rs.getString("poi_id");
                String category = rs.getString("poi_category");
                
                if(!map.containsKey(poi_id)){
                    poi = new Poi(poi_id);
                    
                    poi.getAnnotations().addAnnotation("categories", "set", new HashSet<Object>());
                    ((Set)poi.getAnnotations().getAnnotation("categories").getValue()).add(category);
                    
                    map.put(poi_id, poi);
                }
                else{
                    ((Set)map.get(poi_id).getAnnotations().getAnnotation("categories").getValue()).add(category);
                    
                }
                
//                if(poi_id.equals(previous_poi)){
//                    ((Set)poi.getAnnotations().getAnnotation("category").getValue()).add(category);
//                }
//                else{
//                    if(!previous_poi.equals("-1")){
//                        pois.add(poi);
//                        poi=null;
//                    }
//                    poi = new Poi(poi_id);
//                    poi.getAnnotations().addAnnotation("category", "set", new HashSet<Object>());
//                    ((Set)poi.getAnnotations().getAnnotation("category").getValue()).add(category);
//                }
//                previous_poi = poi_id;
            }
            
//            if(poi!=null){
//                pois.add(poi);
//            }
            
            for(Poi p : map.values()){
                pois.add(p);
            }
            
            
            return pois;
        }
}
