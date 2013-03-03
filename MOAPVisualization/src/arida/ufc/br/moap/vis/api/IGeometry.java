package arida.ufc.br.moap.vis.api;

import com.google.gson.JsonObject;

public interface IGeometry {
	public GeometryType getGeometryType();
	public double[][] getCoordinates();
	
	public JsonObject toJson();
}
