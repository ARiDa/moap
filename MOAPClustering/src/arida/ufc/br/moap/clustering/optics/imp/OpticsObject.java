//package arida.ufc.br.moap.clustering.optics.imp;
//
//import java.util.List;
//
//public class OpticsObject<T> implements Comparable<OpticsObject<T>> {
//	private T object;
//	private boolean processed;
//	private double reachabilityDistance;
//	private double coreDistance;
//	private List<OpticsObject<T>> neighbors;
//	private int clusterID;
//	
//
//	public OpticsObject(T object) {
//		this.object = object;
//		this.processed = false;
//		this.reachabilityDistance = Optics.UNDEFINED;
//		this.coreDistance = Optics.UNDEFINED;
//	}
//
//	public T getObject() {
//		return object;
//	}
//
//	public void setObject(T object) {
//		this.object = object;
//	}
//
//	public int getClusterID() {
//		return clusterID;
//	}
//
//	public void setClusterID(int clusterID) {
//		this.clusterID = clusterID;
//	}
//
//	public boolean isProcessed() {
//		return processed;
//	}
//
//	public void setProcessed(boolean processed) {
//		this.processed = processed;
//	}
//
//	public double getReachabilityDistance() {
//		return reachabilityDistance;
//	}
//
//	public void setReachabilityDistance(double reachability_distance) {
//		this.reachabilityDistance = reachability_distance;
//	}
//
//	public double getCoreDistance() {
//		return coreDistance;
//	}
//
//	public void setCoreDistance(double core_distance) {
//		this.coreDistance = core_distance;
//	}
//
//	public List<OpticsObject<T>> getNeighbors() {
//		return neighbors;
//	}
//
//	public void setNeighbors(List<OpticsObject<T>> neighbors) {
//		this.neighbors = neighbors;
//	}
//
//	@Override
//	public int compareTo(OpticsObject<T> o) {
//		// TODO Auto-generated method stub
//		if(this.getReachabilityDistance() < o.getReachabilityDistance()){
//			return -11;
//		}
//		if(this.getReachabilityDistance() > o.getReachabilityDistance()){
//			return 1;
//		}
//		return 0;
//	}
//	
//	@Override
//	public String toString(){
//		return "OpticObject of "+object.toString() +"\n with reachability-distance "+reachabilityDistance +" and core-distance "+coreDistance+"\n Cluster:"+clusterID;
//	}
//	
//	
//	
//	
//	
//	
//	
//}
