package test.examples;

import java.sql.Timestamp;
import java.util.List;

import location.recommender.beans.TrajectoryOfPoisLoader;
import mf.core.beans.Interval;
import mf.core.beans.MovingObject;

import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.plugin.ClusteringCoefficient;
import org.gephi.statistics.plugin.Degree;

import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkExporterImpl;
import arida.ufc.br.networks.UserNetwork;

public class UserNetworkExample2 {
	public static void main(String[] args){

		// Example of User Network from Trajectory of Cluster Pois

		String query = "select * from stop_poi_1200s_150m where (extract(epoch from end_time)-extract(epoch from start_time)) <= 25200 order by userid,start_time ";

		List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects = TrajectoryOfPoisLoader.loaderTrajectoryOfPois(query);

		UserNetwork userNetwork = new UserNetwork(movingObjects);
		userNetwork.buildNetwork();

		GraphModel graphModel = userNetwork.getUserNetwork().getGraphModel();
		AttributeModel attributeModel = userNetwork.getAttributeModel();

		System.out.println("Nodes: " + userNetwork.getUserNetwork().getNodeCount());
		System.out.println("Edges: " + userNetwork.getUserNetwork().getEdgeCount());
		System.out.println("Computing ... distances");

		//DEGREE
		Degree degree = new Degree();
		degree.execute(graphModel, attributeModel);
		System.out.println("Degree: " + degree.getAverageDegree());

		////WEIGHTED DEGREE
		//        WeightedDegree wdegree = new WeightedDegree();
		//        wdegree.execute(graphModel, attributeModel);
		//        System.out.println("Weighted Degree: " + wdegree.getAverageDegree());

		//CLUSTERING COEFFICIENT
		ClusteringCoefficient clustering = new ClusteringCoefficient();
		clustering.setDirected(graphModel.isDirected());
		clustering.execute(graphModel, attributeModel);
		Double cc = clustering.getAverageClusteringCoefficient();
		System.out.println("Clustering: " + cc);

		//PAGE RANK
//		PageRank pr = new PageRank();
//		//        pr.setProbability(0.25);
//		pr.setUseEdgeWeight(true);
//		pr.execute(graphModel, attributeModel);

		//AVERAGE SHORTEST PATH LENGTH
//		GraphDistance graphDistance = new GraphDistance();
//		graphDistance.setDirected(graphModel.isDirected());
//		graphDistance.setNormalized(true);
//		graphDistance.execute(graphModel, attributeModel);
//		Double diameter = graphDistance.getDiameter();
//		Double path = graphDistance.getPathLength();
//		System.out.println("Path Length: " + path);
//		System.out.println("Diameter: " + diameter);


		//COMPONENTS
//		ConnectedComponents comps = new ConnectedComponents();
//		comps.setDirected(graphModel.isDirected());
//		comps.execute(graphModel, attributeModel);
//		System.out.println(comps.getReport());

		try {
			Network network = new Network("user_network");
	        NetworkExporterImpl exp;
			exp = new NetworkExporterImpl(network,userNetwork.getUserNetwork().getGraphModel(),userNetwork.getAttributeModel());
			exp.exportNetwork();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
