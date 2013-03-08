package arida.ufc.br.moap.vis.engine.jxmap;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;

import arida.ufc.br.moap.vis.api.IFeaturable;
import arida.ufc.br.moap.vis.engine.api.ILayer;
import arida.ufc.br.moap.vis.engine.api.IVisualizationEngine;

public class JXMapEngine {

	private JXMapKit map;
	private IVisualizationEngine engine;
	private List<ILayer> layers;
	
	public JXMapEngine(JXMapKit map,IVisualizationEngine engine){
		this.map = map;
		this.engine = engine;
		this.layers = new ArrayList<>();
	}

	public JXMapKit getMap() {
		return map;
	}

	public void setMap(JXMapKit map) {
		this.map = map;
	}
	
	public void run(){
		CompoundPainter<JXMapViewer> cp = getLayerCompoundPainter(layers);
		this.map.getMainMap().setOverlayPainter(cp);
		this.map.updateUI();
		
	};
	
	public void addLayer(int i,ILayer layer){
		this.layers.add(i, layer);
	}
	
	public void addLayer(ILayer layer){
		this.layers.add(layer);
	}
	
	public void removeLayer(ILayer layer){
		this.layers.remove(layer);
	}
	
	public void removeLayer(int i){
		this.layers.remove(i);
	}
	
	private Painter<JXMapViewer> getLayerPainter(final ILayer layer) {
		Painter<JXMapViewer> overlay = new Painter<JXMapViewer>() {

			@Override
			public void paint(Graphics2D g, JXMapViewer map, int arg2, int arg3) {
				/*
				 * Implement the layer here, where the layer contains a list of IFeaturables
				 */
				g.setColor(layer.getColor());
				for(IFeaturable feature : layer.getFeaturables()){
					Shape shape = engine.getShape(map,feature);
					g.draw(shape);
				}
			}

		};

		return overlay;

	}
	
	@SuppressWarnings({"unchecked" })
	private CompoundPainter<JXMapViewer> getLayerCompoundPainter(final List<ILayer> layers){
		CompoundPainter<JXMapViewer> cp = new CompoundPainter<JXMapViewer>();
		cp.setCacheable(false);
		
		int size = layers.size(); 
		Painter<JXMapViewer>[] paintersArray = new Painter[size];
		
		for(int i=0;i<size;i++){
			ILayer layer = layers.get(i);
			paintersArray[i] = getLayerPainter(layer);
		}
		
		cp.setPainters(paintersArray);
		
		return cp;
	}

}
