package arida.ufc.br.moap.rec.evaluation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import arida.ufc.br.moap.core.beans.Interval;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
import arida.ufc.br.moap.rec.location.beans.StopTable;

/**
 * 
 * @author igobrilhante
 */
public class KFoldCrossValidation {
	private int k;
	private List<StopTable<String, Timestamp>> dataset;
	private Map<Integer, Map<String, List<Map.Entry<List<String>, Interval<Timestamp>>>>> folds;

	// public KFoldCrossValidation(int k,List<StopTable<String, Timestamp>>
	// stopTable){
	// this.k = k;
	// this.dataset = new ArrayList<StopTable<String,Timestamp>>(stopTable);
	// this.folds = new HashMap<Integer,Map<String,List<Map.Entry<List<String> ,
	// Interval<Timestamp>>>>>();
	// }
	//
	// private void partition(){
	// for(int i =0;i<k;i++){
	// folds.put(i, new HashMap<String,List<Map.Entry<List<String> ,
	// Interval<Timestamp>>>>());
	// }
	// int size = 0, index = 0;
	// int round = 0;
	// Random rand = new Random();
	// while(!dataset.isEmpty()){
	// size = dataset.size();
	// index = rand.nextInt(size);
	// StopTable<String, Timestamp> row = dataset.get(index);
	//
	// Map<String,List<Map.Entry<List<String> , Interval<Timestamp>>>> data =
	// folds.get(round%k);
	//
	// if(data.containsKey(row.getUserId())){
	// List<Map.Entry<List<String> , Interval<Timestamp>>> list =
	// data.get(row.getUserId());
	//
	// }
	//
	// }
	// }

        /**
         * 
         * @param query
         * @param prefix
         * @throws SQLException
         * @throws Exception
         */
        public static void KFoldCrossValidation(String query,String prefix)
			throws SQLException, Exception {

		int size = 0;
		PostgresqlProvider db = new PostgresqlProvider();
		ResultSet r = db.getResultSet(query);
		while(r.next()){
			size++;
		}
		r.close();
		System.out.println("Size: "+size);
		int folds = (int) (size / 5);
		String[] queries = new String[5];

		queries[0] = "select * from (" + query
				+ ") q1 order by random() limit " + folds;
		System.out.println(queries[0]);
		db.createTableAsQuery(""+prefix+"_" + 0, queries[0]);
		
		queries[1] = "select * from ((" + query + ") except ("
				+ "select * from "+prefix+"_" + 0 + ")) q  \n"
				+ " order by random() limit " + folds;
		System.out.println(queries[1]);
		db.createTableAsQuery(""+prefix+"_" + 1, queries[1]);

		queries[2] = "select * from ((" + query + ") except ( select * from ("
				+ "(select * from "+prefix+"_" + 0 + ") union \n" + " "
				+ "(select * from "+prefix+"_" + 1 + ")"
				+ ") a ) ) q order by random() limit " + folds;
		System.out.println(queries[2]);
		db.createTableAsQuery(""+prefix+"_" + 2, queries[2]);

		queries[3] = "select * from ((" + query + ") except (select * from ("
				+ "(select * from "+prefix+"_" + 0 + ") union \n" + " "
				+ "(select * from "+prefix+"_" + 1 + ") union \n" + " "
				+ "(select * from "+prefix+"_" + 2 +") "
				+ ") a ) ) q order by random() limit " + folds;
		System.out.println(queries[3]);
		db.createTableAsQuery(""+prefix+"_" + 3, queries[3]);

		queries[4] = "select * from ((" + query + ") except (select * from ("
				+ "(select * from "+prefix+"_" + 0 + ") union "
				+ "(select * from "+prefix+"_" + 1 + ") \n" + " union "
				+ "(select * from "+prefix+"_" + 2 + ") union "
				+ "(select * from "+prefix+"_" + 3 + ")" +
						") a)) q";
		System.out.println(queries[4]);
		db.createTableAsQuery(""+prefix+"_" + 4, queries[4]);

//		db.close();

	}

}
