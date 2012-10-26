/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.dbmanager;

import arida.ufc.br.moap.core.database.spi.ConnectionProperty;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.gephi.data.attributes.AbstractAttributeModel;
import org.gephi.data.attributes.AttributeTableImpl;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.openide.util.Lookup;

/**
 *
 * @author igobrilhante
 */
public class DBManager implements Serializable {

    private ConnectionProperty cm;
    private Connection connection;
    private static DBManager instance;
    public final int COMMIT_LIMIT = 100;
    private final Logger logger = Logger.getLogger(DBManager.class);

    private DBManager() throws SQLException, IOException, ClassNotFoundException {
        this.cm = ConnectionProperty.getInstance();
        this.connection = cm.getConnection();
        logger.info("Connected to "+this.cm.getUrl()+" as "+this.cm.getUsername());
        //this.connection.setAutoCommit(false);
        
    }

    public static DBManager getInstance() throws SQLException, IOException, ClassNotFoundException{
        if(instance==null){
            return new DBManager();
        }
        return instance;
    }
    public Connection getConnection() {
        return this.connection;
    }

    public ConnectionProperty getConnectionDBManager(){
        return this.cm;
    }
    
    public void close() throws SQLException{
        this.connection.close();
    }
    public synchronized void createTable(String table_name, String attributes) throws Exception,SQLException {
        logger.info("Creating table " + table_name);
        Statement state = connection.createStatement();
        String query = "SELECT COUNT(*) count FROM pg_stat_user_tables WHERE schemaname='public' and relname = '" + table_name + "'";
        ResultSet result = state.executeQuery(query);
        int update;
        //System.out.println(query);
        result.next();
        if (result.getInt(1) > 0) {
            query = "DROP TABLE " + table_name;
            update = state.executeUpdate(query);
            //System.out.println("Drop table result: "+update); 
        }
        query = "drop table if exists "+table_name+"; CREATE TABLE " + table_name + " (" + attributes + ");";


        update = state.executeUpdate(query);
        //System.out.println("Create table result: "+update);
        state.close();
//        connection.commit();
    }

    public synchronized void createTable(String table_name, String attributes, boolean replace) {
    }
    
    public synchronized boolean tableExists(String table) throws SQLException{
        Statement state = connection.createStatement();
        String q = "SELECT COUNT(*) count FROM pg_stat_user_tables WHERE schemaname='public' and relname = '" + table + "'";
        ResultSet result = state.executeQuery(q);
        int update;
        //System.out.println(query);
        result.next();
        int r = result.getInt(1);
        result.close();
        state.close();
        if ( r == 0)
            return false;
        return true;
    }
     public synchronized void createTableAsQuery(String table_name, String query) throws Exception,SQLException {
        logger.info("Creating table " + table_name);
        Statement state = connection.createStatement();
        String q = "SELECT COUNT(*) count FROM pg_stat_user_tables WHERE schemaname='public' and relname = '" + table_name + "'";
        ResultSet result = state.executeQuery(q);
        int update;
        //System.out.println(query);
        result.next();
        if (result.getInt(1) > 0) {
            q = "DROP TABLE " + table_name;
            update = state.executeUpdate(q);
            //System.out.println("Drop table result: "+update); 
        }
        q = "CREATE TABLE " + table_name + " as " +query;


        update = state.executeUpdate(q);
        //System.out.println("Create table result: "+update);
        state.close();
//        connection.commit();
    }
     
         public synchronized void createTableAsQuery(String table_name, String query,String index) throws Exception,SQLException {
        logger.info("Creating table " + table_name);
        Statement state = connection.createStatement();
        String q = "SELECT COUNT(*) count FROM pg_stat_user_tables WHERE schemaname='public' and relname = '" + table_name + "'";
        ResultSet result = state.executeQuery(q);
        int update;
        //System.out.println(query);
        result.next();
        if (result.getInt(1) > 0) {
            q = "DROP TABLE " + table_name;
            update = state.executeUpdate(q);
            //System.out.println("Drop table result: "+update); 
        }
        q = "CREATE TABLE " + table_name + " as " +query;


        update = state.executeUpdate(q);
        //System.out.println("Create table result: "+update);
        if(!index.equals(""))
            state.execute("ALTER  TABLE "+table_name+" ADD PRIMARY KEY ("+index+")");
//        connection.commit();
        state.close();
    }  

