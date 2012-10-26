/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import arida.ufc.br.networkmanager.impl.NetworkController;

/**
 *
 * @author igobrilhante
 */
public class CommunitySimilarity {
    private String network;
    private String nodecomm;
    private String edgecomm;
    
    public CommunitySimilarity(String network){
        this.network = network;
        this.nodecomm = network+NetworkController.NODE_COMMUNITY_SUFIX;
        this.edgecomm = network+NetworkController.EDGE_COMMUNITY_SUFIX;
    }
    
        public void getSimilarityUser(String trip_table){
            String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from "  + trip_table + " t)";

        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";


        
        String moveAsTrajectoryQuery = " (select comm,count(*) count"
        + " from"
        + " ("
        + " select distinct e.comm ,t1.id"
        + " from"
        + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
        + " and e.source = t1.id2::text and e.target = t2.id2::text "
        + " ) a"
        + " group by comm)";
        
//        String moveAsUserQuery = " select comm,count(*) count"
//        + " from"
//        + " ("
//        + " select distinct n1.comm ,t1.id"
//        + " from"
//        + " " + core + " t1," + aux + " t2, " + nodecomm + " n1," + nodecomm + " n2"
//        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
//        + " and n1.node = t1.id2::text and n2.node = t2.id2::text and n1.comm=n2.comm"
//        + " ) a"
//        + " group by comm";
        
        String query = "\n-- SIMILARITY TRAJECTORIES \n"
                + " select a.comm,t1.count,b.comm,t2.count,count(*),"
                + " count(*)/(t1.count)::numeric as similarity"
        + "\n from"
        + "\n ("
        + "\n select distinct e.comm ,t1.id"
        + "\n from"
        + "\n " + core + " t1," + aux + " t2, " + edgecomm + " e"
        + "\n where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
        + "\n and e.source = t1.id2::text and e.target = t2.id2::text "
        + "\n ) a,"
        + "\n ("
        + "\n select distinct e.comm ,t1.id"
        + "\n from"
        + "\n " + core + " t1," + aux + " t2, " + edgecomm + " e"
        + "\n where t1.id=t2.id  and min = t2.start_time and t1.id2!=t2.id2"
        + "\n and e.source = t1.id2::text and e.target = t2.id2::text "
        + "\n ) b, "+moveAsTrajectoryQuery+" t1, "+moveAsTrajectoryQuery +" t2"
        + "\n where a.id = b.id and a.comm!=b.comm and t1.comm = a.comm and t2.comm = b.comm"
        + "\n group by a.comm,t1.count,b.comm,t2.count";
        
        System.out.println(query);
    }
    
    public void getSimilarityTraj(String trip_table){
            String aux = "(select distinct t.id,trip,id2,start_time,end_time,time_duration "
                + " from "  + trip_table + " t)";

        String core = "(select t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time,min(t2.start_time) min"
                + " from " + aux + " t1," + aux + " t2"
                + " where t1.id = t2.id and t1.trip = t2.trip and t1.end_time < t2.start_time"
                + " group by t1.id,t1.trip,t1.id2,t1.start_time,t1.end_time)";


        
        String moveAsTrajectoryQuery = " (select comm,count(*) count"
        + " from"
        + " ("
        + " select distinct e.comm ,t1.id,t1.trip"
        + " from"
        + " " + core + " t1," + aux + " t2, " + edgecomm + " e"
        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
        + " and e.source = t1.id2::text and e.target = t2.id2::text "
        + " ) a"
        + " group by comm)";
        
//        String moveAsUserQuery = " select comm,count(*) count"
//        + " from"
//        + " ("
//        + " select distinct n1.comm ,t1.id"
//        + " from"
//        + " " + core + " t1," + aux + " t2, " + nodecomm + " n1," + nodecomm + " n2"
//        + " where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
//        + " and n1.node = t1.id2::text and n2.node = t2.id2::text and n1.comm=n2.comm"
//        + " ) a"
//        + " group by comm";
        
        String query = "\n-- SIMILARITY TRAJECTORIES \n"
                + " select a.comm,t1.count,b.comm,t2.count,count(*),"
                + " count(*)/(t1.count)::numeric as similarity"
        + "\n from"
        + "\n ("
        + "\n select distinct e.comm ,t1.id,t1.trip"
        + "\n from"
        + "\n " + core + " t1," + aux + " t2, " + edgecomm + " e"
        + "\n where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
        + "\n and e.source = t1.id2::text and e.target = t2.id2::text "
        + "\n ) a,"
        + "\n ("
        + "\n select distinct e.comm ,t1.id,t1.trip"
        + "\n from"
        + "\n " + core + " t1," + aux + " t2, " + edgecomm + " e"
        + "\n where t1.id=t2.id and t1.trip = t2.trip and min = t2.start_time and t1.id2!=t2.id2"
        + "\n and e.source = t1.id2::text and e.target = t2.id2::text "
        + "\n ) b, "+moveAsTrajectoryQuery+" t1, "+moveAsTrajectoryQuery +" t2"
        + "\n where a.id = b.id and a.trip = b.trip and a.comm!=b.comm and t1.comm = a.comm and t2.comm = b.comm"
        + "\n group by a.comm,t1.count,b.comm,t2.count";
        
        System.out.println(query);
    }
    
    public void getSimilarityNodes(){
        String aux = "(select comm,count(*) from "+nodecomm+" group by comm)";
        String query = "\n-- SIMILARITY NODES \n"
                + " select c1.comm,c2.comm,count(*) intersection,"
                + " count(*)/(n1.count)::numeric as similarity"
                + " from "+nodecomm+" c1, "+nodecomm+" c2"+", "+aux+" n1,"+aux+"n2"
                + " where c1.node = c2.node and c1.comm!=c2.comm"
                + " and c1.comm = n1.comm and c2.comm = n2.comm"
                + " group by c1.comm,c2.comm,n1.count,n2.count";
        
        System.out.println(query);
    }
}
