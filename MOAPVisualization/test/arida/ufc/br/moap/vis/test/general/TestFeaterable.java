package arida.ufc.br.moap.vis.test.general;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import arida.ufc.br.moap.vis.api.GeometryType;
import arida.ufc.br.moap.vis.api.IFeaturable;
import arida.ufc.br.moap.vis.api.IFeature;
import arida.ufc.br.moap.vis.api.IGeometry;

public class TestFeaterable implements IFeaturable {

	@Override
	public IFeature getFeature() {
		// TODO Auto-generated method stub
		return new IFeature() {
			
			@Override
			public JsonObject toJson() {
				// TODO Auto-generated method stub
				return new JsonObject();
			}
			
			@Override
			public JsonArray getProperties() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IGeometry getGeometry() {
				// TODO Auto-generated method stub
				return new IGeometry() {
					
					@Override
					public JsonObject toJson() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public GeometryType getGeometryType() {
						// TODO Auto-generated method stub
						return GeometryType.POINT;
					}
					
					@Override
					public double[][] getCoordinates() {
						// TODO Auto-generated method stub
						double[][] point = new double[1][2];
						return point ;
					}
				};
			}
		};
	}


}
