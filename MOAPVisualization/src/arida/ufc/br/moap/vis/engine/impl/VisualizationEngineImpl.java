package arida.ufc.br.moap.vis.engine.impl;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;

import arida.ufc.br.moap.vis.api.GeometryType;
import arida.ufc.br.moap.vis.api.IFeaturable;
import arida.ufc.br.moap.vis.api.IGeometry;
import arida.ufc.br.moap.vis.engine.api.IVisualizationEngine;

public class VisualizationEngineImpl implements IVisualizationEngine {

	@Override
	public Shape getShape(JXMapViewer viewer, IFeaturable feature) {
		// TODO Auto-generated method stub
		
		GeometryType type = feature.getFeature().getGeometry().getGeometryType();
		switch (type) {
		case POINT:
			return getPoint(viewer,feature);
		default:
			break;
		}
		
		return null;
	}
	
	private Shape getPoint(JXMapViewer viewer,IFeaturable feature){
		IGeometry geom = feature.getFeature().getGeometry();
		
		double[][] coord = geom.getCoordinates();
		
		Point2D pt = viewer.getTileFactory().geoToPixel(new GeoPosition(coord[0][0], coord[0][1]), viewer.getZoom());
			
		Ellipse2D e = new Ellipse2D.Double(pt.getX(), pt.getY(), 1.0, 1.0);
		
		return e;
		
	}



}
