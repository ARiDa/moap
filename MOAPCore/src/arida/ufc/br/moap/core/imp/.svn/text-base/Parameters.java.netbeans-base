package arida.ufc.br.moap.core.imp;

import java.util.HashMap;

/**
 *
 * Parameters commonly used in algorithms.With this parameters is possible
 * represent some algorithm you want, because the input of an algorithm can
 * be a representation of itself.
 * 
 * @author franzejr
 */
public class Parameters {
    /*
     * All parameters from an algorithm
     */
    private HashMap<String, Object> parameters = new HashMap<String, Object>();
    /*
     * You can add a Parameter,to do it, you need to
     * specify the parameter name and the value
     */
    public void addParam(String name,Object o){
        parameters.put(name, o);
    }
    
    /*
     * @return Parameter
     */
    public Object getParam(String paramName){
        return parameters.get(paramName);
    }
    
    /*
     * Remove a parameter
     */
    public void removeParam(String o){
        parameters.remove(o);
    }
    /*
     * Update a Parameter
     */
    public void updateParam(String name, Object o ){
        parameters.put(name, o);
    }
    
    
}
