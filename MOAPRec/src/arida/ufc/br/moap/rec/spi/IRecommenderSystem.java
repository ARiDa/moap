package arida.ufc.br.moap.rec.spi;


import arida.ufc.br.moap.core.algorithm.spi.IAlgorithm;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;

/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public interface IRecommenderSystem<U, I> extends IAlgorithm {
	
    /**
     * 
     * @return
     */
    public RecommendationSet<U, I> getRecommendation();
	

}
