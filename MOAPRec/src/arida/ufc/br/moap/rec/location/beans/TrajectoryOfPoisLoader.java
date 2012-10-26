package arida.ufc.br.moap.rec.location.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import arida.ufc.br.moap.core.beans.Interval;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;

/**
 * 
 * @author igobrilhante
 */
public class TrajectoryOfPoisLoader {
	static Logger logger = Logger.getLogger(TrajectoryOfPoisLoader.class);
	
        /**
         * 
         * @param query
         * @return
         */
        public static List<MovingObject<List<String>, Interval<Timestamp>>> loaderTrajectoryOfPois(String query){
		PostgresqlProvider provider = new PostgresqlProvider();
		ResultSet rs = provider.getResultSet(query);
		String user_id = "";
		int previous_trip = -1;
		Timestamp previous_time = new Timestamp(new Date().getTime());
		List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects = new ArrayList<MovingObject<List<String>,Interval<Timestamp>>>();

		try {
			logger.info("Loading data from database");
			while(rs.next()){
				String current_user = rs.getString("userId");
				String current_poi = rs.getString("poiId");
				int current_trip = rs.getInt("trajId");
				Timestamp start_time = rs.getTimestamp("start_time");
				Timestamp end_time = rs.getTimestamp("end_time");
				
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
        
        /**
         * 
         * @param query
         * @return
         */
        public static List<MovingObject<List<String>, org.joda.time.Interval>> loaderTrajectory(String query){
		PostgresqlProvider provider = new PostgresqlProvider();
		ResultSet rs = provider.getResultSet(query);
		String user_id = "";
		int previous_trip = -1;
		Timestamp previous_time = new Timestamp(new Date().getTime());
		List<MovingObject<List<String>, org.joda.time.Interval>> movingObjects = new ArrayList<MovingObject<List<String>,org.joda.time.Interval>>();

		try {
			logger.info("Loading data from database");
			while(rs.next()){
				String current_user = rs.getString("userId");
				String current_poi = rs.getString("poiId");
				int current_trip = rs.getInt("trajId");
				Timestamp start_time = rs.getTimestamp("start_time");
				Timestamp end_time = rs.getTimestamp("end_time");
				
				if(!user_id.equals(current_user)){ // New user
					List<String> list = new ArrayList<String>();
					list.add(current_poi);
					org.joda.time.Interval interval = new org.joda.time.Interval(start_time.getTime(), end_time.getTime());
					MovingObject<List<String>, org.joda.time.Interval> mo = new MovingObject<List<String>, org.joda.time.Interval>(current_user);
					Trajectory<List<String>, org.joda.time.Interval> t = new Trajectory<List<String>, org.joda.time.Interval>(Integer.toString(current_trip));
					t.addPoint(list, interval);
					
					mo.addTrajectory(t);
					movingObjects.add(mo);
					
					
				}
				else{// If the current user is the same as the previous one
					
					if(previous_time.equals(start_time)){ // if the current time is the same as the previous one
						MovingObject<List<String>, org.joda.time.Interval> mo = movingObjects.get(movingObjects.size()-1);
						Trajectory<List<String>, org.joda.time.Interval> t = mo.getTrajectory(mo.getTrajectories().size()-1);
						t.getPoints().get(t.getPoints().size()-1).add(current_poi);
					}
					else{ // if the current time is not the same as the previous one
						if(current_trip == previous_trip){ // still the same trip
							MovingObject<List<String>, org.joda.time.Interval> mo = movingObjects.get(movingObjects.size()-1);
							Trajectory<List<String>, org.joda.time.Interval> t = mo.getTrajectory(mo.getTrajectories().size()-1);
							List<String> list = new ArrayList<String>();
							list.add(current_poi);
							
							org.joda.time.Interval interval = new org.joda.time.Interval(start_time.getTime(), end_time.getTime());
							t.addPoint(list, interval);
							
						}
						else{ // add new trajectory to the user
							MovingObject<List<String>, org.joda.time.Interval> mo = movingObjects.get(movingObjects.size()-1);
							Trajectory<List<String>, org.joda.time.Interval> t = new Trajectory<List<String>, org.joda.time.Interval>(Integer.toString(current_trip));
							List<String> list = new ArrayList<String>();
							list.add(current_poi);
							org.joda.time.Interval interval = new org.joda.time.Interval(start_time.getTime(), end_time.getTime());
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
