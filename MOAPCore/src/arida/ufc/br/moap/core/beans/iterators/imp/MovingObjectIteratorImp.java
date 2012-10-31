/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.iterators.api.IMovingObjectIterator;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public class MovingObjectIteratorImp implements IMovingObjectIterator {

    private List<MovingObject> array;
    private int currentIndex = 0;
    private int currentSize;
    public MovingObjectIteratorImp(List<MovingObject> list){
        this.array = list;
        this.currentSize = list.size();
    }
    @Override
    public boolean hasNext() {
        return currentIndex < currentSize && array.get(currentIndex) != null;
    }

    @Override
    public MovingObject next() {
        return array.get(currentIndex++);
    }

    @Override
    public void remove() {
       /*
        * Not Implemented
        */
    }
    
}
