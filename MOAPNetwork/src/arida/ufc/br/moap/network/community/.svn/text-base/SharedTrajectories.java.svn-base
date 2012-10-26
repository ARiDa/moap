/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import arida.ufc.br.moap.core.algorithm.spi.IAlgorithm;
import arida.ufc.br.moap.network.beans.PoiNetwork;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;

/**
 *
 * @author igobrilhante
 */
public class SharedTrajectories implements IAlgorithm{

    private List<Community<Edge>> communities;
    private Map<Community<Edge>, Map<Community<Edge>,Double>> results;
    private Map<String,Set<Community<Edge>>> trajectoryCommunities;
    private Map<Community<Edge>,Set<String>> communityTrajectories;
    private Logger logger = Logger.getLogger(SharedNodes.class);
    
    
    public SharedTrajectories(List<Community<Edge>> communities) {
        this.communities = communities;
    }

    @Override
    public void execute() {
        logger.info("Shared Trajectories Execute");
        initialize();
        logger.info("Shared Trajectories");
        sharedTrajectories();
        logger.info("Shared Trajectories Execute End");
    }

    // COMPUTE SHARED TRAJECTORIES: HOW A COMMUNITY SHARES ITS TRAJECTORIES WITH RESPECT TO OTHER ONE
    private void sharedTrajectories() {

        this.communityTrajectories = new HashMap<Community<Edge>, Set<String>>();

        for(Community<Edge> comm_i : this.communities){
            
            if(!this.communityTrajectories.containsKey(comm_i)){
                setCommunityTrajectories(comm_i);
            }
            
            
            Set<String> comm_i_trajectories = this.communityTrajectories.get(comm_i);
//            System.out.println(comm_i_nodes);
            for(String node : comm_i_trajectories){
                
                Set<Community<Edge>> comms = this.trajectoryCommunities.get(node);
//                System.out.println(comms);
                for(Community comm_j : comms){
                    if(comm_i.compareTo(comm_j)<0){
                        
                        if(!this.communityTrajectories.containsKey(comm_j)){
                            setCommunityTrajectories(comm_j);
                        }
                        
                        Set<String> comm_j_trajectories = this.communityTrajectories.get(comm_j);
                        
                        Set<String> intersection = new HashSet<String>(comm_i_trajectories);
                        intersection.retainAll(comm_j_trajectories);
                        
                        this.results.get(comm_i).put(comm_j, (double)intersection.size()/comm_i_trajectories.size());
                        this.results.get(comm_j).put(comm_i, (double)intersection.size()/comm_j_trajectories.size());
                    }
                }
                
            }
            
            // For memory issues
            this.communityTrajectories.remove(comm_i);
        }


    }

    private void setCommunityTrajectories(Community<Edge> comm_i){
            List<Edge> edges = comm_i.getMembers();
            this.communityTrajectories.put(comm_i, new HashSet<String>());
            for(Edge edge : edges){
                List<String> trajs = getTrajectories(edge);
                for(String traj : trajs){
                    this.communityTrajectories.get(comm_i).add(traj);
                }
            }
    }
    
    public double getSharedTrajectories(Community<Edge> c1, Community<Edge> c2) {

        double value = 0.0;
        
        if(this.results.containsKey(c1)){
            if(this.results.get(c1).containsKey(c2)){
                value = this.results.get(c1).get(c2);
            }
        }
        
        return value;
    }

    private void initialize() {
 
       
        this.trajectoryCommunities = new HashMap<String, Set<Community<Edge>>>();
        this.results = new HashMap<Community<Edge>, Map<Community<Edge>, Double>>();
        
        logger.info("Indexing communities per trajectory");
        for(Community<Edge> comm : this.communities){
//            System.out.println(comm);
            this.results.put(comm, new HashMap<Community<Edge>, Double>());
            
            List<Edge> edges = comm.getMembers();

            for(Edge edge : edges){
                List<String> trajs = getTrajectories(edge);
                
                for(String traj : trajs){
                    if(!this.trajectoryCommunities.containsKey(traj)){
                        this.trajectoryCommunities.put(traj, new HashSet<Community<Edge>>());
                    }
                    this.trajectoryCommunities.get(traj).add(comm);
                }
            }
            
        }

    }
    
    private List<String> getTrajectories(Edge edge){
        List<String> trajs;
        String att = (String)edge.getEdgeData().getAttributes().getValue(PoiNetwork.TRAJECTORIES);
        
        String[] traj_list = att.split(",");
        trajs = Arrays.asList(traj_list);
        
        return trajs;
    }
}
