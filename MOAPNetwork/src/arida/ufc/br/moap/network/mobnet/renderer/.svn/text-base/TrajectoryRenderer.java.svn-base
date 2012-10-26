/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobnet.renderer;

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
public class TrajectoryRenderer {
    
    private static TrajectoryRenderer instance;
    private NetworkController nc;
    private JXMapKit map;
    
    private TrajectoryRenderer() throws Exception{
        map = MapRenderer.getInstance().getMap();
        nc = NetworkController.getInstance();
    }
    
    public static TrajectoryRenderer getInstance() throws Exception{
        if(instance==null){
            return new TrajectoryRenderer();
        }
        return instance;
    }
    
    public synchronized void  renderTrajectory(String network) throws SQLException{
            final ArrayList<GeoPosition> region = new ArrayList<GeoPosition>();
            region.add(new GeoPosition(45.464149, 9.187682));
            region.add(new GeoPosition(45.6, 9.4));
//            jxMapKit.setCenterPosition(new GeoPosition(45.464149, 9.187682));
            map.setZoom(6);

            String query = "  "
                        + " select * from "+network+NetworkController.NODE_SUFIX;

            
            
            Statement s = nc.getDatabase().getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            
            final HashMap<String, ArrayList<GeoPosition>> user_trajectories = new HashMap<String, ArrayList<GeoPosition>>();
            final HashMap<String, Integer> user_idx = new HashMap<String, Integer>();
            int count = 0;
            int index = 0;
            System.out.println(query);
            while (rs.next()) {
//                        waypoints.add(new Waypoint(rs.getDouble("y"), rs.getDouble("x
                String user = rs.getString("id");
                System.out.println("User: "+user);
                String geo_astext = rs.getString("object");
                if(!geo_astext.isEmpty()){
                    PGgeometry geom = new PGgeometry(geo_astext);
                    ArrayList<GeoPosition> list = new ArrayList<GeoPosition>();
                    if(geom.getGeoType() == Geometry.LINESTRING){
                        System.out.println("Linestring");
                        LineString line = (LineString)geom.getGeometry();
                        for(Point p : line.getPoints()){
                            GeoPosition gp = new GeoPosition(p.getY(), p.getX());
                            list.add(gp);
                        }

                        user_trajectories.put(user, list);

                        user_idx.put(user, index);

                        index++;

                    }
                    else{
                        System.out.println("Not linestring");
                    }
                    map.setCenterPosition(list.get(0));
                }
                    
            }
            rs.close();
            s.close();
//            jxMapKit.setCenterPosition((GeoPosition)(user_trajectories.values().toArray()[0]));
            final ArrayList<Color> colors = (ArrayList<Color>) ColorUtils.colorTheme1(user_idx.size());
            System.out.println("Trajectories: "+user_idx.size());

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

                    for (String u : user_trajectories.keySet()) {
                        ArrayList<GeoPosition> list = user_trajectories.get(u);
                        int color_index = user_idx.get(u);
                        GeneralPath trajectory = new GeneralPath();
                        Point2D p;
                        p = map.getTileFactory().geoToPixel(list.get(0), map.getZoom());
                        Ellipse2D start = new Ellipse2D.Double(p.getX(),p.getY(), 8.0, 8.0);
                        p = map.getTileFactory().geoToPixel(list.get(list.size()-1), map.getZoom());
                        Rectangle2D end = new Rectangle2D.Double(p.getX(),p.getY(), 8.0, 8.0);
                        g.setStroke(new BasicStroke(5.3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.setPaint(Color.BLACK);
                        g.draw(start);
                        g.draw(end);
                        g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        g.setPaint(colors.get(color_index));
                        g.draw(start);
                        g.draw(end);
                        Path2D path = new Path2D.Double();
                        Line2D line;
                        Point2D pt1 = null,pt2 = null;
                        int c = 1;
                        for(GeoPosition position : list){
                            if(c>1){
                                pt1 = pt2;
                                pt2 = map.getTileFactory().geoToPixel(position, map.getZoom());
                                line = new Line2D.Double(pt1, pt2);
                                g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                                g.draw(line);
                            }
                            else{
                                pt2 = map.getTileFactory().geoToPixel(position, map.getZoom());
                            }
                            c++;
//                            System.out.println(position.toString());
                            
//                            String att = atts.get(u);
//                            trajectory.append(new Ellipse2D.Double(pt1.getX(), pt1.getY(), 10.0, 10.0), true);
//                            Ellipse2D e = new Ellipse2D.Double(pt1.getX(), pt1.getY(), 10, 10);
//                            trajectory.append(e, true);
//                            g.draw(e);
                        }
                        

//                        g.setStroke(new BasicStroke(7.3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//                        g.setPaint(Color.BLACK);
//                        g.draw(trajectory);
//
                        
//                        g.draw(trajectory);
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
    
    public synchronized void  renderTrajectory(String network,String traj_list) throws SQLException{
        final ArrayList<GeoPosition> region = new ArrayList<GeoPosition>();
            region.add(new GeoPosition(45.464149, 9.187682));
            region.add(new GeoPosition(45.6, 9.4));
//            jxMapKit.setCenterPosition(new GeoPosition(45.464149, 9.187682));
            map.setZoom(6);

            String query  = "  "
                        + " select * from "+network+NetworkController.NODE_SUFIX
                        + " where id in ("+traj_list+")";
            
            
            
            Statement s = nc.getDatabase().getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            
            final HashMap<String, ArrayList<GeoPosition>> user_trajectories = new HashMap<String, ArrayList<GeoPosition>>();
            final HashMap<String, Integer> user_idx = new HashMap<String, Integer>();
            int count = 0;
            int index = 0;
            System.out.println(query);
            while (rs.next()) {
//                        waypoints.add(new Waypoint(rs.getDouble("y"), rs.getDouble("x
                String user = rs.getString("id");
                System.out.println("User: "+user);
                String geo_astext = rs.getString("object");
                if(!geo_astext.isEmpty()){
                    PGgeometry geom = new PGgeometry(geo_astext);
                    ArrayList<GeoPosition> list = new ArrayList<GeoPosition>();
                    if(geom.getGeoType() == Geometry.LINESTRING){
                        System.out.println("Linestring");
                        LineString line = (LineString)geom.getGeometry();
                        for(Point p : line.getPoints()){
                            GeoPosition gp = new GeoPosition(p.getY(), p.getX());
                            list.add(gp);
                        }

                        user_trajectories.put(user, list);

                        user_idx.put(user, index);

                        index++;

                    }
                    else{
                        System.out.println("Not linestring");
                    }
                    map.setCenterPosition(list.get(0));
                }
                    
            }
            rs.close();
            s.close();
//            jxMapKit.setCenterPosition((GeoPosition)(user_trajectories.values().toArray()[0]));
            final ArrayList<Color> colors = (ArrayList<Color>) ColorUtils.colorTheme1(user_idx.size());
            System.out.println("Trajectories: "+user_idx.size());

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

                    for (String u : user_trajectories.keySet()) {
                        
                        ArrayList<GeoPosition> list = user_trajectories.get(u);
                        System.out.println(u + " "+list.size());
                        int color_index = user_idx.get(u);
                        GeneralPath trajectory = new GeneralPath();
                        Point2D p;
                        p = map.getTileFactory().geoToPixel(list.get(0), map.getZoom());
                        Ellipse2D start = new Ellipse2D.Double(p.getX(),p.getY(), 8.0, 8.0);
                        p = map.getTileFactory().geoToPixel(list.get(list.size()-1), map.getZoom());
                        Rectangle2D end = new Rectangle2D.Double(p.getX(),p.getY(), 8.0, 8.0);
                        g.setPaint(colors.get(color_index));
                        g.draw(start);
                        g.draw(end);
                        Path2D path = new Path2D.Double();
                        Line2D line;
                        Point2D pt1 = null,pt2 = null;
                        int c = 1;
                        for(GeoPosition position : list){
                            System.out.println(position);
                            if(c>1){
                                pt1 = pt2;
                                pt2 = map.getTileFactory().geoToPixel(position, map.getZoom());
                                line = new Line2D.Double(pt1, pt2);
                                g.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                                g.draw(line);
                            }
                            else{
                                pt2 = map.getTileFactory().geoToPixel(position, map.getZoom());
                            }
                            c++;
//                            System.out.println(position.toString());
                            
//                            String att = atts.get(u);
//                            trajectory.append(new Ellipse2D.Double(pt1.getX(), pt1.getY(), 10.0, 10.0), true);
//                            Ellipse2D e = new Ellipse2D.Double(pt1.getX(), pt1.getY(), 10, 10);
//                            trajectory.append(e, true);
//                            g.draw(e);
                        }
                        

//                        g.setStroke(new BasicStroke(7.3f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
//                        g.setPaint(Color.BLACK);
//                        g.draw(trajectory);
//
                        
//                        g.draw(trajectory);
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
