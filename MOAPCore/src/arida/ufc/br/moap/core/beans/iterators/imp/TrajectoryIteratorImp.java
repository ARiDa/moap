/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.imp;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.iterators.api.ITrajectoryIterator;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryIteratorImp implements ITrajectoryIterator {

    private List<Trajectory> array;
    private int currentIndex = 0;
    private int currentSize;
    
    public TrajectoryIteratorImp(List trajectories){
        this.array = trajectories;
        this.currentSize = array.size();
    }
    
    @Override
    public boolean hasNext() {
        return currentIndex < currentSize && array.get(currentIndex) != null;
    }

    @Override
    public Trajectory next() {
        return array.get(currentIndex++);
    }

    @Override
    public void remove() {
       /*
        * Not Implemented
        */
    }
    
}
