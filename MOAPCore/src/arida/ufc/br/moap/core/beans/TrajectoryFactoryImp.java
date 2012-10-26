/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;
import arida.ufc.br.moap.core.spi.IAnnotable;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryFactoryImp<S extends IAnnotable,T>  implements ITrajectoryFactory {

   
    
    @Override
    public Trajectory<S,T>  newTrajectory(String id) {
        return new Trajectory(id);
        
    }

    @Override
    public MovingObject<S,T> newMovingObject(String id) {
        return new MovingObject(id);
    }
    
}
