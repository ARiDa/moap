package arida.ufc.br.moap.importer.spi;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.sql.ResultSet;

/**
 *
 * Translate a ResultSet for a specific model.
 *
 * @author franzejr
 * @author igobrilhante
 */
public interface ITranslater {

    public void translate(ResultSet rs, IDataModel model);
}
