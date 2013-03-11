package unit.src.arida.ufc.br.moap.functions.spatial;

import static org.junit.Assert.*;

import org.junit.Test;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.function.api.IDistanceFunction;
import arida.ufc.br.moap.functions.spatial.Haversine;
import arida.ufc.br.moap.functions.spatial.SphericalLawofCosines;

public class HaversineTest {

	@Test
	public void test() {
		IDistanceFunction<LatLonPoint> hav = new Haversine();
		IDistanceFunction<LatLonPoint> sphe = new SphericalLawofCosines();
		
		LatLonPoint l1 = new LatLonPoint(10.31982, 43.5764);
		LatLonPoint l2 = new LatLonPoint(10.44902, 43.69891);
		
		double distSpher = sphe.evaluate(l1,l2);
		double distHav = hav.evaluate(l1, l2);
		
		System.out.println("Shperical Law of Cosines: "+distSpher);
		System.out.println("Haversine: "+distHav);
	}

}
