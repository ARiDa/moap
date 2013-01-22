package arida.ufc.br.moap.core.spi;

import java.util.Collection;

/**
 *
 * Abstraction Data, the DataModel can be input or output from the algorithm
 *
 * @author franzejr
 */
public interface IDataModel<T> {
    
    public String getName();
    public Collection<T> getInstances();
    public void addInstance(T instance);

}
