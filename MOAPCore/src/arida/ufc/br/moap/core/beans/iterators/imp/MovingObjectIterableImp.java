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
import java.util.concurrent.locks.Lock;

/**
 *
 * @author igobrilhante
 */
public class MovingObjectIterableImp implements IMovingObjectIterable {

    private MovingObjectIteratorImp iterator;

    public MovingObjectIterableImp(List<MovingObject> list, Lock lock) {
        this.iterator = new MovingObjectIteratorImp(list, lock);

    }

    @Override
    public IMovingObjectIterator iterator() {
        return iterator;
    }

    @Override
    public MovingObject[] toArray() {
        ArrayList<MovingObject> list = new ArrayList<MovingObject>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list.toArray(new MovingObject[0]);

    }

    @Override
    public void doBreak() {
        if (this.iterator.lock != null) {
            this.iterator.lock.unlock();
        }
    }
}
