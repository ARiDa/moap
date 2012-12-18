/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.database.spi.*;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author igobrilhante
 */
//@ServiceProvider(service=Database.class)
public abstract class PostgresqlProvider extends AbstractDatabase implements Serializable {

//	private static final int SRID = Integer.parseInt(PostgisProperties.getInstance().getType("srid"));
    private static final long serialVersionUID = -85997221180839532L;
//    private ConnectionProperty cm;
//    private Connection connection;
//    private static PostgresqlBuilder instance;
    public final int COMMIT_LIMIT = 100;
    private final Logger logger = Logger.getLogger(PostgresqlProvider.class);

    public PostgresqlProvider() {
        System.out.println("a");
    }

//    private PostgresqlBuilder(){
//    	instance = new PostgresqlBuilder();     
//    }
//    public static PostgresqlBuilder getInstance(){
//        if(instance==null){
//            return new PostgresqlBuilder();
//        }
//        return (PostgresqlBuilder) instance;
//    }
//    public Connection getConnection() {
//        return this.connection;
//    }
//    public ConnectionProperty getConnectionDBManager(){
//        return this.cm;
//    }
    public void close() throws SQLException {
        this.connection.close();
    }

    public synchronized void createTable(String table_name, String attributes) throws Exception, SQLException {
        logger.info("Creating table " + table_name);
        Statement state = connection.createStatement();
        String query = "SELECT COUNT(*) count FROM pg_stat_user_tables WHERE schemaname='public' and relname = '" + table_name + "'";
        ResultSet result = state.executeQuery(query);
        int update;
        //System.out.println(query);
        result.next();
        if (result.getInt(1) > 0) {
            //System.out.println("Drop table result: "+update); 
        }
        query = "DROP TABLE if exists " + table_name;
        update = state.executeUpdate(query);
        query = "CREATE TABLE " + table_name + " (" + attributes + ")";


        update = state.executeUpdate(query);
        //System.out.println("Create table result: "+update);
        state.close();
//        connection.commit();
    }

    public synchronized void createTable(String table_name, String attributes, boolean replace) {
    }

    public synchronized boolean tableExists(String table) throws SQLException {
        Statement state = connection.createStatement();
        String q = "SELECT COUNT(*) count FROM pg_stat_user_tables WHERE schemaname='public' and relname = '" + table + "'";
        ResultSet result = state.executeQuery(q);
        int update;
        //System.out.println(query);
        result.next();
        int r = result.getInt(1);
        result.close();
        state.close();
        if (r == 0) {
            return false;
        }
        return true;
    }

    public synchronized void createTableAsQuery(String table_name, String query) throws Exception, SQLException {
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
        q = "DROP TABLE IF EXISTS " + table_name + "; CREATE TABLE " + table_name + " as " + query;



        update = state.executeUpdate(q);
        //System.out.println("Create table result: "+update);
        state.close();
//        connection.commit();
    }

