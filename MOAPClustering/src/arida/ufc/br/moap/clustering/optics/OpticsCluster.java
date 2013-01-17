package arida.ufc.br.moap.clustering.optics;

import arida.ufc.br.moap.clustering.api.ICluster;
import java.util.ArrayList;
import java.util.List;


/**
 * @author igobrilhante
 *
 * @param <T>
 */
public class OpticsCluster<T> implements ICluster<T>,Comparable<Integer> {
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
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Integer arg0) {
		// TODO Auto-generated method stub
		return id-arg0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "Cluster "+id+" : "+objects;
	}

	
	
	
	
	
}
