package arida.ufc.br.moap.vis.engine.impl;
import com.google.gson.JsonObject;

import arida.ufc.br.moap.vis.api.GeometryType;
import arida.ufc.br.moap.vis.api.IGeometry;

public class Geometry implements IGeometry {

	private GeometryType geometry;
	private double[][] coordinates;

	public Geometry(GeometryType geometry, double[][] coordinates) {
		this.geometry = geometry;
		this.coordinates = coordinates;
	}

	@Override
	public GeometryType getGeometryType() {
		return geometry;
	}

	@Override
	public double[][] getCoordinates() {
		return coordinates;
	}

	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
