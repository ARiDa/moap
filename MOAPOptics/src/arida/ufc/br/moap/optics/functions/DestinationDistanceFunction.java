package arida.ufc.br.moap.optics.functions;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;

/**
 * 
 * @author igobrilhante
 *
 * @param <S> S represents the spatial extent of the trajectories
 * <p>
 * This distance function computes the distance between the destinations of two given trajectories
 * </p>
 */
public class DestinationDistanceFunction<S,T> implements
		IDistanceFunction<Trajectory<S, T>> {
	
	/**
	 * Distance Function to compute the distance between two spatial extent
	 */
	private final IDistanceFunction<S> spatialFunction;

	public DestinationDistanceFunction(IDistanceFunction<S> spatialFunction) {
		this.spatialFunction = spatialFunction;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Destination Distance Function";
	}

	@Override
	public Double evaluate(Trajectory<S, T> traj1, Trajectory<S, T> traj2) {

		S traj1PointN = traj1.getPoint(traj1.getPointCount()-1);
		S traj2PointN = traj2.getPoint(traj2.getPointCount()-1);

		double distance = spatialFunction.evaluate(traj1PointN, traj2PointN);

		return distance;
	}
}
