package arida.ufc.br.moap.stop.spi;

import arida.ufc.br.moap.core.algorithm.spi.AbstractAlgorithm;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import java.util.List;
/*
 * Abstract class which all stop algorithm must be. There are methods for
 * stop algorithms.
 */
public abstract class IStopAlgorithm extends AbstractAlgorithm<ITrajectoryModel,ITrajectoryModel> {
   
    /*
     * Default method do get all the stay points.
     * Each algorithm will decide how to use this method.
     * 
     * @return List of Stops
     */
    public abstract List<? extends IStop> getStops();
}
