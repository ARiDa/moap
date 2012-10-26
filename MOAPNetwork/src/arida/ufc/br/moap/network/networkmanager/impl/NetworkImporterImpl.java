/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.networkmanager.impl;

import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.networkmanager.api.NetworkManagerImporter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import org.apache.log4j.Logger;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.Issue;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.io.importer.api.PropertiesAssociations;
import org.gephi.io.importer.api.Report;
import org.gephi.io.importer.impl.ImportContainerImpl;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.io.processor.spi.Processor;
import org.gephi.io.processor.spi.Scaler;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

/**
 *
 * @author igobrilhante
 */
public class NetworkImporterImpl implements NetworkManagerImporter {

    private Report report;
    private NetworkController netmanager;
    private DBManager database;
    private Network network;
    private GraphModel graphModel;
    private AttributeModel attributeModel;
    private Container container;
    private EdgeDefault edgeType;
//    private final Logger logger = Logger.getLogger(NetworkImporterImpl.class);
    PropertiesAssociations properties = new PropertiesAssociations();
    
    public NetworkImporterImpl(Network network) throws Exception {
            this.network = network;
            this.container = new ImportContainerImpl();
            this.report = new Report();
            this.container.setReport(report);
            this.netmanager = NetworkController.getInstance();
            this.database = netmanager.getDatabase();
            
        

        //Default node associations
        properties.addNodePropertyAssociation(PropertiesAssociations.NodeProperties.ID, "id");
        properties.addNodePropertyAssociation(PropertiesAssociations.NodeProperties.LABEL, "label");
        properties.addNodePropertyAssociation(PropertiesAssociations.NodeProperties.X, "x");
        properties.addNodePropertyAssociation(PropertiesAssociations.NodeProperties.Y, "y");
        properties.addNodePropertyAssociation(PropertiesAssociations.NodeProperties.SIZE, "size");

        //Default edge associations
        properties.addEdgePropertyAssociation(PropertiesAssociations.EdgeProperties.ID, "id");
        properties.addEdgePropertyAssociation(PropertiesAssociations.EdgeProperties.SOURCE, "source");
        properties.addEdgePropertyAssociation(PropertiesAssociations.EdgeProperties.TARGET, "target");
        properties.addEdgePropertyAssociation(PropertiesAssociations.EdgeProperties.LABEL, "label");
        properties.addEdgePropertyAssociation(PropertiesAssociations.EdgeProperties.WEIGHT, "weight");
    }

    public Network getNetwork() {
        return network;
    }

    public void setEdgeType(EdgeDefault type){
        this.edgeType = type;
    }
    private void getNodes(String nodeQuery) throws SQLException {

        //Factory
        ContainerLoader.DraftFactory factory = container.getLoader().factory();

        //Properties
//        PropertiesAssociations properties = db.getPropertiesAssociations();

        Statement s = database.getConnection().createStatement();
        try {
            s.executeQuery(nodeQuery);
        }
        catch (SQLException ex) {
            report.logIssue(new Issue("Failed to execute Node query", Issue.Level.SEVERE, ex));
            return;
        }
        
        ResultSet rs = s.getResultSet();
        findNodeAttributesColumns(rs);
        AttributeTable nodeClass = container.getLoader().getAttributeModel().getNodeTable();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnsCount = metaData.getColumnCount();
        int count = 0;
        while (rs.next()) {
            NodeDraft node = factory.newNodeDraft();
            for (int i = 0; i < columnsCount; i++) {
                String columnName = metaData.getColumnLabel(i + 1);
                PropertiesAssociations.NodeProperties p = properties.getNodeProperty(columnName);
                if (p != null) {
                    injectNodeProperty(p, rs, i + 1, node);
                }
                else {
                    //Inject node attributes
                    AttributeColumn col = nodeClass.getColumn(columnName);
                    injectNodeAttribute(rs, i + 1, col, node);
                }
            }
            container.getLoader().addNode(node);
            ++count;
        }
        rs.close();
        s.close();
        
    }
    
