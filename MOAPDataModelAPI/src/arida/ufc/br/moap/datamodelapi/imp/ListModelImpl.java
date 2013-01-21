/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * General Model for tabular
 * 
 * @author igobrilhante
 */
public class ListModelImpl<O> extends ArrayList<O> implements IDataModel<O> {
        
    public ListModelImpl(){
       super();
    }
    
    public ListModelImpl(List<O> data){
       super(data);
    }
    

    @Override
    public String getName() {
        return "List Model";
    }

    @Override
    public Collection<O> getInstances() {
        return this;
    }
    
}
