package arida.ufc.br.moap.stop.spi;

import arida.ufc.br.moap.core.algorithm.spi.AbstractAlgorithm;
import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Interval;
/*
 * Abstract class which all stop algorithm must be. There are methods for
 * stop algorithms.
 */
public abstract class IStopAlgorithm extends AbstractAlgorithm<ITrajectoryModel<LatLonPoint,DateTime>,ITrajectoryModel<? extends IStop,Interval> > {
   
    /*
     * Default method do get all the stay points.
     * Each algorithm will decide how to use this method.
     * 
     * @return List of Stops
     */
    public abstract List<? extends IStop> getStops();
}
