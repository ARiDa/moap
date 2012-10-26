/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.networkmanager.impl;

import arida.ufc.br.networkmanager.api.NetworkManagerExporter;
//import com.sun.jdi.InvalidTypeException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeValue;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.io.importer.api.EdgeDefault;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author igobrilhante
 */
public class NetworkExporterImpl implements NetworkManagerExporter {

    private NetworkController netmanager;
    private final Logger logger = Logger.getLogger(NetworkExporterImpl.class);
    private Network network;
    private GraphModel graphModel;
    private AttributeModel attributeModel;

    public NetworkExporterImpl(Network network) throws Exception {
        this.network = network;
        netmanager = NetworkController.getInstance();
        this.attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        this.graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
    }
    
    public NetworkExporterImpl(Network network,GraphModel graphModel,AttributeModel attributeModel) throws Exception {
        this.network = network;
        this.graphModel = graphModel;
        this.attributeModel = attributeModel;
        netmanager = NetworkController.getInstance();
    }

    @Override
    public void exportNetwork() {

        if (netmanager == null) {
            throw new NullPointerException("Network Manager is NULL");
        }
        try {
            netmanager.getDatabase().getConnection().setAutoCommit(false);
            String network_name = network.getName();
            logger.info("Exporting network: " + network.getDescription());
            String node_table = network_name + NetworkController.NODE_SUFIX;
            String edge_table = network_name + NetworkController.EDGE_SUFIX;

            
            // LOCK GRAPH
            graphModel.getGraph().readLock();
//            storeNetwork(graphModel);

            storeNodes(node_table, attributeModel.getNodeTable(), graphModel.getHierarchicalGraph());
            storeEdges(edge_table, attributeModel.getEdgeTable(), graphModel.getHierarchicalGraph());

            netmanager.getDatabase().getConnection().commit();
//            netmanager.getDatabase().getConnection().close();

            // UNLOCK GRAPH
            graphModel.getGraph().readUnlock();
        }
        catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void storeNodes(String network_name, AttributeTable nodeTable, HierarchicalGraph graph) throws Exception {
//        AttributeController ac = Lookup.getDefault().lookup(AttributeControl
        netmanager.getDatabase().createTable(network_name, "");
        PreparedStatement statement = netmanager.getDatabase().getConnection().prepareStatement(
                netmanager.getDatabase().prepareStatement(netmanager.getDatabase().getNumberOfColumns(nodeTable) + 2,
                network_name));

        // ID
        netmanager.getDatabase().addColumn(network_name, "id", "text");
        // LABEL
        netmanager.getDatabase().addColumn(network_name, "label", "text");

        for (int i = 0; i < nodeTable.getColumns().length; i++) {
            AttributeColumn nodeColumn = nodeTable.getColumn(i);
            if (!nodeColumn.getOrigin().equals(AttributeOrigin.PROPERTY)) {
                netmanager.getDatabase().addColumn(network_name, nodeColumn.getId(), netmanager.getDatabase().getType(nodeColumn.getType()));
            }
        }

        int count = 0;
        for (Node node : graph.getNodes()) {
            int position = 1;
            statement.setString(position, node.getNodeData().getId());
            position++;
            statement.setString(position, node.getNodeData().getLabel());


            AttributeRow atts = (AttributeRow) node.getNodeData().getAttributes();
            for (AttributeValue value : atts.getValues()) {
                AttributeColumn attcol = value.getColumn();
                if (!attcol.getOrigin().equals(AttributeOrigin.PROPERTY)) {
                    position++;
                    //logger.info("Col "+attcol.getId()+" Value "+value.getValue().toString()+" "+position);
                    switch (attcol.getType()) {
                        case STRING:
                            if (value.getValue() == null) {
                                statement.setString(position, "");
                            }
                            else {
                                statement.setString(position, value.getValue().toString());
                            }
                            break;
                        case INT:
                            statement.setInt(position, Integer.parseInt(value.getValue().toString()));
                            break;
                        case DOUBLE:
                            statement.setDouble(position, Double.valueOf(value.getValue().toString()));
                            break;
                        case FLOAT:
                            statement.setFloat(position, Float.valueOf(value.getValue().toString()));
                            break;
                        case LONG:
                            statement.setLong(position, Long.valueOf(value.getValue().toString()));
                        default:
                            throw new Exception("Type" + attcol.getType().getTypeString() + " does not exist");
                            
                    }
                }
            }
            count++;
            statement.addBatch();
            if (count == netmanager.getDatabase().COMMIT_LIMIT) {
                statement.executeBatch();
                logger.info("Commiting " + count + " lines");
                netmanager.getDatabase().getConnection().commit();
                statement.clearBatch();
                count = 0;
            }

        }
        statement.executeBatch();
        statement.close();
        logger.info("Commiting " + count + " lines");
        netmanager.getDatabase().getConnection().commit();
        

    }

    private void storeEdges(String network_name, AttributeTable edgeTable, HierarchicalGraph graph) throws Exception {

        netmanager.getDatabase().createTable(network_name, "");
        PreparedStatement statement = netmanager.getDatabase().getConnection().prepareStatement(
                netmanager.getDatabase().prepareStatement(
                netmanager.getDatabase().getNumberOfColumns(edgeTable) + 5, network_name));

        int count = 0;

        // ID
        netmanager.getDatabase().addColumn(network_name, "id", "text");
        // LABEL
        netmanager.getDatabase().addColumn(network_name, "label", "text");
        // SOURCE
        netmanager.getDatabase().addColumn(network_name, "source", "text");
        // TARGET
        netmanager.getDatabase().addColumn(network_name, "target", "text");
        // WEIGHT
        netmanager.getDatabase().addColumn(network_name, "weight", "numeric");

        for (int i = 0; i < edgeTable.getColumns().length; i++) {
            AttributeColumn edgeColumn = edgeTable.getColumn(i);
            if (!edgeColumn.getOrigin().equals(AttributeOrigin.PROPERTY)) {
                netmanager.getDatabase().addColumn(network_name, edgeColumn.getId(), netmanager.getDatabase().getType(edgeColumn.getType()));
            }
            //logger.info("Column: "+nodeColumn.getId());
        }

        for (Edge edge : graph.getEdges()) {
            int position = 1;
            statement.setString(position, edge.getEdgeData().getId());
            position++;
            statement.setString(position, edge.getEdgeData().getLabel());
            position++;
            statement.setString(position, edge.getEdgeData().getSource().getId());
            position++;
            statement.setString(position, edge.getEdgeData().getTarget().getId());
            position++;
            statement.setFloat(position, edge.getWeight());

            AttributeRow atts = (AttributeRow) edge.getEdgeData().getAttributes();
            for (AttributeValue value : atts.getValues()) {
                AttributeColumn col = value.getColumn();
                if (!col.getOrigin().equals(AttributeOrigin.PROPERTY)) {
                    //logger.info("Col "+col.getId()+" Value "+value.getValue().toString());
                    position++;
                    switch (col.getType()) {
                        case STRING:
                            if (value.getValue() == null) {
                                statement.setString(position, "");
                            }
                            else {
                                statement.setString(position, value.getValue().toString());
                            }
                            break;
                        case INT:
                            statement.setInt(position, Integer.parseInt(value.getValue().toString()));
                            break;
                        case DOUBLE:
                            statement.setDouble(position, Double.valueOf(value.getValue().toString()));
                            break;
                        case FLOAT:
                            statement.setFloat(position, Float.valueOf(value.getValue().toString()));
                            break;
                        case LONG:
                            statement.setLong(position, Long.valueOf(value.getValue().toString()));
                        default:
                            new RuntimeException("Type" + col.getType().getTypeString() + " does not exist");
                            break;
                    }
                }
            }
            count++;
            statement.addBatch();
            if (count == netmanager.getDatabase().COMMIT_LIMIT) {
                statement.executeBatch();
                logger.info("Commiting " + count + " lines");
                netmanager.getDatabase().getConnection().commit();
                statement.clearBatch();
                count = 0;
            }
        }

        statement.executeBatch();
        statement.close();
        logger.info("Commiting " + count + " lines");
        netmanager.getDatabase().getConnection().commit();
        
    }

    private void storeNetwork(GraphModel graphModel) throws SQLException {
        PreparedStatement ps = netmanager.getDatabase().getConnection().prepareStatement("SELECT COUNT(*) count FROM " + NetworkController.NETWORKMANAGER + " WHERE id = ?");
        ps.setString(1, network.getName());
        ResultSet rs = ps.executeQuery();
        if(graphModel.isDirected())
            network.setEdgeType(EdgeDefault.DIRECTED);
        else
            network.setEdgeType(EdgeDefault.UNDIRECTED);
        int count = 0;
        String query = "";
        while (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        if (count > 0) {
            query = "UPDATE " + NetworkController.NETWORKMANAGER + " SET description=?,nodes=?,edges=?,edge_type=? WHERE id=?";
            ps = netmanager.getDatabase().getConnection().prepareStatement(query);
            ps.setString(5, network.getName());
            ps.setString(1, network.getDescription());
            ps.setInt(2, graphModel.getGraph().getNodeCount());
            ps.setInt(3, graphModel.getGraph().getEdgeCount());
            ps.setString(4, network.getEdgeType().name());
        }
        else {
            query = "INSERT INTO " + NetworkController.NETWORKMANAGER + " VALUES (?,?,?,?,?)";
            ps = netmanager.getDatabase().getConnection().prepareStatement(query);
            ps.setString(1, network.getName());
            ps.setString(2, network.getDescription());
            ps.setInt(3, graphModel.getGraph().getNodeCount());
            ps.setInt(4, graphModel.getGraph().getEdgeCount());
            ps.setString(5, network.getEdgeType().name());
        }
        ps.execute();
        ps.close();
    }
}
