package arida.ufc.br.moap.core.beans;

import java.util.Collection;

public class Linestring {
	
	private Collection<LatLonPoint> points;

	public Collection<LatLonPoint> getPoints() {
		return points;
	}

	public void setPoints(Collection<LatLonPoint> points) {
		this.points = points;
	}
	
	
}
