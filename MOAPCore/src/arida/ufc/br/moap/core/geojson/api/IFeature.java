package arida.ufc.br.moap.core.geojson.api;

import com.google.gson.JsonObject;

/**
 * 
 * @author igobrilhante
 *
 */
public interface IFeature {
	
	/**
	 * 
	 * @return
	 */
	public JsonObject getFeature();
	
	/**
	 * 
	 * @return
	 */
	public String getFeatureAsString();
	
	/**
	 * 
	 * @return
	 */
	public double[] getCoordinates();
	
	/**
	 * 
	 * @return
	 */
	public JsonObject getGeometry();

	/**
	 * 
	 * @return
	 */
	public JsonObject getProperties();
	
}
