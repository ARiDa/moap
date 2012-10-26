/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.weka;

import arida.ufc.br.moap.network.dbmanager.DBManager;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import org.openide.util.Exceptions;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DBScan;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 *
 * @author igobrilhante
 */
public class ClusterPOI {
    
    
    private String query;
    private String table;
    private int minPoints; // min number of points
    private double epsilon; // distance
    private DBManager db;
    private Logger logger = Logger.getLogger(ClusterPOI.class);
    public ClusterPOI(String query,String table,int minPoints,double epsilon){
        try {
            this.db = DBManager.getInstance();
            this.query = query;
            this.minPoints = minPoints;
            this.epsilon = epsilon;
            this.table = table;
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    public void cluster() throws Exception{

            logger.info("Clustering "+query);
            InstanceQuery iq = new InstanceQuery();
            iq.setUsername(db.getConnectionDBManager().getUsername());
            iq.setPassword(db.getConnectionDBManager().getPassword());
            Instances data = iq.retrieveInstances(query);
            this.db.createTable("tmp_cluster", "latitude double precision,longitude double precision, clus int");
            PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO tmp_cluster VALUES (?,?,?)");
            
            // Clustering
            DBScan cluster = new DBScan();
            cluster.setMinPoints(minPoints);
            cluster.setEpsilon(epsilon);
            cluster.buildClusterer(data);
            
//            ClusterEvaluation ce = new ClusterEvaluation();
//            ce.setClusterer(cluster);
//            logger.info(ce.clusterResultsToString());
            double lat,lon;
            int c;
            for(int i = 0; i < data.numInstances(); i++){
                try{
                    lat = data.instance(i).value(0);
                    lon = data.instance(i).value(1);
//                    System.out.println(data.instance(i).toString());
                    c = cluster.clusterInstance(data.instance(i));
    //                System.out.println(lat);
                    ps.setDouble(1, lat);
                    ps.setDouble(2, lon);
                    ps.setInt(3, c);

                    ps.addBatch();
                }
                catch(Exception e){
                    System.out.println("Outlier");
                }
                
                
            }
            logger.info("Number of clusters: "+cluster.numberOfClusters());
            logger.info("Commiting");
            ps.executeBatch();
            ps.close();
            
            // Creating new table table_cluster
            String newtable = table+"_cluster$";
            
            String query2 = "select * from tmp_cluster t,"+table+" i where st_x(object)=t.latitude and st_y(object)=t.longitude";
            this.db.createTableAsQuery(newtable, query2);
            
            Statement stat = this.db.getConnection().createStatement();
            stat.execute("drop table tmp_cluster");
            
            
//            this.db.getConnection().commit();
            this.db.getConnection().close();
            

    }
}
