/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.beans;

import arida.ufc.br.moap.core.beans.Interval;
import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import java.sql.Timestamp;
import java.util.List;

import arida.ufc.br.networkmanager.impl.Network;
import arida.ufc.br.networkmanager.impl.NetworkController;
import arida.ufc.br.networkmanager.impl.NetworkExporterImpl;


import org.apache.log4j.Logger;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.Report;
import org.gephi.project.api.ProjectController;
import org.gephi.statistics.plugin.PageRank;
import org.jfree.data.time.MovingAverage;
//import org.jdesktop.application.TaskMonitor;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor.Task;

/**
 *
 * @author igobrilhante
 */
public class PoiNetwork implements NetworkBuilder {
	public static final String USERS = "users";
	public static final String VISITS_COUNT = "visits_count";
        public static final String TRAJECTORIES = "trajectories";
        public static final String TRAJECTORY_COUNT = "trajectory_count";
	private NetworkController nc;
	private String table_input;
	private Network network;
	private DirectedGraph graph;
	private AttributeModel attributeModel;
	private Report report;
	private List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects;
	Logger logger = Logger.getLogger(PoiNetwork.class);

        /**
         * 
         * @param network
         * @param table_input
         * @throws Exception
         */
        public PoiNetwork(Network network, String table_input) throws Exception {
		this.nc = NetworkController.getInstance();
		this.network = network;
		this.table_input = table_input;
		this.report = new Report();
	}
        /**
         * 
         * @param movingObjects
         */
        public PoiNetwork(List<MovingObject<List<String>, Interval<Timestamp>>> movingObjects){
		this.movingObjects = movingObjects;
		this.report = new Report();
	}

