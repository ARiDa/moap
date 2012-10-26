/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.networkmanager1;

/**
 *
 * @author igobrilhante
 */
public enum NodeProperty implements NetworkPropertyInterface {
    DEGREE,
    INDEGREE,
    OUTDEGREE,
    AVG_TIME,
    CLUSTERING,
    BETWEENESSCENTRALITY,
    CLOSNESSCENTRALITY,
    INWEIGHT_AS_TRAJECTORIES,
    OUTWEIGHT_AS_USERS,
    NUMBEROFCOMMUNITIES,
    COMMUNITYCENTRALITY,
    STOPS_AS_TRAJECTORIES,
    STOPS_AS_USERS;
    
    public static boolean isNodeProperty(String property){
        for(NodeProperty p : values()){
            if(p.toString().equalsIgnoreCase(property))
                return true;
        }
        return false;
    }
    
    public String toString(){
        switch(this){
            case INDEGREE: return "In-degree";
            case OUTDEGREE: return "Out-degree";
        }
        return null;
    }
}
