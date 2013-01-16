/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.function.api;

/**
 *
 * @author igobrilhante
 */
/**
 *
 * @author igobrilhante
 * 
 * Define a function with no arguments that returns a value T
 */
public interface IFunction<T> extends IFunctor {
    
    /**
     * This is a function that has no parameters and returns T
     * 
     * @return a value of type T 
     */
    public T evaluate();
}
