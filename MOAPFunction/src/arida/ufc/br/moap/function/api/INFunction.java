/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.function.api;

/**
 *
 * @author igobrilhante
 */
public interface INFunction<A,T> extends IFunctor {
    
    /**
     * Definition of of N-Function which takes n variables of type A and returns T
     * 
     * @param object
     * @return 
     */
    public T evaluate(A... object);
}
