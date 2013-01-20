package arida.ufc.br.moap.clustering.api;

import java.util.List;

/**
 * @author igobrilhante
 *
 * @param <T>
 */
public interface ICluster<T> {


    /**
     * @return
     */
    public int getId();

    /**
     * @return
     */
    public List<T> getObjects();
}
