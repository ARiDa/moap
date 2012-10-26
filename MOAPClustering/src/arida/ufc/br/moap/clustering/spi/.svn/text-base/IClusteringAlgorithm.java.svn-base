package arida.ufc.br.moap.clustering.spi;

import arida.ufc.br.moap.core.algorithm.spi.AbstractAlgorithm;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;
import java.util.Collection;

/**
 * @author igobrilhante
 *
 * @param <D>
 * @param <T>
 */
public abstract class IClusteringAlgorithm extends AbstractAlgorithm {

    protected IDistanceFunction distanceFunction;
    /**
     * @return
     */
    public abstract String getName();

    public void setDistanceFunction(IDistanceFunction distanceFunction){
        this.distanceFunction = distanceFunction;
    }
    /**
     * @return
     */
    public abstract Collection<ICluster> getClusters();
}