    public synchronized void createSpatialIndex(String table, String index, String attribute) {
        try {
            logger.info("Create Index");
            // CREATE INDEX [indexname] ON [tablename] USING GIST ( [geometrycolumn] );
            String q = "CREATE INDEX " + index + " ON " + table + " USING GIST (" + attribute + ") ";

            if (tableExists(table)) {
                Statement stat = connection.createStatement();
                stat.executeUpdate(q);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public synchronized void createTableAsQuery(String table_name, String query, String index) throws Exception, SQLException {
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
        q = "CREATE TABLE " + table_name + " as " + query;


        update = state.executeUpdate(q);
        //System.out.println("Create table result: "+update);
        if (!index.equals("")) {
            state.execute("ALTER  TABLE " + table_name + " ADD PRIMARY KEY (" + index + ")");
        }
//        connection.commit();
        state.close();
    }

    public synchronized void addColumn(String table_name, String attribute, String type) throws Exception {

        Statement state = connection.createStatement();
        String query = "SELECT count(*) FROM information_schema.columns WHERE table_name = '" + table_name + "' and column_name='" + attribute + "'";
        ResultSet rs = state.executeQuery(query);
        int count = 0;
        while (rs.next()) {
            count = rs.getInt(1);
        }
        if (count > 0) {
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
                } else {
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

//    public synchronized String getType(AttributeType type) {
//        switch (type) {
//            case STRING:
//                return "text";
//            case INT:
//                return "integer";
//            case DOUBLE:
//                return "numeric";
//            case FLOAT:
//                return "numeric";
//            case LONG:
//                return "bigint";
//            default:
//                return null;
//        }
//    }
//
//    public synchronized  AttributeType getAttributeType(String type) {
//        if (type.equalsIgnoreCase("text")) {
//            return AttributeType.STRING;
//        }
//        if (type.equalsIgnoreCase("integer")) {
//            return AttributeType.LONG;
//        }
//        if (type.equalsIgnoreCase("int4")) {
//            return AttributeType.LONG;
//        }
//        if (type.equalsIgnoreCase("numeric")) {
//            return AttributeType.FLOAT;
//        }
//        throw new RuntimeException("Type " + type + " has not been found");
//    }
//
//    public int getNumberOfColumns(AttributeTable table) {
//        int count = 0;
//        for (AttributeColumn c : table.getColumns()) {
//            if (!c.getOrigin().equals(AttributeOrigin.PROPERTY)) {
//                count++;
//            }
//        }
//        System.out.println(count);
//        return count;
//    }
//
//    public synchronized AttributeTableImpl getTableColumns(ResultSet res, String name) throws SQLException {
//        AbstractAttributeModel model = (AbstractAttributeModel) Lookup.getDefault().lookup(AttributeController.class).getModel();
//        
//        AttributeTableImpl table = new AttributeTableImpl(model, name);
//        
//        ResultSetMetaData metadata = res.getMetaData();
//        for (int i = 0; i < metadata.getColumnCount(); i++) {
//            AttributeType type = getAttributeType(metadata.getColumnTypeName(i+1));
//            
//            table.addColumn(metadata.getColumnName(i+1), type);
//        }
//        
//        return table;
//    }
    public synchronized Object[] getColumnNames(ResultSet res) throws SQLException {
        ResultSetMetaData metadata = res.getMetaData();
        Object[] columnNames = new Object[metadata.getColumnCount()];
        for (int i = 0; i < metadata.getColumnCount(); i++) {

            columnNames[i] = metadata.getColumnLabel(i + 1);

        }
        return columnNames;
    }

    public synchronized String mergeStrings(List<String> strings) {
        String output = "";

        for (int i = 0; i < strings.size(); i++) {
            if (i == 0) {
                output += "'" + strings.get(i) + "'";
            } else {
                output += ",'" + strings.get(i) + "'";
            }
        }

        return output;
    }

    public synchronized String mergeIntegers(List<Integer> integers) {
        String output = "";

        for (int i = 0; i < integers.size(); i++) {
            if (i == 0) {
                output += integers.get(i);
            } else {
                output += "," + integers.get(i);
            }
        }

        return output;
    }

    public synchronized List<String> getTables() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();

        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery("SELECT relname FROM pg_stat_user_tables WHERE schemaname='public'");
        while (rs.next()) {
            list.add(rs.getString("relname"));
        }
        rs.close();
        s.close();
        return list;
    }

    public synchronized void dropTable(String table) throws SQLException {
        Statement s = connection.createStatement();
        s.executeUpdate("DROP TABLE IF EXISTS " + table);
        s.close();
    }

    // Retrieve object/table from the database
    @Override
    public Object getObject(String name) {
        // TODO Auto-generated method stub
        return null;
    }

//	@Override
//	public void setObject(String name,Object object) {
//		// TODO Auto-generated method stub
//		Class c;
//		
//		if(object instanceof Collection){
//			Collection collection = (Collection)object;
//			Object o = collection.iterator().next().getClass();
//			
//			if( o instanceof LatLonPoint){
//				if(o instanceof TimeStampedPoint){
//					Postgis.toPGPoint((TimeStampedPoint)o, SRID);
//				}
//				// create postgis point
//				else{
//					Postgis.toPGPoint((LatLonPoint)o,SRID);
//				}
//				
//				
//			}
//			else{
//				if( o instanceof Linestring){
//					Postgis.toPGLineString((Linestring)o,SRID);
//				}
//			}
//			
//		}
//		else{
//
//		}
//	}
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "Postgresql";
    }

    @Override
    public String getDriverClass() {
        // TODO Auto-generated method stub
        return "org.postgresql.Driver";
    }

    @Override
    public Object getObject(Class c, String query) {
        // TODO Auto-generated method stub


        return null;
    }

    @Override
    public void setObject(Class c, String query) {
        // TODO Auto-generated method stub
    }

    private static synchronized String createAttributes(Map<String, String> attributes) {
        StringBuilder builder = new StringBuilder();
        for (String att : attributes.keySet()) {
            String type = attributes.get(att);
            builder.append(att + " ");
            builder.append(type);
            builder.append(",");
        }
        return builder.substring(0, builder.length() - 1);
    }

    @Override
    public void setObject(String name, Object object) {
        // TODO Auto-generated method stub
    }

    public ResultSet getResultSet(String query) {
        try {
            Statement stat = connection.createStatement();
            ResultSet res = stat.executeQuery(query);
            return res;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void commit() {
        try {
            this.getConnection().commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
