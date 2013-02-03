package arida.ufc.br.moap.core.geojson.api;

public enum GeometryType {
	POINT("Point"),
	LINESTRING("LineString");
	
	private final String type;
	
	GeometryType(String type){
		this.type = type;
	}
	
    @Override
    public String toString() {
        return type;
    }

    /**
     * The name of the enum constant.
     *
     * @return the name of the enum constant
     */
    public String getTypeString() {
        return super.toString();
    }

    /**
     * Returns the
     * <code>Class</code> the type is associated with.
     *
     * @return the <code>class</code> the type is associated with
     */
    public String getType() {
        return type;
    }
}