    public synchronized void addColumn(String table_name, String attribute, String type) throws Exception {
        
        Statement state = connection.createStatement();
        String query = "SELECT count(*) FROM information_schema.columns WHERE table_name = '"+table_name+"' and column_name='"+attribute+"'";
        ResultSet rs = state.executeQuery(query);
        int count = 0;
        while(rs.next()){
            count = rs.getInt(1);
        }
        if(count>0){
            query = "ALTER TABLE " + table_name + " DROP COLUMN " + attribute;
            state.executeUpdate(query);
        }
        query = "ALTER TABLE " + table_name + " ADD COLUMN " + attribute + " " + type;
        System.out.println(query);
        state.executeUpdate(query);
        
        
        state.close();
//        connection.commit();
    }
    


    public synchronized String prepareStatement(int atts, String table) {

        if (atts > 0) {
            String statement = "INSERT INTO " + table + " VALUES (";
            for (int i = 0; i < atts; i++) {
                if (i == atts - 1) {
                    statement += "?";
                }
                else {
                    statement += "?,";
                }
            }
            statement += ")";
            return statement;
        }
        throw new RuntimeException("Number of attributes is invalid");

    }

    private void storeAttributes() {
    }

    public synchronized String getType(AttributeType type) {
        switch (type) {
            case STRING:
                return "text";
            case INT:
                return "integer";
            case DOUBLE:
                return "numeric";
            case FLOAT:
                return "numeric";
            case LONG:
                return "bigint";
            default:
                return null;
        }
    }

    public synchronized  AttributeType getAttributeType(String type) {
        if (type.equalsIgnoreCase("text")) {
            return AttributeType.STRING;
        }
        if (type.equalsIgnoreCase("integer")) {
            return AttributeType.LONG;
        }
        if (type.equalsIgnoreCase("int4")) {
            return AttributeType.LONG;
        }
        if (type.equalsIgnoreCase("numeric")) {
            return AttributeType.FLOAT;
        }
        throw new RuntimeException("Type " + type + " has not been found");
    }

    public int getNumberOfColumns(AttributeTable table) {
        int count = 0;
        for (AttributeColumn c : table.getColumns()) {
            if (!c.getOrigin().equals(AttributeOrigin.PROPERTY)) {
                count++;
            }
        }
        System.out.println(count);
        return count;
    }

    public synchronized AttributeTableImpl getTableColumns(ResultSet res, String name) throws SQLException {
        AbstractAttributeModel model = (AbstractAttributeModel) Lookup.getDefault().lookup(AttributeController.class).getModel();
        
        AttributeTableImpl table = new AttributeTableImpl(model, name);
        
        ResultSetMetaData metadata = res.getMetaData();
        for (int i = 0; i < metadata.getColumnCount(); i++) {
            AttributeType type = getAttributeType(metadata.getColumnTypeName(i+1));
            
            table.addColumn(metadata.getColumnName(i+1), type);
        }
        
        return table;
    }
    
    public synchronized Object[] getColumnNames(ResultSet res) throws SQLException{
        ResultSetMetaData metadata = res.getMetaData();
        Object[] columnNames = new Object[metadata.getColumnCount()];
        for (int i = 0; i < metadata.getColumnCount(); i++) {
            
            columnNames[i] = (String)metadata.getColumnLabel(i+1);
            
        }
        return columnNames;
    }

    public synchronized String mergeStrings(List<String> strings){
        String output = "";
        
        for(int i=0;i<strings.size();i++){
            if(i==0)
                output += "'"+strings.get(i) +"'";
            else
                output += ",'"+strings.get(i) +"'";
        }
        
        return output;
    }
    
        public synchronized String mergeIntegers(List<Integer> integers){
        String output = "";
        
        for(int i=0;i<integers.size();i++){
            if(i==0)
                output += integers.get(i);
            else
                output += ","+integers.get(i);
        }
        
        return output;
    }
        
    public synchronized List<String> getTables() throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("SELECT relname FROM pg_stat_user_tables WHERE schemaname='public'");
        while(rs.next()){
            list.add(rs.getString("relname"));
        }
        rs.close();
        s.close();
        return list;
    }
    
    public synchronized void dropTable(String table) throws SQLException{
        Statement s = connection.createStatement();
        s.executeUpdate("DROP TABLE IF EXISTS "+table);
        s.close();
    }
    

    
}
