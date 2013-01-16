package arida.ufc.br.moap.distance.imp;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;


/**
 * @author igobrilhante
 *
 */
public class SphericalLawofCosines implements IDistanceFunction<LatLonPoint> {

    private final double R_EARTH = 6371; // Meters
	/*
     * (non-Javadoc) @see
     * mf.algorithm.evaluate.spi.IDistanceFunction#evaluate(java.lang.Object,
     * java.lang.Object)
     */

    @Override
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
        double theta = lon2 - lon1;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + 
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);

//        dist = dist * 60 * 1.1515; // Miles
//		  if (unit == "K") {
//        dist = dist * 1.609344; // KM
        
//        dist = meters2degree(dist);
//		  } else if (unit == "N") {
//		  	dist = dist * 0.8684;
//		    }
        return (dist);
    }

    private double meters2degree(double meters){
        return meters*(180/Math.PI/R_EARTH);
    }
    /*
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */
    /*
     * :: This function converts decimal degrees to radians             :
     */
    /*
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */
    /**
     * @param deg
     * @return
     */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */
    /*
     * :: This function converts radians to decimal degrees             :
     */
    /*
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */
    /**
     * @param rad
     * @return
     */
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public String getName() {
        throw new UnsupportedOperationException("Spherical Law of Cosines");
    }
}
