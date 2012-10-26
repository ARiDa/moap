package test.utilities;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import location.recommender.beans.StopTable;
import mf.core.beans.Interval;
import mf.core.beans.MovingObject;

public class StopTableTest {
	public static void main(String[] args) throws SQLException{
		String query = "select id,id2::text ,1 as trajectoryId, start_time, end_time from stop_poi_1200s_150m " +
				" where (extract(epoch from end_time)-extract(epoch from start_time)) <= 25200 " +
				" order by id,start_time";
		List<StopTable<String, Timestamp>> stopTable = StopTable.loadStopTable(query);
		
		List<MovingObject<List<String>, Interval<Timestamp>>> mos = StopTable.loaderTrajectoryOfPois(stopTable);
		System.out.println(mos.size());
	}
}
