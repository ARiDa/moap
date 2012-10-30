package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.TrajectoryFactoryImp;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * ITrajectoryModel implementation.
 *
 * @author franzejr
 * @author rafaelelias
 */
public class TrajectoryModelImpl<S,T> implements ITrajectoryModel{
    /*
     * List of all trajectories. Remembering that a trajectory
     * is a set of points which each point has a Latitude,Longitude
     * and one Timestamp.
     */
    
    /*
     *Identify a trajectory 
     */
    private HashMap<String,Trajectory<S,T>> trajectoryIndices = new HashMap<String, Trajectory<S,T>>();
    /*
     * Identify Moving Objects
     */
    private HashMap<String,MovingObject> movingObjectIndices = new HashMap<String, MovingObject>();
    /*
     * Build a trajectory for a model
     */
    private ITrajectoryFactory<S,T> factory = new TrajectoryFactoryImp<S,T>();


    @Override
    public Trajectory getTrajectory(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Collection<Trajectory<S,T>> getTrajectories() {
            return this.trajectoryIndices.values();
    }

    @Override
    public void addMovingObject(MovingObject mo) {
        this.movingObjectIndices.put(mo.getId(), mo);
        
    }

    @Override
    public MovingObject getMovingObject(String id) {
        return this.movingObjectIndices.get(id);
    }

    @Override
    public int getTrajectoryCount() {
        return this.trajectoryIndices.values().size();
    }

    @Override
    public int getMovingObjectCount() {
        return this.movingObjectIndices.size();
    }

    @Override
    public void addTrajectory(String id,Trajectory trajectory) {
        this.trajectoryIndices.put(id, trajectory);
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

    @Override
    public Collection getTrajectories(MovingObject mo) {
        List<Trajectory> list = new ArrayList<Trajectory>();
        for(Trajectory traj : this.trajectoryIndices.values()){
            if(traj.getMovingObject().equals(mo)){
                list.add(traj);
            }
        }
        return list;
    }

    @Override
    public Collection getTrajectories(String moId) {
        MovingObject mo = this.movingObjectIndices.get(moId);
        return getTrajectories(mo);
    }

    
}
