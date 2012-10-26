/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import arida.ufc.br.moap.network.networkmanager1.EdgeProperty;
import arida.ufc.br.moap.network.networkmanager1.NetworkPropertyInterface;

/**
 *
 * @author igobrilhante
 */
public enum CommunityProperty implements NetworkPropertyInterface {

    NODES,
    INDEGREE,
    OUTDEGREE,
    EDGES,
    MOVES_AS_TRAJECTORIES,
    MOVES_AS_USERS,
    AVG_STOP_TIME,
    AVG_MOVE_TIME,
    POIS,
    POI_TYPES,
    STOPS_AS_TRAJECTORIES,
    DISTANCE_AS_TRAJECTORIES,
    DISTANCE_AS_USERS,
    STOPS_AS_USERS;

    public static boolean isCommunityProperty(String property) {
        for (CommunityProperty p : values()) {
            if (p.toString().equalsIgnoreCase(property)) {
                return true;
            }
        }
        return false;
    }
    
    
    @Override
    public String toString(){
        switch(this){
            case AVG_MOVE_TIME: return "Average Move Time";
            case AVG_STOP_TIME: return "Average Stop Time";
            case STOPS_AS_TRAJECTORIES: return "Number of Stops";
            case STOPS_AS_USERS: return "Number of Stops";
            case MOVES_AS_TRAJECTORIES: return "Number of Moves";
            case MOVES_AS_USERS: return "Number of Moves";
            case DISTANCE_AS_TRAJECTORIES: return "Number of Trajectories";
            default: return this.name();
        }
    }
}
