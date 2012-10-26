package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.TrajectoryFactoryImp;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * ITrajectoryModel implementation.
 *
 * @author franzejr
 * @author rafaelelias
 */
public class TrajectoryModelImpl implements ITrajectoryModel{
    /*
     * List of all trajectories. Remembering that a trajectory
     * is a set of points which each point has a Latitude,Longitude
     * and one Timestamp.
     */
    private HashMap<String,MovingObject> movingObjectIndices = new HashMap<String, MovingObject>();
    private ITrajectoryFactory factory = new TrajectoryFactoryImp();


//    @Override
//    public void addTrajectory(List<Trajectory> trajectory) {
//        trajectory.addAll(trajectory);
//    }

    @Override
    public Trajectory getTrajectory(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<Trajectory> getTrajectories() {
        Collection<Trajectory> trajectorySet = new HashSet<Trajectory>();
        for(MovingObject mo : movingObjectIndices.values()){
            trajectorySet.addAll(mo.getTrajectories());
        }
        return trajectorySet;
    }

    @Override
    public void addMovingObject(MovingObject mo) {
        this.movingObjectIndices.put(mo.getId(), mo);
        
    }

//    @Override
//    public void setMovingObject(String trajectoryId) {
//        this.trajectoryIndices.get(trajectoryId).setMovingObject(trajectoryId);
//    }

    @Override
    public MovingObject getMovingObject(String id) {
        return this.movingObjectIndices.get(id);
    }

    @Override
    public int getTrajectoryCount() {
        int count = 0;
        for(MovingObject mo : movingObjectIndices.values()){
            count += mo.getTrajectoryCount();
        }
        return count;
    }

    @Override
    public int getMovingObjectCount() {
        return this.movingObjectIndices.size();
    }

    @Override
    public void addTrajectory(Trajectory trajectory) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String toString(){
        return "";
    }

    @Override
    public ITrajectoryFactory factory() {
        return this.factory;
    }

    @Override
    public MovingObject getMovingObject(int idx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<MovingObject> getMovingObjects() {
        return this.movingObjectIndices.values();
    }

    
}