    private void getEdges(String edgeQuery) throws SQLException {

        //Factory
        ContainerLoader.DraftFactory factory = container.getLoader().factory();

        //Properties

        //PropertiesAssociations properties = database.getPropertiesAssociations();

        Statement s = database.getConnection().createStatement();
        try {
            s.executeQuery(edgeQuery);
        }
        catch (SQLException ex) {
            report.logIssue(new Issue("Failed to execute Edge query", Issue.Level.SEVERE, ex));
            return;
        }
        ResultSet rs = s.getResultSet();
        findEdgeAttributesColumns(rs);
        AttributeTable edgeClass = container.getLoader().getAttributeModel().getEdgeTable();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnsCount = metaData.getColumnCount();
        int count = 0;
        while (rs.next()) {
            EdgeDraft edge = factory.newEdgeDraft();
            for (int i = 0; i < columnsCount; i++) {
                String columnName = metaData.getColumnLabel(i + 1);
                PropertiesAssociations.EdgeProperties p = properties.getEdgeProperty(columnName);
                if (p != null) {
                    injectEdgeProperty(p, rs, i + 1, edge);
                }
                else {
                    //Inject edge attributes
                    AttributeColumn col = edgeClass.getColumn(columnName);
                    injectEdgeAttribute(rs, i + 1, col, edge);
                }
            }
            
            container.getLoader().addEdge(edge);
            ++count;
        }
        rs.close();
        s.close();
    }
    
    private void getNodesAttributes(Connection connection) throws SQLException {
    }
    
    private void getEdgesAttributes(Connection connection) throws SQLException {
    }
    
    private void injectNodeProperty(PropertiesAssociations.NodeProperties p, ResultSet rs, int column, NodeDraft nodeDraft) throws SQLException {
        switch (p) {
            case ID:
                String id = rs.getString(column);
                if (id != null) {
                    nodeDraft.setId(id);
                }
                break;
            case LABEL:
                String label = rs.getString(column);
                if (label != null) {
                    nodeDraft.setLabel(label);
                }
                break;
            case X:
                float x = rs.getFloat(column);
                if (x != 0) {
                    nodeDraft.setX(x);
                }
                break;
            case Y:
                float y = rs.getFloat(column);
                if (y != 0) {
                    nodeDraft.setY(y);
                }
                break;
            case Z:
                float z = rs.getFloat(column);
                if (z != 0) {
                    nodeDraft.setZ(z);
                }
                break;
            case R:
                break;
            case G:
                break;
            case B:
                break;
        }
    }
    
    private void injectEdgeProperty(PropertiesAssociations.EdgeProperties p, ResultSet rs, int column, EdgeDraft edgeDraft) throws SQLException {
        switch (p) {
            case ID:
                String id = rs.getString(column);
                if (id != null) {
                    edgeDraft.setId(id);
                }
                break;
            case LABEL:
                String label = rs.getString(column);
                if (label != null) {
                    edgeDraft.setLabel(label);
                }
                break;
            case SOURCE:
                String source = rs.getString(column);
                if (source != null) {
                    NodeDraft sourceNode = container.getLoader().getNode(source);
                    edgeDraft.setSource(sourceNode);
                }
                break;
            case TARGET:
                String target = rs.getString(column);
                if (target != null) {
                    NodeDraft targetNode = container.getLoader().getNode(target);
                    edgeDraft.setTarget(targetNode);
                }
                break;
            case WEIGHT:
                float weight = rs.getFloat(column);
                if (weight != 0) {
                    edgeDraft.setWeight(weight);
                }
                break;
            case R:
                break;
            case G:
                break;
            case B:
                break;
        }
    }
    
