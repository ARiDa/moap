//package arida.ufc.br.moap.vis.test.general;
//
//import java.awt.Color;
//import java.awt.Shape;
//import java.awt.geom.Point2D;
//import java.awt.geom.Rectangle2D;
//
//import org.jdesktop.swingx.JXMapKit;
//import org.jdesktop.swingx.JXMapViewer;
//import org.jdesktop.swingx.mapviewer.GeoPosition;
//
//import arida.ufc.br.moap.vis.api.GeometryType;
//import arida.ufc.br.moap.vis.api.IFeaturable;
//import arida.ufc.br.moap.vis.engine.api.ILayer;
//import arida.ufc.br.moap.vis.engine.api.IVisualizationEngine;
//import arida.ufc.br.moap.vis.engine.jxmap.JXMapEngine;
//
//public class TestEngine {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		JXMapKit map = new JXMapKit();
//		IVisualizationEngine visEngine = new IVisualizationEngine() {
//			
//			@Override
//			public Shape getShape(JXMapViewer viewer,IFeaturable feature) {
//				// TODO Auto-generated method stub
//				GeometryType type = feature.getFeature().getGeometry().getGeometryType();
//				double[][] point = feature.getFeature().getGeometry().getCoordinates();
//				
//				Point2D p1 = viewer.getTileFactory().geoToPixel(new GeoPosition(point[0][1], point[0][0]), viewer.getZoom());
//
//				if(type == GeometryType.POINT){
//					Rectangle2D rec = new Rectangle2D.Double(p1.getX(), p1.getY(), 1.0, 2.0);
//					return rec;
//				}
//				return null;
//			}
//		};
//		JXMapEngine engine = new JXMapEngine(map, visEngine);
//		engine.addLayer(new ILayer() {
//			
//			@Override
//			public void setFeaturables(IFeaturable[] features) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void removeFeaturable(int i) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void removeFeaturable(IFeaturable feature) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public String getName() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public int getId() {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//			
//			@Override
//			public IFeaturable[] getFeaturables() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public Color getColor() {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//			@Override
//			public void addFeaturable(IFeaturable feature) {
//				// TODO Auto-generated method stub
//				
//			}
//		})
//	}
//
//}