        /**
         * 
         * @return
         */
        public DirectedGraph getPoiNetwork(){
		return this.graph;
	}
        /**
         * 
         * @return
         */
        public AttributeModel getAttributeModel(){
		return this.attributeModel;
	}
        /**
         * 
         */
        @Override
//	public void buildNetwork() {

//		logger.info("Creating network of points of interest");
//		String query_nodes = " SELECT t.location_id as id,location_label as label, st_geomfromtext(location_object) as object,avg(t.time_duration) as avg_time"
//				+ " FROM " + table_input + " t"
//				+ " GROUP BY t.location_id,t.location_label,t.location_object";
//
//		String query_core = "\n select t1.USER_ID,t1.USER_TRIP,t1.LOCATION_ID,t1.START_TIME,t1.END_TIME,min(t2.START_TIME) min"
//				+ " from " + table_input + " t1, " + table_input + " t2\n"
//				+ " where t1.user_id=t2.user_id and t1.user_trip=t2.user_trip and t1.end_time<t2.start_time\n"
//				+ " group by t1.user_id,t1.user_trip,t1.location_id,t1.start_time,t1.end_time\n";
//
//		String query_trips = "\n-- TRIPS\n"
//				+ "\nselect source,target,count(*) trips\n"
//				+ " from \n"
//				+ " ("
//				+ " SELECT distinct t1.user_id,t1.user_trip,t1.location_id source,t2.location_id target\n"
//				+ " FROM ("+query_core+") t1," + table_input + " t2\n"
//				+ " where t1.user_id=t2.user_id and t1.user_trip=t2.user_trip and t1.min=t2.start_time and t1.location_id!=t2.location_id \n"
//				+ " ) a\n"
//				+ " group by source,target\n";
//		String query_users = "\n-- USERS\n"
//				+ "\nselect source,target,count(*) users\n"
//				+ " from \n"
//				+ " ("
//				+ " SELECT distinct t1.user_id,t1.location_id source,t2.location_id target\n"
//				+ " FROM ("+query_core+") t1," + table_input + " t2"
//				+ " where t1.user_id=t2.user_id and t1.user_trip=t2.user_trip and t1.min=t2.start_time and t1.location_id!=t2.location_id \n"
//				+ " ) a \n"
//				+ " group by source,target\n";
//
//		String query_edges = " select t.source,t.target,t.trips as trajectories,u.users as weight\n"
//				+ " from ("+query_trips+") t, ("+query_users+") u\n"
//				+ " where t.source=u.source and t.target=u.target\n";
//		//        String query_edges = " SELECT  SOURCE, TARGET,COUNT(USER_TRIP) WEIGHT\n"
//		//                + " FROM \n"
//		//                + " ( \n"
//		//                + " SELECT SP1.USER_ID,SP1.USER_TRIP,SP1.LOCATION_ID SOURCE,SP2.LOCATION_ID TARGET\n"
//		//                + " FROM\n"
//		//                + " " + table_input + " SP1, " + table_input + " SP2,\n"
//		//                + " (SELECT SP1.USER_ID USER_ID,SP1.USER_TRIP USER_TRIP,SP1.DATETIME DATETIME1,MIN(SP2.DATETIME) DATETIME2\n"
//		//                + " FROM " + table_input + " SP1, " + table_input + " SP2\n"
//		//                + " WHERE SP1.USER_ID=SP2.USER_ID AND SP1.USER_TRIP=SP2.USER_TRIP AND SP1.DATETIME < SP2.DATETIME\n"
//		//                + " GROUP BY SP1.USER_ID,SP1.USER_TRIP,SP1.DATETIME\n"
//		//                + " ) AUX,\n"
//		//                + " (\n"
//		//                + " SELECT USER_ID,USER_TRIP,DATETIME,COUNT(*)\n"
//		//                + " FROM " + table_input + " SP\n"
//		//                + " GROUP BY USER_ID,USER_TRIP,DATETIME\n"
//		//                + " ) AUX2\n"
//		//                + " WHERE SP1.USER_ID=AUX.USER_ID \n"
//		//                + " AND AUX.USER_ID=AUX2.USER_ID\n"
//		//                + " AND AUX.USER_TRIP=AUX2.USER_TRIP\n"
//		//                + " AND SP1.USER_TRIP=AUX.USER_TRIP \n"
//		//                + " AND SP1.USER_ID=SP2.USER_ID\n"
//		//                + " AND SP1.USER_TRIP=SP2.USER_TRIP\n"
//		//                + " AND SP1.DATETIME = AUX.DATETIME1 \n"
//		//                + " AND SP2.DATETIME=AUX.DATETIME2\n"
//		//                + " AND SP2.DATETIME=AUX2.DATETIME\n"
//		//                + " AND SP1.LOCATION_ID!=SP2.LOCATION_ID\n"
//		//                + " GROUP BY SP1.USER_ID,SP1.USER_TRIP,SP1.LOCATION_ID,SP2.LOCATION_ID\n"
//		//                + " ORDER BY SP1.USER_ID,SP1.USER_TRIP\n"
//		//                + " ) MAIN\n"
//		//                + " GROUP BY SOURCE,TARGET\n";
//		//        System.out.println(query_edges);
//		logger.info(query_nodes);
//		logger.info(query_edges);
//		try {
//			System.out.println(query_edges);
//			nc.storeQueryAsNetwork(network, query_nodes, "id", query_edges, "source,target");
//			nc.computeProperties(network.getName());
//			Weight weights = new Weight(network.getName());
//			//            weights.execute();
//			logger.info("End");
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	public void buildNetwork(){
		logger.info("Building Poi Network");
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		pc.getCurrentWorkspace();
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		DirectedGraph poiNetwork = graphModel.getDirectedGraph();
		this.attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
		AttributeTable edgeTable = attributeModel.getEdgeTable();
		edgeTable.addColumn(USERS,USERS, AttributeType.STRING,AttributeOrigin.DATA,"");
                edgeTable.addColumn(TRAJECTORIES,TRAJECTORIES, AttributeType.STRING,AttributeOrigin.DATA,"");
                
		edgeTable.addColumn(VISITS_COUNT,VISITS_COUNT, AttributeType.INT,AttributeOrigin.DATA,0);
                edgeTable.addColumn(TRAJECTORY_COUNT,TRAJECTORY_COUNT, AttributeType.INT,AttributeOrigin.DATA,0);
                
		logger.info("Creating nodes and edges");
		
		for(int a=0;a<movingObjects.size();a++){ // for each moving object
			MovingObject<List<String>, Interval<Timestamp>> mo = movingObjects.get(a);
			List<Trajectory<List<String>, Interval<Timestamp>>> trajs = movingObjects.get(a).getTrajectories();
			
			for(int b=0;b<trajs.size();b++){ // for each trajectory of the moving object
				Trajectory<List<String>, Interval<Timestamp>> t = trajs.get(b);
				int size = t.getPoints().size();
				int j = 1;
                                String user_trajectory_id = mo.getId()+"_"+t.getId();
				while(j<size){// i < j 
					// Previous point
					List<String> point_i = t.getPoints().get(j-1);
					Interval<Timestamp> time_i = t.getTimes().get(j-1);
					
					// Current point
					List<String> point_j = t.getPoints().get(j);
					Interval<Timestamp> time_j = t.getTimes().get(j);

					for(int s = 0;s<point_i.size();s++){ // for each point at i
						String source = point_i.get(s);
						Node n = poiNetwork.getNode(source);
						if(n == null){ // create a new node source
							n = graphModel.factory().newNode(source);
							n.getNodeData().setLabel(source);
//							poiNetwork.addNode(n);
						}
						
						for(int s2 = 0;s2<point_j.size();s2++){ // for each point at j
							String target = point_j.get(s2);
							
							if(!source.equals(target)){ // Avoid auto-loops
								Node n2 = poiNetwork.getNode(target);
								
								if(n2 == null){ // create a new node target
									n2 = graphModel.factory().newNode(target);
									n2.getNodeData().setLabel(target);
//									poiNetwork.addNode(n2);
								}

								Edge edge = graphModel.factory().newEdge(n, n2); // Edge
								if(!poiNetwork.contains(edge)){ // there is no such edge, create new one
									if(!poiNetwork.contains(n)){
										poiNetwork.addNode(n);
									}
									if(!poiNetwork.contains(n2)){
										poiNetwork.addNode(n2);
									}
//									edge = graphModel.factory().newEdge(n, n2);
									edge.setWeight(1f);
									edge.getEdgeData().getAttributes().setValue(USERS, mo.getId());
                                                                        edge.getEdgeData().getAttributes().setValue(TRAJECTORIES, user_trajectory_id);
									edge.getEdgeData().getAttributes().setValue(VISITS_COUNT, 1);
                                                                        edge.getEdgeData().getAttributes().setValue(TRAJECTORY_COUNT, 1);
									poiNetwork.addEdge(edge);
								}
								else{ // there already exists such edge, update this edge
//									System.out.println("updade edge");
                                                                    
                                                                        // USER DETAILS
									edge = poiNetwork.getEdge(n, n2);
									String users = (String) edge.getEdgeData().getAttributes().getValue(USERS);
									Integer visits_count = (Integer) edge.getEdgeData().getAttributes().getValue(VISITS_COUNT);
									String[] split = users.split(",");
									String sequence1 = mo.getId();
                                                                        if(split.length == 1){
                                                                            if(!split[0].equals(mo.getId())){
                                                                                users = users + ","+ mo.getId();
										float weight = edge.getWeight();
										edge.setWeight(weight+1);
										edge.getEdgeData().getAttributes().setValue(USERS, users);
                                                                            }
                                                                        }
                                                                        else{
                                                                            if( !(users.contains(sequence1+",") || users.contains(","+sequence1))){
										users = users + ","+ mo.getId();
										float weight = edge.getWeight();
										edge.setWeight(weight+1);
										edge.getEdgeData().getAttributes().setValue(USERS, users);
                                                                            }
                                                                        }
                                                                        
                                                                        // TRAJECTOY DETAILS
                                                                        String trajectories = (String) edge.getEdgeData().getAttributes().getValue(TRAJECTORIES);
									Integer trajectory_count = (Integer) edge.getEdgeData().getAttributes().getValue(TRAJECTORY_COUNT);
									split = trajectories.split(",");
                                                                        if(split.length == 1){
                                                                            if(!split[0].equals(user_trajectory_id)){
                                                                                trajectories = trajectories + ","+ user_trajectory_id;
										edge.getEdgeData().getAttributes().setValue(TRAJECTORIES, trajectories);
                                                                                edge.getEdgeData().getAttributes().setValue(TRAJECTORY_COUNT, trajectory_count+1);
                                                                                
                                                                            }
                                                                        }
                                                                        else{
                                                                            if( !(trajectories.contains(user_trajectory_id+",") || trajectories.contains(","+user_trajectory_id))){
										trajectories = trajectories + ","+ user_trajectory_id;
										edge.getEdgeData().getAttributes().setValue(TRAJECTORIES, trajectories);
                                                                                edge.getEdgeData().getAttributes().setValue(TRAJECTORY_COUNT, trajectory_count+1);
                                                                            }
                                                                        }
									
									edge.getEdgeData().getAttributes().setValue(VISITS_COUNT, visits_count+1);

								}
							}
						}
					}
					j++; // next position
				}
			}
		}

//		logger.info("Storing into the database");
		// Export PoiNetwork into a database
//		network.setEdgeType(EdgeDefault.DIRECTED);
//		NetworkExporterImpl exporter = new NetworkExporterImpl(network);
//		exporter.exportNetwork();
                logger.info("Poi Network Building Complete");
		this.graph = poiNetwork;
//      PAGE RANK

//		logger.info("End Building Poi Network");
		//Create nodes
		//        Node n0 = graphModel.factory().newNode(�n0�);
		//        n0.getNodeData().setLabel(�Node 0�);
		//        Node n1 = graphModel.factory().newNode(�n1�);
		//        n1.getNodeData().setLabel(�Node 1�);
		//        //Create an edge - directed and weight 1
		//        Edge e1 = graphModel.factory().newEdge(n1, n0, 1f, true);
		//        //Append as a Directed Graph
		//        DirectedGraph directedGraph = graphModel.getDirectedGraph();
		//        directedGraph.addNode(n0);
		//        directedGraph.addNode(n1);
		//        directedGraph.addEdge(e1);

	}




}
