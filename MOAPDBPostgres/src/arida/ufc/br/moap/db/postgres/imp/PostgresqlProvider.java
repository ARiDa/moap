package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.database.spi.*;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.importer.csv.imp.Translater;
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
 * Provider to work with PostgreSQL
 *
 * @author igobrilhante
 * @author franzejr
 */
public class PostgresqlProvider extends AbstractDatabase implements Serializable {

    private static final long serialVersionUID = -85997221180839532L;
    public final int COMMIT_LIMIT = 100;
    private final Logger logger = Logger.getLogger(PostgresqlProvider.class);

    public PostgresqlProvider(String user, String password, String url) {
        super(user, password, url);
    }

    public AbstractDatabase getInstance() {
        if (instance == null) {
            instance = new PostgresqlProvider(user, password, url);
        }
        
        return instance;
    }

    @Override
    public IDataModel getInstanceModel(String query, IDataModel model) {
        Translater translater = new Translater();
        translater.translate(query, model);

        return model;
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public synchronized void createTable(String table_name, String attributes) throws Exception, SQLException {
        logger.info("Creating table " + table_name);
        Statement state = connection.createStatement();
        String query = "";
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
        state.close();
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

    /*
     * @return ResultSet
     */
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
}
