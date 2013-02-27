package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.spi.IDataModel;


/*
 * This class provides common methods for other algorithms
 * 
 */

public abstract class AbstractParametricAlgorithm<I extends IDataModel<?>, O extends IDataModel<?> > extends AbstractAlgorithm<O> {

    /*
     * Parameters which will be used by algorithm
     */
    protected Parameters parameters;
    

    /*
     * Validate the parameters
     * @return 
     */
    protected boolean areParametersValid(Parameters input){
        
        return this.parameters.validate(parameters);
    }

    /*
     * The main method to execute the algorithm
     * 
     * @param data Input data
     * @param parameters Parameters which will be used in the algorithm
     */
    public abstract O execute(I data, Parameters parameters);

}
