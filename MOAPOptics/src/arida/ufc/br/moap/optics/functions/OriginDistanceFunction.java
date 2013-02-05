package arida.ufc.br.moap.optics.functions;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;

/**
 * 
 * @author igobrilhante
 *
 * @param <S> S represents the spatial extent of the trajectories
 * <p>
 * This distance function computes the distance between the origin of two given trajectories
 * </p>
 */
public class OriginDistanceFunction<S,T> implements
IDistanceFunction<Trajectory<S, T>> {

	/**
	 * Distance Function to compute the distance between two spatial extent
	 */
	private final IDistanceFunction<S> spatialFunction;
	
	public OriginDistanceFunction(IDistanceFunction<S> spatialFunction){
		this.spatialFunction = spatialFunction;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Origin distance function";
	}

	@Override
	public Double evaluate(Trajectory<S, T> traj1, Trajectory<S, T> traj2) {
		
		S traj1Point1 = traj1.getPoint(0);
		S traj2Point1 = traj2.getPoint(0);
		
		double distance = spatialFunction.evaluate(traj1Point1, traj2Point1);
		
		return distance;
	}

}
