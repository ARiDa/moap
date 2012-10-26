/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.importer.spi;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.core.spi.IDataModel;

/**
 *
 * @author igobrilhante
 */
public interface IDataImporter<D extends IDataModel> {
    
    public void buildImport(D dataModel,Parameters parameters);
}
