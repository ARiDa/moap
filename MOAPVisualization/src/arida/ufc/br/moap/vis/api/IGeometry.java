package arida.ufc.br.moap.vis.api;

import com.google.gson.JsonObject;
/*
 * Definition of Geometry type
 */
public interface IGeometry {
	
	public GeometryType getGeometryType();
	
	/*
	 * [index][DIMENSION (x,y) - (lat,long)]
	 * 
	 * Example:
	 * [[2,4],[3,7]]
	 * 
	 */
	
	public double[][] getCoordinates();
	
	public JsonObject toJson();
}
