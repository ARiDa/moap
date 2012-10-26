/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.networkmanager.impl;

import arida.ufc.br.moap.network.dbmanager.DBManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.log4j.Logger;
import org.gephi.data.attributes.AttributeRowImpl;
import org.gephi.data.attributes.AttributeTableImpl;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.project.api.ProjectController;
import org.gephi.statistics.plugin.ClusteringCoefficient;
import org.gephi.statistics.plugin.ConnectedComponents;
import org.gephi.statistics.plugin.Degree;
import org.gephi.statistics.plugin.GraphDistance;
import org.gephi.statistics.plugin.Hits;
import org.gephi.statistics.plugin.PageRank;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author igobrilhante
 */
public class NetworkController {

    private static final String NODE = "_node";
    private static final String EDGE = "_edge";
    private static final String POSIX = "\\\\$";
    private static final String SUFIX = "$";
    public static final String COMMUNITY_REPORT_SUFIX = "_commreport$";
    public static final String NODE_COMMUNITY_SUFIX = "_nodecomm$";
    public static final String EDGE_COMMUNITY_SUFIX = "_edgecomm$";
    public static final String NODE_SUFIX = NODE + SUFIX;
    public static final String EDGE_SUFIX = EDGE + SUFIX;
    private static final String NODE_POSIX = NODE + POSIX;
    private static final String EDGE_POSIX = EDGE + POSIX;
    public static final String NETWORKMANAGER = "networkmanager" + SUFIX;
    public final Logger logger = Logger.getLogger(NetworkController.class);
    private DBManager database;
    private static NetworkController instance;
    private EdgeDefault edgetype;

    public EdgeDefault getEdgeType() {
        return edgetype;
    }

    public void setEdgeType(EdgeDefault edgetype) {
        this.edgetype = edgetype;
    }

    private NetworkController() throws Exception {
        database = DBManager.getInstance();
        if (!hasNetworkManager()) {
            initialize();
        }

    }

    public static NetworkController getInstance() throws Exception {
        if (instance == null) {
            instance = new NetworkController();

        }
        return instance;
    }

