/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import arida.ufc.br.moap.core.algorithm.spi.IAlgorithm;
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
public class SharedNodes implements IAlgorithm{

    private List<Community<Edge>> communities;
    private Map<Community<Edge>, Map<Community<Edge>,Double>> results;
    private Map<Node,Set<Community<Edge>>> nodeCommunities;
    private Map<Community<Edge>,Set<Node>> communityNodes;
    private Logger logger = Logger.getLogger(SharedNodes.class);
    
    
    public SharedNodes(List<Community<Edge>> communities) {
        this.communities = communities;
    }

    @Override
    public void execute() {
        logger.info("Shared Nodes Execute");
        initialize();
        logger.info("Shared Nodes");
        sharedNodes();
        logger.info("Shared Nodes Execute End");
    }

    // COMPUTE SHARED NODES: HOW A COMMUNITY SHARES ITS NODES WITH RESPECT TO OTHER ONE
    private void sharedNodes() {

        this.communityNodes = new HashMap<Community<Edge>, Set<Node>>();

        int ci_idx;
        int cj_idx;
        int[] ci_array;
        int[] cj_array;
        
        Set<Node> cj_nodes;
        
        for(Community<Edge> comm_i : this.communities){
            
            if(!this.communityNodes.containsKey(comm_i)){
                setCommunityNodes(comm_i);
            }
            
            
            Set<Node> comm_i_nodes = this.communityNodes.get(comm_i);
//            System.out.println(comm_i_nodes);
            for(Node node : comm_i_nodes){
                
                Set<Community<Edge>> comms = this.nodeCommunities.get(node);
//                System.out.println(comms);
                for(Community comm_j : comms){
                    if(comm_i.compareTo(comm_j)<0){
                        
                        if(!this.communityNodes.containsKey(comm_j)){
                            setCommunityNodes(comm_j);
                        }
                        
                        Set<Node> comm_j_nodes = this.communityNodes.get(comm_j);
                        
                        Set<Node> intersection = new HashSet<Node>(comm_i_nodes);
                        intersection.retainAll(comm_j_nodes);
                        
                        this.results.get(comm_i).put(comm_j, (double)intersection.size()/comm_i_nodes.size());
                        this.results.get(comm_j).put(comm_i, (double)intersection.size()/comm_j_nodes.size());
                    }
                }
                
            }
            
            // For memory issues
            this.communityNodes.remove(comm_i);
        }


    }

    private void setCommunityNodes(Community<Edge> comm_i){
            List<Edge> edges = comm_i.getMembers();
            this.communityNodes.put(comm_i, new HashSet<Node>());
            for(Edge edge : edges){
                this.communityNodes.get(comm_i).add(edge.getSource());
                this.communityNodes.get(comm_i).add(edge.getTarget());
            }
    }
    
    public double getSharedNodes(Community<Edge> c1, Community<Edge> c2) {

        double value = 0.0;
        
        if(this.results.containsKey(c1)){
            if(this.results.get(c1).containsKey(c2)){
                value = this.results.get(c1).get(c2);
            }
        }
        
        return value;
    }

    private void initialize() {
 
       
        this.nodeCommunities = new HashMap<Node, Set<Community<Edge>>>();
        this.results = new HashMap<Community<Edge>, Map<Community<Edge>, Double>>();
        
        logger.info("Indexing communities per node");
        for(Community<Edge> comm : this.communities){
//            System.out.println(comm);
            this.results.put(comm, new HashMap<Community<Edge>, Double>());
            
            List<Edge> edges = comm.getMembers();
//            System.out.println("Edges: "+edges);
            for(Edge edge : edges){
                if(!this.nodeCommunities.containsKey(edge.getSource())){
                    this.nodeCommunities.put(edge.getSource(), new HashSet<Community<Edge>>());
                }
                this.nodeCommunities.get(edge.getSource()).add(comm);
                
                if(!this.nodeCommunities.containsKey(edge.getTarget())){
                    this.nodeCommunities.put(edge.getTarget(), new HashSet<Community<Edge>>());
                }
                this.nodeCommunities.get(edge.getTarget()).add(comm);
//                System.out.println(this.nodeCommunities);
            }
            
        }
        


    }
}
