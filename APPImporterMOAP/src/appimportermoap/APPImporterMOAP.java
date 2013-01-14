/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appimportermoap;

import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author franzejr
 */
public class APPImporterMOAP {

    public static void main(String[] args) {
        
        List<Object> tables = new ArrayList<Object>();
        
        IDataModel model = new TrajectoryModelImpl();
        
        PostgresqlProvider provider = new PostgresqlProvider("root","root", "jdbc:postgresql://localhost/test");
        String query = "select * from trajetorias;";
        model = provider.getInstanceModel(query,model );
    }
    
}