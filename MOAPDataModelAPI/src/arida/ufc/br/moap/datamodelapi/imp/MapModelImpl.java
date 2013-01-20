/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author igobrilhante
 */
public class MapModelImpl<O1,O2> extends AbstractMap<O1, O2> implements IDataModel<O1> {
    
    private HashMap data;
    
    public MapModelImpl(){
        this.data = new HashMap();
    }

    @Override
    public Set<Entry<O1, O2>> entrySet() {
        return this.data.entrySet();
    }

    @Override
    public String getName() {
        return "Map Model";
    }

    @Override
    public Collection<O1> getObjects() {
        return data.keySet();
    }
    
}
