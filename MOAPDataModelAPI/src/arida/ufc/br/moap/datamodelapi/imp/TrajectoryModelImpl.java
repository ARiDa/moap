package arida.ufc.br.moap.datamodelapi.imp;

import arida.ufc.br.moap.core.algorithm.spi.ITrajectoryFactory;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.beans.TrajectoryFactoryImp;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * ITrajectoryModel implementation.
 *
 * @author franzejr
 * @author rafaelelias
 */
public class TrajectoryModelImpl<S, T> implements ITrajectoryModel {
    /*
     * List of all trajectories. Remembering that a trajectory
     * is a set of points which each point has a Latitude,Longitude
     * and one Timestamp.
     */
    /*
     * Trajectory List
     */
    private List<Trajectory<S, T>> trajectories = new ArrayList<Trajectory<S, T>>();
    /*
     *Identify a trajectory 
     */
    private HashMap<String, Trajectory<S, T>> trajectoryIndices = new HashMap<String, Trajectory<S, T>>();
    /*
     * Identify Moving Objects
     */
    private HashMap<String, MovingObject> movingObjectIndices = new HashMap<String, MovingObject>();
    /*
     * Moving Object List
     */
    private List<MovingObject> movingObjects = new ArrayList<MovingObject>();
    /*
     * Build a trajectory for a model
     */
    private ITrajectoryFactory<S, T> factory = new TrajectoryFactoryImp<S, T>();
    
    @Override
    public Trajectory<S, T> getTrajectory(int id) {
        return trajectories.get(id);
    }
    
    @Override
    public Collection<Trajectory<S, T>> getTrajectories() {
        return this.trajectories;
    }
    
    @Override
    public void addMovingObject(MovingObject mo) {
        this.movingObjects.add(mo);
        this.movingObjectIndices.put(mo.getId(), mo);
        
    }
    
    @Override
    public MovingObject getMovingObject(String id) {
        return this.movingObjectIndices.get(id);
    }
    
    @Override
    public int getTrajectoryCount() {
        return this.trajectories.size();
    }
    
    @Override
    public int getMovingObjectCount() {
        return this.movingObjects.size();
    }
    
    @Override
    public void addTrajectory(Trajectory trajectory) {
        this.trajectories.add(trajectory);
        this.trajectoryIndices.put(trajectory.getId(), trajectory);
    }
    
    @Override
    public String toString() {
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
        return this.getMovingObjects();
    }
    
    @Override
    public Collection<Trajectory<S,T>> getTrajectories(MovingObject mo) {
        List<Trajectory<S,T>> list = new ArrayList<Trajectory<S,T>>();
        for (Trajectory traj : this.trajectoryIndices.values()) {
            if (traj.getMovingObject().equals(mo)) {
                list.add(traj);
            }
        }
        return list;
    }
    
    @Override
    public Collection<Trajectory<S,T>> getTrajectories(String moId) {
        MovingObject mo = this.movingObjectIndices.get(moId);
        return getTrajectories(mo);
    }
    
    @Override
    public Trajectory getTrajectory(String id) {
        return this.trajectoryIndices.get(id);
    }
    
    @Override
    public Trajectory removeTrajectory(int id) {
        Trajectory t = this.trajectories.remove(id);
        this.trajectoryIndices.remove(t.getId());
        return t;
    }
    
    @Override
    public Trajectory removeTrajectory(String id) {
        Trajectory t = this.trajectoryIndices.remove(id);
        this.trajectories.remove(t);
        
        return t;
    }
    
    @Override
    public MovingObject removeMovingObject(int idx) {
        MovingObject mo = this.movingObjects.get(idx);
        this.movingObjectIndices.remove(mo.getId());
        
        return mo;
    }
    
    @Override
    public MovingObject removeMovingObject(String id) {
        MovingObject mo = this.movingObjectIndices.get(id);
        this.movingObjects.remove(mo);
        
        return mo;
    }
}
