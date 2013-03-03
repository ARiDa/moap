package arida.ufc.br.moap.vis.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * This interface defines the scope of a feature.
 *
 */
public interface IFeature {
	/**
	 * 
	 * @return geometry
	 */
	public IGeometry getGeometry();
	/**
	 * 
	 * @return properties in the format of JsonArray
	 */
	public JsonArray getProperties();
	/**
	 * 
	 * @return get json representation. This may be useful for exporting and importing data.
	 */
	public JsonObject toJson();
}
