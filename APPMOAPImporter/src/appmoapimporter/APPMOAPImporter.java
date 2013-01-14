package appmoapimporter;

import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.db.postgres.imp.PostgresqlProvider;

/**
 *
 * @author franzejr
 */
public class APPMOAPImporter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        IDataModel model = null;
        
        PostgresqlProvider provider = new PostgresqlProvider();
        model = provider.getModel();
       
    }
}
