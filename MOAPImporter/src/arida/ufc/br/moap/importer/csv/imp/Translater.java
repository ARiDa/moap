package arida.ufc.br.moap.importer.csv.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.importer.spi.ITranslater;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import org.openide.util.Exceptions;

/**
 *
 * The main class to translate a ResultSet for a specific Model
 * 
 * @author franzejr
 * @author igobrilhante
 */
public class Translater implements ITranslater {
    
    
    /* Type mapping used for reading experiment results */
  /** Type mapping for STRING used for reading experiment results. */
  public static final int STRING = 0;
  /** Type mapping for BOOL used for reading experiment results. */
  public static final int BOOL = 1;
  /** Type mapping for DOUBLE used for reading experiment results. */
  public static final int DOUBLE = 2;
  /** Type mapping for BYTE used for reading experiment results. */
  public static final int BYTE = 3;
  /** Type mapping for SHORT used for reading experiment results. */
  public static final int SHORT = 4;
  /** Type mapping for INTEGER used for reading experiment results. */
  public static final int INTEGER = 5;
  /** Type mapping for LONG used for reading experiment results. */
  public static final int LONG = 6;
  /** Type mapping for FLOAT used for reading experiment results. */
  public static final int FLOAT = 7;
  /** Type mapping for DATE used for reading experiment results. */
  public static final int DATE = 8; 
  /** Type mapping for TEXT used for reading, e.g., text blobs. */
  public static final int TEXT = 9; 
  /** Type mapping for TIME used for reading TIME columns. */
    
    Connection connection;
    ResultSet resultSet;
    
    public Translater(Connection connection){
        this.connection = connection;
    }
    
    /*
     * Fill the Model with the data from the table
     */
    @Override
    public void translate(String query, IDataModel model) {
        
        try {
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
            
            int i = 0;
            while(resultSet.next()){
                //TODO
                typeName(databaseType[i]);
                i++;
            }
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
         
    }
    
   /**
   * Returns the name associated with a SQL type.
   *
   * @param type 	the SQL type
   * @return 		the name of the type
   */
  public static String typeName(int type) {
    switch (type) {
      case Types.BIGINT :
	return "BIGINT ";
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
