/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.networkmanager1;

import arida.ufc.br.moap.network.chart.CumulativeDistribution;
import arida.ufc.br.moap.network.chart.HistogramChart;
import arida.ufc.br.moap.network.chart.ScatterChart;
import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.moap.network.community.CommunityProperty;
import arida.ufc.br.moap.network.community.TimePeriod;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class NetworkReport {

    private String network;
    private String trip_table;
    private String location_table;
    private String node;
    private String edge;
    private String nodecomm;
    private DBManager db;

    public NetworkReport(String network, String location_table, String trip_table) {
        try {
            this.db = DBManager.getInstance();
            this.network = network;
            this.location_table = location_table;
            this.trip_table = trip_table;
            this.node = network + NetworkController.NODE_SUFIX;
            this.edge = network + NetworkController.EDGE_SUFIX;
            this.nodecomm = network + NetworkController.NODE_COMMUNITY_SUFIX;
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

    public void plotDegreeCumulativeDistribution(){
        try {
            
            String query = "select indegree,outdegree,inweight_as_trajectories as inweight,outweight_as_trajectories as outweight from "+node;
            System.out.println(query);
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            ArrayList<Double> indegree = new ArrayList<Double>();
            ArrayList<Double> outdegree = new ArrayList<Double>();
            ArrayList<Double> inweight = new ArrayList<Double>();
            ArrayList<Double> outweight = new ArrayList<Double>();
            int size = 0;
            while(rs.next()){
                indegree.add(rs.getDouble("indegree"));
                outdegree.add(rs.getDouble("outdegree"));
                inweight.add(rs.getDouble("inweight"));
                outweight.add(rs.getDouble("outweight"));
            }
            
            XYSeriesCollection dataset = new XYSeriesCollection();
            CumulativeDistribution c = new CumulativeDistribution(indegree);
            dataset.addSeries(c.getCumulativeDistribution("indegree"));
            c = new CumulativeDistribution(outdegree);
            dataset.addSeries(c.getCumulativeDistribution("outdegree"));
//            c = new CumulativeDistribution(inweight);
//            dataset.addSeries(c.getCumulativeDistribution("inweight"));
//            c = new CumulativeDistribution(outweight);
//            dataset.addSeries(c.getCumulativeDistribution("outweight"));
            ScatterChart chart = new ScatterChart("Cumulative Distribution of Cp","k","P(Compactness ≤ k)", dataset);
            chart.createPlot();
                

        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }
    
    public void getNetworkReport(NetworkPropertyInterface property, boolean loglog) {
        try {

            String query = "";
            if (property instanceof NodeProperty) {
                query = getQueryReport((NodeProperty) property);
            }
            else {
                query = getQueryReport((EdgeProperty) property);
            }
            System.out.println(query);
            Statement s = db.getConnection().createStatement();

            ResultSet rs = s.executeQuery(query);
            XYSeriesCollection dataset = new XYSeriesCollection();
            XYSeries serie = new XYSeries("Property: " + property);
//            double[] data = new double[];
            int count = 0;
            while (rs.next()) {
                serie.add(rs.getDouble("property"), rs.getDouble("value"));
//                count = count+rs.getInt("weight");
//                count++;
            }
            double[] data = new double[count];
            rs = s.executeQuery(query);
            count = 0;
            float min = 0.0f;
            float max = 0.0f;
            float aux = 0.0f;
            while (rs.next()) {
                serie.add(rs.getDouble("property"), rs.getDouble("value"));
                aux = rs.getFloat("value");
//                int n = rs.getInt("weight");
//                for(int i =0; i<n;i++){
//                data[count] = aux;
//                    count++;
//                }
//
//                if (aux > max) {
//                    max = aux;
//                }


            }
            rs.close();
            s.close();
            dataset.addSeries(serie);
            String xaxis = property.toString();
            String yaxis = "Frequency";
            if (property.toString().contains("_AS_")) {
                xaxis = property.toString().split("_AS_")[0];
                yaxis = property.toString().split("_AS_")[1];
            }

            ScatterChart chart = new ScatterChart("Network Property", xaxis, yaxis, dataset);
            chart.setLogarithmicAxis(loglog);
            chart.createPlot();

//            HistogramDataset hdataset = new HistogramDataset();
//         
//            hdataset.addSeries("Network", data, Math.round(max));
//            
//            HistogramChart chart = new HistogramChart("Network Report", "Distance", "Number of Trajectories",hdataset);
//            chart.createPlot();
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }

    }

    public void getNetworkReport(List<NetworkPropertyInterface> properties, boolean loglog) {
        try {
            XYSeriesCollection dataset = new XYSeriesCollection();
            for (NetworkPropertyInterface property : properties) {
                String query = "";
                if (property instanceof NodeProperty) {
                    query = getQueryReport((NodeProperty) property);
                }
                else {
                    query = getQueryReport((EdgeProperty) property);
                }
                System.out.println(query);
                Statement s = db.getConnection().createStatement();

                ResultSet rs = s.executeQuery(query);
                
                XYSeries serie = new XYSeries(property.toString());
//            double[] data = new double[];
                int count = 0;
                while (rs.next()) {
                    serie.add(rs.getDouble("property"), rs.getDouble("value"));
//                count = count+rs.getInt("weight");
                    count++;
                }
                double[] data = new double[count];
                rs = s.executeQuery(query);
                count = 0;
                float min = 0.0f;
                float max = 0.0f;
                float aux = 0.0f;
                while (rs.next()) {
                    serie.add(rs.getDouble("property"), rs.getDouble("value"));
                    aux = rs.getFloat("value");
//                int n = rs.getInt("weight");
//                for(int i =0; i<n;i++){
                    data[count] = aux;
//                    count++;
//                }

                    if (aux > max) {
                        max = aux;
                    }


                }
                rs.close();
                s.close();
                dataset.addSeries(serie);
                

                

//            HistogramDataset hdataset = new HistogramDataset();
//         
//            hdataset.addSeries("Network", data, Math.round(max));
//            
//            HistogramChart chart = new HistogramChart("Network Report", "Distance", "Number of Trajectories",hdataset);
//            chart.createPlot();
            }
//                    String xaxis = property.toString();
//                String yaxis = "Frequency";
//                if (property.toString().contains("_AS_")) {
//                    xaxis = property.toString().split("_AS_")[0];
//                    yaxis = property.toString().split("_AS_")[1];
//                }
        ScatterChart chart = new ScatterChart("Network Property", "Number of Trajectories", "Number of Edges", dataset);
                chart.setLogarithmicAxis(loglog);
                chart.createPlot();
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }


    }

    public double[] getDataNetwork(NetworkPropertyInterface property) {
        try {
            String query = "";
            if (property instanceof NodeProperty) {
                query = getQueryReport((NodeProperty) property);
            }
            else {
                query = getQueryReport((EdgeProperty) property);
            }
            Statement s = db.getConnection().createStatement();

            ResultSet rs = s.executeQuery(query);
            //            XYSeriesCollection dataset = new XYSeriesCollection();
            //            XYSeries serie = new XYSeries("Property: "+property);
            //            double[] data = new double[];
            int count = 0;
            while (rs.next()) {
                //                serie.add(rs.getDouble("weight"), rs.getDouble("value"));

                count = count + rs.getInt("weight");
            }
            double[] data = new double[count];
            rs = s.executeQuery(query);
            count = 0;
            float max = 0.0f;
            float aux = 0.0f;
            while (rs.next()) {
                //                serie.add(rs.getDouble("property"), rs.getDouble("value"));
                aux = rs.getFloat("value");
                int n = rs.getInt("weight");
                for (int i = 0; i < n; i++) {
                    data[count] = aux;
                    count++;
                }

                if (aux > max) {
                    max = aux;
                }


            }
            rs.close();
            s.close();

            return data;
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public XYSeries getXYSerieNetwork(NetworkPropertyInterface property) {
        XYSeries series = new XYSeries("Network");
        String query = "";

        try {

            if (property instanceof EdgeProperty) {
                query = getQueryReport((EdgeProperty) property);
            }
            if (property instanceof NodeProperty) {
                query = getQueryReport((NodeProperty) property);
            }


            Statement s = db.getConnection().createStatement();
            System.out.println(query);
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                series.add(rs.getDouble("property"), rs.getDouble("value"));
            }

        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return series;
    }

    public TimeSeries getTimeSerieNetwork(NetworkPropertyInterface property, String time1, String time2, TimePeriod period) {
        try {

            String query = "";
            if (property instanceof CommunityProperty) {
                query = getQueryTemporalReport((CommunityProperty) property, period);
            }
            else {
                System.out.println("erro");
            }
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(time1));
            ps.setTimestamp(2, Timestamp.valueOf(time2));
            ResultSet rs = ps.executeQuery();
            TimeSeries serie = new TimeSeries("Network");

            switch (period) {
                case HOUR:
                    while (rs.next()) {
                        serie.add(new Hour(rs.getInt("hour"), rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), rs.getInt("value"));
                    }
                    break;
                case DAY:
                    while (rs.next()) {
                        serie.add(new Day(rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), rs.getInt("value"));
                    }
                    break;
                case MONTH:
                    while (rs.next()) {
                        serie.add(new Month(rs.getInt("month"), rs.getInt("year")), rs.getInt("value"));
                    }
                    break;
                case YEAR:
                    while (rs.next()) {
                        serie.add(new Year(rs.getInt("year")), rs.getInt("value"));
                    }
                    break;
            }
            return serie;
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    private String getQueryReport(EdgeProperty property) {
        String query = "";
        String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from " + trip_table + " t"
                + " ORDER BY start_time,end_time)";
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
                        //                + " select distance/1000::numeric as property,sum(weight) as value"
                        //                + " from"
                        //                + " ("
                        + " select distinct c1.id,c2.id,st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))/1000 as value,e.weight "
                        + " from " + edge + " e,  " + centroid + " c1," + centroid + " c2"
                        + " where e.source = c1.id and e.target = c2.id" //                + " ) a"
                        //                + " group by distance/1000::numeric"
                        ;
                break;
            case DISTANCE_AS_USERS:
                query = "\n-- EDGE REPORT\n"
                        //                + " select distance/1000::numeric as property,sum(weight) as value"
                        //                + " from"
                        //                + " ("
                        + " select distinct c1.id,c2.id,st_distance(st_transform(c1.cen,26986),st_transform(c2.cen,26986))/1000 as value,e.users weight "
                        + " from " + edge + " e,  " + centroid + " c1," + centroid + " c2"
                        + " where e.source = c1.id and e.target = c2.id" //                + " ) a"
                        //                + " group by distance/1000::numeric"
                        ;
                break;
            default:
                query = " select " + property + " as property,count(*) as value"
                        + " from " + edge
                        + " group by " + property;
                break;
        }

        return query;
    }

    private String getQueryReport(NodeProperty property) {

        String query = " select " + property.name() + " as property,count(*) as value"
                + " from " + node
                + " group by " + property.name();

        return query;
    }

    private String getQueryTemporalReport(CommunityProperty property, TimePeriod period) {
        String query = "";
        String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from " + trip_table + " t"
                + " ORDER BY start_time,end_time)";
        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";
        String centroid = " (select n.id,st_centroid(st_union(c.object)) cen"
                + " from " + location_table + " c, " + node + " n"
                + " where c.clus::text = n.id"
                + " group by n.id)";

        switch (property) {
            case STOPS_AS_TRAJECTORIES:
                query = "\n-- TRAJECTORIES REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as value"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",t.id user_id,t.trip"
                        + " from " + trip_table + " t," + node + " n1"
                        + " where t.start_time >= ? and t.start_time <= ? "
                        + " and t.id2::text = n1.id"
                        + " ) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case STOPS_AS_USERS:
                query = "\n-- USERS REPORT\n"
                        + " select " + period.getFormat2Select() + ",count(*) as value"
                        + " from ("
                        + " select distinct " + period.getFormat2Query("start_time") + ",t.id user_id"
                        + " from " + trip_table + " t," + node + " n1"
                        + " where t.start_time >= ? and t.start_time <= ? "
                        + " and t.id2::text = n1.id"
                        + " ) a"
                        + " group by " + period.getFormat2Select() + "\n";
                break;
            case MOVES_AS_TRAJECTORIES:
                query = " select " + period.getFormat2Select() + ",count(*) as value"
                        + " from"
                        + " ("
                        + " select distinct " + period.getFormat2Query("t1.end_time") + " ,t1.id,t1.trip"
                        + " from"
                        + " " + core + " t1," + aux + " t2," + edge
                        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                        + " and t1.end_time >=? and t2.start_time <=? "
                        + " and source = t1.id2::text and target=t2.id2::text"
                        + " ) a"
                        + " group by " + period.getFormat2Select();
                System.out.println(query);
                break;
            case MOVES_AS_USERS:
                query = " select " + period.getFormat2Select() + ",count(*) as value"
                        + " from"
                        + " ("
                        + " select distinct " + period.getFormat2Query("t1.end_time") + " ,t1.id"
                        + " from"
                        + " " + core + " t1," + aux + " t2," + edge
                        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                        + " and t1.end_time >=? and t2.start_time <=? "
                        + " and source = t1.id2::text and target=t2.id2::text"
                        + " ) a"
                        + " group by " + period.getFormat2Select();
                break;
        }

        return query;
    }
}
