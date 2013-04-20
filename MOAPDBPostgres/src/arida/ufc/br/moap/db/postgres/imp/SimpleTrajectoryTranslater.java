package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.core.spi.Type;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.importer.exceptions.MissingHeaderAttribute;
import arida.ufc.br.moap.importer.spi.ITranslater;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    
    private final String TRAJ_ID = "trajid";
    private final String USER_ID = "userid";
    private final String LATITUDE = "lat";
    private final String LONGITUDE = "lon";
    private final String TIME = "time";
    private boolean hasTrajectoryId = false;
    private String timeFormat = "yyyy-mm-dd HH:mm:ss";
    private Set<String> mandatoryIdx;
    private Set<String> annotationIdx;
    
    private Map<String,Type> attributeTypes;
    private Map<String,Integer> attributeDBTypes;
    
    private int[] columnType;
    Connection connection;
    ResultSet resultSet;

    public SimpleTrajectoryTranslater(Connection connection) {
        this.connection = connection;
        
        this.mandatoryIdx = new HashSet<String>();
        this.mandatoryIdx.add(LATITUDE);
        this.mandatoryIdx.add(LONGITUDE);
        this.mandatoryIdx.add(TIME);
        this.mandatoryIdx.add(USER_ID);
        this.mandatoryIdx.add(TRAJ_ID);
        
    }

    public void setTimeFormat(String format){
    	this.timeFormat = format;
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
                
                
                String userid = "";
                
                DateTimeFormatter fmt = DateTimeFormat.forPattern(timeFormat);
                String lastTrajId = "";
                String newTrajId = "0";
                
                while (resultSet.next()) {
                	String newuserid = resultSet.getString(USER_ID);
                	
                	LatLonPoint latLonPoint = new LatLonPoint();
                    double latitude = Double.valueOf(resultSet.getString(LATITUDE));
                    double longitude = Double.valueOf(resultSet.getString(LONGITUDE));
                    latLonPoint.setLatitude(latitude);
                    latLonPoint.setLongitude(longitude);
                    
                    // Add annotation to the points
                    for(String annotation : this.annotationIdx){
                    	
                    	Type type = this.attributeTypes.get(annotation);
                    	int dbType = this.attributeDBTypes.get(annotation);
                    	
                    	Object value = GeneralTranslater.getValue(dbType, annotation, resultSet);
                    	latLonPoint.getAnnotations().addAnnotation(annotation, type, value);
                    }
                    
                    if(hasTrajectoryId){
                    	newTrajId = resultSet.getString(TRAJ_ID);
                    }
                    
                    Object time = null;
                    Type timeType = this.attributeTypes.get(TIME);
                    if(timeType == Type.INT){
                    	time = resultSet.getInt(TIME);
                    }
                    else{
                    	if(timeType == Type.LONG){
                    		time = new DateTime(resultSet.getLong(TIME));
                    	}
                    	else{
                    		time = DateTime.parse(resultSet.getString(TIME), fmt);
                    	}
                    }
//                    DateTime timestamp = DateTime.parse(resultSet.getString(TIME), fmt);
                	
                	if(!newuserid.equalsIgnoreCase(userid)){
                		MovingObject movingObject = this.trajectoryDataModel.factory().newMovingObject(newuserid);
                		
                		Trajectory<LatLonPoint, Object> trajectory = this.trajectoryDataModel.factory().newTrajectory(newuserid + "_"+newTrajId, movingObject);

                        trajectory.addPoint(latLonPoint, time);
                        
                        trajectoryDataModel.addTrajectory(trajectory);

                	}
                	else{
                		Trajectory trajectory = this.trajectoryDataModel.getTrajectory(this.trajectoryDataModel.getTrajectoryCount()-1);
                		MovingObject movingObject = trajectory.getMovingObject();
                		/*
                		 * New trajectory
                		 */
                		if(!lastTrajId.equalsIgnoreCase(newTrajId)){
                			trajectory = this.trajectoryDataModel.factory().newTrajectory(newuserid + "_"+newTrajId,movingObject );
                		}

                        trajectory.addPoint(latLonPoint, time);
                		
                		
                	}
                	
                	userid = newuserid;
                	lastTrajId = newTrajId;
                	
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
        
        /**
         * Check if there is the attribute trajid to represent the trajectory id
         * This is an optional attribute when a user might have more than one trajectory
         */
        if(header.contains(TRAJ_ID)){
        	this.hasTrajectoryId = true;
        }
        
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
        if(!header.contains(USER_ID)){
        	error += "userid,";
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
        this.attributeTypes = new HashMap<String,Type>();
        this.attributeDBTypes = new HashMap<String,Integer>();
        try {
            rsmd = resultSet.getMetaData();
            this.columnType = new int[rsmd.getColumnCount()];
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            	String columnName = rsmd.getColumnName(i);
                header.add(columnName);
                
                // Attribute types
                int dbType = rsmd.getColumnType(i);
                Type t = GeneralTranslater.getType(dbType);
                
                this.attributeTypes.put(columnName, t );
                this.attributeDBTypes.put(columnName, dbType);
            }
            
            // Remove the attributes of the header that are mandatory
            this.annotationIdx = new HashSet<String>(header);
            this.annotationIdx.removeAll(mandatoryIdx);
            
            return header;
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        throw new MissingHeaderAttribute("Missing header attributes");
    }
}