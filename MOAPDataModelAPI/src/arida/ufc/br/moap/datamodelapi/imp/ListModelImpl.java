/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public class ListModelImpl<O> extends AbstractList<O> implements IDataModel<O> {
    
    private List<O> data;
    
    public ListModelImpl(){
        this.data = new ArrayList();
    }
    
    public ListModelImpl(List<O> data){
        this.data = new ArrayList<O>(data);
    }

    @Override
    public String getName() {
        return "List Model";
    }

    @Override
    public Collection<O> getObjects() {
        return this.data;
    }

    @Override
    public O get(int index) {
        return data.get(index);
    }

    @Override
    public int size() {
        return data.size();
    }
    
}
