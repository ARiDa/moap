/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.iterators.api.IMovingObjectIterable;
import arida.ufc.br.moap.core.beans.iterators.api.IMovingObjectIterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public class MovingObjectIterableImp implements IMovingObjectIterable {

    private MovingObjectIteratorImp iterator;
    public MovingObjectIterableImp(List<MovingObject> list){
        this.iterator = new MovingObjectIteratorImp(list);
               
    }
    @Override
    public IMovingObjectIterator iterator() {
        return iterator;
    }

    @Override
    public MovingObject[] toArray() {
        ArrayList<MovingObject> list = new ArrayList<MovingObject>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }
        
        return list.toArray(new MovingObject[0]);
             
    }
    

    
}
