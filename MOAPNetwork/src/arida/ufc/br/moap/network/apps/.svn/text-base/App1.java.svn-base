///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package arida.ufc.br.moap.network.apps;
//
//import arida.ufc.br.networkmanager.impl.Network;
//import arida.ufc.br.networkmanager.impl.NetworkController;
//import arida.ufc.br.networkmanager.impl.NetworkExporterImpl;
//import arida.ufc.br.networkmanager.impl.NetworkImporterImpl;
//import java.util.ArrayList;
//
//import org.gephi.algorithms.shortestpath.DijkstraShortestPathAlgorithm;
//import org.gephi.data.attributes.api.AttributeController;
//import org.gephi.data.attributes.api.AttributeModel;
//import org.gephi.graph.api.Edge;
//import org.gephi.graph.api.EdgeIterable;
//import org.gephi.graph.api.EdgeIterator;
//import org.gephi.graph.api.Graph;
//import org.gephi.graph.api.GraphController;
//import org.gephi.graph.api.GraphModel;
//import org.gephi.graph.api.Node;
//import org.gephi.io.importer.api.EdgeDefault;
//import org.gephi.project.api.ProjectController;
//import org.gephi.project.api.Workspace;
//import org.gephi.statistics.plugin.ClusteringCoefficient;
//import org.gephi.statistics.plugin.ConnectedComponents;
//import org.gephi.statistics.plugin.Degree;
//import org.gephi.statistics.plugin.EigenvectorCentrality;
//import org.gephi.statistics.plugin.GraphDistance;
//import org.gephi.statistics.plugin.Hits;
//import org.gephi.statistics.plugin.PageRank;
//import org.gephi.statistics.plugin.WeightedDegree;
//import org.openide.util.Exceptions;
//import org.openide.util.Lookup;
//
///**
// *
// * @author igobrilhante
// */
//public class App1 {
//
//    public static void main(String[] args) throws InterruptedException {
//
//        System.out.println("Computing Network Properties");
//        ArrayList<Integer> hours = new ArrayList<Integer>();
//        hours.add(4);
////        hours.add(6);
////        hours.add(8);
////        hours.add(10);
////        hours.add(12);
//
//        ArrayList<String> meters = new ArrayList<String>();
////        meters.add("100"); 
//        meters.add("150");
//        
//        ArrayList<String> networks = new ArrayList<String>();
////        networks.add("poi");
//        networks.add("location");
//        for (int j = 0; j < meters.size(); j++) {
//            for (int i = 0; i < hours.size(); i++) {
//                String parameters = "_" + hours.get(i).toString() + "h_" + meters.get(j) + "m";
//
//                String net = "poi_10h_150m";
//                System.out.println("#########################################");
//                System.out.println("Network: "+net);
//                Network n = new Network(net);
//                NetworkImporterImpl importer = null;
//                try {
//                    importer = new NetworkImporterImpl(n);
//                    importer.importNetwork();
//                }
//                catch (Exception ex) {
//                    Exceptions.printStackTrace(ex);
//                }
//
//                AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
//                System.out.println(attributeModel.getEdgeTable().hasColumn("weight"));
//                GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
//                Node v = graphModel.getDirectedGraph().getNode("664");
//                System.out.println(v);
//                System.out.println(v.getNodeData().getId());
//                System.out.println(v.getNodeData().getLabel());
//                System.out.println(v.getNodeData().getAttributes().getValue("pageranks"));
////                for(EdgeIterator e = graphModel.getDirectedGraph().getEdges().iterator();e.hasNext();e.next()){
////                	Edge edge = e.next();
////                	System.out.println(edge.getSource().getNodeData().getLabel()
////                			+" "+
////                			edge.getTarget().getNodeData().getLabel()+
////                			" "+
////                			edge.getWeight());
////                }
////
////                // CONVERTING DIRECTED TO UNDIRECTED NETWORK
////                Graph network;
////                if (graphModel.isUndirected()) {
////                    network = graphModel.getHierarchicalUndirectedGraph();//Directed2UndirectedNetwork.directed2UndirectedNetwork(digraph);
////                }
////                else {
////                    System.out.println("dir");
////                    network = graphModel.getHierarchicalDirectedGraph();
////                }
////
////                System.out.println("Nodes: " + network.getNodeCount());
////                System.out.println("Edges: " + network.getEdgeCount());
////                System.out.println("Computing ... distances");
////
//////      DEGREE
////                Degree degree = new Degree();
////                degree.execute(graphModel, attributeModel);
////                System.out.println("Degree: " + degree.getAverageDegree());
////  
////////      WEIGHTED DEGREE
//////                WeightedDegree wdegree = new WeightedDegree();
//////                wdegree.execute(graphModel, attributeModel);
//////                System.out.println("Weighted Degree: " + wdegree.getAverageDegree());
////
//////      CLUSTERING COEFFICIENT
////                ClusteringCoefficient clustering = new ClusteringCoefficient();
////                clustering.setDirected(graphModel.isDirected());
////                clustering.execute(graphModel, attributeModel);
////                Double cc = clustering.getAverageClusteringCoefficient();
////                System.out.println("Clustering: " + cc);
////
////      PAGE RANK
////                PageRank pr = new PageRank();
////                pr.setUseEdgeWeight(true);
////                pr.execute(graphModel, attributeModel);
//////
////////      AVERAGE SHORTEST PATH LENGTH
////                GraphDistance graphDistance = new GraphDistance(); 
////                graphDistance.setDirected(graphModel.isDirected());
//////                graphDistance.setNormalized(true);
////                graphDistance.execute(graphModel, attributeModel);
////                Double diameter = graphDistance.getDiameter();
////                Double path = graphDistance.getPathLength();
////                System.out.println("Path Length: " + path);
////                System.out.println("Diameter: " + diameter);
////
//////      HITS
////                Hits hits = new Hits();
////
////                hits.execute(graphModel, attributeModel);
////
//////      ECCENTRENCITY
////                
//////      COMPONENTS
////                ConnectedComponents comps = new ConnectedComponents();
////                comps.setDirected(graphModel.isDirected());
////                comps.execute(graphModel, attributeModel);
////                System.out.println(comps.getReport());
////
////                ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
////                pc.closeCurrentWorkspace();
////                try {
////
////                    NetworkExporterImpl exporter = new NetworkExporterImpl(importer.getNetwork());
////                    exporter.exportNetwork();
////
////                }
////                catch (Exception e) {
////                    e.printStackTrace();
////                }
//                
////                Thread.sleep(5000);
//            }
//        }
//
//
//    }
//}
