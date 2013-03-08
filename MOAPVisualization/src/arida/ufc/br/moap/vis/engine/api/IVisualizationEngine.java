package arida.ufc.br.moap.vis.engine.api;

import java.awt.Shape;

import org.jdesktop.swingx.JXMapViewer;

import arida.ufc.br.moap.vis.api.IFeaturable;

public interface IVisualizationEngine {
	
	
	public Shape getShape(JXMapViewer viewer,IFeaturable feature);
	
//	public 
	
}
