/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobilitymining;

import arida.ufc.br.moap.network.chart.BarChart;
import arida.ufc.br.moap.network.chart.TimeSeriesChart;
import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.moap.network.community.TimePeriod;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.jfree.data.time.*;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class CardinalDirectionDistribution {

    private ArrayList<Double> lat_o = new ArrayList<Double>();
    private ArrayList<Double> long_o = new ArrayList<Double>();
    private ArrayList<Double> lat_d = new ArrayList<Double>();
    private ArrayList<Double> long_d = new ArrayList<Double>();
    private ArrayList<Integer> weights = new ArrayList<Integer>();
    private Double p_o_x, p_o_y, p_d_x, p_d_y;
    private String network;
    private String nodecomm;
    private String node;
    private String edge;

    public CardinalDirectionDistribution() {
    }

    public CardinalDirectionDistribution(String network) {
        this.network = network;
        this.nodecomm = network + NetworkController.NODE_COMMUNITY_SUFIX;
        this.edge = network + NetworkController.EDGE_SUFIX;
    }

    public void getCardinalDirectionDistribution(String community_id) {
        try {
            Map<String, Map<CardinalDirection, Integer>> map = new HashMap<String, Map<CardinalDirection, Integer>>();
            Map<CardinalDirection, Integer> directions = new EnumMap<CardinalDirection, Integer>(CardinalDirection.class);
            for (CardinalDirection direction : CardinalDirection.values()) {
                directions.put(direction, 0);
                map.put(community_id, directions);
            }

            DBManager db = DBManager.getInstance();
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(" select n1.comm,source,target,e.weight,st_x(c1.cen) x_o,st_y(c1.cen) y_o,st_x(c2.cen) x_d,st_y(c2.cen) y_d"
                    + " from location_4h_150m_edge$ e,"
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c1,"
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c2,"
                    + " location_4h_150m_nodecomm$ n1,"
                    + " location_4h_150m_nodecomm$ n2"
                    + " where source = c1.clus::text and target = c2.clus::text"
                    + " and n1.node = source and n2.node = target"
                    + " and n1.comm = n2.comm and n1.comm in ('" + community_id + "')");

            int count = 0;
            while (rs.next()) {

                long_o.add(count, rs.getDouble("x_o"));
                lat_o.add(count, rs.getDouble("y_o"));

                long_d.add(count, rs.getDouble("x_d"));
                lat_d.add(count, rs.getDouble("y_d"));

                weights.add(count, rs.getInt("weight"));

                count++;
            }

            directions = map.get(community_id);
            for (int i = 0; i < lat_d.size(); i++) {

                Integer weight = weights.get(i);
                p_o_x = long_o.get(i);
                p_o_y = lat_o.get(i);

                p_d_x = long_d.get(i);
                p_d_y = lat_d.get(i);

                CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                directions.put(direction, directions.get(direction) + weight);
//                System.out.println("Origem: "+p_o_x+ " " + p_o_y +" Destino: " + p_d_x+ " " + p_d_y );
//                System.out.println("P: "+p_x+ " " + p_y );
//                System.out.println("distancePoPd: "+distancePoPd);
//                System.out.println("distancePdP: "+ distancePdP);
//                System.out.println("Degree: "+degree);
//                System.out.println("##############");
            }
            map.put(community_id, directions);
            BarChart chart = new BarChart("Cardinal Direction Distribution", "Number of Trajectories", "Cardinal Direction", map);
            chart.createPlot();
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

//        return null;

    }

    // List of communities to be compared
    public void getCardinalDirectionDistribution(ArrayList community_ids) {
        try {
            Map<String, Map<CardinalDirection, Integer>> map = new HashMap<String, Map<CardinalDirection, Integer>>();
            for (Object o : community_ids) {
                Map<CardinalDirection, Integer> directions = new EnumMap<CardinalDirection, Integer>(CardinalDirection.class);
                for (CardinalDirection direction : CardinalDirection.values()) {
                    directions.put(direction, 0);
                }
                map.put(o.toString(), directions);
            }



            DBManager db = DBManager.getInstance();
            String parameter = db.mergeStrings(community_ids);
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(" select n1.comm,source,target,e.weight,st_x(c1.cen) x_o,st_y(c1.cen) y_o,st_x(c2.cen) x_d,st_y(c2.cen) y_d"
                    + " from location_4h_150m_edge$ e,"
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c1,"
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c2,"
                    + " location_4h_150m_nodecomm$ n1,"
                    + " location_4h_150m_nodecomm$ n2"
                    + " where source = c1.clus::text and target = c2.clus::text"
                    + " and n1.node = source and n2.node = target"
                    + " and n1.comm = n2.comm and n1.comm in (" + parameter + ")");

            int count = 0;
            ArrayList<String> communities = new ArrayList<String>();
            while (rs.next()) {
                communities.add(count, rs.getString("comm"));

                long_o.add(count, rs.getDouble("x_o"));
                lat_o.add(count, rs.getDouble("y_o"));

                long_d.add(count, rs.getDouble("x_d"));
                lat_d.add(count, rs.getDouble("y_d"));

                weights.add(count, rs.getInt("weight"));

                count++;
            }
            for (int i = 0; i < lat_d.size(); i++) {
                String community_id = communities.get(i);
                Integer weight = weights.get(i);
                p_o_x = long_o.get(i);
                p_o_y = lat_o.get(i);

                p_d_x = long_d.get(i);
                p_d_y = lat_d.get(i);


                Map<CardinalDirection, Integer> directions = map.get(community_id);

                CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                directions.put(direction, directions.get(direction) + weight);
                map.put(community_id, directions);
//                System.out.println("Origem: "+p_o_x+ " " + p_o_y +" Destino: " + p_d_x+ " " + p_d_y );
//                System.out.println("P: "+p_x+ " " + p_y );
//                System.out.println("distancePoPd: "+distancePoPd);
//                System.out.println("distancePdP: "+ distancePdP);
//                System.out.println("Degree: "+degree);
//                System.out.println("##############");
            }
//            return map;
            BarChart chart = new BarChart("Cardinal Direction Distribution", "Number of Trajectories", "Cardinal Direction", map);
            chart.createPlot();
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

//        return null;

    }

    public void getCardinalDirectionDistribution() {
        try {
            String community_id = "Network";
            Map<String, Map<CardinalDirection, Integer>> map = new HashMap<String, Map<CardinalDirection, Integer>>();
            Map<CardinalDirection, Integer> directions = new EnumMap<CardinalDirection, Integer>(CardinalDirection.class);
            for (CardinalDirection direction : CardinalDirection.values()) {
                directions.put(direction, 0);
                map.put(community_id, directions);
            }

            DBManager db = DBManager.getInstance();
            Statement s = db.getConnection().createStatement();
            ResultSet rs = s.executeQuery(" select source,target,e.weight,st_x(c1.cen) x_o,st_y(c1.cen) y_o,st_x(c2.cen) x_d,st_y(c2.cen) y_d"
                    + " from location_4h_150m_edge$,"
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c1,"
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c2"
                    + " where source = c1.clus::text and target = c2.clus::text");

            int count = 0;
            while (rs.next()) {

                long_o.add(count, rs.getDouble("x_o"));
                lat_o.add(count, rs.getDouble("y_o"));

                long_d.add(count, rs.getDouble("x_d"));
                lat_d.add(count, rs.getDouble("y_d"));

                weights.add(count, rs.getInt("weight"));
                count++;
            }

            directions = map.get(community_id);
            for (int i = 0; i < lat_d.size(); i++) {

                Integer weight = weights.get(i);

                p_o_x = long_o.get(i);
                p_o_y = lat_o.get(i);

                p_d_x = long_d.get(i);
                p_d_y = lat_d.get(i);

                CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                directions.put(direction, directions.get(direction) + weight);
//                System.out.println("Origem: "+p_o_x+ " " + p_o_y +" Destino: " + p_d_x+ " " + p_d_y );
//                System.out.println("P: "+p_x+ " " + p_y );
//                System.out.println("distancePoPd: "+distancePoPd);
//                System.out.println("distancePdP: "+ distancePdP);
//                System.out.println("Degree: "+degree);
//                System.out.println("##############");
            }
            map.put(community_id, directions);
            BarChart chart = new BarChart("Cardinal Direction Distribution","Number of Trajectories", "Cardinal Direction", map);
            chart.createPlot();
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

//        return null;

    }

//    public void getVectorPlot(String community_id){
//        try {
//            DBManager db = DBManager.getInstance();
//            Statement s = db.getConnection().createStatement();
//            ResultSet rs = s.executeQuery(" select n1.comm,source,target,st_x(c1.cen) x_o,st_y(c1.cen) y_o,st_x(c2.cen) x_d,st_y(c2.cen) y_d"
//                    + " from location_4h_150m_edge$,"
//                    + " (select clus,st_centroid(st_union(object)) cen"
//                    + " from location_cluster$"
//                    + " group by clus) c1,"
//                    + " (select clus,st_centroid(st_union(object)) cen"
//                    + " from location_cluster$"
//                    + " group by clus) c2,"
//                    + " location_4h_150m_nodecomm$ n1,"
//                    + " location_4h_150m_nodecomm$ n2"
//                    + " where source = c1.clus::text and target = c2.clus::text"
//                    + " and n1.node = source and n2.node = target"
//                    + " and n1.comm = n2.comm and n1.comm in ('"+community_id+"')");
//            VectorSeries series = new VectorSeries(community_id);
//            
//            while(rs.next()){
//                System.out.println(1);
//                series.add(rs.getDouble("x_o"),
//                        rs.getDouble("y_o"),
//                        rs.getDouble("x_d"),
//                        rs.getDouble("y_d"));
//            }
//            s.close();
//            rs.close();
//            VectorSeriesCollection dataset = new VectorSeriesCollection();
//            dataset.addSeries(series);
//            
//            VectorChart chart = new VectorChart(community_id, dataset);
//            chart.createPlot();
//            
//        }
//        catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//        catch (ClassNotFoundException ex) {
//            Exceptions.printStackTrace(ex);
//        }        catch (SQLException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//    }
    // ############################################ Considering the time
    public void getTemporalCardinalDirectionDistribution(String trip_table, String community_id, String time1, String time2, TimePeriod period) {
        try {


            DBManager db = DBManager.getInstance();


            String aux = "(select distinct t.id,trip,clus as id2,start_time,end_time,time_duration,c.cen cen "
                    + " from " + trip_table + " t, "
                    + " (select clus,st_centroid(st_union(object)) cen"
                    + " from location_cluster$"
                    + " group by clus) c"
                    + " where c.clus = t.id2)";

            String core = "(select t1.id,t1.trip,t1.id2,t1.cen,t1.start_time,t1.end_time,min(t2.start_time) min"
                    + " from " + aux + " t1," + aux + " t2"
                    + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                    + " group by t1.id,t1.trip,t1.id2,t1.cen,t1.start_time,t1.end_time)";

            String query = " select " + period.getFormat2Select() + " ,x_o, y_o, x_d, y_d,sum(weight) weight"
                    + " from"
                    + " ("
                    + " select distinct " + period.getFormat2Query("t1.end_time") + " ,st_x(t1.cen) x_o,st_y(t1.cen) y_o,st_x(t2.cen) x_d,st_y(t2.cen) y_d,e.weight"
                    + " from"
                    + " " + core + " t1," + aux + " t2, " + nodecomm + " n1," + nodecomm + " n2," + edge + " e"
                    + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                    + " and e.source = t1.id2::text and e.target = t2.id2::text"
                    + " and t1.end_time >=? and t2.start_time <=?"
                    + " and n1.node = t1.id2::text and n2.node = t2.id2::text and n1.comm=n2.comm and n1.comm = ?"
                    + " ) a"
                    + " group by " + period.getFormat2Select() + " ,x_o, y_o, x_d, y_d";

            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(time1));
            ps.setTimestamp(2, Timestamp.valueOf(time2));
            ps.setString(3, community_id);
//            System.out.println(ps.toString());

            ResultSet rs = ps.executeQuery();
            int count = 0;

            Map<CardinalDirection,TimeSeries> map = new EnumMap<CardinalDirection, TimeSeries>(CardinalDirection.class);
            for (CardinalDirection c : CardinalDirection.values()) {
                map.put(c, new TimeSeries(c));
            }
            int weight;
            
            
            
            switch (period) {
                case HOUR:
                    while (rs.next()) {
                        weight = rs.getInt("weight");
                        p_o_x = rs.getDouble("x_o");
                        p_d_x = rs.getDouble("x_d");
                        p_o_y = rs.getDouble("y_o");
                        p_d_y = rs.getDouble("y_d");
                        CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                        System.out.println(direction);
                        TimeSeries serie = map.get(direction);
                        Number number = serie.getValue(new Hour(rs.getInt("hour"), rs.getInt("day"), rs.getInt("month"), rs.getInt("year")));
                        if(number != null)
                            serie.addOrUpdate(new Hour(rs.getInt("hour"), rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), weight+number.intValue());
                        else
                            serie.addOrUpdate(new Hour(rs.getInt("hour"), rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), weight);    
                    }
                    break;
                case DAY:
                    while (rs.next()) {
                        weight = rs.getInt("weight");
                        p_o_x = rs.getDouble("x_o");
                        p_d_x = rs.getDouble("x_d");
                        p_o_y = rs.getDouble("y_o");
                        p_d_y = rs.getDouble("y_d");
                        CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                        System.out.println(direction);
                        TimeSeries serie = map.get(direction);
                        Number number = serie.getValue(new Day(rs.getInt("day"), rs.getInt("month"), rs.getInt("year")));
                        if(number != null)
                            serie.addOrUpdate(new Day(rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), weight+number.intValue());
                        else
                            serie.addOrUpdate(new Day(rs.getInt("day"), rs.getInt("month"), rs.getInt("year")), weight);
                    }
                    break;
                case MONTH:
                    while (rs.next()) {
                        weight = rs.getInt("weight");
                        p_o_x = rs.getDouble("x_o");
                        p_d_x = rs.getDouble("x_d");
                        p_o_y = rs.getDouble("y_o");
                        p_d_y = rs.getDouble("y_d");
                        CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                        System.out.println(direction);
                        TimeSeries serie = map.get(direction);
                        Number number = serie.getValue(new Month(rs.getInt("month"), rs.getInt("year")));
                        if(number != null)
                            serie.addOrUpdate(new Month(rs.getInt("month"), rs.getInt("year")), weight+number.intValue());
                        else
                            serie.addOrUpdate(new Month(rs.getInt("month"), rs.getInt("year")), weight);
                    }
                    break;
                case YEAR:
                    while (rs.next()) {
                        weight = rs.getInt("weight");
                        p_o_x = rs.getDouble("x_o");
                        p_d_x = rs.getDouble("x_d");
                        p_o_y = rs.getDouble("y_o");
                        p_d_y = rs.getDouble("y_d");
                        CardinalDirection direction = getCardinalDirection(p_o_x, p_d_x, p_o_y, p_d_y);
                        System.out.println(direction);
                        TimeSeries serie = map.get(direction);
                        Number number = serie.getValue(new Year(rs.getInt("year")));
                        if(number != null)
                            serie.addOrUpdate(new Year(rs.getInt("year")), weight+number.intValue());
                        else
                            serie.addOrUpdate(new Year(rs.getInt("year")), weight);
                    }
                    break;
            }

            TimeSeriesCollection dataset = new TimeSeriesCollection();
            for(TimeSeries s : map.values()){
                dataset.addSeries(s);
            }
            TimeSeriesChart chart = new TimeSeriesChart("Cardinal Direction Distribution: Community "+community_id, period.toString(),"Number of Trajectories", dataset);
            chart.createPlot();
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

//        return null;

    }

    private CardinalDirection getCardinalDirection(double p_o_x, double p_d_x, double p_o_y, double p_d_y) {
        double p_x,p_y;
        if (p_o_y < p_d_y) {
            p_x = p_d_x;
            p_y = p_o_y;
        }
        else {
            p_x = p_o_x;
            p_y = p_d_y;
        }
        Double distancePoPd = SpatialDistance.distance(p_o_x, p_o_y, p_d_x, p_d_y, "K");
        Double distancePdP = SpatialDistance.distance(p_x, p_y, p_d_x, p_d_y, "K");
        Double sin = distancePdP / distancePoPd;
        Double degree = Math.toDegrees(Math.asin(sin));
//        System.out.println(degree);
        if (p_o_y < p_d_y) {// Norte
            if (p_o_x < p_d_x) {// Leste
                if (degree >= 0 && degree < 22.5) {
//                            System.out.println("Movimento Leste");
                    return CardinalDirection.EAST;
                }
                else {
                    if (degree >= 22.5 && degree < 67.5) {
//                                System.out.println("Movimento Nordeste");
                        return CardinalDirection.NORTH_EAST;
                    }
                    else if (degree >= 67.5 && degree <= 90) {
//                                System.out.println("Movimento Norte");
                        return CardinalDirection.NORTH;
                    }
                }
            }
            else { // Oeste
                if (degree >= 0 && degree < 22.5) {
//                            System.out.println("Movimento Oeste");
                    return CardinalDirection.WEST;
                }
                else {
                    if (degree >= 22.5 && degree < 67.5) {
//                                System.out.println("Movimento Noroeste");
                        return CardinalDirection.NORTH_WEST;
                    }
                    else if (degree >= 67.5 && degree <= 90) {
//                                System.out.println("Movimento Norte");
                        return CardinalDirection.NORTH;
                    }
                }
            }
        }
        else { // Sul
            if (p_o_x < p_d_x) {// Leste
                if (degree >= 0 && degree < 22.5) {
//                            System.out.println("Movimento Sul");
                    return CardinalDirection.SOUTH;

                }
                else {
                    if (degree >= 22.5 && degree < 67.5) {
//                                System.out.println("Movimento Sudeste");
                        return CardinalDirection.SOUTH_EAST;
                    }
                    else if (degree >= 67.5 && degree <= 90) {
//                                System.out.println("Movimento Leste");
                        return CardinalDirection.EAST;
                    }
                }
            }
            else { // Oeste
                if (degree >= 0 && degree < 22.5) {
//                            System.out.println("Movimento Sul");
                    return CardinalDirection.SOUTH;
                }
                else {
                    if (degree >= 22.5 && degree < 67.5) {
//                                System.out.println("Movimento Sudoeste");
                        return CardinalDirection.SOUTH_WEST;
                    }
                    else if (degree >= 67.5 && degree <= 90) {
//                                System.out.println("Movimento Oeste");
                        return CardinalDirection.WEST;
                    }
                }
            }
        }
        return null;
    }
}
