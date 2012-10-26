/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.algorithm.spi;

import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.spi.IAnnotable;

/**
 *
 * @author igobrilhante
 */
public interface ITrajectoryFactory<S extends IAnnotable,T> {
    
    public Trajectory<S,T>  newTrajectory(String id);
    
    public MovingObject<S,T>  newMovingObject(String id);
    
}
