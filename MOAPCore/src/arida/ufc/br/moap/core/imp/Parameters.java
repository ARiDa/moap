package arida.ufc.br.moap.core.imp;

import java.util.HashMap;
import java.util.Set;

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
    private HashMap<String,Class> parametersClass = new HashMap<String,Class>();
    /*
     * You can add a Parameter,to do it, you need to
     * specify the parameter name and the value
     */
    public void addParam(String name,Object value){
        parameters.put(name, value);
        parametersClass.put(name, value.getClass());
    }
    
    /*
     * @return Parameter
     */
    public Object getParamValue(String paramName){
        return parameters.get(paramName);
    }
    
        public Class getParamClass(String paramName){
        return parametersClass.get(paramName);
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
    
    public Set<String> getParameters(){
        return this.parameters.keySet();
    }
    
    
}
