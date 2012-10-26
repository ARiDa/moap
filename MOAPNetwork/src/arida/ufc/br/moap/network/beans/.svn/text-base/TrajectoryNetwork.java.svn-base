/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.beans;

import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.sql.SQLException;
import org.gephi.io.importer.api.EdgeDefault;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryNetwork implements NetworkBuilder {

    private Network network;
    private String trajectory_dataset;
    private int encounter;
    private NetworkController nc;
    private double st; // Sptial Threshold
    private double tt; // Temporal Threshold
    
    /**
     * 
     * @param network
     * @param trajectory_dataset
     * @param st
     * @param tt
     * @param encounter
     * @throws Exception
     */
    public TrajectoryNetwork(Network network,String trajectory_dataset,double st, double tt, int encounter) throws Exception{
        this.network = network;
        this.st = st;
        this.tt = tt;
        this.trajectory_dataset = trajectory_dataset;
        this.nc = NetworkController.getInstance();
    }
    
    private double meters2degrees(double meters){
        double degrees = 0.0;
        
        double PI = 3.14159;
	double R_EARTH = 6371000;
	degrees = (meters*(180.0/PI/R_EARTH));
        
        return degrees;
    }
    
    /**
     * 
     */
    @Override
    public void buildNetwork() {
        try {
            
            
            String node_query = ""
                    + " select userid as id, userlabel as label,st_astext(st_makeline(object)) as object"
                    + " from "+trajectory_dataset + " t"
                    + " group by userid,userlabel";
            
            String edge_query = ""
                    + " select user1 as source,user2 as target,count(*) as weight"
                    + " from"
                    + " ("
                    + " select t1.userid user1, t2.userid user2"
                    + " from"
                    + "  "
                    + trajectory_dataset
                    + "  t1,"
                    + "  "
                    + trajectory_dataset
                    + "  t2"
                    + " where ST_DWithin(t1.object,t2.object,"+Double.toString(meters2degrees(st))+")"
                    + " and abs(extract(epoch from t1.datetime) - extract(epoch from t2.datetime)) <= "+Double.toString(tt)
                    + " and t1.userid < t2.userid"
                    + " ) a"
                    + " group by user1,user2"
                    + " having count(*) > "+encounter;
            System.out.println(node_query);
            System.out.println(edge_query);
            network.setEdgeType(EdgeDefault.UNDIRECTED);
            nc.storeQueryAsNetwork(network, node_query, "id", edge_query, "source,target");
            nc.computeProperties(network.getName());
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
}
