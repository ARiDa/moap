/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans.iterators.api;

import arida.ufc.br.moap.core.beans.MovingObject;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public interface IMovingObjectIterable extends Iterable<MovingObject> {
    
    @Override
    public IMovingObjectIterator iterator();
    public void doBreak();
    public MovingObject[] toArray();
}
