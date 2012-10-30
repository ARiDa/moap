package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.IAnnotable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author igobrilhante
 *
 * @param <S>
 * @param <T>
 */
public class Trajectory<S,T> implements IAnnotable {

    private MovingObject movingObject;
    private String id;
    private List<S> points;
    private List<T> times;
    private Annotations annotations;

    /**
     * @param id
     * 
     */
    @Deprecated 
    public Trajectory(String id) {
        this.id = id;
        this.points = new ArrayList<S>();
        this.times = new ArrayList<T>();

    }
    
    public Trajectory(String id,MovingObject movingObject) {
        this.id = id;
        this.movingObject = movingObject;
        this.points = new ArrayList<S>();
        this.times = new ArrayList<T>();

    }

    public MovingObject getMovingObject() {
        return movingObject;
    }

    public void setMovingObject(MovingObject movingObject) {
        this.movingObject = movingObject;
    }

    /**
     * @param id
     * @param points
     * @param times
     */
    public Trajectory(String id, List<S> points, List<T> times) {
        this.id = id;
        this.points = points;
        this.times = times;
//		int size = points.size();
//		int i = 0;
//		timeStampedPoints = new ArrayList<Pair<S,T>>();
//		while(i<size){
//			timeStampedPoints.add(new Pair<S, T>(points.get(i), times.get(i)));
//			i++;
//		}
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return
     */
    public List<S> getPoints() {
        return points;
    }

    public S getPoint(int idx) {
        return this.points.get(idx);
    }

    /**
     * @param points
     */
    public void setPoints(List<S> points) {
        this.points = points;
    }

    public void addPoint(S point, T time) {
        this.points.add(point);
        this.times.add(time);
    }

    /**
     * @return
     */
    public List<T> getTimes() {
        return times;
    }

    public T getTime(int idx) {
        return this.times.get(idx);
    }

    /**
     * @param times
     */
    public void setTimes(List<T> times) {
        this.times = times;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
//		return "Trajectory with "+points.size()+" positions";
        return "Trajectory " + id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
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
        Trajectory<S, T> other = (Trajectory<S, T>) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public Annotations getAnnotations() {
        // TODO Auto-generated method stub
        if (this.annotations == null) {
            this.annotations = new Annotations();
        }
        return this.annotations;
    }

    public int getPointCount() {
        return this.points.size();
    }
}