    private void initialize() throws SQLException, Exception {
        logger.info("Network Manager initializing ...");
        Statement statement = database.getConnection().createStatement();
        database.createTable(NETWORKMANAGER, "id text,description text,nodes integer, edges integer,edge_type text,createdin timestamp");

        // Seek for networks
        String query = "select distinct (regexp_split_to_array(t1.relname, '\\" + NODE_POSIX + "'))[1] as name"
                + " from pg_stat_user_tables t1,pg_stat_user_tables t2"
                + " where (regexp_split_to_array(t1.relname, '\\" + NODE_POSIX + "'))[1]=(regexp_split_to_array(t2.relname, '\\" + EDGE_POSIX + "'))[1]"
                + " and t1.relname like '%\\" + NODE_POSIX + "' and t2.relname like '%\\" + EDGE_POSIX + "'";
        System.out.println(query);
        ResultSet res = statement.executeQuery(query);
        //logger.info(query);
        PreparedStatement ps = database.getConnection().prepareStatement("INSERT INTO " + NETWORKMANAGER + " VALUES (?,?,?,?,?,?)");
        String name, description = "", edge_type = "";
        int nodes = 0, edges = 0;
        int count = 0;
        while (res.next()) {
            name = res.getString("name");
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, nodes);
            ps.setInt(4, edges);
            ps.setString(5, edge_type);
            ps.setTimestamp(6, new Timestamp(new Date().getTime()));
            ps.addBatch();
            count++;
            if (count == 100) {
                ps.executeBatch();
                count = 0;
                database.getConnection().commit();
                ps.clearBatch();
            }
        }
        if (count > 0) {
            ps.executeBatch();
        }
        ps.close();
        statement.close();
//        database.getConnection().commit();
    }

    public DBManager getDatabase() {
        return database;
    }

    public List<String> getNetworks() throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Statement statement = this.database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("select id from "+NETWORKMANAGER);
        while(rs.next()){
            list.add(rs.getString(1));
        }
        rs.close();
        statement.close();
        return list;
    }
    public AttributeTable getNetworkTable() throws SQLException, Exception {
        if (!hasNetworkManager()) {
            initialize();
        }
        Statement statement = this.database.getConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT * FROM " + NETWORKMANAGER);
        AttributeTableImpl table = database.getTableColumns(res, "network");
        while (res.next()) {
            String id = res.getString("id");
            String description = res.getString("description");
            int nodes = res.getInt("nodes");
            int edges = res.getInt("edges");
            AttributeRowImpl row = new AttributeRowImpl(table, null);
            row.setValue(table.getColumn("id"), id);
            row.setValue(table.getColumn("description"), description);
            row.setValue(table.getColumn("nodes"), nodes);
            row.setValue(table.getColumn("edges"), edges);
        }

        return table;
    }

    public TableModel getNetworkTableModel() throws SQLException, Exception {
        if (!hasNetworkManager()) {
            initialize();
        }

        Statement statement = this.database.getConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT count(*) FROM " + NETWORKMANAGER);

        int rownum = 0;
        while (res.next()) {
            rownum = res.getInt(1);
        }

        res = statement.executeQuery("SELECT * FROM " + NETWORKMANAGER);
        System.out.println(rownum);
        int i = 0, j = 0;
        Object[] columnNames = this.database.getColumnNames(res);
        Object[][] data = new Object[rownum][columnNames.length];
        while (res.next()) {
            for (Object column : columnNames) {
                Object ob = res.getObject(column.toString());
//                System.out.println(column.toString());
//                System.out.println(ob.toString());
                data[i][j] = ob;
                j++;
            }
            i++;
            j = 0;

        }
        res.close();
        statement.close();
        TableModel tableModel = new DefaultTableModel(data, columnNames);
//        System.out.println("ini");
        return tableModel;
    }

    public TableModel getTableModel(String network, String model) throws SQLException, Exception {
        if (!hasNetworkManager()) {
            initialize();
        }

        Statement statement = this.database.getConnection().createStatement();
        if(!this.database.tableExists(network + model)){
            return new DefaultTableModel();
        }
        
        ResultSet res = statement.executeQuery("SELECT count(*) FROM " + network + model);

        int rownum = 0;
        while (res.next()) {
            rownum = res.getInt(1);
        }

        res = statement.executeQuery("SELECT * FROM " + network + model);
        System.out.println(rownum);
        int i = 0, j = 0;
        Object[] columnNames = this.database.getColumnNames(res);
        Object[][] data = new Object[rownum][columnNames.length];
        while (res.next()) {
            for (Object column : columnNames) {
                Object ob = res.getObject(column.toString());
//                System.out.println(column.toString());
//                System.out.println(ob.toString());
                data[i][j] = ob;
                j++;
            }
            i++;
            j = 0;

        }
        res.close();
        statement.close();
        TableModel tableModel = new DefaultTableModel(data, columnNames);
//        System.out.println("ini");
        return tableModel;
    }

    private boolean hasNetworkManager() throws SQLException {
        Statement statement = this.database.getConnection().createStatement();
        ResultSet res = statement.executeQuery("SELECT count(*) as count FROM pg_stat_user_tables WHERE relname='" + NETWORKMANAGER + "'");
        while (res.next()) {
            if (res.getInt("count") < 1) {
//               throw new RuntimeException("There is no Network Manager in this database");
                return false;
            }
        }
        return true;

    }

    private void checkNetworkManager() throws SQLException, Exception {
        logger.info("Check network manager");
        if (!hasNetworkManager()) {
            initialize();
        }
    }

    protected void storeNetwork(Network network) throws SQLException, Exception {
        logger.info("Store Network");

        removeSingleNodes(network.getName());
        PreparedStatement ps = this.getDatabase().getConnection().prepareStatement("INSERT INTO " + NETWORKMANAGER + " VALUES (?,?,?,?,?,?)");
        Statement stat = this.database.getConnection().createStatement();
        ResultSet rs = stat.executeQuery("select count(*) from " + network.getName() + NODE_SUFIX);
        int nodes = 0;
        while (rs.next()) {
            nodes = rs.getInt(1);
        }
        rs = stat.executeQuery("select count(*) from " + network.getName() + EDGE_SUFIX);

        int edges = 0;
        while (rs.next()) {
            edges = rs.getInt(1);
        }
        rs.close();
        ps.setString(1, network.getName());
        ps.setString(2, network.getDescription());
        ps.setInt(3, nodes);
        ps.setInt(4, edges);
        ps.setString(5, network.getEdgeType().toString());
        ps.setTimestamp(6, new Timestamp(new Date().getTime()));
        ps.execute();
        ps.close();

    }

    public synchronized void removeSingleNodes(String network_name) throws SQLException {
        logger.info("Removing single nodes ...");
        String remove_isolated_nodes = " DELETE FROM " + network_name + NODE_SUFIX + " node "
                + " WHERE 0=(SELECT COUNT(*) FROM " + network_name + EDGE_SUFIX + " edge WHERE edge.source = node.id or edge.target=node.id)";
        Statement stat = this.getDatabase().getConnection().createStatement();
        stat.execute(remove_isolated_nodes);
        stat.close();
    }

    public synchronized void storeQueryAsNetwork(Network network, String node_query, String node_index, String edge_query, String edge_index) throws SQLException, Exception {
        logger.info("Store query as network");
        System.out.println("Store query as network");
        // Set auto commit false
        this.getDatabase().getConnection().setAutoCommit(false);
        checkNetworkManager();
        Statement stat = this.database.getConnection().createStatement();
        // Does it have the network?
        removeNetwork(network);
        // Node Query
        logger.info("Creating node table");
        System.out.println("Creating node table");
        stat.execute("CREATE TABLE " + network.getName() + NODE_SUFIX + " AS " + node_query);
        if (!node_index.equals("")) {
            stat.execute("ALTER  TABLE " + network.getName() + NODE_SUFIX + " ADD PRIMARY KEY (" + node_index + ")");
        }

        // Edge Query
        logger.info("Creating edge table");
        System.out.println("Creating edge table");
        stat.execute("CREATE TABLE " + network.getName() + EDGE_SUFIX + " AS " + edge_query);
        if (!edge_index.equals("")) {
            stat.execute("ALTER  TABLE " + network.getName() + EDGE_SUFIX + " ADD PRIMARY KEY (" + edge_index + ")");
        }
        stat.close();
        // Store the network in Network Manager
        System.out.println("Store network");
        storeNetwork(network);
        // Commit
        this.getDatabase().getConnection().commit();

    }

    public synchronized boolean hasTheNetwork(Network network) throws SQLException {
        Statement stat = this.getDatabase().getConnection().createStatement();
        ResultSet rs = stat.executeQuery("SELECT count(*) FROM " + NETWORKMANAGER + " WHERE id = '" + network.getName() + "'");
        while (rs.next()) {
//            System.out.println(rs.getInt(1));
            if (rs.getInt(1) > 0) {
                return true;
            }
        }
        rs.close();
        stat.close();
        return false;

    }

    private synchronized void removeNetwork(Network network) throws SQLException {
        logger.info("Removing network " + network.getName());
        Statement stat = this.getDatabase().getConnection().createStatement();
        stat.executeUpdate("DELETE FROM " + NETWORKMANAGER + " WHERE id = '" + network.getName() + "'");
        stat.close();
//        stat.executeUpdate("DROP TABLE IF EXISTS " + network.getName() + NODE_SUFIX);
//        stat.executeUpdate("DROP TABLE IF EXISTS " + network.getName() + EDGE_SUFIX);
        this.getDatabase().dropTable(network.getName() + NODE_SUFIX);
        this.getDatabase().dropTable(network.getName() + EDGE_SUFIX);
        this.getDatabase().dropTable(network.getName() + NODE_COMMUNITY_SUFIX);
        this.getDatabase().dropTable(network.getName() + EDGE_COMMUNITY_SUFIX);
        this.getDatabase().dropTable(network.getName() + COMMUNITY_REPORT_SUFIX);
    }
    
    public synchronized void computeProperties(String network_name){
        Network n = new Network(network_name);
                NetworkImporterImpl importer = null;
                try {
                    importer = new NetworkImporterImpl(n);
                    importer.importNetwork();
                }
                catch (Exception ex) {
                    Exceptions.printStackTrace(ex);
                }

                AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();

                GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();

                // CONVERTING DIRECTED TO UNDIRECTED NETWORK
                Graph network;
                if (graphModel.isUndirected()) {
                    network = graphModel.getHierarchicalUndirectedGraph();//Directed2UndirectedNetwork.directed2UndirectedNetwork(digraph);
                }
                else {
                    System.out.println("dir");
                    network = graphModel.getHierarchicalDirectedGraph();
                }

                System.out.println("Nodes: " + network.getNodeCount());
                System.out.println("Edges: " + network.getEdgeCount());
                System.out.println("Computing ... distances");

//      DEGREE
                Degree degree = new Degree();
                degree.execute(graphModel, attributeModel);
                System.out.println("Degree: " + degree.getAverageDegree());
  
////      WEIGHTED DEGREE
//                WeightedDegree wdegree = new WeightedDegree();
//                wdegree.execute(graphModel, attributeModel);
//                System.out.println("Weighted Degree: " + wdegree.getAverageDegree());

//      CLUSTERING COEFFICIENT
                ClusteringCoefficient clustering = new ClusteringCoefficient();
                clustering.setDirected(graphModel.isDirected());
                clustering.execute(graphModel, attributeModel);
                Double cc = clustering.getAverageClusteringCoefficient();
                System.out.println("Clustering: " + cc);

//      PAGE RANK
                PageRank pr = new PageRank();
//                pr.setProbability(0.25);
                pr.setUseEdgeWeight(true);
                pr.execute(graphModel, attributeModel);

//      AVERAGE SHORTEST PATH LENGTH
                GraphDistance graphDistance = new GraphDistance();
                graphDistance.setDirected(graphModel.isDirected());
                graphDistance.setNormalized(true);
                graphDistance.execute(graphModel, attributeModel);
                Double diameter = graphDistance.getDiameter();
                Double path = graphDistance.getPathLength();
                System.out.println("Path Length: " + path);
                System.out.println("Diameter: " + diameter);

//      HITS
                Hits hits = new Hits();

                hits.execute(graphModel, attributeModel);

//      COMPONENTS
                ConnectedComponents comps = new ConnectedComponents();
                comps.setDirected(graphModel.isDirected());
                comps.execute(graphModel, attributeModel);
                System.out.println(comps.getReport());

                ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
                pc.closeCurrentWorkspace();
                try {

                    NetworkExporterImpl exporter = new NetworkExporterImpl(importer.getNetwork());
                    exporter.exportNetwork();

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                
    }
}
