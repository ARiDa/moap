package arida.ufc.br.moap.importer.database.imp;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
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
import org.joda.time.Interval;
import org.openide.util.Exceptions;

/**
 *
 * The main class to translate a ResultSet for a specific Model
 *
 * @author franzejr
 * @author igobrilhante
 */
public class Translater implements ITranslater {

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

    public Translater(Connection connection) {
        this.connection = connection;
    }

    /*
     * Fill the Model with the data from the table
     */
    @Override
    public void translate(String query, IDataModel model) {
        trajectoryDataModel = (ITrajectoryModel) model;
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
                    DateTime timestamp = DateTime.parse(resultSet.getString(TIME));
  
                    latLonPoint.setLatitude(latitude);
                    latLonPoint.setLongitude(longitude);
                    trajectory.addPoint(latLonPoint, timestamp);
                    
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
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                header.add(rsmd.getColumnName(i));
            }
            
            return header;
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        throw new MissingHeaderAttribute("Missing header attributes");
    }
}

/*
 * //TODO 
 * make a general translater
 * try {
 DatabaseMetaData mtdt = connection.getMetaData();
            
 Statement statement = connection.createStatement();
 resultSet = statement.executeQuery(query);
            
 ResultSetMetaData rsmd = resultSet.getMetaData();
            
 int databaseType[] = new int[rsmd.getColumnCount()];
 String columnName[] = new String[rsmd.getColumnCount()];
            
 for (int i = 0; i < rsmd.getColumnCount(); i++) {
 databaseType[i] = rsmd.getColumnType(i);
 columnName[i] = rsmd.getColumnName(i);
 }
            
 while(resultSet.next()){
 for (int i = 0; i < rsmd.getColumnCount(); i++) {
 Object object = typeName(databaseType[i], resultSet.getObject(i));
 }
 }
 } catch (SQLException ex) {
 Exceptions.printStackTrace(ex);
 }
        
 * 
 *
 * 
 * 
 *   
 * Returns the name associated with a SQL type.
 *
 * @param type 	the SQL type
 * @return 		the name of the type
 *
 public static Object typeName(int type, Object object) {
 switch (type) {
 case Types.BIGINT :
 return (Integer) object;
 case Types.BINARY:
 return "BINARY";
 case Types.BIT:
 return "BIT";
 case Types.CHAR:
 return "CHAR";
 case Types.DATE:
 return "DATE";
 case Types.DECIMAL:
 return "DECIMAL";
 case Types.DOUBLE:
 return "DOUBLE";
 case Types.FLOAT:
 return "FLOAT";
 case Types.INTEGER:
 return "INTEGER";
 case Types.LONGVARBINARY:
 return "LONGVARBINARY";
 case Types.LONGVARCHAR:
 return "LONGVARCHAR";
 case Types.NULL:
 return "NULL";
 case Types.NUMERIC:
 return "NUMERIC";
 case Types.OTHER:
 return "OTHER";
 case Types.REAL:
 return "REAL";
 case Types.SMALLINT:
 return "SMALLINT";
 case Types.TIME:
 return "TIME";
 case Types.TIMESTAMP:
 return "TIMESTAMP";
 case Types.TINYINT:
 return "TINYINT";
 case Types.VARBINARY:
 return "VARBINARY";
 case Types.VARCHAR:
 return "VARCHAR";
 default:
 return "Unknown";
 }
 }
    
 }
 * 
 */
