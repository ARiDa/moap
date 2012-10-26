package test.examples;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.project.api.ProjectController;
import org.gephi.statistics.plugin.PageRank;
import org.openide.util.Lookup;

import location.recommender.beans.RecommendationDataset;
import mf.core.beans.Interval;
import mf.core.beans.MovingObject;
import mf.core.beans.Trajectory;
import arida.ufc.br.locationrecommender.algorithms.TopK;
import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkExporterImpl;
import arida.ufc.br.networks.PoiNetwork;
import arida.ufc.br.networks.UserNetwork;
import recommendation.database.RecommendationDB;
import recommender.evaluation.SurprisingGain;
import recommender.system.poirank.PoiRank;
import recommender.system.pr.s1.WeightedSlopeOne;


/**
 * @author igobrilhante
 * 
 * Example of PoiRank
 * 
 * Data
 * 
 * 
 * 
 * 
 * 
 * 
 * u1: C,B,A,C
 * u2: D,A,D,E,C,E
 * u3: A,F,A
 * u4: G,F,G,I,H,G
 * 
 */
public class PoiRankExample {
	public static void main(String[] args) throws Exception{
		
		// u1
		
		MovingObject<List<String>, Interval<Timestamp>> u1 = new MovingObject<List<String>, Interval<Timestamp>>("u1");
		Trajectory<List<String>, Interval<Timestamp>> t1 = new Trajectory<List<String>, Interval<Timestamp>>("t1");
		
		List<String> l1 = new ArrayList<String>();
		l1.add("C");
		Interval<Timestamp> int1 = new Interval<Timestamp>(new Timestamp(1), new Timestamp(2));
		List<String> l2 = new ArrayList<String>();
		l2.add("B");
		Interval<Timestamp> int2 = new Interval<Timestamp>(new Timestamp(3), new Timestamp(4));
		List<String> l3 = new ArrayList<String>();
		l3.add("A");
		Interval<Timestamp> int3 = new Interval<Timestamp>(new Timestamp(5), new Timestamp(6));
		List<String> l4 = new ArrayList<String>();
		l4.add("C");
		Interval<Timestamp> int4 = new Interval<Timestamp>(new Timestamp(7), new Timestamp(8));
		t1.addPoint(l1, int1);
		t1.addPoint(l2, int2);
		t1.addPoint(l3, int3);
		t1.addPoint(l4, int4);
		u1.addTrajectory(t1);
		// u2
		
		MovingObject<List<String>, Interval<Timestamp>> u2 = new MovingObject<List<String>, Interval<Timestamp>>("u2");
		Trajectory<List<String>, Interval<Timestamp>> t2 = new Trajectory<List<String>, Interval<Timestamp>>("t2");
		
		l1 = new ArrayList<String>();
		l1.add("D");
		int1 = new Interval<Timestamp>(new Timestamp(1), new Timestamp(2));
		l2 = new ArrayList<String>();
		l2.add("A");
		int2 = new Interval<Timestamp>(new Timestamp(3), new Timestamp(4));
		l3 = new ArrayList<String>();
		l3.add("D");
		int3 = new Interval<Timestamp>(new Timestamp(5), new Timestamp(6));
		l4 = new ArrayList<String>();
		l4.add("E");
		int4 = new Interval<Timestamp>(new Timestamp(7), new Timestamp(8));
		
		List<String> l5 = new ArrayList<String>();
		l5.add("C");
		Interval<Timestamp> int5 = new Interval<Timestamp>(new Timestamp(9), new Timestamp(10));
		
		List<String> l6 = new ArrayList<String>();
		l6.add("E");
		Interval<Timestamp> int6 = new Interval<Timestamp>(new Timestamp(11), new Timestamp(12));
		
		t2.addPoint(l1, int1);
		t2.addPoint(l2, int2);
		t2.addPoint(l3, int3);
		t2.addPoint(l4, int4);
		t2.addPoint(l5, int5);
		t2.addPoint(l6, int6);
		u2.addTrajectory(t2);
		
		// u3
		
		MovingObject<List<String>, Interval<Timestamp>> u3 = new MovingObject<List<String>, Interval<Timestamp>>("u3");
		Trajectory<List<String>, Interval<Timestamp>> t3 = new Trajectory<List<String>, Interval<Timestamp>>("t3");
		
		l1 = new ArrayList<String>();
		l1.add("A");
		int1 = new Interval<Timestamp>(new Timestamp(1), new Timestamp(2));
		l2 = new ArrayList<String>();
		l2.add("F");
		int2 = new Interval<Timestamp>(new Timestamp(3), new Timestamp(4));
		l3 = new ArrayList<String>();
		l3.add("A");
		int3 = new Interval<Timestamp>(new Timestamp(5), new Timestamp(6));

		
		t3.addPoint(l1, int1);
		t3.addPoint(l2, int2);
		t3.addPoint(l3, int3);
		u3.addTrajectory(t3);
		
		// u4
		
		MovingObject<List<String>, Interval<Timestamp>> u4 = new MovingObject<List<String>, Interval<Timestamp>>("u4");
		Trajectory<List<String>, Interval<Timestamp>> t4 = new Trajectory<List<String>, Interval<Timestamp>>("t4");
		
		l1 = new ArrayList<String>();
		l1.add("G");
		int1 = new Interval<Timestamp>(new Timestamp(1), new Timestamp(2));
		l2 = new ArrayList<String>();
		l2.add("F");
		int2 = new Interval<Timestamp>(new Timestamp(3), new Timestamp(4));
		l3 = new ArrayList<String>();
		l3.add("G");
		int3 = new Interval<Timestamp>(new Timestamp(5), new Timestamp(6));
		l4 = new ArrayList<String>();
		l4.add("I");
		int4 = new Interval<Timestamp>(new Timestamp(7), new Timestamp(8));
		
		l5 = new ArrayList<String>();
		l5.add("H");
		int5 = new Interval<Timestamp>(new Timestamp(9), new Timestamp(10));
		
		l6 = new ArrayList<String>();
		l6.add("G");
		int6 = new Interval<Timestamp>(new Timestamp(11), new Timestamp(12));
		
		t4.addPoint(l1, int1);
		t4.addPoint(l2, int2);
		t4.addPoint(l3, int3);
		t4.addPoint(l4, int4);
		t4.addPoint(l5, int6);
		t4.addPoint(l6, int6);
		u4.addTrajectory(t4);
		
		
		List<MovingObject<List<String>, Interval<Timestamp>>> mos = new ArrayList<MovingObject<List<String>,Interval<Timestamp>>>();
		mos.add(u1);
		mos.add(u2);
		mos.add(u3);
		mos.add(u4);
		
		RecommendationDataset<String, String> dataset = new RecommendationDataset<String, String>();
		Map<String, Double> m1 = new HashMap<String, Double>();
		m1.put("C", 2.0);
		m1.put("B", 1.0);
		m1.put("A", 1.0);
		dataset.addUser("u1", m1);
		Map<String, Double> m2 = new HashMap<String, Double>();
		m2.put("D", 2.0);
		m2.put("E", 2.0);
		m2.put("A", 1.0);
		m2.put("C", 1.0);
		dataset.addUser("u2", m2);
		Map<String, Double> m3 = new HashMap<String, Double>();
		m3.put("F", 1.0);
		m3.put("A", 2.0);
		dataset.addUser("u3", m3);
		Map<String, Double> m4 = new HashMap<String, Double>();
		m4.put("G", 3.0);
		m4.put("F", 1.0);
		m4.put("I", 1.0);
		m4.put("H", 1.0);
		dataset.addUser("u4", m4);
		
//		System.out.println(dataset);
//        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
//        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		
		PoiNetwork pn = new PoiNetwork(mos);
		pn.buildNetwork();
		
		for(Edge edge :pn.getPoiNetwork().getEdges()){
			System.out.println(edge.getSource().getNodeData().getLabel()+" - "+edge.getTarget().getNodeData().getLabel());
		}
		
        PageRank pr = new PageRank();
//      pr.setProbability(0.25);
        pr.setUseEdgeWeight(true);
        pr.execute(pn.getPoiNetwork().getGraphModel(), pn.getAttributeModel());
		
//        System.out.println(pn.getAttributeModel().getNodeTable().hasColumn("pageranks"));
//		System.out.println(pn.getPoiNetwork().getEdgeCount()+" "+pn.getPoiNetwork().getNodeCount());
        
//		try {
//			Network network = new Network("cpoi_network_example");
//	        NetworkExporterImpl exp;
//			exp = new NetworkExporterImpl(network,
//					pn.getPoiNetwork().getGraphModel(),
//					pn.getAttributeModel());
//			exp.exportNetwork();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		UserNetwork userNetwork = new UserNetwork(mos);
		userNetwork.buildNetwork();
		
//		try {
//			Network network = new Network("cuser_network_example");
//	        NetworkExporterImpl exp;
//			exp = new NetworkExporterImpl(network,userNetwork.getUserNetwork().getGraphModel(),
//					userNetwork.getAttributeModel());
//			exp.exportNetwork();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		WeightedSlopeOne<String, String> wso = new WeightedSlopeOne<String, String>(dataset.getDataset());
		wso.execute();
		Map<String,Map<String,Double>> a = wso.getRecommendation().getDataset();
		System.out.println("########################## Weighted Slope One ");
		for(String u : a.keySet()){
			System.out.println(u);
			System.out.println(a.get(u));
		}
		Map<String,Map<String,Double>> b = new HashMap<String, Map<String,Double>>(a);
		
		PoiRank<String, String> poiRank = new PoiRank<String, String>(pn,userNetwork, dataset);
		
		poiRank.setPredictions(b);
		poiRank.execute();
		RecommendationDataset<String, String> predictions_pr = new RecommendationDataset<String, String>();
		predictions_pr.setDataset(poiRank.getRecommendation());
		
		System.out.println("########################## Poi Rank ");
		for(String u : poiRank.getRecommendation().keySet()){
			System.out.println(u);
			System.out.println(poiRank.getRecommendation().get(u));
		}
		
		double gain_pr = 0.0;
		int count = 0;
		int k =1;
		SurprisingGain<String,String> sg_pr = new SurprisingGain<String, String>(dataset);
		// For each user, count the number of ``surprising recommendations''
		for(String user : predictions_pr.getUsers()){
				SortedMap<String, Double> recSet = TopK.getTopK(predictions_pr.getDataset().get(user), k);
				gain_pr = gain_pr + sg_pr.getNumberOfSurprisingItems(user, recSet.keySet());
				count++;
		}
		gain_pr = gain_pr/count;
//		avg_gain += gain;
		System.out.println("PoiRank Surprising Gain "+gain_pr);
		
		
		
		double gain_ws1 = 0.0;
		RecommendationDataset<String, String> predictions_ws1 = new RecommendationDataset<String, String>();
		predictions_ws1.setDataset(a);
		
		SurprisingGain<String,String> sg_ws1 = new SurprisingGain<String, String>(dataset);
		// For each user, count the number of ``surprising recommendations''
		
		for(String user :  predictions_ws1.getUsers()){
				SortedMap<String, Double> recSet = TopK.getTopK(predictions_ws1.getDataset().get(user), k);
				gain_ws1 = gain_ws1 + sg_ws1.getNumberOfSurprisingItems(user, recSet.keySet());
				count++;
		}
		gain_ws1 = gain_ws1/count;
//		avg_gain += gain;
		System.out.println("Weighted Slope One Surprising Gain "+gain_ws1);
		
		double surprisingGain = (gain_pr-gain_ws1)/gain_ws1;
		System.out.println("Surprising Gain:" + surprisingGain);
		
		RecommendationDB.storeRecommendation(predictions_pr, "poirankexample");
		
		// User Network
		


	}
}
