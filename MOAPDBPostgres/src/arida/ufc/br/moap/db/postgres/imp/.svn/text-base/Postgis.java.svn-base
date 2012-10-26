package arida.ufc.br.moap.db.postgres.imp;

//package mf.bd.postgresql.imp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.postgis.LineString;
//
//import mf.core.beans.Linestring;
//import mf.core.beans.LatLonPoint;
//import mf.core.beans.TimeStampedPoint;
//
//public class Postgis {
//	private static Postgis instance;
//	
//	private Postgis(){ instance = new Postgis(); }
//		
//	public static org.postgis.Point toPGPoint(LatLonPoint p,int SRID){
//		org.postgis.Point pgPoint = new org.postgis.Point();
//		pgPoint.setSrid(SRID);
//		pgPoint.setX(p.getX());
//		pgPoint.setY(p.getY());
//		
//		
//		return pgPoint;
//	}
//	
//	public static org.postgis.Point toPGPoint(TimeStampedPoint p,int SRID){
//		org.postgis.Point pgPoint = new org.postgis.Point();
//		pgPoint.setSrid(SRID);
//		pgPoint.setX(p.getX());
//		pgPoint.setY(p.getY());
//		pgPoint.setY(p.getTime().getTime());
//		
//		
//		return pgPoint;
//	}
//	
//	public static LineString toPGLineString(Linestring line,int SRID){
//		List<org.postgis.Point> points = new ArrayList<org.postgis.Point>();
//		
//		for(LatLonPoint p : line.getPoints()){
//			points.add(toPGPoint(p,SRID));
//		}
//
//		LineString pgLine = new LineString((org.postgis.Point[]) points.toArray());
//		return pgLine;
//	}
//}
