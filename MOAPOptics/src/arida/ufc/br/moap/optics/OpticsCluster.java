package arida.ufc.br.moap.optics;

import java.util.ArrayList;
import java.util.List;

import arida.ufc.br.moap.clustering.api.ICluster;


/**
 * @author igobrilhante
 *
 * @param <T>
 */
public class OpticsCluster<T> implements ICluster<T>,Comparable<OpticsCluster<T>> {
    
	private final int id;
	private List<T> objects;
	
	/**
	 * @param id
	 */
	public OpticsCluster(int id){
		this.id = id;
		this.objects = new ArrayList<T>();
	}
	
	/* (non-Javadoc)
	 * @see mf.algorithm.clustering.spi.ICluster#getId()
	 */
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	/* (non-Javadoc)
	 * @see mf.algorithm.clustering.spi.ICluster#getObjects()
	 */
	@Override
	public List<T> getObjects() {
		// TODO Auto-generated method stub
		return this.objects;
	}
	
	/**
	 * @param object
	 */
	public void addObject(T object){
		this.objects.add(object);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "Cluster "+id+" : "+objects;
	}

    @Override
    public int compareTo(OpticsCluster<T> o) {
        return this.id-o.id;
    }

	
	
	
	
	
}
