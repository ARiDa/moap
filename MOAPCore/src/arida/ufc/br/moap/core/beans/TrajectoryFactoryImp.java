/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;

/**
 *
 * @author igobrilhante
 */
public class TrajectoryFactoryImp<S,T>  implements ITrajectoryFactory<S,T> {

   
    
    public Trajectory<S,T>  newTrajectory(String id, MovingObject mo) {
        return new Trajectory<S,T>(id, mo);
        
    }

    public MovingObject newMovingObject(String id) {
        return new MovingObject(id);
    }
    
}
