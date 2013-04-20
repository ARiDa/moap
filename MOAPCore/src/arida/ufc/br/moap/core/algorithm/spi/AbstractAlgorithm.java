package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;

public abstract class AbstractAlgorithm<O extends IDataModel<?>> {

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
  
    public abstract String getName();
    
    public Reporter getReporter(){
    	return this.report;
    }
}
