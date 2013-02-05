package arida.ufc.br.moap.optics.functions;

import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.distance.spi.IDistanceFunction;

public class ODDistanceFunction<S,T> implements IDistanceFunction<Trajectory<S,T >> {

	private final IDistanceFunction<S> spatialFunction;
	
	public ODDistanceFunction(IDistanceFunction<S> spatialFunction){
		this.spatialFunction = spatialFunction;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Origin-Destination distance function";
	}

	@Override
	public Double evaluate(Trajectory<S, T> arg0, Trajectory<S, T> arg1) {
		// TODO Auto-generated method stub
		
		double d1 = this.spatialFunction.evaluate(arg0.getPoint(0),arg1.getPoint(0) );
		double d2 = this.spatialFunction.evaluate(arg0.getPoint(arg0.getPointCount()-1),arg1.getPoint(arg1.getPointCount()-1) );
		
		return (d1+d2)/2;
	}

}
