/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.iterators.api.ITrajectoryIterable;
import arida.ufc.br.moap.core.beans.iterators.api.ITrajectoryIterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryIterableImp implements ITrajectoryIterable {

    private TrajectoryIteratorImp iterator;
    
    public TrajectoryIterableImp(List trajectories){
        this.iterator = new TrajectoryIteratorImp(trajectories);
    }
    
    @Override
    public ITrajectoryIterator iterator() {
        return iterator;
    }

    @Override
    public Trajectory[] toArray() {
        ArrayList<Trajectory> list = new ArrayList<Trajectory>();
         for (; iterator.hasNext();) {
            list.add(iterator.next());
        }
         
         return list.toArray(new Trajectory[0]);
    }


    
}
