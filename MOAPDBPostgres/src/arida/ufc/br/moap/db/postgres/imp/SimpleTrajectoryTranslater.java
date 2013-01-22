package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.importer.exceptions.MissingHeaderAttribute;
import arida.ufc.br.moap.importer.spi.ITranslater;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openide.util.Exceptions;

/**
 *
 * The main class to translate a ResultSet for a specific Model(ITrajectoryModel)
 * All trajectories must have lat,lon,time in your header.
 *
 * @author franzejr
 * @author igobrilhante
 */
public class SimpleTrajectoryTranslater implements ITranslater {

    /*
     * Necessary Parameters
     */
    public static final String PARAMETER_FILE = "file";
    /*
     * Standard Variables
     */
    private ITrajectoryModel trajectoryDataModel;
    private final String LATITUDE = "lat";
    private final String LONGITUDE = "lon";
    private final String TIME = "time";
    Connection connection;
    ResultSet resultSet;

    public SimpleTrajectoryTranslater(Connection connection) {
        this.connection = connection;
    }

    /*
     * Fill the Model with the data from the table
     */
    @Override
    public void translate(String query, IDataModel model) {
        if(model instanceof ITrajectoryModel){
          trajectoryDataModel = (ITrajectoryModel) model;
        }
        else {
            throw new UnsupportedOperationException("Not supported yet: "+model.getClass());
        }
        
        Statement statement;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            if(isHeaderValid(resultSet)){
                Integer i = 0;
                while (resultSet.next()) {
                    MovingObject movingObject = this.trajectoryDataModel.factory().newMovingObject(i.toString());
                    Trajectory<LatLonPoint, DateTime> trajectory = this.trajectoryDataModel.factory().newTrajectory(i + "_0", movingObject);
                    
                    LatLonPoint latLonPoint = new LatLonPoint();
                    double latitude = Double.valueOf(resultSet.getString(LATITUDE));
                    double longitude = Double.valueOf(resultSet.getString(LONGITUDE));
                    
                    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss.SSSSSS");
                    
                    DateTime timestamp = DateTime.parse(resultSet.getString(TIME), fmt);
  
                    latLonPoint.setLatitude(latitude);
                    latLonPoint.setLongitude(longitude);
                    trajectory.addPoint(latLonPoint, timestamp);
                    
                    trajectoryDataModel.addTrajectory(trajectory);
                    
                    i++;
                }
            } 
            else{
                throw new MissingHeaderAttribute("Missing header attributes");
            }

        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
    

    /*
     * Verify if the header is valid
     * for a header be valid, it's necessary it must has
     * Latitude, Longitude and Timestamp
     * 
     * @param resultSet ResultSet
     */
    private boolean isHeaderValid(ResultSet resultSet) {
        Set<String> header = getColumnNames(resultSet);
        
        String error = "";

        if (!header.contains(LATITUDE)) {
            error += "lat,";
        }
        if (!header.contains(LONGITUDE)) {
            error += "lon,";
        }
        if (!header.contains(TIME)) {
            error += "time,";
        }
        if (!error.equals("")) {
            int size = error.length() - 1;
            String msg = error.substring(0, size);
            throw new MissingHeaderAttribute("Missing header attributes: " + msg);
        }

        return true;
    }
    
    private Set<String> getColumnNames(ResultSet resultSet){
        ResultSetMetaData rsmd;
        Set<String> header = new HashSet<String>();
        try {
            rsmd = resultSet.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                header.add(rsmd.getColumnName(i));
            }
            
            return header;
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        throw new MissingHeaderAttribute("Missing header attributes");
    }
}