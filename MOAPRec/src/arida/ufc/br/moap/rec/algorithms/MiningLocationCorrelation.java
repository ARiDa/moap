package arida.ufc.br.moap.rec.algorithms;

//package arida.ufc.br.locationrecommender.algorithms;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import mf.core.beans.TimeStampedPoint;
//import mf.core.beans.SampleTrajectory;
//import mf.core.beans.Trajectory;
//import mf.core.beans.User;
//
//import arida.ufc.br.locationrecommender.utilities.Utilities;
//import arida.ufc.br.staypoint.algorithms.StayPointDetection;
//import arida.ufc.br.staypoint.beans.StayPoint;
//
//public class MiningLocationCorrelation {
//	private List<User> users; // List of users
//	private Map<User,SampleTrajectory> userTrajectories; // List of trajectories, where trajectories are composed by points in time
//	private double timeThres; // Time Threshold for stay point detection
//	private double distThres; // Distance threshold for stay point detection
//	private double tripPartition; // Trip partition threshold
//	
//	private List<StayPoint> userStayPoints;
//	private Map<User,Trajectory<StayPoint>> trajs_as_staypoints = new HashMap<User, Trajectory<StayPoint>>();
////	private H // Sequence of locations
//	
//	public void execute(){
//		for(User u : users){
//			
//			SampleTrajectory traj_u = userTrajectories.get(u);
//			StayPointDetection<StayPoint> stayPointDetection =  new StayPointDetection<StayPoint>(traj_u,distThres,timeThres);
//			List<StayPoint> st_u = stayPointDetection.getStayPoints(); // Stay points of User u
//			Trajectory<StayPoint> traj_as_stayPoints_u = new Trajectory<StayPoint>("1", st_u);
//			
//			
//			trajs_as_staypoints.put(u, traj_as_stayPoints_u);
//			userStayPoints.addAll(st_u);
//		}
//	}
//	
//}
