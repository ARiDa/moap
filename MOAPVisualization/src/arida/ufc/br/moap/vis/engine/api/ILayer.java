package arida.ufc.br.moap.vis.engine.api;

import java.awt.Color;

import arida.ufc.br.moap.vis.api.IFeaturable;

public interface ILayer {
	
	public IFeaturable[] getFeaturables();
	public void setFeaturables(IFeaturable[] features);
	public void addFeaturable(IFeaturable feature);
	public void removeFeaturable(IFeaturable feature);
	public void removeFeaturable(int i);
	public int getId();
	public String getName();
	public Color getColor();
	public boolean isVisible();
	
}
