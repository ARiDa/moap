package arida.ufc.br.moap.clustering.api;

import arida.ufc.br.moap.core.algorithm.spi.AbstractAlgorithm;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import java.util.Collection;

/**
 * @author igobrilhante
 *
 * @param <D>
 * @param <T>
 */
public abstract class IClusteringAlgorithm<
        T1,
        I extends IDataModel<T1>,
        O extends IDataModel<ICluster<T1>>
    > 
    extends AbstractAlgorithm<I ,O> {

    protected IDistanceFunction<T1> distanceFunction;
//    /**
//     * @return
//     */
//    public abstract String getName();

    /**
     * 
     * @param distanceFunction 
     */
    public void setDistanceFunction(IDistanceFunction<T1> distanceFunction){
        this.distanceFunction = distanceFunction;
    }
    
//    /**
//     * @return
//     */
//    public abstract Collection<ICluster<T>> getClusters();
    
    
    
}
