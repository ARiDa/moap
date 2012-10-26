/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobilitymining;

import arida.ufc.br.moap.network.dbmanager.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import org.postgis.PGgeometry;

/**
 *
 * @author igobrilhante
 */
public class UserTrajectory {

    final Logger logger = Logger.getLogger(UserTrajectory.class);

    // Contruct trajectory with a stop takes more than a given time at a point
    public void userTrajectory(String table_input, String table_output, int trip_threshold) throws Exception {
        logger.info("User Trajectory");
        DBManager db = DBManager.getInstance();
        ((org.postgresql.PGConnection)db.getConnection()).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
        ((org.postgresql.PGConnection)db.getConnection()).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
        db.createTable(table_output, "id integer, trip integer,object geometry,start_time timestamp ,end_time timestamp ,time_duration numeric");
        db.getConnection().setAutoCommit(false);
        Statement state = db.getConnection().createStatement();
        PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO " + table_output + " VALUES (?,?,?,?,?,?)");
        String query = "SELECT * FROM " + table_input + " a";
        logger.info(query);
        //String query_insert;
        logger.info("Loading data from " + table_input);
        ResultSet r = state.executeQuery(query);
        int id_old = -1, id, id2;
        int trip = 0;
        Timestamp start_time,end_time;
        PGgeometry object;;
        Long duration;
        Double distance;
        int count = 0;
        while (r.next()) {
            id = r.getInt("id");
//            id2 = r.getInt("id2");
            start_time = r.getTimestamp("start_time");
            end_time = r.getTimestamp("end_time");
            object = (PGgeometry)r.getObject("object");
//            distance = r.getDouble("distance");
            duration = r.getLong("time_duration"); // seconds
            if (id != id_old) {
                trip = 1;
                id_old = id;
            }
            if (duration > trip_threshold) {
                    trip++;
            }
            else {
                    //query_insert = "INSERT INTO "+table_output+" VALUES ("+id+","+trip+",'"+datetime+"',"+lat+","+lon+")";
                    ps.setInt(1, id);
                    ps.setInt(2, trip);
                    ps.setObject(3, object);
//            ps.setTimestamp(4,datetime);
//            ps.setDouble(5, distance);
                    ps.setTimestamp(4, start_time);
                    ps.setTimestamp(5, end_time);
                    ps.setLong(6, duration);
                    //ps.executeUpdate();
                    count++;
                    ps.addBatch();
                    if (count == 100) {
                        ps.executeBatch();
                        db.getConnection().commit();
                        ps.clearBatch();

                        count = 0;
                    }
           }


        }
        if (count > 0) {
            ps.executeBatch();
            db.getConnection().commit();
            ps.clearBatch();
        }
        logger.info("Commiting ...");
        db.getConnection().commit();
        ps.close();
        state.close();
//        db.getConnection().close();
        logger.info("End");
    }

    // Construct trajectories with the time between two points exceed a given time
    public void tripConstructor2(String table_input, String table_output, int trip_threshold) throws Exception {
        logger.info("Trip Constructor 2");
        DBManager db = DBManager.getInstance();

        db.createTable(table_output, "id integer, id2 integer, trip integer, start_time timestamp,end_time timestamp,distance numeric,duration_text text,time_duration numeric");
        db.getConnection().setAutoCommit(false);
        Statement state = db.getConnection().createStatement();
        PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO " + table_output + " VALUES (?,?,?,?,?,?,?,?)");
        String query = "SELECT * FROM " + table_input + " a";
        //String query_insert;
        logger.info("Loading data from " + table_input);
        ResultSet r = state.executeQuery(query);
//        ResultSetMetaData metadata = r.getMetaData();
//        String[] columns = new String[metadata.getColumnCount()];
//        String[] type = new String[metadata.getColumnCount()];
//        for(int i =0; i<metadata.getColumnCount();i++){
//            columns[i] = metadata.getColumnLabel(i);
//            type[i] = metadata.getColumnTypeName(i);
//        }
        int id_old = -1, id, id2;
        int trip = 0, trip_old = 0;
        Timestamp start_time = null, end_time = null, start_old = null, end_old = null;
        Double duration_seconds = 0.0;
        String duration;
        Double distance;
        int count = 0;
        boolean trip_end = false;

        while (r.next()) {
            // Last duration

            id = r.getInt("id");
            id2 = r.getInt("id2");
            start_time = r.getTimestamp("start_time");
            end_time = r.getTimestamp("end_time");
            duration = r.getString("duration_text");
            duration_seconds = r.getDouble("time_duration");
            distance = r.getDouble("distance");
            if (id != id_old) {
                trip = 1;
                id_old = id;
            }
            else {
                if ((start_time.getTime() / 1000) - (end_old.getTime() / 1000) >= trip_threshold) {
                    trip++;
                }

            }
//            else if(duration_seconds >= trip_threshold){
//               trip++;
//            }
            //query_insert = "INSERT INTO "+table_output+" VALUES ("+id+","+trip+",'"+datetime+"',"+lat+","+lon+")";
            ps.setInt(1, id);
            ps.setInt(2, id2);
            ps.setInt(3, trip);
            ps.setTimestamp(4, start_time);
            ps.setTimestamp(5, end_time);
            ps.setDouble(6, distance);
            ps.setString(7, duration);
            ps.setDouble(8, duration_seconds);

            //ps.executeUpdate();
            count++;
            ps.addBatch();

            start_old = start_time;
            end_old = end_time;

            // Creating another trip
            if (count == 100) {
                ps.executeBatch();
                db.getConnection().commit();
                ps.clearBatch();

                count = 0;
            }
        }
        if (count > 0) {
            ps.executeBatch();
            db.getConnection().commit();
            ps.clearBatch();
        }
        logger.info("Commiting ...");
        db.getConnection().commit();
        ps.close();
        state.close();
//        db.getConnection().close();
        logger.info("End");
    }
}
