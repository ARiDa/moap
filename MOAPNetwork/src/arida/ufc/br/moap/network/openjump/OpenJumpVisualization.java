/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.openjump;

import arida.ufc.br.networkmanager.impl.NetworkController;

/**
 *
 * @author igobrilhante
 */
public class OpenJumpVisualization {
    private String network;
    private String location_table;
    private String node;
    private String edge;
    
    public OpenJumpVisualization(String network,String location_table){
        this.network = network;
        this.location_table = location_table;
        this.node = network + NetworkController.NODE_SUFIX;
        this.edge = network + NetworkController.EDGE_SUFIX;
    }
    
    public void vizNodes(){
        String query = 
                  " select n.id,st_buffer(st_centroid(st_union(object)),0.001) cen"
                + " from " + location_table + " c, " + node + " n"
                + " where c.clus::text = n.id"
                + " group by n.id";
        System.out.println(query);
    }
    
    public void vizEdges(){
        String query = 
                  " select e.source,e.target,st_buffer(st_makeline(c1.cen,c2.cen),0.0007) line "
                + " from"
                + " (select n.id,st_centroid(st_union(object)) cen"
                + " from " + location_table + " c, " + node + " n"
                + " where c.clus::text = n.id"
                + " group by n.id) c1,"
                + " (select n.id,st_centroid(st_union(object)) cen"
                + " from " + location_table + " c, " + node + " n"
                + " where c.clus::text = n.id"
                + " group by n.id) c2,"
                + " "+edge+" e"
                + " where e.source = c1.id and e.target = c2.id"
                ;
        System.out.println(query);
    }
}
