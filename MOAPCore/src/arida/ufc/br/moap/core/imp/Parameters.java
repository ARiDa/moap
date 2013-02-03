package arida.ufc.br.moap.core.imp;

import java.util.HashMap;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * Parameters commonly used in algorithms.With this parameters is possible
 * represent some algorithm you want, because the input of an algorithm can be a
 * representation of itself.
 *
 * @author franzejr
 */
public class Parameters {
    /*
     * All parameters from an algorithm
     */

    private HashMap<String, Object> parameters = new HashMap<String, Object>();
    private HashMap<String, Class<? extends Object>> parametersClass = new HashMap<String, Class<? extends Object>>();
    /*
     * You can add a Parameter,to do it, you need to
     * specify the parameter name and the value
     */

    public void addParam(String name, Object value) {
        parameters.put(name, value);
        parametersClass.put(name, value.getClass());
    }

    public void addClass(String name, Class<? extends Object> c) {
        parametersClass.put(name, c);
    }

    /*
     * @return Parameter
     */
    public Object getParamValue(String paramName) {
        return parameters.get(paramName);
    }

    public Class<? extends Object> getParamClass(String paramName) {
        return parametersClass.get(paramName);
    }

    /*
     * Remove a parameter
     */
    public void removeParam(String o) {
        parameters.remove(o);
    }
    /*
     * Update a Parameter
     */

    public void updateParam(String name, Object o) {
        parameters.put(name, o);
    }

    public Set<String> getParameters() {
        return this.parameters.keySet();
    }

    @Override
    public String toString() {
        ToStringBuilder toString = new ToStringBuilder(this);

        if (this.parameters.values().size() > 0) {
            for (String s : this.parameters.keySet()) {

                toString.append(s, this.parameters.get(s));
            }
        } else {
            for (String s : this.parametersClass.keySet()) {

                toString.append(s, this.parametersClass.get(s).getName());
            }
        }

        return toString.toString();

    }

    public boolean validate(Parameters params) {
        //        Set<String> diff = new HashSet<String>(input.getParameters());
//        diff.removeAll(this.parameters.getParameters());
//
//        if(!diff.isEmpty()){
//            throw new MissingParameter();
//        }

        Validate.notEmpty(params.getParameters(), "Expected " + this.toString());
        for (String s : this.parametersClass.keySet()) {
            // Validate the input
            Validate.isTrue(params.getParameters().contains(s),
                    "Parameter should contain %s of type %s",
                    s, this.getParamClass(s));

            // Validate the input type
            Validate.isInstanceOf(this.getParamClass(s), params.getParamValue(s),
                    "Parameter for %s should be %s",
                    s, this.getParamClass(s));
        }



        return true;
    }
}
