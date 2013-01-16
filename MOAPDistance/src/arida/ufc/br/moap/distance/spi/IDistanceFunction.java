package arida.ufc.br.moap.distance.spi;

import arida.ufc.br.moap.function.api.IBinaryFunction;

/**
 * IDistanceFunction is a binary function applied to two arguments of the same type T returning a double.
 * 
 * @author igobrilhante
 * @param <T> 
 */
public interface IDistanceFunction<T> extends IBinaryFunction<T, T, Double> {
    
    /**
     * 
     * @param o1
     * @param o2
     * @return 
     */
    @Override
    public Double evaluate(T o1,T o2);
}
