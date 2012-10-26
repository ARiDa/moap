/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.stop.imp;

import arida.ufc.br.moap.core.beans.Annotations;
import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.stop.spi.IStop;

/**
 *
 * @author igobrilhante
 * 
 * <p>
 * Stay point is a part of the trajectory where a moving object spent at least a given amount of time within a given spatial
 * threshold
 * </p>
 * <p>
 * It is represented as a point (Latitude and Longitude) and a time interval.
 * </p>
 */
public class StayPoint implements IStop<LatLonPoint> {

    
    /*
     * Spatial position
     */
    private LatLonPoint extent;
    private Annotations annotations;
    /**
     *
     * @param extent
     * @param interval
     */
    public StayPoint(LatLonPoint extent) {
        this.extent = extent;
    }
    
    
    /**
     *
     * @return {@link LatLonPoint}
     */
    @Override
    public LatLonPoint getExtent() {
        return this.extent;
    }

    @Override
    public Annotations getAnnotations() {
        if(this.annotations == null){
            this.annotations = new Annotations();
        }
        return this.annotations;
    }


}
