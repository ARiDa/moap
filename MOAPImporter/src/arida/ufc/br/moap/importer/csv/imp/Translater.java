package arida.ufc.br.moap.importer.csv.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.importer.spi.ITranslater;

/**
 *
 * The main class to translate a ResultSet for a specific Model
 * 
 * @author franzejr
 * @author igobrilhante
 */
public class Translater implements ITranslater {

    /*
     * Fill the Model with the data from the table
     */
    @Override
    public void translate(String query, IDataModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
