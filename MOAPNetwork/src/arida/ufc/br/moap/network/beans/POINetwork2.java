/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.beans;

import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkController;
import org.apache.log4j.Logger;
import org.gephi.io.importer.api.Issue;
import org.gephi.io.importer.api.Report;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class POINetwork2 implements NetworkBuilder {
    private Report report;
    final Logger logger = Logger.getLogger(POINetwork2.class); 
    private NetworkController nc;
    private Network network;
    private String table_input;
    // Build a location network
    /**
     * 
     * @param network
     * @param table_input
     * @throws Exception
     */
    public POINetwork2(Network network,String table_input) throws Exception{
        this.nc = NetworkController.getInstance();
        this.network = network;
        this.table_input = table_input;
        this.report = new Report();
    }
    
    /**
     * 
     */
    @Override
    public void buildNetwork() {
            
            logger.info("Creating network of points of interest");
            String query_nodes = " SELECT DISTINCT POI_ID as id, POI_LABEL as label FROM " + table_input + " table_input";
            logger.info(query_nodes);
            String query_edges = " SELECT  SOURCE, TARGET,COUNT(USER_TRIP) TOTAL,SUM(WEIGHT) WEIGHT \n"
                    + " FROM \n"
                    + " ( \n"
                    + " SELECT SP1.USER_ID,SP1.USER_TRIP,SP1.POI_ID SOURCE,SP2.POI_ID TARGET,\n"
                    + " MAX\n"
                    + " (CASE WHEN AUX2.COUNT=1 THEN 1\n"
                    + " ELSE (1-(SP2.DISTANCE/AUX2.SUM))/(AUX2.COUNT-1)\n"
                    + " END) WEIGHT\n"
                    + " FROM\n"
                    + " " + table_input + " SP1, " + table_input + " SP2,\n"
                    + " (SELECT SP1.USER_ID USER_ID,SP1.USER_TRIP USER_TRIP,SP1.DATETIME DATETIME1,MIN(SP2.DATETIME) DATETIME2\n"
                    + " FROM " + table_input + " SP1, " + table_input + " SP2\n"
                    + " WHERE SP1.USER_ID=SP2.USER_ID AND SP1.USER_TRIP=SP2.USER_TRIP AND SP1.DATETIME < SP2.DATETIME\n"
                    + " GROUP BY SP1.USER_ID,SP1.USER_TRIP,SP1.DATETIME\n"
                    + " ) AUX,\n"
                    + " (\n"
                    + " SELECT USER_ID,USER_TRIP,DATETIME,COUNT(*),SUM(DISTANCE)\n"
                    + " FROM " + table_input + " SP\n"
                    + " GROUP BY USER_ID,USER_TRIP,DATETIME\n"
                    + " ) AUX2\n"
                    + " WHERE SP1.USER_ID=AUX.USER_ID \n"
                    + " AND AUX.USER_ID=AUX2.USER_ID\n"
                    + " AND AUX.USER_TRIP=AUX2.USER_TRIP\n"
                    + " AND SP1.USER_TRIP=AUX.USER_TRIP \n"
                    + " AND SP1.USER_ID=SP2.USER_ID\n"
                    + " AND SP1.USER_TRIP=SP2.USER_TRIP\n"
                    + " AND SP1.DATETIME = AUX.DATETIME1 \n"
                    + " AND SP2.DATETIME=AUX.DATETIME2\n"
                    + " AND SP2.DATETIME=AUX2.DATETIME\n"
                    + " AND SP1.POI_ID!=SP2.POI_ID\n"
                    + " GROUP BY SP1.USER_ID,SP1.USER_TRIP,SP1.POI_ID,SP2.POI_ID\n"
                    + " ORDER BY SP1.USER_ID,SP1.USER_TRIP\n"
                    + " ) MAIN\n"
                    + " GROUP BY SOURCE,TARGET\n";
            logger.info(query_edges);
            
        try {
            nc.storeQueryAsNetwork(network, query_nodes, "id", query_edges, "source,target");
        }
        catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
       
    }
}
