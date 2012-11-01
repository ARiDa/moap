/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.iterators.api.IMovingObjectIterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author igobrilhante
 */
public class MovingObjectIteratorImp implements IMovingObjectIterator {

    private List<MovingObject> array;
    private int currentIndex = 0;
    private int currentSize;
    protected Lock lock;

    public MovingObjectIteratorImp(List<MovingObject> list, Lock lock) {
        this.array = list;
        this.currentSize = list.size();
        this.lock = lock;
    }

    @Override
    public boolean hasNext() {
        boolean res = currentIndex < currentSize && array.get(currentIndex) != null;
        if (!res && lock != null) {
            lock.unlock();
        }
        return res;
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
