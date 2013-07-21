package arida.ufc.viewer;

import java.awt.Color;

import ac.essex.graphing.plotting.PlotSettings;

public class PlotCostLinearFunctionSetting extends PlotSettings{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1696231681183604100L;

	public PlotCostLinearFunctionSetting(double xMin, double xMax, double yMin, double yMax, Color color, String title, double gridSpacingX, double gridSpacingY){
		super(xMin, xMax, yMin, yMax);
        this.setPlotColor(color); 
        this.setGridSpacingX(gridSpacingX); 
        this.setGridSpacingY(gridSpacingY); 
        this.setTitle(title); 
	}
}
