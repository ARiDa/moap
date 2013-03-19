package arida.ufc.br.moap.vis.engine.impl;
import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import arida.ufc.br.moap.vis.api.IFeaturable;
import arida.ufc.br.moap.vis.engine.api.ILayer;


public class Layer implements ILayer {
	
	private ArrayList<IFeaturable> featurables = new ArrayList<IFeaturable>();
	private Stroke stroke;
	private Color color;
	
	@Override
	public List<IFeaturable> getFeaturables() {
		return  featurables;
	}

	@Override
	public void setFeaturables(IFeaturable[] features) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFeaturable(IFeaturable feature) {
		featurables.add(feature);
		
	}

	@Override
	public void removeFeaturable(IFeaturable feature) {
		featurables.remove(feature);
		
	}

	@Override
	public void removeFeaturable(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public Stroke getStroke() {
		return stroke;
	}

	@Override
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

}
