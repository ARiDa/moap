package arida.ufc.br.moap.rec.locationrecommender.utilities;

import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



/**
 * 
 * @author igobrilhante
 */
public class Utilities {

	// Convert a list of stay points into a trajectory of stay points
//	public static synchronized <T, S> Trajectory<S> trajectoryAsSequenceOfStayPoints(List<T> list){
//		Trajectory<S> trajAsStayPoints = new Trajectory<S>("s",list);
////		trajAsStayPoints.setPoints(list);
////		Collections.sort(trajAsStayPoints.getPoints());
//		return trajAsStayPoints;
//	}
	
    /**
     * 
     * @param table
     * @param file
     * @throws SQLException
     * @throws Exception
     */
    public static synchronized void insertTrajectoriesIntoDatabase(String table,String file) throws SQLException, Exception{
		PostgresqlProvider db = new PostgresqlProvider();
		db.createTable(table, "userid text, trajId text,poiId text,start_time timestamp, end_time timestamp");
		PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO "+table+" VALUES(?,?,?,?,?)");
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		int userid = 0;
		while((line = br.readLine())!=null){
			String[] traj = line.split("\t");
			Date date = new Date();
			long time = date.getTime();
			for(int i=0 ; i < traj.length;i++){
				String poiId = traj[i];
				time = time+3600*1000; 
				Timestamp start_time = new Timestamp(time);
				time = time+3600*1000;
				Timestamp end_time = new Timestamp(time);
				
				ps.setString(1, Integer.toString(userid)); // userid
				ps.setString(2, "1"); // trajid
				ps.setString(3,poiId); // poiId
				ps.setTimestamp(4, start_time); // start_time
				ps.setTimestamp(5, end_time); // end_time
				ps.addBatch();
				
			}
			ps.executeBatch();
			ps.clearBatch();
			userid++;
			
		}
		ps.close();

	}
	
        /**
         * 
         * @param args
         */
        public static void main(String[] args){
		String dir = "/Users/igobrilhante/ECIR/";
		String f[] = {"TrajFirenze","TrajGlasgow","TrajSanFrancisco"};
		
		for(int i=0;i<f.length;i++){
			String table = f[i];
			try {
				insertTrajectoriesIntoDatabase(table,dir+f[i]);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
