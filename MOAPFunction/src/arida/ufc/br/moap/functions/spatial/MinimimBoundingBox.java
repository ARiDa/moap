/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.functions.spatial;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.Pair;
import arida.ufc.br.moap.function.api.IBinaryFunction;
import arida.ufc.br.moap.function.api.IUnaryFunction;

import java.util.Collection;

/**
 *
 * @author igobrilhante
 */
public class MinimimBoundingBox implements IUnaryFunction<Collection<LatLonPoint>, Pair<LatLonPoint, LatLonPoint>>, IBinaryFunction<LatLonPoint, LatLonPoint, Double> {

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

    @Override
    public Double evaluate(LatLonPoint bottom_left, LatLonPoint upper_right) {
        double area;
        

        
        LatLonPoint upper_left = new LatLonPoint(upper_right.getLongitude(), bottom_left.getLatitude());
        
        LatLonPoint bottom_right = new LatLonPoint(bottom_left.getLongitude(), upper_right.getLatitude());
        
        // Distance in KM
        Haversine distance = new Haversine(); 
        
        double upper_distance = distance.evaluate(bottom_left, upper_left);
        System.out.println(upper_distance);
        
        double side_distance = distance.evaluate(bottom_left, bottom_right);
        System.out.println(side_distance);
        
        area = upper_distance*side_distance;
        
        return area;
    }
    
    
}
