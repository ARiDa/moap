package arida.ufc.br.moap.importer.spi;

import arida.ufc.br.moap.core.spi.IDataModel;

/**
 *
 * Translate a ResultSet for a specific model.
 * It's possible to build a translate for a model.
 * At MOAP you can already find a model for Simple Trajectories
 * or a General Translater.
 * 
 * @see SimpleTrajectoryTranslater
 * @see GeneralTranslater
 *
 * @author franzejr
 * @author igobrilhante
 */
public interface ITranslater<T extends IDataModel> {
    /*
     * Translate the data from a Query for a specific model
     * 
     * @param query Query in SQL
     * @param model DataModel that will be used
     */
    public void translate(String query,T  model);
}
