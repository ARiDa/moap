package arida.ufc.br.moap.datamodelapi.spi;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.TrajectoryFactoryImp;
import arida.ufc.br.moap.core.spi.IAnnotable;
import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author franzejr
 * @author rafaelelias
 */
public interface ITrajectoryModel<S,T> extends IDataModel {
    
    /*
     * Add a single trajectory
     */
    
    public void addTrajectory(Trajectory<S,T> trajectory);
    /*
     * @return Trajectory
     */
    public Trajectory<S,T> getTrajectory(int id);
    
    public Trajectory<S,T> getTrajectory(String id);
    
    public Trajectory<S,T> removeTrajectory(int id);
    
    public Trajectory<S,T> removeTrajectory(String id);
    
    public ITrajectoryFactory<S,T> factory();
    
    /*
     * @return List of All trajectories for a Moving Object
     */
    public Collection<Trajectory<S,T>> getTrajectories(MovingObject mo);
    
    /*
     * @return Collection of all trajectory for a given Moving Object
     */
    public Collection<Trajectory<S,T>> getTrajectories(String mo);
    
    /*
     * @return List of All trajectories
     */
    public Collection<Trajectory<S,T>> getTrajectories();
    
    public Collection<MovingObject> getMovingObjects();
    /*
     * Add a moving object
     */
    public void addMovingObject(MovingObject mo);

    /*
     * @return Moving Object
     */
    public MovingObject getMovingObject(int idx);
    
    public MovingObject getMovingObject(String id);
    
    public MovingObject removeMovingObject(int idx);
    
    public MovingObject removeMovingObject(String id);
    /*
     * @return Trajectory count
     */
    public int getTrajectoryCount();
    /*
     * @return Moving Object count
     */
    public int getMovingObjectCount();
    
    
    
    
}
