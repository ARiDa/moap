package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;

/*
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
     * The main method to execute the algorithm
     * 
     * @param data Input data
     * @param parameters Parameters which will be used in the algorithm
     */
    public abstract O execute(I data, Parameters parameters);
}
