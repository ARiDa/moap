/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.iterators.api.ITrajectoryIterable;
import arida.ufc.br.moap.core.beans.iterators.api.ITrajectoryIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryIterableImp<S,T> implements ITrajectoryIterable<S,T> {

    private TrajectoryIteratorImp<S,T> iterator;
    
    public TrajectoryIterableImp(List<Trajectory<S,T>> trajectories,Lock lock){
        this.iterator = new TrajectoryIteratorImp<S,T>(trajectories,lock);
    }
    
    @Override
    public ITrajectoryIterator<S,T> iterator() {
        return iterator;
    }

    @SuppressWarnings("unchecked")
	@Override
    public Trajectory<S,T>[] toArray() {
        ArrayList<Trajectory<S,T>> list = new ArrayList<Trajectory<S,T>>();
         for (; iterator.hasNext();) {
            list.add(iterator.next());
        }
         
         return list.toArray(new Trajectory[0]);
    }

    @Override
    public void doBreak() {
        if (this.iterator.lock != null) {
            this.iterator.lock.unlock();
        }
    }

	@Override
	public Collection<Trajectory<S,T>> toCollection() {
		// TODO Auto-generated method stub
		return Arrays.asList(toArray());
	}


    
}
