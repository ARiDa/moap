package arida.ufc.br.moap.vis.engine.impl;

import java.awt.Polygon;
import java.awt.Shape;
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

		GeometryType type = feature.getFeature().getGeometry()
				.getGeometryType();
		switch (type) {
		case POINT:
			return getPoint(viewer, feature);
		case LINESTRING:
			return getLineString(viewer, feature);
		case POLYGON:
			return getPolygon(viewer, feature);
		default:
			break;
		}

		return null;
	}
	
	/*
	 * Return a LineString Shape.
	 * 
	 * @param viewer
	 * @param feature
	 * @return lineString
	 */
	private Shape getLineString(JXMapViewer viewer, IFeaturable feature) {
		/*
		 * This class has the same behavior than {@link Polygon2D}, except that
		 * the figure is not closed.
		 */
		Polyline2D lineString = new Polyline2D();
		IGeometry geom = feature.getFeature().getGeometry();
		Point2D pt = null;
		double[][] coords = geom.getCoordinates();

		for (int i = 0; i < coords.length; i++) {
			for (int y = 0; y < coords[i].length; y++) {
				pt = viewer.getTileFactory().geoToPixel(new GeoPosition(coords[i][1], coords[i][0]), viewer.getZoom());
				lineString.addPoint(pt);
			}
		}

		return lineString;
	}
	/*
	 * Return a Polygon Shape
	 * 
	 * @param viewer
	 * @param feature
	 * 
	 * @return polygon
	 */
	private Shape getPolygon(JXMapViewer viewer, IFeaturable feature) {
		IGeometry geom = feature.getFeature().getGeometry();
		Polygon polygon = new Polygon();
		Point2D pt = null;
		double[][] coords = geom.getCoordinates();

		for (int i = 0; i < coords.length; i++) {
			for (int y = 0; y < coords[i].length; y++) {
				pt = viewer.getTileFactory().geoToPixel(
						new GeoPosition(coords[i][1], coords[i][0]),
						viewer.getZoom());
				polygon.addPoint((int) pt.getX(), (int) pt.getY());

			}
		}

		return polygon;
	}
	/*
	 * Return a Point Shape
	 * 
	 * @param viewer
	 * @param feature
	 * 
	 * @return point
	 */
	private Shape getPoint(JXMapViewer viewer, IFeaturable feature) {
		IGeometry geom = feature.getFeature().getGeometry();

		double[][] coord = geom.getCoordinates();

		Point2D pt = viewer.getTileFactory().geoToPixel(
				new GeoPosition(coord[0][1], coord[0][0]), viewer.getZoom());
		
		Polyline2D point = new Polyline2D();
		point.addPoint(pt);
		
		return point;

	}

}
