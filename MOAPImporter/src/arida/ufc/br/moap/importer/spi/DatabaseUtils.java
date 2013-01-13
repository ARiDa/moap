package arida.ufc.br.moap.importer.spi;

/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    DatabaseUtils.java
 *    Copyright (C) 1999 University of Waikato, Hamilton, New Zealand
 *
 */


import arida.ufc.br.moap.core.database.imp.SQLUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * DatabaseUtils provides utility functions for accessing the experiment
 * database. The jdbc
 * driver and database to be used default to "jdbc.idbDriver" and
 * "jdbc:idb=experiments.prp". These may be changed by creating
 * a java properties file called DatabaseUtils.props in user.home or
 * the current directory. eg:<p>
 *
 * <code><pre>
 * jdbcDriver=jdbc.idbDriver
 * jdbcURL=jdbc:idb=experiments.prp
 * </pre></code><p>
 *
 * @author Len Trigg (trigg@cs.waikato.ac.nz)
 * @version $Revision: 5240 $
 */
public class DatabaseUtils
  implements Serializable {

  /** for serialization. */
  static final long serialVersionUID = -8252351994547116729L;
  
  /** The name of the table containing the index to experiments. */
  public static final String EXP_INDEX_TABLE = "Experiment_index";

  /** The name of the column containing the experiment type (ResultProducer). */
  public static final String EXP_TYPE_COL = "Experiment_type";

  /** The name of the column containing the experiment setup (parameters). */
  public static final String EXP_SETUP_COL = "Experiment_setup";
  
  /** The name of the column containing the results table name. */
  public static final String EXP_RESULT_COL = "Result_table";

  /** The prefix for result table names. */
  public static final String EXP_RESULT_PREFIX = "Results";

  /** The name of the properties file. */
  public final static String PROPERTY_FILE = "DatabaseUtils.props";

  /** Holds the jdbc drivers to be used (only to stop them being gc'ed). */
  protected Vector DRIVERS = new Vector();

  /** keeping track of drivers that couldn't be loaded. */
  protected static Vector DRIVERS_ERRORS;

  /** Properties associated with the database connection. */
  protected Properties PROPERTIES;

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
  public static final int TIME = 10; 
  
  /** Database URL. */
  protected String m_DatabaseURL;
 
  /** The prepared statement used for database queries. */
  protected transient PreparedStatement m_PreparedStatement;
   
  /** The database connection. */
  protected transient Connection m_Connection;

  /** True if debugging output should be printed. */
  protected boolean m_Debug = false;
  
  /** Database username. */
  protected String m_userName = "";

  /** Database Password. */
  protected String m_password = "";

  /* mappings used for creating Tables. Can be overridden in DatabaseUtils.props*/
  /** string type for the create table statement. */
  protected String m_stringType = "LONGVARCHAR";
  /** integer type for the create table statement. */
  protected String m_intType = "INT";
  /** double type for the create table statement. */
  protected String m_doubleType = "DOUBLE";

  /** For databases where Tables and Columns are created in upper case. */
  protected boolean m_checkForUpperCaseNames = false;

  /** For databases where Tables and Columns are created in lower case. */
  protected boolean m_checkForLowerCaseNames = false;

  /** setAutoCommit on the database? */
  protected boolean m_setAutoCommit = true;

  /** create index on the database? */
  protected boolean m_createIndex = false;

  /** the keywords for the current database type. */
  protected HashSet<String> m_Keywords = new HashSet<String>();

  /** the character to mask SQL keywords (by appending this character). */
  protected String m_KeywordsMaskChar = "_";
  
  /**
   * Reads properties and sets up the database drivers.
   *
   * @throws Exception 	if an error occurs
   */
  public DatabaseUtils(String propertyFile) throws Exception {
    if (DRIVERS_ERRORS == null)
      DRIVERS_ERRORS = new Vector();

    try {
      PROPERTIES = SQLUtils.readProperties(propertyFile);

      // Register the drivers in jdbc DriverManager
      String drivers = PROPERTIES.getProperty("jdbcDriver", "jdbc.idbDriver");

      if (drivers == null) {
        throw new Exception("No database drivers (JDBC) specified");
      }
      // The call to newInstance() is necessary on some platforms
      // (with some java VM implementations)
      StringTokenizer st = new StringTokenizer(drivers, ", ");
      while (st.hasMoreTokens()) {
        String driver = st.nextToken();
        boolean result;
        try {
          Class.forName(driver);
          DRIVERS.addElement(driver);
          result = true;
        }
        catch (Exception e) {
          result = false;
        }
        if (m_Debug || (!result && !DRIVERS_ERRORS.contains(driver))) 
          System.err.println(
              "Trying to add database driver (JDBC): " + driver 
              + " - " + (result ? "Success!" : "Error, not in CLASSPATH?"));
        if (!result)
          DRIVERS_ERRORS.add(driver);
      }
    } catch (Exception ex) {
      System.err.println("Problem reading properties. Fix before continuing.");
      System.err.println(ex);
    }
    
    m_DatabaseURL = PROPERTIES.getProperty("jdbcURL");
    m_stringType  = PROPERTIES.getProperty("CREATE_STRING", "LONGVARCHAR");
    m_intType     = PROPERTIES.getProperty("CREATE_INT", "INT");
    m_doubleType  = PROPERTIES.getProperty("CREATE_DOUBLE", "DOUBLE");
    m_checkForUpperCaseNames = PROPERTIES.getProperty(
				"checkUpperCaseNames", "false").equals("true");
    m_checkForLowerCaseNames = PROPERTIES.getProperty(
				"checkLowerCaseNames", "false").equals("true");
    m_setAutoCommit = PROPERTIES.getProperty(
			"setAutoCommit", "false").equals("true");
    m_createIndex   = PROPERTIES.getProperty(
			"createIndex", "false").equals("true");
    setKeywords(PROPERTIES.getProperty(
			"Keywords", "AND,ASC,BY,DESC,FROM,GROUP,INSERT,ORDER,SELECT,UPDATE,WHERE"));
    setKeywordsMaskChar(PROPERTIES.getProperty("KeywordsMaskChar", "_"));
  }
  
  /** 
   * returns key column headings in their original case. Used for
   * those databases that create uppercase column names.
   * 
   * @param columnName	the column to retrieve the original case for
   * @return		the original case
   */
  protected String attributeCaseFix(String columnName){
    if (m_checkForUpperCaseNames) {
      String ucname = columnName.toUpperCase();
      if (ucname.equals(EXP_TYPE_COL.toUpperCase())) {
	return EXP_TYPE_COL;
      } else if (ucname.equals(EXP_SETUP_COL.toUpperCase())) {
	return EXP_SETUP_COL;
      } else if (ucname.equals(EXP_RESULT_COL.toUpperCase())) {
	return EXP_RESULT_COL;
      } else {
	return columnName;
      }
    }
    else if (m_checkForLowerCaseNames) {
      String ucname = columnName.toLowerCase();
      if (ucname.equals(EXP_TYPE_COL.toLowerCase())) {
	return EXP_TYPE_COL;
      } else if (ucname.equals(EXP_SETUP_COL.toLowerCase())) {
	return EXP_SETUP_COL;
      } else if (ucname.equals(EXP_RESULT_COL.toLowerCase())) {
	return EXP_RESULT_COL;
      } else {
	return columnName;
      }
    }
    else {
      return columnName;
    }
  }
 
  /**
   * translates the column data type string to an integer value that indicates
   * which data type / get()-Method to use in order to retrieve values from the
   * database (see DatabaseUtils.Properties, InstanceQuery()). Blanks in the type 
   * are replaced with underscores "_", since Java property names can't contain blanks.
   * 
   * @param type 	the column type as retrieved with 
   * 			java.sql.MetaData.getColumnTypeName(int)
   * @return 		an integer value that indicates
   * 			which data type / get()-Method to use in order to 
   * 			retrieve values from the
   */
  public int translateDBColumnType(String type) {
    try {
      // Oracle, e.g., has datatypes like "DOUBLE PRECISION"
      // BUT property names can't have blanks in the name (unless escaped with
      // a backslash), hence also check for names where the blanks are 
      // replaced with underscores "_":
      String value = PROPERTIES.getProperty(type);
      String typeUnderscore = type.replaceAll(" ", "_");
      if (value == null)
	value = PROPERTIES.getProperty(typeUnderscore);
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
	  "Unknown data type: " + type + ". "
	  + "Add entry in " + PROPERTY_FILE + ".\n"
	  + "If the type contains blanks, either escape them with a backslash "
	  + "or use underscores instead of blanks.");
    }
  }

  /**
   * Converts an array of objects to a string by inserting a space
   * between each element. Null elements are printed as ?
   *
   * @param array 	the array of objects
   * @return 		a value of type 'String'
   */
  public static String arrayToString(Object[] array) {
    String result = "";
    if (array == null) {
      result = "<null>";
    } else {
      for (int i = 0; i < array.length; i++) {
	if (array[i] == null) {
	  result += " ?";
	} else {
	  result += " " + array[i];
	}
      }
    }
    return result;
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

  /**
   * Returns the tip text for this property.
   * 
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String databaseURLTipText() {
    return "Set the URL to the database.";
  }

  /**
   * Get the value of DatabaseURL.
   *
   * @return 		Value of DatabaseURL.
   */
  public String getDatabaseURL() {
    return m_DatabaseURL;
  }
  
  /**
   * Set the value of DatabaseURL.
   *
   * @param newDatabaseURL 	Value to assign to DatabaseURL.
   */
  public void setDatabaseURL(String newDatabaseURL) {
    m_DatabaseURL = newDatabaseURL;
  }

  /**
   * Returns the tip text for this property.
   * 
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String debugTipText() {
    return "Whether debug information is printed.";
  }
  
  /**
   * Sets whether there should be printed some debugging output to stderr or not.
   * 
   * @param d 		true if output should be printed
   */
  public void setDebug(boolean d) {
    m_Debug = d;
  }

  /**
   * Gets whether there should be printed some debugging output to stderr or not.
   * 
   * @return 		true if output should be printed
   */
  public boolean getDebug() {
    return m_Debug;
  }

  /**
   * Returns the tip text for this property.
   * 
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String usernameTipText() {
    return "The user to use for connecting to the database.";
  }

  /** 
   * Set the database username.
   *
   * @param username 	Username for Database.
   */
  public void setUsername(String username){
    m_userName = username; 
  }
  
  /** 
   * Get the database username.
   *
   * @return 		Database username
   */
  public String getUsername(){
    return m_userName;
  }

  /**
   * Returns the tip text for this property.
   * 
   * @return 		tip text for this property suitable for
   * 			displaying in the explorer/experimenter gui
   */
  public String passwordTipText() {
    return "The password to use for connecting to the database.";
  }

  /** 
   * Set the database password.
   *
   * @param password 	Password for Database.
   */
  public void setPassword(String password){
    m_password = password;
  }
  
  /** 
   * Get the database password.
   *
   * @return  		Password for Database.
   */
  public String getPassword(){
    return m_password;
  }

  /**
   * Opens a connection to the database.
   *
   * @throws Exception 	if an error occurs
   */
  public void connectToDatabase() throws Exception {
    if (m_Debug) {
      System.err.println("Connecting to " + m_DatabaseURL);
    }
    if (m_Connection == null) {
      if (m_userName.equals("")) {
	try {
	  m_Connection = DriverManager.getConnection(m_DatabaseURL);
	} catch (java.sql.SQLException e) {
	  
	  // Try loading the drivers
	  for (int i = 0; i < DRIVERS.size(); i++) {
	    try {
	      Class.forName((String)DRIVERS.elementAt(i));
	    } catch (Exception ex) {
	      // Drop through
	    }
	  }
	  m_Connection = DriverManager.getConnection(m_DatabaseURL);
	}
      } else {
	try {
	  m_Connection = DriverManager.getConnection(m_DatabaseURL, m_userName,
						     m_password);
	} catch (java.sql.SQLException e) {
	  
	  // Try loading the drivers
	  for (int i = 0; i < DRIVERS.size(); i++) {
	    try {
	      Class.forName((String)DRIVERS.elementAt(i));
	    } catch (Exception ex) {
	      // Drop through
	    }
	  }
	  m_Connection = DriverManager.getConnection(m_DatabaseURL, m_userName,
						     m_password);
	}
      }
    }
    m_Connection.setAutoCommit(m_setAutoCommit);
  }

  /**
   * Closes the connection to the database.
   *
   * @throws Exception 	if an error occurs
   */
  public void disconnectFromDatabase() throws Exception {
    if (m_Debug) {
      System.err.println("Disconnecting from " + m_DatabaseURL);
    }
    if (m_Connection != null) {
      m_Connection.close();
      m_Connection = null;
    }
  }
  
  /**
   * Returns true if a database connection is active.
   *
   * @return 		a value of type 'boolean'
   */
  public boolean isConnected() {
    return (m_Connection != null);
  }

  /**
   * Returns whether the cursors only support forward movement or are
   * scroll sensitive (with ResultSet.CONCUR_READ_ONLY concurrency).
   * Returns always false if not connected
   * 
   * @return		true if connected and the cursor is scroll-sensitive
   * @see		ResultSet#TYPE_SCROLL_SENSITIVE
   * @see		ResultSet#TYPE_FORWARD_ONLY
   * @see		ResultSet#CONCUR_READ_ONLY
   */
  public boolean isCursorScrollSensitive() {
    boolean	result;
    
    result = false;
    
    try {
      if (isConnected())
	result = m_Connection.getMetaData().supportsResultSetConcurrency(
	    		ResultSet.TYPE_SCROLL_SENSITIVE, 
	    		ResultSet.CONCUR_READ_ONLY);
    }
    catch (Exception e) {
      // ignored
    }
    
    return result;
  }
  
  /**
   * Checks whether cursors are scrollable in general, false otherwise 
   * (also if not connected).
   * 
   * @return		true if scrollable and connected
   * @see		#getSupportedCursorScrollType()
   */
  public boolean isCursorScrollable() {
    return (getSupportedCursorScrollType() != -1);
  }
  
  /**
   * Returns the type of scrolling that the cursor supports, -1 if not
   * supported or not connected. Checks first for TYPE_SCROLL_SENSITIVE
   * and then for TYPE_SCROLL_INSENSITIVE. In both cases CONCUR_READ_ONLY
   * as concurrency is used.
   * 
   * @return		the scroll type, or -1 if not connected or no scrolling supported
   * @see		ResultSet#TYPE_SCROLL_SENSITIVE
   * @see		ResultSet#TYPE_SCROLL_INSENSITIVE
   */
  public int getSupportedCursorScrollType() {
    int		result;
    
    result = -1;
    
    try {
      if (isConnected()) {
	if (m_Connection.getMetaData().supportsResultSetConcurrency(
	    		ResultSet.TYPE_SCROLL_SENSITIVE, 
	    		ResultSet.CONCUR_READ_ONLY))
	  result = ResultSet.TYPE_SCROLL_SENSITIVE;
	
	if (result == -1) {
	  if (m_Connection.getMetaData().supportsResultSetConcurrency(
	      		ResultSet.TYPE_SCROLL_INSENSITIVE, 
	      		ResultSet.CONCUR_READ_ONLY))
	    result = ResultSet.TYPE_SCROLL_INSENSITIVE;
	}
      }
    }
    catch (Exception e) {
      // ignored
    }
    
    return result;
  }

  /**
   * Executes a SQL query. Caller must clean up manually with 
   * <code>close()</code>.
   *
   * @param query 	the SQL query
   * @return 		true if the query generated results
   * @throws SQLException if an error occurs
   * @see #close()
   */
  public boolean execute(String query) throws SQLException {
    if (!isConnected())
      throw new IllegalStateException("Not connected, please connect first!");
    
    if (!isCursorScrollable())
      m_PreparedStatement = m_Connection.prepareStatement(
	  query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    else
      m_PreparedStatement = m_Connection.prepareStatement(
	  query, getSupportedCursorScrollType(), ResultSet.CONCUR_READ_ONLY);
    
    return(m_PreparedStatement.execute());
  }

  /**
   * Gets the results generated by a previous query. Caller must clean up 
   * manually with <code>close(ResultSet)</code>. Returns null if object has
   * been deserialized.
   *
   * @return 		the result set.
   * @throws SQLException if an error occurs
   * @see #close(ResultSet)
   */
  public ResultSet getResultSet() throws SQLException {
    if (m_PreparedStatement != null)
      return m_PreparedStatement.getResultSet();
    else
      return null;
  }

  /**
   * Executes a SQL DDL query or an INSERT, DELETE or UPDATE.
   *
   * @param query 	the SQL DDL query
   * @return 		the number of affected rows
   * @throws SQLException if an error occurs
   */
  public int update(String query) throws SQLException {
    if (!isConnected())
      throw new IllegalStateException("Not connected, please connect first!");
    
    Statement statement;
    if (!isCursorScrollable())
      statement = m_Connection.createStatement(
	  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    else
      statement = m_Connection.createStatement(
	  getSupportedCursorScrollType(), ResultSet.CONCUR_READ_ONLY);
    int result = statement.executeUpdate(query);
    statement.close();
    
    return result;
  }

  /**
   * Executes a SQL SELECT query that returns a ResultSet. Note: the ResultSet
   * object must be closed by the caller.
   *
   * @param query 	the SQL query
   * @return 		the generated ResultSet
   * @throws SQLException if an error occurs
   */
  public ResultSet select(String query) throws SQLException {
    if (!isConnected())
      throw new IllegalStateException("Not connected, please connect first!");
    
    Statement statement;
    if (!isCursorScrollable())
      statement = m_Connection.createStatement(
	  ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    else
      statement = m_Connection.createStatement(
	  getSupportedCursorScrollType(), ResultSet.CONCUR_READ_ONLY);
    ResultSet result = statement.executeQuery(query);
    
    return result;
  }

  /**
   * closes the ResultSet and the statement that generated the ResultSet to
   * avoid memory leaks in JDBC drivers - in contrast to the JDBC specs, a lot
   * of JDBC drives don't clean up correctly.
   * 
   * @param rs		the ResultSet to clean up
   */
  public void close(ResultSet rs) {
    try {
      Statement statement = rs.getStatement();
      rs.close();
      statement.close();
      statement = null;
      rs = null;
    }
    catch (Exception e) {
      // ignored
    }
  }
  
  /**
   * closes the m_PreparedStatement to avoid memory leaks.
   */
  public void close() {
    if (m_PreparedStatement != null) {
      try {
	m_PreparedStatement.close();
	m_PreparedStatement = null;
      }
      catch (Exception e) {
	// ignored
      }
    }
  }
  
  /**
   * Checks that a given table exists.
   *
   * @param tableName 	the name of the table to look for.
   * @return 		true if the table exists.
   * @throws Exception 	if an error occurs.
   */
  public boolean tableExists(String tableName) throws Exception {
    if (!isConnected())
      throw new IllegalStateException("Not connected, please connect first!");
    
    if (m_Debug) {
      System.err.println("Checking if table " + tableName + " exists...");
    }
    DatabaseMetaData dbmd = m_Connection.getMetaData();
    ResultSet rs;
    if (m_checkForUpperCaseNames) {
      rs = dbmd.getTables (null, null, tableName.toUpperCase(), null);
    } else if (m_checkForLowerCaseNames) {
      rs = dbmd.getTables (null, null, tableName.toLowerCase(), null);
    } else {
      rs = dbmd.getTables (null, null, tableName, null);
    }
    boolean tableExists = rs.next();
    if (rs.next()) {
      throw new Exception("This table seems to exist more than once!");
    }
    rs.close();
    if (m_Debug) {
      if (tableExists) {
	System.err.println("... " + tableName + " exists");
      } else {
	System.err.println("... " + tableName + " does not exist");
      }
    }
    return tableExists;
  }

  /**
   * processes the string in such a way that it can be stored in the
   * database, i.e., it changes backslashes into slashes and doubles single 
   * quotes.
   * 
   * @param s		the string to work on
   * @return		the processed string
   */
  public static String processKeyString(String s) {
    return s.replaceAll("\\\\", "/").replaceAll("'", "''");
  }
  

  /**
   * Executes a database query to insert a result for the supplied key
   * into the database.           
   *
   * @param tableName 	the name of the table where the result is stored
   * @param rp 		the ResultProducer who will generate the result if 
   * 			required
   * @param key 	the key for the result
   * @param result 	the result to store
   * @throws Exception 	if an error occurs
   */
  public void putResultInTable(String tableName,
			       Object [] key,
			       Object [] result)
    throws Exception {
    
    String query = "INSERT INTO " + tableName
      + " VALUES ( ";
    // Add the results to the table
    for (int i = 0; i < key.length; i++) {
      if (i != 0) {
	query += ',';
      }
      if (key[i] != null) {
	if (key[i] instanceof String) {
	  query += "'" + processKeyString(key[i].toString()) + "'";
	} else if (key[i] instanceof Double) {
	  query += safeDoubleToString((Double)key[i]);
	} else {
	  query += key[i].toString();
	}
      } else {
	query += "NULL";
      }
    }
    for (int i = 0; i < result.length; i++) {
      query +=  ',';
      if (result[i] != null) {
	if (result[i] instanceof String) {
	  query += "'" + result[i].toString() + "'";
	} else  if (result[i] instanceof Double) {
	  query += safeDoubleToString((Double)result[i]);
	} else {
	  query += result[i].toString();
	  //!!
	  //System.err.println("res: "+ result[i].toString());
	}
      } else {
	query += "NULL";
      }
    }
    query += ')';
    
    if (m_Debug) {
      System.err.println("Submitting result: " + query);
    }
    update(query);
    close();
  }
  
  /**
   * Inserts a + if the double is in scientific notation.
   * MySQL doesn't understand the number otherwise.
   * 
   * @param number	the number to convert
   * @return		the number as string
   */
  private String safeDoubleToString(Double number) {
    // NaN is treated as NULL
    if (number.isNaN())
      return "NULL";

    String orig = number.toString();

    int pos = orig.indexOf('E');
    if ((pos == -1) || (orig.charAt(pos + 1) == '-')) {
      return orig;
    } else {
      StringBuffer buff = new StringBuffer(orig);
      buff.insert(pos + 1, '+');
      return new String(buff);
    }
  }

  /**
   * Returns true if the experiment index exists.
   *
   * @return 		true if the index exists
   * @throws Exception 	if an error occurs
   */
  public boolean experimentIndexExists() throws Exception {
    return tableExists(EXP_INDEX_TABLE);
  }
 

  /**
   * Gets the name of the experiment table that stores results from a
   * particular ResultProducer.
   *
   * @param rp 		the ResultProducer
   * @return 		the name of the table where the results for this 
   * 			ResultProducer are stored, or null if there is no 
   * 			table for this ResultProducer.
   * @throws Exception 	if an error occurs
   */
  
  
  /**
   * Sets the keywords (comma-separated list) to use.
   * 
   * @param value	the list of keywords
   */
  public void setKeywords(String value) {
    String[] 	keywords;
    int		i;
    
    m_Keywords.clear();
    
    keywords = value.replaceAll(" ", "").split(",");
    for (i = 0; i < keywords.length; i++)
      m_Keywords.add(keywords[i].toUpperCase());
  }
  
  /**
   * Returns the currently stored keywords (as comma-separated list).
   * 
   * @return		the list of keywords
   */
  public String getKeywords() {
    String		result;
    Vector<String>	list;
    int			i;
    
    list = new Vector<String>(m_Keywords);
    Collections.sort(list);
    
    result = "";
    for (i = 0; i < list.size(); i++) {
      if (i > 0)
	result += ",";
      result += list.get(i);
    }
    
    return result;
  }
  
  /**
   * Sets the mask character to append to table or attribute names that
   * are a reserved keyword.
   * 
   * @param value	the new character
   */
  public void setKeywordsMaskChar(String value) {
    m_KeywordsMaskChar = value;
  }
  
  /**
   * Returns the currently set mask character.
   * 
   * @return		the character
   */
  public String getKeywordsMaskChar() {
    return m_KeywordsMaskChar;
  }
  
  /**
   * Checks whether the given string is a reserved keyword.
   * 
   * @param s		the string to check
   * @return		true if the string is a keyword
   * @see		#m_Keywords
   */
  public boolean isKeyword(String s) {
    return m_Keywords.contains(s.toUpperCase());
  }

}
