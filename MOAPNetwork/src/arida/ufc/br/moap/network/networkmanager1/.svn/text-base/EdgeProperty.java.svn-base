/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.networkmanager1;

/**
 *
 * @author igobrilhante
 */
public enum EdgeProperty implements NetworkPropertyInterface {
    MOVE_AS_TRAJECTORIES,
    WEIGHT,
    USERS,
    MOVE_AS_USERS,
    DISTANCE_AS_TRAJECTORIES,
    DISTANCE_AS_USERS;
    
        public static boolean isEdgeProperty(String property){
        for(EdgeProperty p : values()){
            if(p.toString().equalsIgnoreCase(property))
                return true;
        }
        return false;
    }
        

}
