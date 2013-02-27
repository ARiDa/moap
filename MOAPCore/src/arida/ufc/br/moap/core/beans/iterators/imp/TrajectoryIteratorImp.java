/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.iterators.api.ITrajectoryIterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryIteratorImp<S,T> implements ITrajectoryIterator<S,T> {

    private List<Trajectory<S,T>> array;
    private int currentIndex = 0;
    private int currentSize;
    protected Lock lock;
    
    public TrajectoryIteratorImp(List<Trajectory<S,T>> trajectories,Lock lock){
        this.array = trajectories;
        this.currentSize = array.size();
        this.lock = lock;
    }
    
    @Override
    public boolean hasNext() {
        return currentIndex < currentSize && array.get(currentIndex) != null;
    }

    @Override
    public Trajectory<S,T> next() {
        return array.get(currentIndex++);
    }

    @Override
    public void remove() {
       /*
        * Not Implemented
        */
    }
    
}
