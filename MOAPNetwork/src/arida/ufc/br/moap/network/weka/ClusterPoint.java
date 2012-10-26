package arida.ufc.br.moap.network.weka;

import arida.ufc.br.moap.core.database.spi.ConnectionPropertyLocation;
import arida.ufc.br.moap.network.dbmanager.DBManager;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.gephi.io.database.drivers.PostgreSQLDriver;
import org.openide.util.Exceptions;
import weka.clusterers.DBScan;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class ClusterPoint {
	
    private String query;
    private String table;
    private int minPoints; // min number of points
    private double epsilon; // distance
    private DBManager db;
    private Logger logger = Logger.getLogger(ClusterPoint.class);
    public ClusterPoint(String query,String table,int minPoints,double epsilon){
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
//          db.getConnection().setAutoCommit(false);
          InstanceQuery iq = new InstanceQuery();
          iq.setUsername(db.getConnectionDBManager().getUsername());
          iq.setPassword(db.getConnectionDBManager().getPassword());
          Instances data = iq.retrieveInstances(query);
          this.db.createTable(table, "clus int, object geometry");
          PreparedStatement ps = db.getConnection().prepareStatement("INSERT INTO "+table+" VALUES (?,st_geomfromtext(?,4326))");
          
          // Clustering
          DBScan cluster = new DBScan();
          cluster.setMinPoints(minPoints);
          cluster.setEpsilon(epsilon);
          logger.info("Clustering data");
          System.out.println("Clustering data");
          cluster.buildClusterer(data);
          
//          ClusterEvaluation ce = new ClusterEvaluation();
//          ce.setClusterer(cluster);
//          logger.info(ce.clusterResultsToString());
          double lat,lon;
          int c;
          logger.info("Storing data");
          System.out.println("Storing data");
          for(int i = 0; i < data.numInstances(); i++){
              try{
                  lon = data.instance(i).value(0);
                  lat = data.instance(i).value(1);
//                  System.out.println(data.instance(i).toString());
                  c = cluster.clusterInstance(data.instance(i));
  //                System.out.println(lat);
                  String geom = "POINT("+lon+" "+lat+")";
//'                  ps.setDouble(1, lat);
//                  ps.setDouble(2, lon);
                  ps.setInt(1, c);
                  ps.setString(2, geom);

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
          
//          db.getConnection().commit();
          
          // Creating new table table_cluster
//          String newtable = table;
          
//          String query2 = "select * from tmp_cluster t,"+table+" i where st_x(object)=t.latitude and st_y(object)=t.longitude";
//          this.db.createTableAsQuery(newtable, query2);
//          
//          Statement stat = this.db.getConnection().createStatement();
//          stat.execute("drop table tmp_cluster");
          
          
//          this.db.getConnection().commit();
//          this.db.getConnection().close();
          
          

  }
	  
//	  public static void main(String[] args){
//		        try {
//		            System.out.println("cluster");
//		            ClusterPoint instance = new ClusterPoint(
//		            		" select st_x(st_startpoint(object)) longitude,st_y(st_startpoint(object)) latitude" +
//		            		" from stops_150m_1200s "
//		                    , "clustered_stops_milan"
//		                    , 1
//		                    , 0.00135);
//		            instance.cluster();
//		            // TODO review the generated test code and remove the default call to fail.
//		            assert true;
//		        }
//		        catch (Exception ex) {
//		            Exceptions.printStackTrace(ex);
//		        }
//	  }
}
