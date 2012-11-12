package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.IAnnotable;

/**
 * @author igobrilhante
 * 
 */
public class LatLonPoint implements IAnnotable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6597463564933758852L;
	protected double lon;
	protected double lat;
	private Annotations annotations;
	
	public LatLonPoint(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	public LatLonPoint() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return
	 */
	public double getLongitude() {
		return lon;
	}
	/**
	 * @param x
	 */
	public void setLongitude(double x) {
		this.lon = x;
	}
	/**
	 * @return
	 */
	public double getLatitude() {
		return lat;
	}
	/**
	 * @param y
	 */
	public void setLatitude(double y) {
		this.lat = y;
	}
        
        public double getDistance(LatLonPoint point2){
            return Math.sqrt(Math.pow(this.getLatitude() - point2.getLatitude(),2) + ( Math.pow(this.getLongitude() - point2.getLongitude(),2) ) );
        }
        
        public static double getDistanceTwoPoints(LatLonPoint p1, LatLonPoint p2){
            return Math.sqrt(Math.pow(p1.getLatitude() - p2.getLatitude(),2) + ( Math.pow(p1.getLongitude() - p2.getLongitude(),2) ) );
        }
	
	/* (non-Javadoc)
	 * @see java.awt.Point#toString()
	 */
	@Override
	public String toString(){
		return lon+" "+lat;
	}
	@Override
	public Annotations getAnnotations() {
		// TODO Auto-generated method stub
                if(this.annotations == null){
                    this.annotations = new Annotations();
                }
		return this.annotations;
	}
        

}