    private void injectNodeAttribute(ResultSet rs, int columnIndex, AttributeColumn column, NodeDraft draft) {
        switch (column.getType()) {
            case BOOLEAN:
                try {
                    boolean val = rs.getBoolean(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a BOOLEAN value for node attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case DOUBLE:
                try {
                    double val = rs.getDouble(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a DOUBLE value for node attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case FLOAT:
                try {
                    float val = rs.getFloat(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a FLOAT value for node attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case INT:
                try {
                    int val = rs.getInt(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a INT value for node attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case LONG:
                try {
                    long val = rs.getLong(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a LONG value for node attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            default: //String
                try {
                    String val = rs.getString(columnIndex);
                    if (val != null) {
                        draft.addAttributeValue(column, val);
                    }
                    else {
                        report.logIssue(new Issue("Failed to get a STRING value for node attribute '" + column.getId() + "'", Issue.Level.WARNING));
                    }
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a STRING value for node attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
        }
    }
    
    private void injectEdgeAttribute(ResultSet rs, int columnIndex, AttributeColumn column, EdgeDraft draft) {
        switch (column.getType()) {
            case BOOLEAN:
                try {
                    boolean val = rs.getBoolean(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a BOOLEAN value for edge attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case DOUBLE:
                try {
                    double val = rs.getDouble(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a DOUBLE value for edge attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case FLOAT:
                try {
                    float val = rs.getFloat(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a FLOAT value for edge attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case INT:
                try {
                    int val = rs.getInt(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a INT value for edge attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            case LONG:
                try {
                    long val = rs.getLong(columnIndex);
                    draft.addAttributeValue(column, val);
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a LONG value for edge attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
            default: //String
                try {
                    String val = rs.getString(columnIndex);
                    if (val != null) {
                        draft.addAttributeValue(column, val);
                    }
                    else {
                        report.logIssue(new Issue("Failed to get a BOOLEAN value for edge attribute '" + column.getId() + "'", Issue.Level.WARNING));
                    }
                }
                catch (SQLException ex) {
                    report.logIssue(new Issue("Failed to get a STRING value for edge attribute '" + column.getId() + "'", Issue.Level.SEVERE, ex));
                }
                break;
        }
    }
    
    private void findNodeAttributesColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnsCount = metaData.getColumnCount();
        AttributeTable nodeClass = container.getLoader().getAttributeModel().getNodeTable();
        for (int i = 0; i < columnsCount; i++) {
            String columnName = metaData.getColumnLabel(i + 1);
            PropertiesAssociations.NodeProperties p = properties.getNodeProperty(columnName);
            if (p == null) {
                //No property associated to this column is found, so we append it as an attribute

                AttributeType type = AttributeType.STRING;
                switch (metaData.getColumnType(i + 1)) {
                    case Types.BIGINT:
                        type = AttributeType.LONG;
                        break;
                    case Types.INTEGER:
                        type = AttributeType.INT;
                        break;
                    case Types.TINYINT:
                        type = AttributeType.INT;
                        break;
                    case Types.SMALLINT:
                        type = AttributeType.INT;
                        break;
                    case Types.BOOLEAN:
                        type = AttributeType.BOOLEAN;
                        break;
                    case Types.FLOAT:
                        type = AttributeType.FLOAT;
                        break;
                    case Types.DOUBLE:
                        type = AttributeType.DOUBLE;
                        break;
                    case Types.VARCHAR:
                        type = AttributeType.STRING;
                        break;
                    default:
                        report.logIssue(new Issue("Unknown SQL Type " + metaData.getColumnType(i + 1) + ", STRING used.", Issue.Level.WARNING));
                        break;
                }
                report.log("Node attribute found: " + columnName + "(" + type + ")");
                nodeClass.addColumn(columnName, type);
            }
        }
    }
    
    private void findEdgeAttributesColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnsCount = metaData.getColumnCount();
        AttributeTable edgeClass = container.getLoader().getAttributeModel().getEdgeTable();
        for (int i = 0; i < columnsCount; i++) {
            String columnName = metaData.getColumnLabel(i + 1);
            PropertiesAssociations.EdgeProperties p = properties.getEdgeProperty(columnName);
            if (p == null) {
                //No property associated to this column is found, so we append it as an attribute
                AttributeType type = AttributeType.STRING;
                switch (metaData.getColumnType(i + 1)) {
                    case Types.BIGINT:
                        type = AttributeType.LONG;
                        break;
                    case Types.INTEGER:
                        type = AttributeType.INT;
                        break;
                    case Types.TINYINT:
                        type = AttributeType.INT;
                        break;
                    case Types.SMALLINT:
                        type = AttributeType.INT;
                        break;
                    case Types.BOOLEAN:
                        type = AttributeType.BOOLEAN;
                        break;
                    case Types.FLOAT:
                        type = AttributeType.FLOAT;
                        break;
                    case Types.DOUBLE:
                        type = AttributeType.DOUBLE;
                        break;
                    case Types.VARCHAR:
                        type = AttributeType.STRING;
                        break;
                    default:
                        report.logIssue(new Issue("Unknown SQL Type " + metaData.getColumnType(i + 1) + ", STRING used.", Issue.Level.WARNING));
                        break;
                }
                
                report.log("Edge attribute found: " + columnName + "(" + type + ")");
                edgeClass.addColumn(columnName, type);
            }
        }
    }

//    public void setDatabase(Database database) {
//        this.database = (EdgeListDatabaseImpl) database;
//    }
//    public Database getDatabase() {
//        return database;
//    }
//    public Report getReport() {
//        return report;
//    }  
    @Override
    public void importNetwork() {
        report.logIssue(new Issue("Importing network " + this.network.getName(),Issue.Level.INFO));
        String name = this.network.getName();
        Statement stat;
        try {
            stat = database.getConnection().createStatement();
//            ResultSet rs = stat.executeQuery("SELECT * FROM " + NetworkController.NETWORKMANAGER + " WHERE id='" + name + "'");
//            int c = 0;
//            String edgeType = "";
//            String description = "";
//            while (rs.next()) {
//                c++;
//                edgeType = rs.getString("edge_type");
//                description = rs.getString("description");
//            }
//            if (c == 0) {
//                throw new RuntimeException("Network "+name + " has not been found");
//            }
            this.network.setEdgeType(edgeType);
//            this.network.setDescription(description);
            String nodeQuery = "SELECT * FROM " + name + NetworkController.NODE_SUFIX;
            String edgeQuery = "SELECT * FROM " + name + NetworkController.EDGE_SUFIX;
//            System.out.println("Edge Type: "+edgeType);
            report.log(properties.getInfos());
            getNodes(nodeQuery);
            getEdges(edgeQuery);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        getNodesAttributes(connection);
//        getEdgesAttributes(connection);


//        throw new UnsupportedOperationException("Not supported yet.");
        switch (this.network.getEdgeType()) {
            case DIRECTED:
                this.container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);
                break;
            case UNDIRECTED:
                this.container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED);
                this.container.setUndirectedSumDirectedEdgesWeight(true);
                break;
            default:
                throw new UnsupportedOperationException("Network Type not supported yet.");
        }
        process(container, new DefaultProcessor(), createNewWorkspace());
    }
    
    private Workspace createNewWorkspace() {
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        
        if (pc.getCurrentProject() == null) {
            report.log("New Project");
            pc.newProject();
        }
        Workspace w = pc.newWorkspace(pc.getCurrentProject());
        
        pc.openWorkspace(w);
        
        
        
        return w;
    }
    
    public void process(Container container) {
        Processor processor = Lookup.getDefault().lookup(Processor.class);
        if (processor == null) {
            throw new RuntimeException("Impossible to find Default Processor");
        }
        process(container, processor, null);
    }
    
    public void process(Container container, Processor processor, Workspace workspace) {
        container.closeLoader();
        if (container.isAutoScale()) {
            Scaler scaler = Lookup.getDefault().lookup(Scaler.class);
            if (scaler != null) {
                scaler.doScale(container);
            }
        }
        processor.setContainer(container.getUnloader());
        processor.setWorkspace(workspace);
        processor.process();
    }
}
