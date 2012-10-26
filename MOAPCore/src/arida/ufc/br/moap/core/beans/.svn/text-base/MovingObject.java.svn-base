package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.IAnnotable;
import java.util.ArrayList;
import java.util.List;

import arida.ufc.br.moap.core.spi.ITemporalFeature;

/**
 * @author igobrilhante
 *
 * @param <T>
 *
 * User Structure
 * @param <S>
 */
public class MovingObject<S extends IAnnotable, T> {
    
    private String id;
    private List<Trajectory<S, T>> trajectories;
    
    public MovingObject(String id) {
        this.id = id;
        this.trajectories = new ArrayList<Trajectory<S, T>>();
    }
    
    public MovingObject(String id, List<Trajectory<S, T>> trajectoryList) {
        super();
        this.id = id;
        this.trajectories = trajectoryList;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void addTrajectory(Trajectory<S, T> trajectory) {
        trajectory.setMovingObject(id);
        this.trajectories.add(trajectory);
    }
    
    public void setTrajectories(List<Trajectory<S, T>> trajectoryList) {
        for (Trajectory traj : trajectoryList) {
            traj.setId(id);
        }
        this.trajectories = trajectoryList;
    }
    
    public List<Trajectory<S, T>> getTrajectories() {
        return this.trajectories;
    }
    
    public Trajectory<S, T> getTrajectory(int index) {
        return this.trajectories.get(index);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MovingObject other = (MovingObject) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return "Moving Object " + id;
    }
    
    public int getTrajectoryCount(){
        return this.trajectories.size();
    }
}
