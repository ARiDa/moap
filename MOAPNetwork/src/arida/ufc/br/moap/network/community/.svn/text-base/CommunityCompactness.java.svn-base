/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import arida.ufc.br.moap.core.algorithm.spi.IAlgorithm;
import arida.ufc.br.moap.network.beans.PoiNetwork;
import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class CommunityCompactness implements IAlgorithm{
    private DBManager db;
    private String network;
    private String node;
    private String edge;
    private String nodecomm;
    private String edgecomm;
    private String location_table;
    private String trip_table;
    private Graph digraph;
    private List<Community<Edge>> communities;
    private Map<Community<Edge>,Double> community_compactness;
    private Map<Community<Edge>,Integer> community_indices;
    private Map<String,Integer> trajectory_indices;
    private Map<Edge,Integer> edge_indices;
    private Map<String,List<Edge>> traj_edges;
    private Map<Community<Edge>,Set<String>> comm_trajs;
    
    public CommunityCompactness(Graph graph,List<Community<Edge>> communities){
        this.digraph = graph;
        this.communities = communities;
    }
    
    public Map<Community<Edge>,Double> getCommunityCompactness(){
        return this.community_compactness;
    }
    
    @Override
    public void execute() {
        this.community_compactness = new HashMap<Community<Edge>, Double>();
//        this.trajectory_indices = new HashMap<String, Integer>();
//        this.community_indices = new HashMap<Community<Edge>, Integer>();
//        this.edge_indices = new HashMap<Edge, Integer>();
        
        // STORE THE EDGES OF EACH TRAJECTORY
        this.traj_edges = new HashMap<String, List<Edge>>();
        // STORE THE TRAJECTORIES OF EACH COMMUNITY
        this.comm_trajs = new HashMap<Community<Edge>, Set<String>>();
        
//        int edge_count = this.digraph.getEdgeCount();
//        int comm_count = this.communities.size();
//        
//        int comm_idx = 0;
//        int traj_idx = 0;
//        int edge_idx = 0;
        
        
        // FIND THE EDGES FOR EACH COMMUNITY
        for(Edge e : this.digraph.getEdges()){
            String traj_list = (String)e.getEdgeData().getAttributes().getValue(PoiNetwork.TRAJECTORIES);
            String[] traj_ids = traj_list.split(",");
            for(int i=0;i<traj_ids.length;i++){

                    if(!this.traj_edges.containsKey(traj_ids[i])){
                        this.traj_edges.put(traj_ids[i], new ArrayList<Edge>());
                    }
                    // INSERT THE EDGE INTO THE TRAJECTORY
                    this.traj_edges.get(traj_ids[i]).add(e);

                }
        }
        // FOR EACH COMMUNITY
        for(Community comm : communities){
//            this.community_indices.put(comm, comm_idx);
//            comm_idx++;
            // MEMBERS OF THE COMMUNITY
            List<Edge> members = comm.getMembers();
            this.comm_trajs.put(comm, new HashSet<String>());
            
            for(Edge e : members){
                Node n1 = this.digraph.getNode(e.getSource().getNodeData().getLabel());
                Node n2 = this.digraph.getNode(e.getTarget().getNodeData().getLabel());
                String traj_list = (String)this.digraph.getEdge(n1,n2).getEdgeData().getAttributes().getValue(PoiNetwork.TRAJECTORIES);
//                System.out.println(String.format("%s %s: %s\n",n1.getNodeData().getLabel()
//                        ,n2.getNodeData().getLabel()
//                        ,traj_list));
                String[] traj_ids = traj_list.split(",");
                
//                if(!edge_indices.containsKey(e)){
//                    this.edge_indices.put(e, edge_idx);
//                    edge_idx++;
//                }
                
                // FOR EACH TRAJECTORY ON THE EDGE
                for(int i=0;i<traj_ids.length;i++){
//                   if(!this.trajectory_indices.containsKey(traj_ids[i])){
//                       this.trajectory_indices.put(traj_ids[i], traj_idx);
//                       traj_idx++;
//                   }
                    
                    // TRAJECTORY OF THE COMMUNITY
                    this.comm_trajs.get(comm).add(traj_ids[i]);
                }
            
            }
            
        }
        
        // FOR EACH COMMUNITY
        for(Community comm : communities){
            int edges = comm.getMembers().size();
            Set<Edge> edge_set = new HashSet<Edge>();
            
            // FOR EACH TRAJECTORY OF THE CURRENT COMMUNITY
            for(String traj : comm_trajs.get(comm)){
                
                // FOR EACH EDGE CROSSED BY THE CURRENT TRAJECTORY
                for(Edge e : traj_edges.get(traj)){
                    edge_set.add(e);
                }
            }
            
            double compactness = (double)edges/edge_set.size();
//            System.out.println(String.format("%s %d/%d = %f", comm.getId(),edges,edge_set.size(),compactness));
            
            community_compactness.put(comm, compactness);
            
        }
        
        
//        
//        int trajectory_count = traj_idx;
//        
//        int[][] comm_traj_matrix = new int[comm_count][trajectory_count];
//        int[][] traj_edge_matrix = new int[trajectory_count][edge_count];
        
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Deprecated
    public CommunityCompactness(String network, String location_table, String trip_table) {
        try {
            this.db = DBManager.getInstance();
            this.network = network;
            this.location_table = location_table;
            this.trip_table = trip_table;
            this.node = network + NetworkController.NODE_SUFIX;
            this.edge = network + NetworkController.EDGE_SUFIX;
            this.nodecomm = network + NetworkController.NODE_COMMUNITY_SUFIX;
            this.edgecomm = network + NetworkController.EDGE_COMMUNITY_SUFIX;
        }
        catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
        catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    @Deprecated
    public String getCompactnessCommunityTrajectory() {
                String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from " + trip_table + " t"
                + " ORDER BY start_time,end_time)";

        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";

        // Trajetorias das comunidades
        String trajectory_per_community = 
                " \n--TRAJECTORY PER COMMUNITY\n  "
                + "\nselect distinct  e.comm,t1.id user_id,t1.trip user_trip"
                + " from"
                + " " + core + " t1," + aux + " t2, " + edgecomm + " e"/*," + nodecomm + " n2"*/
                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
                + " and e.source = t1.id2::text and e.target = t2.id2::text\n";
        
        // Arestas formada pelas trajetórias 
        String edges_per_trajectory = 
                "\n-- TRAJECTORIES\n"
//                + " select user_id,user_trip,count(*) edges"
//                + " from ("
                + " SELECT distinct t1.id user_id,t1.trip user_trip,t1.id2 source,t2.id2 target\n"
                + " FROM "+core+" t1," + aux + " t2, "+edge+ " e \n"
                + " where t1.id=t2.id and t1.trip=t2.trip and t1.min=t2.start_time and t1.id2!=t2.id2 "
                + " and t1.id2::text = e.source and t2.id2::text=e.target\n"
//                + " ) a"
//                + " group by user_id,user_trip"
                ;
        
        // total de arestas formadas pelas trajetorias de uma comunidade
        String te =
                  "\n select comm,count(*) te"
                + " from"
                + " ("
                + " select distinct t.comm, e.source,e.target"
                + " from "
                + " ("+trajectory_per_community+") t,("+edges_per_trajectory+") e"
                + " where t.user_id=e.user_id and t.user_trip = e.user_trip"
                + " ) a"
                + " group by comm\n";
        
//        String tl =
//                "\n-- LEAVING TRAJECTORIES\n"
//                + " select comm,count(*) tl"
//                + " from ("
//                + " select distinct t.comm,a.user_id,a.user_trip"
//                + " from ("
//                + " SELECT distinct t1.id user_id,t1.trip user_trip,t1.id2 source,t2.id2 target\n"
//                + " FROM "+core+" t1," + aux + " t2\n"
//                + " where t1.id=t2.id and t1.trip=t2.trip and t1.min=t2.start_time and t1.id2!=t2.id2 \n"
//                + " ) a,("+trajectory_per_community+") t"
//                + " where a.user_id = t.user_id and a.user_trip = t.user_trip and "
//                + " 0 = (select count(*) from " + edge + " e, " + nodecomm + " c1, " + nodecomm + " c2 where e.source = a.source::text and e.target = a.target::text"
//                + " and e.source = c1.node and e.target = c2.node and c1.comm = c2.comm and c1.comm = t.comm)"
//                + " ) a"
//                + " group by comm";
        
//        System.out.println(tl);
        
//        String ct = 
//                "   select comm,count(*) ct"
//                + " from ("+trajectory_per_community+") t"
//                + " group by comm";
        
        // Arestas de uma comunidade
//        String ce = 
//                " select comm,count(*) ce"
//                + " from ("
//                + " select distinct c1.comm,c1.node,c2.node"
//                + " from " + edge + " e, " + nodecomm + " c1, " + nodecomm + " c2"
//                + " where e.source = c1.node and e.target = c2.node and c1.comm=c2.comm"
//                + " ) a"
//                + " group by comm";
                String ce = 
                  "\n select comm,count(*) ce"
                + " from " + edgecomm + ""
                + " group by comm\n";
        
        String query = 
                  " select ce.comm,ce,te,ce/te::numeric as compactness"
//                + " ,ct,tl,tl/ct::numeric,(ce/te)*(tl/ct)::numeric"
                + " from ("+te+") te,("+ce+") ce"
//                + " ,("+ct+") ct,("+tl+") tl"
                + " where te.comm = ce.comm "
//                + " and ce.comm = ct.comm and ce.comm = tl.comm"
                ;
        
        System.out.println(query);
        return query;
//        try {
//            Statement ps = db.getConnection().prepareStatement(query);
//            System.out.println(ps.toString());
//            
////            ResultSet rs = ps.executeQuery();
////            while(rs.next()){
////                
////            }
//        }
//        catch (SQLException ex) {
//            Exceptions.printStackTrace(ex);
//        }
    }

//    public void getCommunityBehaviorTrajectory(Object community_id) {
//                String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
//                + " from " + trip_table + " t"
//                + " ORDER BY start_time,end_time)";
//
//        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
//                + " from " + aux + " t1," + aux + " t2"
//                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
//                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";
//
//        // Trajetorias das comunidades
//        String trajectory_per_community = 
//                "\n-- TRAJECTORY PER COMMUNITY\n"
//                + "select distinct  n1.comm,t1.id user_id,t1.trip user_trip"
//                + " from"
//                + " " + core + " t1," + aux + " t2, " + nodecomm + " n1," + nodecomm + " n2"
//                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
//                + " and n1.node = t1.id2::text and n2.node = t2.id2::text and n1.comm=n2.comm\n";
//        
//        // Arestas formadas pelas trajetorias
//        String edges_per_trajectory = 
//                "\n-- EDGES PER TRAJECTORIES\n"
//                + " select user_id,user_trip,count(*) edges"
//                + " from ("
//                + " SELECT distinct t1.id user_id,t1.trip user_trip,t1.id2 source,t2.id2 target"
//                + " FROM "+core+" t1," + aux + " t2\n"
//                + " where t1.id=t2.id and t1.trip=t2.trip and t1.min=t2.start_time and t1.id2!=t2.id2 "
//                + " ) a"
//                + " group by user_id,user_trip\n";
//        
//        String te =
//                 "\n-- TRAJECTORY EDGES\n"
//                +  " select t.comm,sum(edges) te"
//                + " from "
//                + " ("+trajectory_per_community+") t,("+edges_per_trajectory+") e"
//                + " where t.user_id=e.user_id and t.user_trip = e.user_trip"
//                + " group by t.comm\n";
//        
//        String ce = 
//                 "\n-- COMMUNITY EDGES\n"
//                + " select comm,count(*) ce"
//                + " from ("
//                + " select distinct c1.comm,c1.node,c2.node"
//                + " from " + edge + " e, " + nodecomm + " c1, " + nodecomm + " c2"
//                + " where e.source = c1.node and e.target = c2.node and c1.comm=c2.comm"
//                + " ) a"
//                + " group by comm\n";
//        String query = 
//                  " select ce.comm,ce,te,ce/te::numeric as cp"
//                + " from ("+te+") te,("+ce+") ce "
//                + " where te.comm = ce.comm and te.comm = ?";
//        try {
//            PreparedStatement ps = db.getConnection().prepareStatement(query);
//            ps.setString(1, community_id.toString());
//            System.out.println(ps.toString());
//            
////            ResultSet rs = ps.executeQuery();
////            while(rs.next()){
////                
////            }
//        }
//        catch (SQLException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//
//        
//    }
//    
//    public String getCommunityBehaviorUser() {
//                String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
//                + " from " + trip_table + " t"
//                + " ORDER BY start_time,end_time)";
//
//        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
//                + " from " + aux + " t1," + aux + " t2"
//                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
//                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";
//
//        String trajectory_per_community = 
//                "\n-- USER PER COMMUNITY\n"
//                + "select distinct  n1.comm,t1.id user_id"
//                + " from"
//                + " " + core + " t1," + aux + " t2, " + nodecomm + " n1," + nodecomm + " n2"
//                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
//                + " and n1.node = t1.id2::text and n2.node = t2.id2::text and n1.comm=n2.comm\n";
//        
//        String edges_per_trajectory = 
//                "\n-- EDGES PER USER\n"
//                + " select user_id,count(*) edges"
//                + " from ("
//                + " SELECT distinct t1.id user_id,t1.id2 source,t2.id2 target"
//                + " FROM "+core+" t1," + aux + " t2\n"
//                + " where t1.id=t2.id and t1.trip=t2.trip and t1.min=t2.start_time and t1.id2!=t2.id2 "
//                + " ) a"
//                + " group by user_id\n";
//        
//        String te =
//                 "\n-- TRAJECTORY EDGES\n"
//                +  " select t.comm,sum(edges) ue"
//                + " from "
//                + " ("+trajectory_per_community+") t,("+edges_per_trajectory+") e"
//                + " where t.user_id=e.user_id"
//                + " group by t.comm\n";
//        
//        String ce = 
//                 "\n-- COMMUNITY EDGES\n"
//                + " select comm,count(*) ce"
//                + " from ("
//                + " select distinct c1.comm,c1.node,c2.node"
//                + " from " + edge + " e, " + nodecomm + " c1, " + nodecomm + " c2"
//                + " where e.source = c1.node and e.target = c2.node and c1.comm=c2.comm"
//                + " ) a"
//                + " group by comm\n";
//        String query = 
//                  " select ce.comm,ce,ue,ce/ue::numeric cp"
//                + " from ("+te+") ue,("+ce+") ce "
//                + " where ue.comm = ce.comm";
//        return query;
////        try {
////            Statement ps = db.getConnection().prepareStatement(query);
////            System.out.println(ps.toString());
////            
//////            ResultSet rs = ps.executeQuery();
//////            while(rs.next()){
//////                
//////            }
////        }
////        catch (SQLException ex) {
////            Exceptions.printStackTrace(ex);
////        }
//    }
//
//    public void getCommunityBehaviorUser(Object community_id) {
//                String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
//                + " from " + trip_table + " t"
//                + " ORDER BY start_time,end_time)";
//
//        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
//                + " from " + aux + " t1," + aux + " t2"
//                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
//                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";
//
//        String trajectory_per_community = 
//                "\n-- USER PER COMMUNITY\n"
//                + "select distinct  n1.comm,t1.id user_id"
//                + " from"
//                + " " + core + " t1," + aux + " t2, " + nodecomm + " n1," + nodecomm + " n2"
//                + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
//                + " and n1.node = t1.id2::text and n2.node = t2.id2::text and n1.comm=n2.comm\n";
//        
//        String edges_per_trajectory = 
//                "\n-- EDGES PER USER\n"
//                + " select user_id,count(*) edges"
//                + " from ("
//                + " SELECT distinct t1.id user_id,t1.id2 source,t2.id2 target"
//                + " FROM "+core+" t1," + aux + " t2\n"
//                + " where t1.id=t2.id and t1.trip=t2.trip and t1.min=t2.start_time and t1.id2!=t2.id2 "
//                + " ) a"
//                + " group by user_id\n";
//        
//        String te =
//                 "\n-- TRAJECTORY EDGES\n"
//                +  " select t.comm,sum(edges) te"
//                + " from "
//                + " ("+trajectory_per_community+") t,("+edges_per_trajectory+") e"
//                + " where t.user_id=e.user_id"
//                + " group by t.comm\n";
//        
//        String ce = 
//                 "\n-- COMMUNITY EDGES\n"
//                + " select comm,count(*) ue"
//                + " from ("
//                + " select distinct c1.comm,c1.node,c2.node"
//                + " from " + edge + " e, " + nodecomm + " c1, " + nodecomm + " c2"
//                + " where e.source = c1.node and e.target = c2.node and c1.comm=c2.comm"
//                + " ) a"
//                + " group by comm\n";
//        String query = 
//                  " select ce.comm,ce,ue,ce/ue::numeric cp"
//                + ""
//                + " from ("+te+") te,("+ce+") ce "
//                + " where ue.comm = ce.comm and ue.comm = ?";
//        try {
//            PreparedStatement ps = db.getConnection().prepareStatement(query);
//            ps.setString(1, community_id.toString());
//            System.out.println(ps.toString());
//            
////            ResultSet rs = ps.executeQuery();
////            while(rs.next()){
////                
////            }
//        }
//        catch (SQLException ex) {
//            Exceptions.printStackTrace(ex);
//        }
//
//        
//    }


}
