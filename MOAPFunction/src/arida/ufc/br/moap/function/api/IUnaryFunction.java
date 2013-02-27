/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.function.api;

/**
 *
 * @author igobrilhante
 */
public interface IUnaryFunction<A,T> extends IFunctor {
    
    /**
     * Definition of an unary function with parameter A and return T
     * 
     * @param object
     * @return 
     */
    public T evaluate(A object);
}
