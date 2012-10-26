package test.utilities;

import java.sql.Timestamp;
import java.util.List;

import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networks.PoiNetwork;

import location.recommender.beans.TrajectoryOfPoisLoader;
import mf.core.beans.Interval;
import mf.core.beans.MovingObject;
import mf.core.beans.Trajectory;

public class TrajectoryOfPoisLoaderTest {
	public static void main(String[] args){
		String query = "select * from stops_trip_location_1200s_150m order by id,start_time";
		List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects = TrajectoryOfPoisLoader.loaderTrajectoryOfPois(query);
//		for(MovingObject m : l){
//			List<Trajectory> trajs = m.getTrajectories();
//			System.out.println(m);
//			for(Trajectory t : trajs){
//				System.out.println(t);
//				List points = t.getPoints();
//				List times = t.getTimes();
//				for(int i =0; i < times.size();i++){
//					System.out.println(points.get(i));
//					System.out.println(times.get(i));
//				}
//			}
//		}
		Network network = new Network("poiNetwork");
		try {
//			PoiNetwork poiNetwork = new PoiNetwork(movingObjects);
//			poiNetwork.buildNetwork();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
