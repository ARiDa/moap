/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appimportermoap;

import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author franzejr
 */
public class APPImporterMOAP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PostgresqlProvider provider = new PostgresqlProvider(){};
        
        List<Object> tables = new ArrayList<Object>();
        
        PostgresqlProvider provider = new PostgresqlProvider();
        provider.setConnection("user", "pass","port","database");
        
        tables = provider.getTables();
        
        for(Object o : tables){
            System.out.println(o.getTriple());
        }
        
    }