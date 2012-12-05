/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.importer.spi;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.imp.Reporter;
import arida.ufc.br.moap.core.spi.IDataModel;

/**
 *
 * @author igobrilhante
 * 
 * @author franzejr
 */
public interface IDataImporter<D extends IDataModel> {
    
    /**
     * Run the import process
     * @param dataModel    data model which will be imported 
     * @param parameters   parameters to import
     * 
     */
    public void buildImport(D dataModel,Parameters parameters);
    
    /**
     * Returns the import report, filled with logs and potential issues.
     * @return          the import report
     */
    public Reporter getReport();
    
}
