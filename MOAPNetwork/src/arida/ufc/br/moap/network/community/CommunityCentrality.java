/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author igobrilhante
 */
public class CommunityCentrality {
    private DBManager db;
    private String network;
    public CommunityCentrality(String network) throws SQLException, IOException, ClassNotFoundException{
        this.db = DBManager.getInstance();
        this.network = network;
        
    }
    public void communityCentrality() throws Exception{
        db.getConnection().setAutoCommit(false);
        String query = "\nselect node,sum(dissim) communitycentrality"
                + "\nfrom"
                + "\n("
                + "\n-- computing the average similarity and the dissimilarity"
                + "\nselect a.node node,a.comm comm1,1-avg(jac.jaccard) dissim"
                + "\nfrom "+network+NetworkController.NODE_COMMUNITY_SUFIX+" a, "+network+NetworkController.NODE_COMMUNITY_SUFIX+" b,"
                + "\n("
                + "\n--  similarities between communities by using jaccard coefficient"
                + "\nselect comm1,comm2,s1.size,s2.size, (count(*)/(s1.size+s2.size-count(*))::double precision) jaccard"
                + "\nfrom"
                + "\n("
                + "\nselect distinct a.comm comm1,b.comm comm2,a.node"
                + "\nfrom "+network+NetworkController.NODE_COMMUNITY_SUFIX+" a, "+network+NetworkController.NODE_COMMUNITY_SUFIX+" b"
                + "\nwhere a.comm != b.comm and a.node = b.node"
                + "\n) a,"
                + "\n(select comm,count(*) size from "+network+NetworkController.NODE_COMMUNITY_SUFIX+" group by comm) s1,"
                + "\n(select comm,count(*) size from "+network+NetworkController.NODE_COMMUNITY_SUFIX+" group by comm) s2"
                + "\nwhere s1.comm = comm1 and s2.comm = comm2"
                + "\ngroup by comm1,comm2,s1.size,s2.size"
                + "\n) jac"
                + "\nwhere a.comm = jac.comm1 and b.comm = jac.comm2 and a.comm!=b.comm and a.node=b.node"
                + "\ngroup by a.node, a.comm	"
                + "\n) e"
                + "\ngroup by node"
                + "\norder by sum(dissim) desc";
//        System.out.println(query);
        db.addColumn(network+NetworkController.NODE_SUFIX, "communitycentrality","numeric DEFAULT 0.0");
        
        PreparedStatement ps = db.getConnection().prepareStatement("UPDATE "+network+NetworkController.NODE_SUFIX+" SET communitycentrality = ? WHERE id = ?");
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery(query);
        while(rs.next()){
            String node = rs.getString(1);
            Double communitycentrality = rs.getDouble(2);
            
            
            ps.setDouble(1, communitycentrality);
            ps.setString(2, node);
//            System.out.println(ps.toString());
            ps.executeUpdate();
        }
//        ps.executeBatch();
        
        query = "select node,count(*) from "+network+NetworkController.NODE_COMMUNITY_SUFIX+" group by node";
        ps = db.getConnection().prepareStatement("UPDATE "+network+NetworkController.NODE_SUFIX+" SET numberofcommunities = ? WHERE id = ?");
        db.addColumn(network+NetworkController.NODE_SUFIX, "numberofcommunities","integer DEFAULT 0");
        rs = s.executeQuery(query);
        while(rs.next()){
            String node = rs.getString(1);
            int numberofcommunities = rs.getInt(2);
            
            ps.setInt(1, numberofcommunities);
            ps.setString(2, node);
            
            ps.executeUpdate();
        }
        ps.close();
        s.close();
        
        db.getConnection().commit();
        
    }
}
