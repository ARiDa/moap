/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.function.api;

/**
 *
 * @author igobrilhante
 */
public interface ISimilarityFunction<T> extends IBinaryFunction<T, T, Double> {
    
    @Override
    public Double evaluate(T obj1,T obj2);
}
