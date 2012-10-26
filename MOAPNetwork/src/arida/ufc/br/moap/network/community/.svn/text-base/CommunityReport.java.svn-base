/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;


import arida.ufc.br.moap.network.chart.CumulativeDistribution;
import arida.ufc.br.moap.network.chart.HistogramChart;
import arida.ufc.br.moap.network.chart.TimeSeriesChart;
import arida.ufc.br.moap.network.chart.XYBarChart;
import arida.ufc.br.moap.network.chart.ScatterChart;
import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.moap.network.networkmanager1.EdgeProperty;
import arida.ufc.br.moap.network.networkmanager1.NetworkPropertyInterface;
import arida.ufc.br.moap.network.networkmanager1.NetworkReport;
import arida.ufc.br.moap.network.networkmanager1.NodeProperty;
import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class CommunityReport {

    private Network network;
    private String trajectoryQuery;
    private String userQuery;
    private String edgeQuery;
    private String nodeQuery;
    private String moveAsTrajectoryQuery, moveAsUserQuery, inweightQuery, outweightQuery;
    private String degreeQuery, indegreeQuery, outdegreeQuery;
    private String avgdistanceQuery, avg_move_time;
    private String poiQuery;
    private String typepoiQuery;
    private String timeQuery;
    private NetworkController nc;
    private DBManager db;
    private String trip_table;
    private String location_table;
    private String nodecomm;
    private String edge;
    private String node;
    private String edgecomm;
    private String commreport;
    private Logger logger = Logger.getLogger(CommunityReport.class);

    public CommunityReport(Network network) throws Exception {
        logger.info("COMMUNITY REPORT");
        this.nc = NetworkController.getInstance();
        db = nc.getDatabase();
        this.network = network;
        this.nodecomm = network.getName() + NetworkController.NODE_COMMUNITY_SUFIX;
        this.edgecomm = network.getName() + NetworkController.EDGE_COMMUNITY_SUFIX;
        this.edge = network.getName() + NetworkController.EDGE_SUFIX;
        this.node = network.getName() + NetworkController.NODE_SUFIX;
        this.commreport = network.getName()+NetworkController.COMMUNITY_REPORT_SUFIX;
    }

    public void createReport(String trip_table, String location_table) throws Exception {
        logger.info("CREATE REPORT");
        this.db.getConnection().setAutoCommit(false);
        this.trip_table = trip_table;
        this.location_table = location_table;
        String table = this.network.getName() + NetworkController.COMMUNITY_REPORT_SUFIX;
        createQueries();

        String mainQuery = "select q1.comm,q1.count " + CommunityProperty.NODES.name() + ", q2.count " + CommunityProperty.EDGES.name() + ","
                + " q3.count " + CommunityProperty.STOPS_AS_TRAJECTORIES.name() + ",q4.count " + CommunityProperty.STOPS_AS_USERS.name()+ ","
                + " q5.count " + CommunityProperty.POIS.name() + ","
                + " q6.count " + CommunityProperty.POI_TYPES.name() + ", "
                + " q7.count avg_distance,"
                + " q8.count " + CommunityProperty.MOVES_AS_TRAJECTORIES.name() + ","
                + " q9.count " + CommunityProperty.MOVES_AS_USERS.name() + ","
                + " q10.count " + CommunityProperty.AVG_STOP_TIME.name() + ","
                + " q11.count " + CommunityProperty.AVG_MOVE_TIME.name() + " "
                + " from (" + nodeQuery + ") q1, "
                + " (" + edgeQuery + ") q2, "
                + " (" + trajectoryQuery + ") q3, "
                + " (" + userQuery + ") q4, "
                + " (" + poiQuery + ") q5, "
                + " (" + typepoiQuery + ") q6, "
                + " (" + avgdistanceQuery + ") q7, "
                + " (" + moveAsTrajectoryQuery + ") q8, "
                + " (" + moveAsUserQuery + ") q9, "
                + " (" + timeQuery + ") q10, "
                + " (" + avg_move_time + ") q11 "
                + " where q1.comm=q2.comm and q2.comm = q3.comm and q3.comm = q4.comm and q4.comm = q5.comm"
                + " and q5.comm = q6.comm and q6.comm = q7.comm and q7.comm=q8.comm and q8.comm = q9.comm "
                + " and q9.comm = q10.comm and q10.comm = q11.comm";
        System.out.println(mainQuery);
        db.createTableAsQuery(table, mainQuery, "comm");
        
        CommunityCompactness instance = new CommunityCompactness(network.getName(), location_table, trip_table);
        String compactnessQ = instance.getCompactnessCommunityTrajectory();
        db.addColumn(network.getName()+NetworkController.COMMUNITY_REPORT_SUFIX, "compactness","numeric DEFAULT 0.0");
        
        
        Statement statement = this.db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(compactnessQ);
        PreparedStatement ps = this.db.getConnection().prepareStatement("UPDATE "+network.getName()+NetworkController.COMMUNITY_REPORT_SUFIX+" SET compactness = ? WHERE comm = ?");
        while(rs.next()){
            String comm = rs.getString("comm");
            Double compactness = rs.getDouble("compactness");
            
            ps.setString(2, comm);
            ps.setDouble(1, compactness);
            
            ps.executeUpdate();
        }
        ps.close();
        statement.close();
        this.db.getConnection().commit();
    }

    public void createTemporalReport(String trip_table, String location_table, String time1, String time2) throws Exception {
        logger.info("CREATE REPORT");
        String table = this.network.getName() + NetworkController.COMMUNITY_REPORT_SUFIX;
        createQueries(trip_table, location_table, time1, time2);

        String mainQuery = "select q1.comm,q1.count " + CommunityProperty.NODES + ","
                + " q2.count " + CommunityProperty.EDGES + ","
                + " q3.count " + CommunityProperty.STOPS_AS_TRAJECTORIES + ","
                + " q4.count " + CommunityProperty.STOPS_AS_USERS + ","
                + " q5.count " + CommunityProperty.POIS + ","
                + " q6.count " + CommunityProperty.POI_TYPES + ", "
                + " q8.count " + CommunityProperty.MOVES_AS_TRAJECTORIES + ","
                + " q9.count " + CommunityProperty.MOVES_AS_USERS + ","
                + " q10.count " + CommunityProperty.AVG_STOP_TIME + ","
                + " q11.count " + CommunityProperty.AVG_MOVE_TIME + " "
                + " from (" + nodeQuery + ") q1, "
                + " (" + edgeQuery + ") q2, "
                + " (" + trajectoryQuery + ") q3, "
                + " (" + userQuery + ") q4, "
                + " (" + poiQuery + ") q5, "
                + " (" + typepoiQuery + ") q6, "
                + " (" + moveAsTrajectoryQuery + ") q8, "
                + " (" + moveAsUserQuery + ") q9, "
                + " (" + timeQuery + ") q10, "
                + " (" + avg_move_time + ") q11 "
                + " where q1.comm=q2.comm and q2.comm = q3.comm and q3.comm = q4.comm and q4.comm = q5.comm"
                + " and q5.comm = q6.comm and q6.comm = q8.comm  and q8.comm = q9.comm "
                + " and q9.comm = q10.comm and q10.comm = q11.comm";
        System.out.println(mainQuery);
        db.createTableAsQuery(table, mainQuery, "comm");
    }

    // ------------------------------------------------- COMMUNITY REPORT
    private void createQueries() {
        logger.info("CREATE QUERIES");

        String aux = "";
        String core = "";
        trajectoryQuery = "\n-- TRAJECTORY REPORT\n"
                + " select comm,count(*) "
                + " from "
                + " ("
                + " select distinct c.comm comm,t.id user_id,t.trip trip_id "
                + " from " + trip_table + " t, " + location_table + " l, " + nodecomm + " c "
                + " where t.id2 = l.clus and l.clus::text = c.node "
                + " order by c.comm,t.id,t.trip) a "
                + " group by comm"
                + " order by comm\n";
        userQuery = "\n-- USER REPORT\n"
                + " select comm,count(*) "
                + " from "
                + " ("
                + " select distinct c.comm comm,t.id user_id"
                + " from " + trip_table + " t, " + location_table + " l, " + nodecomm + " c "
                + " where t.id2 = l.clus and l.clus::text = c.node "
                + " order by c.comm,t.id) a "
                + " group by comm"
                + " order by comm\n";

        edgeQuery = "\n-- EDGE REPORT\n"
//                + " select comm,count(*)"
//                + " from"
//                + " ("
                + " select e.comm,count(*)"
                + " from "+ edgecomm+" e"
//                + " ) a"
                + " group by comm";
        nodeQuery = "\n-- NODE REPORT\n"
                + " select comm,count(*)"
                + " from " + nodecomm
                + " group by comm\n";

        poiQuery = "\n-- POI REPORT\n"
                + " select comm,sum(a.count) count"
                + " from " + nodecomm + ","
                + " (select clus,count(*) count from " + location_table + " group by clus) a"
                + " where a.clus::text = node"
                + " group by comm\n";

        typepoiQuery = "\n-- TYPE OF POI REPORT\n"
                + " select comm,count(*)"
                + " from"
                + " ("
                + " select distinct comm,general"
                + " from " + nodecomm + ","
                + " (select distinct clus,general from " + location_table + ") a"
                + " where a.clus::text = node"
                + " ) a"
                + " group by comm\n";

        avgdistanceQuery = "\n-- DISTANCIA ESPACIAL ENTRE OS NODES\n"
                + " SELECT e.comm,(avg(st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))))/1000 count"
                + " from"
                + " (select clus,st_centroid(st_union(object)) cen, nc.comm"
                + " from " + location_table + " c, " + nodecomm + " nc"
                + " where c.clus::text = nc.node"
                + " group by clus,nc.comm) c1,"
                + " (select clus,st_centroid(st_union(object)) cen, nc.comm"
                + " from " + location_table + " c, " + nodecomm + " nc"
                + " where c.clus::text = nc.node"
                + " group by clus,nc.comm) c2, "+edgecomm+" e"
                + " where c1.clus::text = e.source and c2.clus::text = e.target"
                + " group by e.comm\n";



        aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from " + trip_table + " t"
                + " ORDER BY start_time,end_time)";

        core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

        moveAsUserQuery = " select comm,count(*) count"
                + " from"
                + " ("
                + " select distinct  e.comm,t1.id"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " ) a"
                + " group by comm";
        moveAsTrajectoryQuery = " select comm,count(*) count"
                + " from"
                + " ("
                + " select distinct  e.comm,t1.id,t1.trip"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " ) a"
                + " group by comm";

        avg_move_time = " select comm,avg(avg)/3600 count"
                + " from"
                + " ("
                + " select e.comm,t1.id2,t2.id2,avg(extract(epoch from t1.min)-extract(epoch from t1.end_time)) avg"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and t1.min = t2.start_time and t1.id2!=t2.id2"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " group by e.comm,t1.id2,t2.id2"
                + " ) a"
                + " group by comm";

        timeQuery = "\n-- AVG STOP TIME REPORT\n"
                + " select c1.comm,avg(n.avg_time::numeric) count"
                + " from " + nodecomm + " c1, " + node + " n"
                + " where c1.node = n.id"
                + " group by c1.comm\n";
    }

    // ------------------------------------------------- COMMUNITY TEMPORAL REPORT
    private void createQueries(String trip_table, String location_table, String time1, String time2) {
        logger.info("CREATE QUERIES");
        trajectoryQuery = "\n-- TRAJECTORY REPORT\n"
                + " select comm,count(*) "
                + " from "
                + " ("
                + " select distinct c.comm comm,t.id user_id,t.trip trip_id "
                + " from " + trip_table + " t, " + location_table + " l, " + nodecomm + " c "
                + " where t.id2 = l.clus and l.clus::text = c.node "
                + " and t.start_time >= '" + time1 + "' and t.start_time <= '" + time2 + "'"
                + " order by c.comm,t.id,t.trip) a "
                + " group by comm"
                + " order by comm\n";

        userQuery = "\n-- USER REPORT\n"
                + " select comm,count(*) "
                + " from "
                + " ("
                + " select distinct c.comm comm,t.id user_id"
                + " from " + trip_table + " t, " + location_table + " l, " + nodecomm + " c "
                + " where t.id2 = l.clus and l.clus::text = c.node "
                + " and t.start_time >= '" + time1 + "' and t.start_time <= '" + time2 + "'"
                + " order by c.comm,t.id) a "
                + " group by comm"
                + " order by comm\n";

        nodeQuery = "\n-- NODE REPORT\n"
                + " select comm,count(*)"
                + " from ("
                + " select distinct clus,comm"
                + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                + " where l.clus::text = n.node"
                + " and t.id2 = l.clus"
                + " and t.start_time >='" + time1 + "' and t.start_time <='" + time2 + "'"
                + " ) a"
                + " group by comm\n";

        poiQuery = "\n-- POI REPORT\n"
                + " select comm,count(*)"
                + " from ("
                + " select distinct id2,comm"
                + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                + " where l.clus::text = n.node"
                + " and t.id2 = l.clus"
                + " and t.start_time >='" + time1 + "' and t.start_time <='" + time2 + "'"
                + " ) a"
                + " group by comm\n";


        typepoiQuery = "\n-- TYPE OF POI REPORT\n"
                + " select comm,count(*)"
                + " from ("
                + " select distinct general,comm"
                + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                + " where l.clus::text = n.node"
                + " and t.id2 = l.clus"
                + " and t.start_time >='" + time1 + "' and t.start_time <='" + time2 + "'"
                + " ) a"
                + " group by comm\n";


//        avgdistanceQuery = "\n-- DISTANCIA ESPACIAL ENTRE OS NODES\n"
//                + " SELECT c1.comm,(avg(st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))))/1000 count"
//                + " from"
//                + " (select clus,st_centroid(st_union(object)) cen, nc.comm"
//                + " from " + location_table + " c, "+nodecomm+" nc"
//                + " where c.clus::text = nc.node"
//                + " group by clus,nc.comm) c1,"
//                + " (select clus,st_centroid(st_union(object)) cen, nc.comm"
//                + " from " + location_table + " c, "+nodecomm+" nc"
//                + " where c.clus::text = nc.node"
//                + " group by clus,nc.comm) c2"
//                + " where c1.comm = c2.comm and c1.clus < c2.clus"
//                + " group by c1.comm\n";

        String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from " + trip_table + " t"
                + " ORDER BY start_time,end_time)";

        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

        avg_move_time = " select comm,avg(move_time)/3600 count"
                + " from"
                + " ("
                + " select distinct e.comm ,t1.id,t1.trip,t1.id2,t2.id2,extract(epoch from t1.min)-extract(epoch from t1.end_time) as move_time"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and t1.end_time >='" + time1 + "' and t2.start_time <='" + time2 + "'"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " ) a"
                + " group by comm";

        edgeQuery = " select comm,count(*) count"
                + " from"
                + " ("
                + " select distinct e.comm ,t1.id2,t2.id2"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and t1.end_time >='" + time1 + "' and t2.start_time <='" + time2 + "'"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " ) a"
                + " group by comm";


        moveAsTrajectoryQuery = " select comm,count(*) count"
                + " from"
                + " ("
                + " select distinct e.comm ,t1.id,t1.trip"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and t1.end_time >='" + time1 + "' and t2.start_time <='" + time2 + "'"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " ) a"
                + " group by comm";

        moveAsUserQuery = " select comm,count(*) count"
                + " from"
                + " ("
                + " select distinct e.comm ,t1.id"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and t1.end_time >='" + time1 + "' and t2.start_time <='" + time2 + "'"
                + " and e.source = t1.id2::text and e.target = t2.id2::text"
                + " ) a"
                + " group by comm";

        timeQuery = "\n-- AVG STOP TIME REPORT\n"
                + " select comm,avg(avg_stop_time) count"
                + " from ("
                + " select clus,avg(time_duration) avg_stop_time"
                + " from " + location_table + " l, " + trip_table + " t"
                + " where t.id2 = l.clus"
                + " and t.start_time >='" + time1 + "' and t.start_time <='" + time2 + "'"
                + " group by clus"
                + " ) a," + nodecomm + " n"
                + " where a.clus::text = n.node"
                + " group by comm\n";

    }

    public void plotDistributionCommunityReport(CommunityProperty property) {
        try {
            PreparedStatement ps = db.getConnection().prepareStatement("select comm," + property.toString() + " as property from " + network.getName() + NetworkController.COMMUNITY_REPORT_SUFIX);

            ResultSet rs = ps.executeQuery();
            int size = 0;
            while (rs.next()) {
                size++;
            }
            double[] data = new double[size];
            size = 0;
            rs = ps.executeQuery();
            while (rs.next()) {
                data[size] = rs.getDouble("property");
                size++;

            }

            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("Property: " + property, data, size);

//            HistogramChart chart = new HistogramChart("Community Report", "Property: " + property, dataset);
//            chart.createPlot();

        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotPassingCommunityDistribution(String location_table, String trip_table){
        try {
            CommunityCompactness passingCommunity = new CommunityCompactness(this.network.getName(), location_table, trip_table);
            String query = passingCommunity.getCompactnessCommunityTrajectory();
            
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            int size = 0;
            XYSeriesCollection dataset = new XYSeriesCollection();
            XYSeries serie = new XYSeries("Communities");
            while(rs.next()){
                serie.add(rs.getDouble("ce"),rs.getDouble("cp"));   
            }
            dataset.addSeries(serie);
            
            ScatterChart chart = new ScatterChart("Communit Report", "Number of Edges", "Cp", dataset);
            chart.createPlot();
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
    
        public void plotPassingCommunityHistogram(String location_table, String trip_table){
        try {
            CommunityCompactness passingCommunity = new CommunityCompactness(this.network.getName(), location_table, trip_table);
            String query = passingCommunity.getCompactnessCommunityTrajectory();
            System.out.println(query);
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            int size = 0;
            while(rs.next()){
                size++; 
            }
            double[] data = new double[size];
            rs = s.executeQuery(query);
            size = 0;
            while(rs.next()){
                data[size] = rs.getDouble("cp");
                size++; 
            }
            
//            CumulativeDistribution c = new CumulativeDistribution(data);
//            c.getCumulativeDistribution();
            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries("", data, 10);
            HistogramChart chart = new HistogramChart("Cp Distribution", "", "", dataset);
            chart.createPlot();
                

        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
    
    public void plotPassingCommunityCumulativeDistribution(String location_table, String trip_table){
        try {
            CommunityCompactness passingCommunity = new CommunityCompactness(this.network.getName(), location_table, trip_table);
            String query = passingCommunity.getCompactnessCommunityTrajectory();
            System.out.println(query);
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            ArrayList<Double> cps = new ArrayList<Double>();
            int size = 0;
            while(rs.next()){
                cps.add(rs.getDouble("cp"));
            }
            
            CumulativeDistribution c = new CumulativeDistribution(cps);
            c.getCumulativeDistribution("Cumulative Distribution of Cp","k","P(Compactness ≤ k)");
                

        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
        
    public void plotDistributionCommunityReport(Object community_id, CommunityProperty property, String location_table, String trip_table, boolean loglogaxis, boolean network_series) {
        try {
            this.location_table = location_table;
            this.trip_table = trip_table;
            String query = getQueryReport(property, trip_table, location_table);
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, community_id.toString());
            ResultSet rs = ps.executeQuery();
            int size = 0;
            XYSeries serie = new XYSeries(community_id.toString());
            while (rs.next()) {
                serie.addOrUpdate(rs.getDouble("property"), rs.getDouble("value"));
            }

            XYSeriesCollection collection = new XYSeriesCollection();
            if (network_series) {
                NetworkReport nr = new NetworkReport(network.getName(), location_table, trip_table);
                if (NodeProperty.isNodeProperty(property.toString())) {
                    collection.addSeries(nr.getXYSerieNetwork(NodeProperty.valueOf(property.toString())));
                }
                else {
                    if (EdgeProperty.isEdgeProperty(property.toString())) {
                        collection.addSeries(nr.getXYSerieNetwork(EdgeProperty.valueOf(property.toString())));
                    }
                }

            }
            collection.addSeries(serie);

            String xaxis = property.toString();
            String yaxis = "Frequency";
            if (property.toString().contains("_AS_")) {
                xaxis = property.toString().split("_AS_")[0];
                yaxis = property.toString().split("_AS_")[1];
            }

            ScatterChart chart = new ScatterChart("Community Report", xaxis, yaxis, collection);
            chart.setLogarithmicAxis(loglogaxis);
            chart.createPlot();


        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotDistributionCommunityReport(List<Object> community_ids, CommunityProperty property, String location_table, String trip_table, boolean loglogaxis, boolean network_series) {
        try {
            this.location_table = location_table;
            this.trip_table = trip_table;
            String query = getQueryReport((CommunityProperty) property, trip_table, location_table);
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            XYSeriesCollection collection = new XYSeriesCollection();

            for (Object o : community_ids) {
                String community_id = o.toString();
                XYSeries serie = new XYSeries(community_id);
                ps.setString(1, community_id);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    serie.add(rs.getDouble("property"), rs.getDouble("value"));
                }
                collection.addSeries(serie);
                ps.clearParameters();
                rs.close();

            }
            ps.close();
            System.out.println(collection.getSeriesCount());

            String xaxis = property.toString();
            String yaxis = "Frequency";
            if (property.toString().contains("_AS_")) {
                xaxis = property.toString().split("_AS_")[0];
                yaxis = property.toString().split("_AS_")[1];
            }
            if (network_series) {
                NetworkReport nr = new NetworkReport(network.getName(), location_table, trip_table);
                if (NodeProperty.isNodeProperty(property.toString())) {
                    collection.addSeries(nr.getXYSerieNetwork(NodeProperty.valueOf(property.toString())));
                }
                else {
                    if (EdgeProperty.isEdgeProperty(property.toString())) {
                        collection.addSeries(nr.getXYSerieNetwork(EdgeProperty.valueOf(property.toString())));
                    }
                }

            }
            ScatterChart chart = new ScatterChart("Community Report", xaxis, yaxis, collection);
            chart.setLogarithmicAxis(loglogaxis);
            chart.createPlot();
//            HistogramChart chart = new HistogramChart("Community Report", xaxis,yaxis, collection);
//            chart.createPlot();


        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotDistributionCommunityReport(Object community_id, CommunityProperty property, String location_table, String trip_table,boolean network) {
        try {
            this.location_table = location_table;
            this.trip_table = trip_table;
            String query = getQueryReport(property, trip_table, location_table);
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, community_id.toString());
            ResultSet rs = ps.executeQuery();
            int size = 0;
            while (rs.next()) {
                size++;
            }
            double[] data = new double[size];
            rs = ps.executeQuery();
            size = 0;
            float max = 0f;
            float aux = 0f;
            while (rs.next()) {
                aux = rs.getFloat("value");
                data[size] = aux;
                size++;

                if (max < aux) {
                    max = aux;
                }
            }
            HistogramDataset dataset = new HistogramDataset();
            dataset.addSeries(community_id.toString(), data, Math.round(max));
//            if(network){
//                NetworkReport nr = new NetworkReport(this.network.getName(),location_table,trip_table);
//                if(NodeProperty.isNodeProperty(property.toString())){
//                     dataset.addSeries("Network",nr.getDataNetwork(NodeProperty.valueOf(property.toString())),Math.round(max));
//                }
//                else{
//                    if(EdgeProperty.isEdgeProperty(property.toString())){
//                        dataset.addSeries("Network",nr.getDataNetwork(EdgeProperty.valueOf(property.toString())),Math.round(max));
//                    }
//                }
//               
//            }


            String xaxis = property.toString();
            String yaxis = "Frequency";
            if (property.toString().contains("_AS_")) {
                xaxis = property.toString().split("_AS_")[0];
                yaxis = property.toString().split("_AS_")[1];
            }

            HistogramChart chart = new HistogramChart("Community Report", xaxis,yaxis, dataset);
            chart.createPlot();



        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotDistributionCommunityReport(List<Object> community_ids, CommunityProperty property, String location_table, String trip_table,boolean network) {
        try {
            this.location_table = location_table;
            this.trip_table = trip_table;
            String query = getQueryReport((CommunityProperty) property, trip_table, location_table);
            System.out.println(query);
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            HistogramDataset dataset = new HistogramDataset();

//            if(network){
//                NetworkReport nr = new NetworkReport(this.network.getName(),location_table,trip_table);
//                
//                if(NodeProperty.isNodeProperty(property.toString())){
//                    double[] d = nr.getDataNetwork(NodeProperty.valueOf(property.toString()));
//                     dataset.addSeries("Network",d,Math.round(d.length));
//                }
//                else{
//                    if(EdgeProperty.isEdgeProperty(property.toString())){
//                        double[] d = nr.getDataNetwork(EdgeProperty.valueOf(property.toString()));
//                        dataset.addSeries("Network",d,Math.round(d.length));
//                    }
//                }
//               
//            }
            
            for (Object o : community_ids) {
                String community_id = o.toString();
                ps.setString(1, community_id);
                ResultSet rs = ps.executeQuery();
                int size = 0;
                while (rs.next()) {
                    size= size + rs.getInt("weight");
                }
                double[] data = new double[size];
                rs = ps.executeQuery();
                size = 0;
                float max = 0f;
                float aux = 0f;
                while (rs.next()) {
                    aux = rs.getFloat("value");
                    int n = rs.getInt("weight");
                    for(int i=0; i< n ;i++){
                        data[size] = aux;
                        size++;
                    }
                    

                    if (max < aux) {
                        max = aux;
                    }
                }

                dataset.addSeries(community_id.toString(), data, Math.round(max));
                ps.clearParameters();
                rs.close();

            }
            ps.close();


            String xaxis = property.toString();
            String yaxis = "Frequency";
            if (property.toString().contains("_AS_")) {
                xaxis = property.toString().split("_AS_")[0];
                yaxis = property.toString().split("_AS_")[1];
            }
//                        if(network_series){
//                NetworkReport nr = new NetworkReport(network.getName(),location_table,trip_table);
//                if(NodeProperty.isNodeProperty(property.toString())){
//                     collection.addSeries(nr.getXYSerieNetwork(NodeProperty.valueOf(property.toString())));
//                }
//                else{
//                    if(EdgeProperty.isEdgeProperty(property.toString())){
//                        collection.addSeries(nr.getXYSerieNetwork(EdgeProperty.valueOf(property.toString())));
//                    }
//                }
//               
//            }
//            ScatterChart chart = new ScatterChart("Community Report", xaxis, yaxis, collection);
//            chart.setLogarithmicAxis(loglogaxis);
//            chart.createPlot();
            HistogramChart chart = new HistogramChart("Community Report", "Distance (km)",property.toString(), dataset);
            chart.setLines(true);
            chart.createPlot();


        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotCommunityReport(CommunityProperty property,boolean loglog) {
        try {
            PreparedStatement ps = db.getConnection().prepareStatement("select " + property.name() + " as property,count(*) as count "
                    + " from " + network.getName() + NetworkController.COMMUNITY_REPORT_SUFIX
                    + " group by "+property.name()
                    
                    );

            ResultSet rs = ps.executeQuery();
            Map<String, Map<String, Double>> map = new HashMap<String, Map<String, Double>>();
            Map<String, Double> aux = new HashMap<String, Double>();
            ArrayList<Double> comms = new ArrayList<Double>();
            ArrayList<Double> value = new ArrayList<Double>();
            XYSeries serie = new XYSeries(property);
            int size = 0;
            while (rs.next()) {
                serie.add(rs.getDouble("property"), rs.getDouble("count"));
////                comms.add(rs.getDouble("comm"));
////                value.add(rs.getDouble("property"));
                size++;
////                aux.put(, rs.getDouble("property"));
            }
            XYSeriesCollection dataset = new XYSeriesCollection(serie);
//            rs = ps.executeQuery();
//            double[][] data = new double[2][size];
//            size = 0;
//            while (rs.next()) {
//                data[0][size] = rs.getDouble("comm");
//                data[1][size] = rs.getDouble("property");
//                size++;
//            }


//            map.put("Property: " + property, aux);
//            BarChart hist = new BarChart("Bar", "Community", map);
//            hist.createPlot();
//            DefaultXYDataset dataset = new DefaultXYDataset();
//            dataset.addSeries(property, data);
//            XYBarDataset xydataset = new XYBarDataset(dataset, 1);
//            XYBarChart chart = new XYBarChart("Property: " + property, "Community", xydataset);
//            chart.createPlot();
            ScatterChart chart = new ScatterChart("Community Report", "Number of Edges", "Number of Communities", dataset);
            
            chart.setLogarithmicAxis(loglog);
            chart.createPlot();
            

        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotTemporalCommunityReport(CommunityProperty property, Object community_id, String time1, String time2,
            TimePeriod period, String trip_table, String location_table, boolean network_series) {

        String id = community_id.toString();
        String propertyQuery = getQueryTimeReport(property, period, trip_table, location_table);
        try {
            PreparedStatement ps = db.getConnection().prepareCall(propertyQuery);
            ps.setTimestamp(1, Timestamp.valueOf(time1));
            ps.setTimestamp(2, Timestamp.valueOf(time2));
            ps.setString(3, id);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            TimeSeries serie = new TimeSeries(id);

            switch (period) {
                case HOUR:
                    while (rs.next()) {
                        serie.add(new Hour(rs.getInt("hour"), rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), rs.getInt("count"));
                    }
                    break;
                case DAY:
                    while (rs.next()) {
                        serie.add(new Day(rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), rs.getInt("count"));
                    }
                    break;
                case MONTH:
                    while (rs.next()) {
                        serie.add(new Month(rs.getInt("month"), rs.getInt("year")), rs.getInt("count"));
                    }
                    break;
                case YEAR:
                    while (rs.next()) {
                        serie.add(new Year(rs.getInt("year")), rs.getInt("count"));
                    }
                    break;
            }

            TimeSeriesCollection dataset = new TimeSeriesCollection();
            
            dataset.addSeries(serie);
            if (network_series) {
                NetworkReport nr = new NetworkReport(network.getName(), location_table, trip_table);
                dataset.addSeries(nr.getTimeSerieNetwork(property, time1, time2, period));
            }

            TimeSeriesChart chart = new TimeSeriesChart("Community Report", period.toString(), "Property: " + property.toString(), dataset);
            chart.createPlot();
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void plotTemporalCommunityReport(CommunityProperty property, List community_ids, String time1, String time2,
            TimePeriod period, String trip_table, String location_table, boolean network_series) {


        String propertyQuery = getQueryTimeReport(property, period, trip_table, location_table);
        try {
            TimeSeriesCollection dataset = new TimeSeriesCollection();
            
            for (Object o : community_ids) {
                String community_id = o.toString();

                PreparedStatement ps = db.getConnection().prepareCall(propertyQuery);
                ps.setTimestamp(1, Timestamp.valueOf(time1));
                ps.setTimestamp(2, Timestamp.valueOf(time2));
                ps.setString(3, community_id);
                System.out.println(ps.toString());
                ResultSet rs = ps.executeQuery();
                TimeSeries serie = new TimeSeries(community_id);

                switch (period) {
                    case HOUR:
                        while (rs.next()) {
                            serie.add(new Hour(rs.getInt("hour"), rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), rs.getInt("count"));
                        }
                        break;
                    case DAY:
                        while (rs.next()) {
                            serie.add(new Day(rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), rs.getInt("count"));
                        }
                        break;
                    case MONTH:
                        while (rs.next()) {
                            serie.add(new Month(rs.getInt("month"), rs.getInt("year")), rs.getInt("count"));
                        }
                        break;
                    case YEAR:
                        while (rs.next()) {
                            serie.add(new Year(rs.getInt("year")), rs.getInt("count"));
                        }
                        break;
                }



                dataset.addSeries(serie);


            }
            if (network_series) {
                NetworkReport nr = new NetworkReport(network.getName(), location_table, trip_table);
                dataset.addSeries(nr.getTimeSerieNetwork(property, time1, time2, period));
            }
            
            TimeSeriesChart chart = new TimeSeriesChart("Community Report", period.toString(), "Property: " + property.toString(), dataset);
            chart.createPlot();
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private String getQueryTimeReport(CommunityProperty property, TimePeriod period, String trip_table, String location_table) {
        String query = "";
        String aux = "";
        String core = "";
        switch (property) {
            case NODES:
                query = "\n-- NODE REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as count"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",clus,comm"
                        + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                        + " where l.clus::text = n.node"
                        + " and t.id2 = l.clus"
                        + " and t.start_time >= ? and t.start_time <= ?"
                        + " and comm = ?) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case STOPS_AS_TRAJECTORIES:
                query = "\n-- TRAJECTORIES REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as count"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",t.id user_id,t.trip trip_id,comm"
                        + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                        + " where l.clus::text = n.node"
                        + " and t.id2 = l.clus"
                        + " and t.start_time >= ? and t.start_time <= ?"
                        + " and comm = ?) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case STOPS_AS_USERS:
                query = "\n-- USERS REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as count"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",n.comm comm,t.id user_id"
                        + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                        + " where l.clus::text = n.node"
                        + " and t.id2 = l.clus"
                        + " and t.start_time >= ? and t.start_time <= ?"
                        + " and comm = ?) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case POIS:
                query = "\n-- USERS REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as count"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",n.comm comm,l.id"
                        + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                        + " where l.clus::text = n.node"
                        + " and t.id2 = l.clus"
                        + " and t.start_time >= ? and t.start_time <= ?"
                        + " and comm = ?) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case POI_TYPES:
                query = "\n-- USERS REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as count"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",n.comm comm,l.general"
                        + " from " + location_table + " l, " + trip_table + " t," + nodecomm + " n"
                        + " where l.clus::text = n.node"
                        + " and t.id2 = l.clus"
                        + " and t.start_time >= ? and t.start_time <= ?"
                        + " and comm = ?) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case AVG_STOP_TIME:
                query = "\n-- AVG STOP TIME REPORT\n"
                        + " select " + period.getFormat2Select() + ",avg(avg_stop_time)/3600 count"
                        + " from ("
                        + " select clus," + period.getFormat2Query("start_time") + ",avg(time_duration) avg_stop_time"
                        + " from " + location_table + " l, " + trip_table + " t"
                        + " where t.id2 = l.clus"
                        + " and t.start_time >=? and t.start_time <= ?"
                        + " group by clus," + period.getFormat2GroupBy("start_time")
                        + " ) a," + nodecomm + " n"
                        + " where a.clus::text = n.node and comm = ?"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case EDGES:
                aux = "(select distinct t.id,trip,clus as id2,start_time,end_time,time_duration "
                        + " from " + trip_table + " t"
                        + " ORDER BY start_time,end_time)";

                core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                        + " from " + aux + " t1," + aux + " t2"
                        + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                        + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

                query = " select " + period.getFormat2Select() + ",count(*) count"
                        + " from"
                        + " ("
                        + " select distinct " + period.getFormat2Query("t1.end_time") + " ,t1.id2,t2.id2"
                        + " from"
                        + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                        + " and t1.end_time >=? and t2.start_time <=?"
                        + " and e.source = t1.id2::text and e.target = t2.id2::text and e.comm = ?"
                        + " ) a"
                        + " group by " + period.getFormat2Select();
                break;
            case MOVES_AS_TRAJECTORIES:
                aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                        + " from " + trip_table + " t)";

                core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                        + " from " + aux + " t1," + aux + " t2"
                        + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                        + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

                query = " select " + period.getFormat2Select() + ",count(*) count"
                        + " from"
                        + " ("
                        + " select distinct " + period.getFormat2Query("t1.end_time") + " ,t1.id,t1.trip"
                        + " from"
                        + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                        + " and t1.end_time >=? and t2.start_time <=?"
                        + " and e.source = t1.id2::text and e.target = t2.id2::text and e.comm = ?"
                        + " ) a"
                        + " group by " + period.getFormat2Select();

                break;
            case MOVES_AS_USERS:
                aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                        + " from " + trip_table + " t)";

                core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                        + " from " + aux + " t1," + aux + " t2"
                        + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                        + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

                query = " select " + period.getFormat2Select() + ",count(*) count"
                        + " from"
                        + " ("
                        + " select distinct " + period.getFormat2Query("t1.end_time") + " ,t1.id"
                        + " from"
                        + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                        + " and t1.end_time >=? and t2.start_time <=?"
                        + " and e.source = t1.id2::text and e.target = t2.id2::text and e.comm = ?"
                        + " ) a"
                        + " group by " + period.getFormat2Select();
                break;

            case AVG_MOVE_TIME:
                aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                        + " from " + trip_table + " t)";

                core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                        + " from " + aux + " t1," + aux + " t2"
                        + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                        + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

                query = " select " + period.getFormat2Select() + ",avg(move_time)/3600 count"
                        + " from"
                        + " ("
                        + " select distinct " + period.getFormat2Query("t1.end_time") + " ,t1.id,t1.trip,t1.id2,t2.id2,extract(epoch from t1.min)-extract(epoch from t1.end_time) as move_time"
                        + " from"
                        + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
                        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                        + " and t1.end_time >=? and t2.start_time <=?"
                        + " and e.source = t1.id2::text and e.target = t2.id2::text and e.comm = ?"
                        + " ) a"
                        + " group by " + period.getFormat2Select();
                break;
        }


        return query;
    }

    private String getQueryReport(CommunityProperty property, String trip_table, String location_table) {
        String query = "";
        String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from " + trip_table + " t)";
        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";
        String centroid = " (select n.id,st_centroid(st_union(c.object)) cen"
                + " from " + location_table + " c, " + node + " n"
                + " where c.clus::text = n.id"
                + " group by n.id)";
        switch (property) {


            case DISTANCE_AS_TRAJECTORIES:
                query = "\n-- EDGE REPORT\n"
//                                        + " select distance/1000::numeric as property,sum(weight) as value"
//                                        + " from"
//                                        + " ("
                        + " select ec.comm,c1.id,c2.id,st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))/1000 as value,e.weight "
                        + " from " + edgecomm +" ec, " + centroid + " c1," + centroid + " c2, "+edge+" e"
                        + " where ec.source = c1.id and ec.target = c2.id and ec.comm=? "
                        + " and ec.source = e.source and ec.target = e.target"
//                        + " ) a"
//                                        + " group by distance"
                        ;
                break;
            case DISTANCE_AS_USERS:
                query = "\n-- EDGE REPORT\n"
//                                        + " select distance as property,sum(weight) as value"
//                                        + " from"
//                                        + " ("
                        + " select distinct ec.comm,c1.id,c2.id,st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))/1000 as value,e.users weight"
                        + " from " + edgecomm +" e, " + centroid + " c1," + centroid + " c2, "+edge+" e"
                        + " where ec.source = c1.id and ec.target = c2.id and ec.comm=? "
                        + " and ec.source = e.source and ec.target = e.target"
//                        + "and c1.id = n1.node and c2.id = n2.node"
//                        + " ) a"
//                                        + " group by distance"
                        ;
                break;
            case AVG_STOP_TIME:
                query = "\n-- AVG STOP TIME REPORT\n"
                        + " select time_duration/3600 as value"
                        //                       + ",count(*) as value"
                        + " from " + nodecomm + " n," + trip_table + " t"
                        + " where t.id2::text = n.node and n.comm=? " //                + " group by time_duration/3600\n"
                        ;
                break;
            default:
                query = "\n-- AVG STOP TIME REPORT\n"
                        + " select "+property.name()+" as value"
                        //                       + ",count(*) as value"
                        + " from " + commreport + " n"
                        + " " //                + " group by time_duration/3600\n"
                        ;
                break;

        }


        return query;
    }
}
