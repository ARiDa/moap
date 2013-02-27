/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.api;

import java.util.Collection;

import arida.ufc.br.moap.core.beans.Trajectory;

/**
 *
 * @author igobrilhante
 */
public interface ITrajectoryIterable<S,T> extends Iterable<Trajectory<S,T>> {
    @Override
    public ITrajectoryIterator<S,T> iterator();
    public void doBreak();
    public Trajectory<S,T>[] toArray();
    public Collection<Trajectory<S,T>> toCollection();

}
