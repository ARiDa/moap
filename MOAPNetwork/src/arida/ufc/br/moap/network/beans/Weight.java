/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.beans;

import arida.ufc.br.moap.network.dbmanager.DBManager;
import arida.ufc.br.networkmanager.impl.NetworkController;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class Weight {
    private String network;
    private DBManager db;
    
    /**
     * 
     * @param network
     */
    public Weight(String network){
        try {
            this.db = DBManager.getInstance();
            this.network = network;
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
    /**
     * 
     * @throws Exception
     */
    public void execute() throws Exception{
        
        // OUT WEIGHT AS TRAJECTORY
        db.getConnection().setAutoCommit(false);
        String query = "\nSELECT id,s.outweight_as_trajectories"
                + " FROM "+network+NetworkController.NODE_SUFIX +" n,"
                + " (SELECT source,sum(trajectories) outweight_as_trajectories FROM "+network+NetworkController.EDGE_SUFIX+" group by source) s"
                + " WHERE n.id = s.source";
//        System.out.println(query);
        db.addColumn(network+NetworkController.NODE_SUFIX, "outweight_as_trajectories","numeric DEFAULT 0.0");
        
        PreparedStatement ps = db.getConnection().prepareStatement("UPDATE "+network+NetworkController.NODE_SUFIX+" SET outweight_as_trajectories = ? WHERE id = ?");
        Statement s = db.getConnection().createStatement();
        ResultSet rs = s.executeQuery(query);
        while(rs.next()){
            String node = rs.getString(1);
            Double outweight_as_trajectories = rs.getDouble(2);
            
            
            ps.setDouble(1, outweight_as_trajectories);
            ps.setString(2, node);
//            System.out.println(ps.toString());
            ps.executeUpdate();
        }
//        ps.executeBatch();
        
        // IN WEIGHT AS TRAJECTORY
        query = "\nSELECT id,s.inweight_as_trajectories"
                + " FROM "+network+NetworkController.NODE_SUFIX +" n,"
                + " (SELECT target,sum(trajectories) inweight_as_trajectories FROM "+network+NetworkController.EDGE_SUFIX+" group by target) s"
                + " WHERE n.id = s.target";
        ps = db.getConnection().prepareStatement("UPDATE "+network+NetworkController.NODE_SUFIX+" SET inweight_as_trajectories = ? WHERE id = ?");
        
        db.addColumn(network+NetworkController.NODE_SUFIX, "inweight_as_trajectories","numeric DEFAULT 0.0");
        rs = s.executeQuery(query);
        while(rs.next()){
            String node = rs.getString(1);
            Double inweight_as_trajectories = rs.getDouble(2);
            
            ps.setDouble(1, inweight_as_trajectories);
            ps.setString(2, node);
            
            ps.executeUpdate();
        }
        
        // OUT WEIGHT AS USERS
        query = "\nSELECT id,s.outweight_as_users"
                + " FROM "+network+NetworkController.NODE_SUFIX +" n,"
                + " (SELECT source,sum(weight) outweight_as_users FROM "+network+NetworkController.EDGE_SUFIX+" group by source) s"
                + " WHERE n.id = s.source";
        ps = db.getConnection().prepareStatement("UPDATE "+network+NetworkController.NODE_SUFIX+" SET outweight_as_users = ? WHERE id = ?");
        
        db.addColumn(network+NetworkController.NODE_SUFIX, "outweight_as_users","numeric DEFAULT 0.0");
        rs = s.executeQuery(query);
        while(rs.next()){
            String node = rs.getString(1);
            Double outweight_as_users = rs.getDouble(2);
            
            ps.setDouble(1, outweight_as_users);
            ps.setString(2, node);
            
            ps.executeUpdate();
        }
        
        // IN WEIGHT AS USERS
        query = "\nSELECT id,s.inweight_as_users"
                + " FROM "+network+NetworkController.NODE_SUFIX +" n,"
                + " (SELECT target,sum(weight) inweight_as_users FROM "+network+NetworkController.EDGE_SUFIX+" group by target) s"
                + " WHERE n.id = s.target";
        ps = db.getConnection().prepareStatement("UPDATE "+network+NetworkController.NODE_SUFIX+" SET inweight_as_users = ? WHERE id = ?");
        
        db.addColumn(network+NetworkController.NODE_SUFIX, "inweight_as_users","numeric DEFAULT 0.0");
        rs = s.executeQuery(query);
        while(rs.next()){
            String node = rs.getString(1);
            Double inweight_as_users = rs.getDouble(2);
            
            ps.setDouble(1, inweight_as_users);
            ps.setString(2, node);
            
            ps.executeUpdate();
        }
        rs.close();
        ps.close();
        s.close();
        
        db.getConnection().commit();
        
    }
}
