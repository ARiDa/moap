/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobnet.renderer;

import arida.ufc.br.networkmanager.impl.NetworkController;
import java.util.ArrayList;
import org.jdesktop.swingx.JXMapKit;
import arida.ufc.br.moap.network.color.ColorUtils;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.painter.Painter;
import org.postgis.Geometry;
import org.postgis.LineString;
import org.postgis.PGgeometry;
import org.postgis.Point;
/**
 *
 * @author igobrilhante
 */
public class CommunityRenderer {
    private static CommunityRenderer instance;
    private JXMapKit map;
    private NetworkController nc;
    
    private CommunityRenderer() throws Exception{
        map = MapRenderer.getInstance().getMap();
        nc = NetworkController.getInstance();
    }
    
    public static CommunityRenderer getInstance() throws Exception{
        if(instance == null)
            return new CommunityRenderer();
        return instance;
    }
    
    public void renderCommunity(String network) throws SQLException{
            
            final ArrayList<GeoPosition> region = new ArrayList<GeoPosition>();
            region.add(new GeoPosition(45.464149, 9.187682));
            region.add(new GeoPosition(45.6, 9.4));
//            map.setCenterPosition(new GeoPosition(45.464149, 9.187682));
            map.setZoom(6);
            //create a Set of waypoints
//            Set<Waypoint> waypoints = new HashSet<Waypoint>();
//            final ArrayList<GeoPosition> nodes = new ArrayList<GeoPosition>();

            Statement s = nc.getDatabase().getConnection().createStatement();

//            while (rs.next()) {
//                nodes.add(new GeoPosition(rs.getDouble("y"), rs.getDouble("x")));
//            }
//
////        for(waypoints)
//            //crate a WaypointPainter to draw the points
//            WaypointPainter painter = new WaypointPainter();
//            painter.setWaypoints(waypoints);

            String query =  "  "
                        + " select e.comm,e.source,e.target,c1.object n1_object,c2.object n2_object"
                        + " from "+network + NetworkController.NODE_SUFIX+" c1,"
                        + " "+network + NetworkController.NODE_SUFIX+" c2,"
                        + " " + network + NetworkController.EDGE_COMMUNITY_SUFIX+" e"
                        + " where e.source = c1.id and e.target = c2.id ";

            ResultSet rs = s.executeQuery(query);
//                    Color[] colors  = {Color.RED,Color.BLUE,Color.GREEN};
            // '104','86','43','6','13'

//                    final HashMap<Shape,String> shapes = new HashMap<Shape, String>();
            final HashMap<String, Integer> attribute = new HashMap<String, Integer>();
            final ArrayList<GeoPosition> source = new ArrayList<GeoPosition>();
            final ArrayList<GeoPosition> target = new ArrayList<GeoPosition>();
            final ArrayList<String> atts = new ArrayList<String>();
            int count = 0;
            int index = 0;
            while (rs.next()) {
//                        waypoints.add(new Waypoint(rs.getDouble("y"), rs.getDouble("x
                PGgeometry geo1 = new PGgeometry(rs.getString("n1_object"));
                PGgeometry geo2 = new PGgeometry(rs.getString("n2_object"));
                
                if(geo1.getGeoType() == Geometry.POINT && geo2.getGeoType() == Geometry.POINT){
                    Point pt1 = (Point)geo1.getGeometry();
                    Point pt2 = (Point)geo2.getGeometry();
                    source.add(index, new GeoPosition(pt1.getY(), pt1.getX()));
                    target.add(index, new GeoPosition(pt2.getY(), pt2.getX()));

                    String att = rs.getString("comm");
                    atts.add(index, att);
                    if (!attribute.containsKey(att)) {
                        attribute.put(att, count);
                        count++;

                    }
                    index++;
                }
                
                
//                        g.draw(line);
            }
            rs.close();
            s.close();
            map.setCenterPosition(source.get(0));
            final ArrayList<Color> colors = (ArrayList<Color>) ColorUtils.colorTheme1(attribute.size());


//            painter.setRenderer(new WaypointRenderer() {
//
//                public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
//
//                    g.setColor(Color.BLUE);
//                    g.fillRoundRect(-10, -10, 10, 10, 10, 10);
//                    return true;
//                }
//            });
            Painter<JXMapViewer> polygonOverlay = new Painter<JXMapViewer>() {

                @Override
                public void paint(Graphics2D g, JXMapViewer map, int w, int h) {

                    g = (Graphics2D) g.create();
                    //convert from viewport to world bitmap
                    Rectangle2D rect = map.getViewportBounds();

                    g.translate(-rect.getX(), -rect.getY());

                    //create a polygon

                    for (int i = 0; i < source.size(); i++) {
                        Point2D pt1 = map.getTileFactory().geoToPixel(source.get(i), map.getZoom());
                        Point2D pt2 = map.getTileFactory().geoToPixel(target.get(i), map.getZoom());
                        String att = atts.get(i);
                        Line2D line = new Line2D.Double(pt1, pt2);
                        Line2D line2 = new Line2D.Double(pt1, pt2);
                        int color_index = attribute.get(att);

                        g.setStroke(new BasicStroke(7.3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.setPaint(Color.BLACK);
                        g.draw(line2);

                        g.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.setPaint(colors.get(color_index));
                        g.draw(line);


                    }
//                    
//                    for(int i=0;i<nodes.size();i++){
//                        Point2D pt = map.getTileFactory().geoToPixel(nodes.get(i), map.getZoom());
//                        g.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//                        g.setPaint(new Color(0  , 0, 255));
//                        
//                        Shape shape = new Ellipse2D.Double(pt.getX(), pt.getY(), 5.0, 5.0);
//                        g.draw(shape);
//                    }




                    g.dispose();

                }
            };
//    jXMapKit1.getMainMap().setOverlayPainter(painter);
            map.getMainMap().setOverlayPainter(polygonOverlay);
            map.repaint();
            map.updateUI();
    }
    
        public void renderCommunity(String network, String list) throws SQLException{
            
            final ArrayList<GeoPosition> region = new ArrayList<GeoPosition>();
            region.add(new GeoPosition(45.464149, 9.187682));
            region.add(new GeoPosition(45.6, 9.4));
            map.setCenterPosition(new GeoPosition(45.464149, 9.187682));
            map.setZoom(5);
            //create a Set of waypoints
//            Set<Waypoint> waypoints = new HashSet<Waypoint>();
//            final ArrayList<GeoPosition> nodes = new ArrayList<GeoPosition>();

            Statement s = nc.getDatabase().getConnection().createStatement();
            ResultSet rs = s.executeQuery(" select n.id,st_x(st_centroid(st_union(object))) x,st_y(st_centroid(st_union(object))) y"
                    + " from location_cluster$ c, location_4h_150m_node$ n"
                    + " where c.clus::text = n.id"
                    + " group by n.id");
//            while (rs.next()) {
//                nodes.add(new GeoPosition(rs.getDouble("y"), rs.getDouble("x")));
//            }
//
////        for(waypoints)
//            //crate a WaypointPainter to draw the points
//            WaypointPainter painter = new WaypointPainter();
//            painter.setWaypoints(waypoints);

            String query =  "  "
                        + " select e.comm,e.source,e.target,st_x(c1.cen) x_source,st_y(c1.cen) y_source,st_x(c2.cen) x_target,st_y(c2.cen) y_target"
                        + " from (select n.id,st_centroid(st_union(object)) cen from location_cluster$ c, location_4h_150m_node$ n where c.clus::text = n.id group by n.id) c1,"
                        + " (select n.id,st_centroid(st_union(object)) cen from location_cluster$ c, " + network + "_node$ n where c.clus::text = n.id group by n.id) c2,"
                        + " " + network + "_edgecomm$ e"
                        + " where e.source = c1.id and e.target = c2.id and e.comm in ("+list+")";

            rs = s.executeQuery(query);
//                    Color[] colors  = {Color.RED,Color.BLUE,Color.GREEN};
            // '104','86','43','6','13'

//                    final HashMap<Shape,String> shapes = new HashMap<Shape, String>();
            final HashMap<String, Integer> attribute = new HashMap<String, Integer>();
            final ArrayList<GeoPosition> source = new ArrayList<GeoPosition>();
            final ArrayList<GeoPosition> target = new ArrayList<GeoPosition>();
            final ArrayList<String> atts = new ArrayList<String>();
            int count = 0;
            int index = 0;
            while (rs.next()) {
//                        waypoints.add(new Waypoint(rs.getDouble("y"), rs.getDouble("x
                source.add(index, new GeoPosition(rs.getDouble("y_source"), rs.getDouble("x_source")));
                target.add(index, new GeoPosition(rs.getDouble("y_target"), rs.getDouble("x_target")));

                String att = rs.getString("comm");
                atts.add(index, att);
                if (!attribute.containsKey(att)) {
                    attribute.put(att, count);
                    count++;

                }
                index++;
//                        g.draw(line);
            }
            final ArrayList<Color> colors = (ArrayList<Color>) ColorUtils.colorTheme1(attribute.size());


//            painter.setRenderer(new WaypointRenderer() {
//
//                public boolean paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint wp) {
//
//                    g.setColor(Color.BLUE);
//                    g.fillRoundRect(-10, -10, 10, 10, 10, 10);
//                    return true;
//                }
//            });
            Painter<JXMapViewer> polygonOverlay = new Painter<JXMapViewer>() {

                @Override
                public void paint(Graphics2D g, JXMapViewer map, int w, int h) {

                    g = (Graphics2D) g.create();
                    //convert from viewport to world bitmap
                    Rectangle2D rect = map.getViewportBounds();

                    g.translate(-rect.getX(), -rect.getY());

                    //create a polygon

                    for (int i = 0; i < source.size(); i++) {
                        Point2D pt1 = map.getTileFactory().geoToPixel(source.get(i), map.getZoom());
                        Point2D pt2 = map.getTileFactory().geoToPixel(target.get(i), map.getZoom());
                        String att = atts.get(i);
                        Line2D line = new Line2D.Double(pt1, pt2);
                        Line2D line2 = new Line2D.Double(pt1, pt2);
                        int color_index = attribute.get(att);

                        g.setStroke(new BasicStroke(7.3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.setPaint(Color.BLACK);
                        g.draw(line2);

                        g.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.setPaint(colors.get(color_index));
                        g.draw(line);


                    }
//                    
//                    for(int i=0;i<nodes.size();i++){
//                        Point2D pt = map.getTileFactory().geoToPixel(nodes.get(i), map.getZoom());
//                        g.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//                        g.setPaint(new Color(0  , 0, 255));
//                        
//                        Shape shape = new Ellipse2D.Double(pt.getX(), pt.getY(), 5.0, 5.0);
//                        g.draw(shape);
//                    }




                    g.dispose();

                }
            };
//    jXMapKit1.getMainMap().setOverlayPainter(painter);
            map.getMainMap().setOverlayPainter(polygonOverlay);
            map.repaint();
            map.updateUI();
    }
}
