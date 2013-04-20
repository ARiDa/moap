package arida.ufc.br.moap.vis.engine.impl;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import arida.ufc.br.moap.vis.api.IFeature;
import arida.ufc.br.moap.vis.api.IGeometry;

public class Feature implements IFeature {

	private final IGeometry geometry;
	private final JsonArray properties;

	public Feature(IGeometry geometry, JsonArray properties) {
		this.geometry = geometry;
		this.properties = properties;
	}

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	@Override
	public JsonArray getProperties() {
		return properties;
	}

	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
