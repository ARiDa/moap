package arida.ufc.br.moap.vis.engine.api;

import java.awt.Color;
import java.awt.Stroke;
import java.util.List;

import arida.ufc.br.moap.vis.api.IFeaturable;
/*
 * Interface for a Layer
 */
public interface ILayer {
	
	public List<IFeaturable> getFeaturables();
	public void setFeaturables(IFeaturable[] features);
	public void addFeaturable(IFeaturable feature);
	public void removeFeaturable(IFeaturable feature);
	public void removeFeaturable(int i);
	public int getId();
	
	public String getName();
	/*
	 * @return color
	 */
	public Color getColor();
	public void setColor(Color color);
	public boolean isVisible();
	public Stroke getStroke();
	public void setStroke(Stroke stroke);
	
}
