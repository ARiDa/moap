
package arida.ufc.br.moap.importer.spi;

/**
 * Importer interface for importing data from database sources.
 *
 * @author franzejr
 */
public interface IDatabaseImporter {

    /**
     * Sets the database description, connection details and queries
     *
     * @param database the database that is to be used to import
     */
    public void setDatabase(IDatabase database);

    /**
     * Returns the current database description, connection details and queries
     *
     * @return the database that is to be used to import
     */
    public IDatabase getDatabase();
}
