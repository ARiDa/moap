package arida.ufc.br.moap.rec.location.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import arida.ufc.br.moap.core.beans.Interval;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;

/**
 * 
 * @author igobrilhante
 * @param <S>
 * @param <T>
 */
public class StopTable<S,T> {
	private String userId;
	private S point;
	private T startTime;
	private T endTime;
	private int trajectoryId;

        /**
         * 
         * @return
         */
        public String getUserId() {
		return userId;
	}
        /**
         * 
         * @param userId
         */
        public void setUserId(String userId) {
		this.userId = userId;
	}
        /**
         * 
         * @return
         */
        public S getPoint() {
		return point;
	}
        /**
         * 
         * @param point
         */
        public void setPoint(S point) {
		this.point = point;
	}
        /**
         * 
         * @return
         */
        public T getStartTime() {
		return startTime;
	}
        /**
         * 
         * @param startTime
         */
        public void setStartTime(T startTime) {
		this.startTime = startTime;
	}
        /**
         * 
         * @return
         */
        public T getEndTime() {
		return endTime;
	}
        /**
         * 
         * @param endTime
         */
        public void setEndTime(T endTime) {
		this.endTime = endTime;
	}

        /**
         * 
         * @return
         */
        public int getTrajectoryId() {
		return trajectoryId;
	}
        /**
         * 
         * @param trajectoryId
         */
        public void setTrajectoryId(int trajectoryId) {
		this.trajectoryId = trajectoryId;
	}
        /**
         * 
         * @param <S1>
         * @param <T1>
         * @param query
         * @return
         * @throws SQLException
         */
        @SuppressWarnings("unchecked")
	public static <S1, T1> List<StopTable<S1, T1>> loadStopTable(String query) throws SQLException{

		PostgresqlProvider db = new PostgresqlProvider();
		ResultSet rs = db.getResultSet(query);
		List<StopTable<S1, T1>> list = new ArrayList<StopTable<S1,T1>>();

		while(rs.next()){
			StopTable<S1, T1> row = new StopTable<S1, T1>();
			row.setUserId(rs.getString("id"));
			row.setPoint((S1)rs.getObject("id2"));
			row.setStartTime((T1)rs.getObject("start_time"));
			row.setEndTime((T1)rs.getObject("end_time"));
			row.setTrajectoryId(rs.getInt("trajectoryId"));
			list.add(row);
		}

		return list;
	}

        /**
         * 
         * @return
         */
        public String toString(){
		return this.userId+","+point+","+startTime+","+endTime;
	}

        /**
         * 
         * @param table
         * @return
         * @throws SQLException
         */
        public static List<MovingObject<List<String>, Interval<Timestamp>>> loaderTrajectoryOfPois(List<StopTable<String, Timestamp>> table) throws SQLException{
		Logger logger = Logger.getLogger("Loader Trajetory of Pois");
		List<StopTable<String, Timestamp>> stopTable = table;
		String user_id = "";
		int previous_trip = -1;
		Timestamp previous_time = new Timestamp(new Date().getTime());
		List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects = new ArrayList<MovingObject<List<String>,Interval<Timestamp>>>();

		logger.info("Loading data from database");
		for(StopTable<String, Timestamp> row : stopTable){
			String current_user = (String)row.getUserId();
			
			int current_trip = (Integer)row.getTrajectoryId();
			Timestamp start_time = (Timestamp)row.getStartTime();
			Timestamp end_time = (Timestamp)row.getEndTime();
			String current_poi = (String)row.getPoint();

			if(!user_id.equals(current_user)){ // New user
				List<String> list = new ArrayList<String>();
				list.add(current_poi);
				Interval<Timestamp> interval = new Interval<Timestamp>(start_time, end_time);
				MovingObject<List<String>, Interval<Timestamp>> mo = new MovingObject<List<String>, Interval<Timestamp>>(current_user);
				Trajectory<List<String>, Interval<Timestamp>> t = new Trajectory<List<String>, Interval<Timestamp>>(Integer.toString(current_trip));
				t.addPoint(list, interval);

				mo.addTrajectory(t);
				movingObjects.add(mo);


			}
			else{// If the current user is the same as the previous one

				if(previous_time.equals(start_time)){ // if the current time is the same as the previous one
					MovingObject<List<String>, Interval<Timestamp>> mo = movingObjects.get(movingObjects.size()-1);
					Trajectory<List<String>, Interval<Timestamp>> t = mo.getTrajectory(mo.getTrajectories().size()-1);
					t.getPoints().get(t.getPoints().size()-1).add(current_poi);
				}
				else{ // if the current time is not the same as the previous one
					if(current_trip == previous_trip){ // still the same trip
						MovingObject<List<String>, Interval<Timestamp>> mo = movingObjects.get(movingObjects.size()-1);
						Trajectory<List<String>, Interval<Timestamp>> t = mo.getTrajectory(mo.getTrajectories().size()-1);
						List<String> list = new ArrayList<String>();
						list.add(current_poi);

						Interval<Timestamp> interval = new Interval<Timestamp>(start_time, end_time);
						t.addPoint(list, interval);

					}
					else{ // add new trajectory to the user
						MovingObject<List<String>, Interval<Timestamp>> mo = movingObjects.get(movingObjects.size()-1);
						Trajectory<List<String>, Interval<Timestamp>> t = new Trajectory<List<String>, Interval<Timestamp>>(Integer.toString(current_trip));
						List<String> list = new ArrayList<String>();
						list.add(current_poi);
						Interval<Timestamp> interval = new Interval<Timestamp>(start_time, end_time);
						t.addPoint(list, interval);

						mo.addTrajectory(t);
					}
				}
			}
			user_id = current_user;
			previous_trip = current_trip;
			previous_time = start_time;
		}

		logger.info("End");
		return movingObjects;
	}

}
