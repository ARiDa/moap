/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;

/**
 *
 * @author igobrilhante
 */
public interface ITrajectoryFactory<S,T> {
    
    public Trajectory<S,T>  newTrajectory(String id, MovingObject mo);
    
    public MovingObject newMovingObject(String id);
    
}
