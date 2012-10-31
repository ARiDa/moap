/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.api;

import arida.ufc.br.moap.core.beans.Trajectory;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public interface ITrajectoryIterable extends Iterable<Trajectory> {
    @Override
    public ITrajectoryIterator iterator();
    
    public Trajectory[] toArray();

}
