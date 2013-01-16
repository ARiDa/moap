package arida.ufc.br.moap.clustering.api;

import java.util.List;

/**
 * @author igobrilhante
 *
 * @param <T>
 */
public interface ICluster<T> {

//    /**
//     * @param id
//     */
//    public void setId(int id);

    /**
     * @return
     */
    public int getId();

    /**
     * @return
     */
    public List<T> getObjects();
}
