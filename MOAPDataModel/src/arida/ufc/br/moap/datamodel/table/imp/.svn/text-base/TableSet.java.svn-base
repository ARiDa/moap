package arida.ufc.br.moap.datamodel.table.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.HashMap;

/**
 * Structure of a table representation.
 *
 * @author franzejr
 */
public class TableSet implements IDataModel {

    private HashMap<String, String> results;

    public void setResult(String key, String value) {
        results.put(key, value);
    }

    public String getResult(String key) {
        return results.get(key);
    }

    public HashMap<String, String> getAllResults() {
        return results;
    }

    public void setAllResults(HashMap<String, String> results) {
        this.results.putAll(results);
    }

    @Override
    public String toString() {
        return results.toString();
    }


}
