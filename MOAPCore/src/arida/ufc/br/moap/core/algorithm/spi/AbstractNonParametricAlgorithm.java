package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.spi.IDataModel;

public abstract class AbstractNonParametricAlgorithm <I extends IDataModel<?>, O extends IDataModel<?> > extends AbstractAlgorithm<O> {

    
    /*
     * The main method to execute the algorithm
     * 
     * @param data Input data
     * @param parameters Parameters which will be used in the algorithm
     */
    public abstract O execute(I data);
    
    public abstract String getName();
}
