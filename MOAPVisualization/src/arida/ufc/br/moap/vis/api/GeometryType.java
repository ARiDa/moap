package arida.ufc.br.moap.vis.api;

public enum GeometryType {
	POINT("Point"),
	LINESTRING("LineString"),
	POLYGON("Polygon"),
	MULTIPOINT("MultiPoint"),
	GEOMETRYCOLLECTION("GeometryCollection");
	
	private String geomType;
	
	private GeometryType(String type){
		this.geomType = type;
	}
	public String getGeometryType(){
		return this.geomType;
	}
}
