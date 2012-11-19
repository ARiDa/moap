package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;

import org.apache.commons.lang3.Validate;

/*
 * This class provides common methods for other algorithms
 * 
 */

public abstract class AbstractAlgorithm<I extends IDataModel, O extends IDataModel> {

    /*
     * Parameters which will be used by algorithm
     */
    protected Parameters parameters;
    /*
     * Result from the algorithm
     */
    protected O result;

    /*
     * @return result Re
     */
    public O getResult() {
        return this.result;
    }
    /*
     * You can look for this report when you want to know in what state 
     * is your algorithm
     * 
     */
    protected Reporter report;

    /*
     * Validate the parameters
     * @return 
     */
    protected boolean areParametersValid(Parameters input){
        
//        Set<String> diff = new HashSet<String>(input.getParameters());
//        diff.removeAll(this.parameters.getParameters());
//
//        if(!diff.isEmpty()){
//            throw new MissingParameter();
//        }
        
//        Validate.notEmpty(input.getParameters());
//        
//        for(String s : this.parameters.getParameters()){
//            // Validate the input
//            Validate.isTrue(input.getParameters().contains(s),
//                    "Parameter should contain %s of type %s",
//                    s, this.parameters.getParamClass(s));
//            
//            // Validate the input type
//            Validate.isInstanceOf(input.getParamClass(s), input.getParamValue(s), 
//                    "Parameter for %s should be %s", 
//                    s, this.parameters.getParamClass(s));
//        }
//        
//        
//        
//        return true;
        
        return this.parameters.validateParameters(parameters);
    }

    /*
     * The main method to execute the algorithm
     * 
     * @param data Input data
     * @param parameters Parameters which will be used in the algorithm
     */
    public abstract O execute(I data, Parameters parameters);
}
