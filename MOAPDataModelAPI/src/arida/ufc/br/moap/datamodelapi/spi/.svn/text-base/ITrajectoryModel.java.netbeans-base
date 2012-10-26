package arida.ufc.br.moap.datamodelapi.spi;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.TrajectoryFactoryImp;
import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author franzejr
 * @author rafaelelias
 */
public interface ITrajectoryModel extends IDataModel {
    
    /*
     * Add a single trajectory
     */
//    public void addTrajectory(List<Trajectory> trajectory);
    
    public void addTrajectory(Trajectory trajectory);
    /*
     * @return Trajectory
     */
    public Trajectory getTrajectory(int id);
    
    public ITrajectoryFactory factory();
    
    /*
     * @return List of All trajectories
     */
    public Collection<Trajectory> getTrajectories();
    
    public Collection<MovingObject> getMovingObjects();
    /*
     * Add a moving object
     */
    public void addMovingObject(MovingObject mo);
    /*
     * Set a moving object into a trajectory
     */
//    public void  setMovingObject(String trajectoryId);
    /*
     * @return Moving Object
     */
    public MovingObject getMovingObject(int idx);
    
    public MovingObject getMovingObject(String id);
    /*
     * @return Trajectory count
     */
    public int getTrajectoryCount();
    /*
     * @return Moving Object count
     */
    public int getMovingObjectCount();
}
