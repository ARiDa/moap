/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.importer.spi.IDataImporter;


/**
 *
 * @author franzejr
 */
public class PostgresqlImporter implements IDataImporter {

    @Override
    public void buildImport(IDataModel dataModel, Parameters parameters) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Reporter getReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    
}
