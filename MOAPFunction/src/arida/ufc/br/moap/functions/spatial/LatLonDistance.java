package arida.ufc.br.moap.functions.spatial;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.function.api.IDistanceFunction;

/**
 * @author igobrilhante
 *
 */
public class LatLonDistance implements IDistanceFunction<LatLonPoint> {

	/* (non-Javadoc)
	 * @see mf.algorithm.distance.spi.IDistanceFunction#distance(java.lang.Object, java.lang.Object)
	 */
	public Double evaluate(LatLonPoint o1, LatLonPoint o2) {
		// TODO Auto-generated method stub
		double d = computeDistance(o1.getLatitude(), o1.getLongitude(),
									o2.getLatitude(), o2.getLongitude());
		return d;
	}
	
	/**
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	private double computeDistance(double lat1, double lon1, double lat2, double lon2) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
//		  dist = dist * 60 * 1.1515;
//		  if (unit == "K") {
//		    dist = dist * 1.609344;
//		  } else if (unit == "N") {
//		  	dist = dist * 0.8684;
//		    }
		  return (dist);
		}

		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::  This function converts decimal degrees to radians             :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/**
		 * @param deg
		 * @return
		 */
		private double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}

		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::  This function converts radians to decimal degrees             :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/**
		 * @param rad
		 * @return
		 */
		private double rad2deg(double rad) {
		  return (rad * 180.0 / Math.PI);
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "D";
		}

}
