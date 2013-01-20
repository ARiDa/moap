package arida.ufc.br.moap.clustering.api;

import arida.ufc.br.moap.core.algorithm.spi.AbstractAlgorithm;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;

/**
 * @author igobrilhante
 *
 * @param <D>
 * @param <T>
 */
public abstract class IClusteringAlgorithm<T> 
    extends AbstractAlgorithm<IDataModel<T> , IDataModel<? extends ICluster<T>>> {

    protected IDistanceFunction<T> distanceFunction;
//    /**
//     * @return
//     */
//    public abstract String getName();

    /**
     * 
     * @param distanceFunction 
     */
    public void setDistanceFunction(IDistanceFunction<T> distanceFunction){
        this.distanceFunction = distanceFunction;
    }
    
//    /**
//     * @return
//     */
//    public abstract Collection<ICluster<T>> getClusters();
    
    
    
}
