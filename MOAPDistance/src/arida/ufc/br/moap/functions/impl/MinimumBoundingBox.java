package arida.ufc.br.moap.functions.impl;

import java.util.Collection;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.Pair;
import arida.ufc.br.moap.function.api.IUnaryFunction;


public class MinimumBoundingBox implements IUnaryFunction<Collection<LatLonPoint>, Pair<LatLonPoint, LatLonPoint>> {

	   private final String name = "Minimum Bounding Box";
	   
	   @Override
	   public int hashCode() {
	       return super.hashCode();
	   }

	   @Override
	   public boolean equals(Object obj) {
	       return super.equals(obj);
	   }

	   @Override
	   public Pair<LatLonPoint, LatLonPoint> evaluate(Collection<LatLonPoint> collection) {

	       double min_lat = 100.0, min_lon = 190.0, max_lat = -100.0, max_lon = -190.0;

	       for (LatLonPoint point : collection) {
	           double lat = point.getLatitude();
	           double lon = point.getLongitude();

	           // min latitude
	           if (lat < min_lat) {
	               min_lat = lat;
	           }

	           // max latitude
	           if (lat > max_lat) {
	               max_lat = lat;
	           }

	           // min longitude
	           if (lon < min_lon) {
	               min_lon = lon;
	           }

	           // max longitude
	           if (lon > max_lon) {
	               max_lon = lon;
	           }

	       }

	       LatLonPoint upper_right = new LatLonPoint(max_lon, max_lat);

	       LatLonPoint bottom_left = new LatLonPoint(min_lon, min_lat);
	       
	       Pair<LatLonPoint,LatLonPoint> pair = new Pair<LatLonPoint, LatLonPoint>(bottom_left, upper_right);
	       
	       return pair;
	   }

	   @Override
	   public String getName() {
	       return this.name;
	   }
}

	  
