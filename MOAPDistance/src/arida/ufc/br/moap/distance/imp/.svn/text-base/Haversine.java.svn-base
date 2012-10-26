package arida.ufc.br.moap.distance.imp;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;


/**
 * @author igobrilhante
 *
 */
public class Haversine implements IDistanceFunction<LatLonPoint> {

    private final double R_EARTH = 6371; // KM
	/*
     * (non-Javadoc) @see
     * mf.algorithm.distance.spi.IDistanceFunction#distance(java.lang.Object,
     * java.lang.Object)
     */

    @Override
    public double distance(LatLonPoint o1, LatLonPoint o2) {
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

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double _lat1 = Math.toRadians(lat1);
        double _lat2 = Math.toRadians(lat2);
       
        
        
        double a = Math.pow(dLat/2, 2) + Math.pow(dLon/2, 2) * Math.cos(_lat1) * Math.cos(_lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = R_EARTH * c;

        return dist;
    }

    

    /*
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */
    /*
     * :: This function converts radians to decimal degrees :
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
}
