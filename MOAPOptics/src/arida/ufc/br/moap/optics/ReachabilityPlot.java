package arida.ufc.br.moap.optics;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import arida.ufc.br.moap.core.imp.Reporter;

public class ReachabilityPlot<T> extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	private final Reporter report = new Reporter(getClass());

	public ReachabilityPlot() {
		super("Reachability Plot");

	}
	
	public void plot(List<OpticsObject<T>> objects){
		report.setReport("Plotting");
		
		DefaultXYDataset dataset = createDataset(objects);
		
		JFreeChart chart = ChartFactory.createXYAreaChart(getTitle(), "ordered points", "reachability distance", dataset, PlotOrientation.VERTICAL, false, true, true);
		ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
		
	}
	
	private DefaultXYDataset createDataset(List<OpticsObject<T>> objects){
		report.setReport("Creating dataset");
		DefaultXYDataset dataset = new DefaultXYDataset();
    	double[][] data = new double[2][objects.size()];
    	
    	int i = 0;
    	for(OpticsObject<T> c : objects){
    		data[0][i] = i;
    		data[1][i] = c.getReachabilityDistance();
    		i++;
    	}
    	dataset.addSeries("serie1", data);
 	
    	return dataset;
	}

}
