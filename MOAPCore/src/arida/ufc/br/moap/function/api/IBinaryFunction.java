/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.function.api;

/**
 *
 * @author igobrilhante
 * 
 * Function with two arguments that return a value
 */
public interface IBinaryFunction<L,R,T> extends IFunctor {
    
    /**
     * This is the definition of a general binary function
     * 
     * @param obj1
     * @param obj2
     * @return a value of type T
     */
    public T evaluate(L obj1,R obj2);
}
