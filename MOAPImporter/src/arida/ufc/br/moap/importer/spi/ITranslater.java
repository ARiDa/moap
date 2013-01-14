package arida.ufc.br.moap.importer.spi;

import arida.ufc.br.moap.core.spi.IDataModel;

/**
 *
 * Translate a ResultSet for a specific model.
 *
 * @author franzejr
 * @author igobrilhante
 */
public interface ITranslater {

    public void translate(String query, IDataModel model);
}
