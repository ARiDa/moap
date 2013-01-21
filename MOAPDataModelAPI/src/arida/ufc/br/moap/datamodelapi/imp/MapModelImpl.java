
package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author igobrilhante
 */
public class MapModelImpl<O1,O2> extends HashMap<O1, O2> implements IDataModel<O1> {
    
    
    public MapModelImpl(){
       super();
    }

//    @Override
//    public Set<Entry<O1, O2>> entrySet() {
//        return this.data.entrySet();
//    }

    @Override
    public String getName() {
        return "Map Model";
    }

    @Override
    public Collection<O1> getInstances() {
        return this.keySet();
    }
    
}
