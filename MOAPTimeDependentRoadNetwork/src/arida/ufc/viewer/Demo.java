package arida.ufc.viewer;

import java.awt.Color;

import ac.essex.graphing.charts.continuous.Circle1;
import ac.essex.graphing.charts.continuous.SineWave;
import ac.essex.graphing.charts.discrete.BarChartPlot;
import ac.essex.graphing.plotting.ContinuousFunctionPlotter;
import ac.essex.graphing.plotting.Graph;
import ac.essex.graphing.plotting.PlotSettings;
import ac.essex.graphing.swing.GraphApplication;

public class Demo {
	 public static void main(String[] args) { 
		 
	        /** 
	         * Use the Graph Application to display the data 
	         */ 
	        //new GraphApplication(getExampleGraph1()); 
		 	//new GraphApplication(getExampleGraph2()); 
		 	new GraphApplication(getExampleGraph3());
	 
	    } 
	 
	    public static Graph getExampleGraph1() { 
	 
	        // Create some data 
	        String[] labels = new String[]{"Standard GP", "SGGP", "1 Layer MLP", "2 Layer MLP", "3 Layer MLP", "K-NN", "Adaboosted GA", "Kohonen NN"}; 
	        double[] lows = new double[]{31.7,76.7,64.1,63.3,63.3,43.3,63.3,40.8}; 
	        double[] high = new double[]{75.8,85.8,68.3,68.3,67.5,55.8,70.8,53.3}; 
	        double[] means = new double[]{60.84,81.66,66.62,65.8,65.5,50.5,68.3,48.8}; 
	 
	        // All the graph settings are stored in this object. 
	        PlotSettings p = new PlotSettings(); 
	         
	        p.setMinX(0); 
	        p.setMinY(0); 
	 
	        p.setMaxX(labels.length); 
	        p.setMaxY(100); 
	 
	        p.setGridSpacingX(1); 
	        p.setGridSpacingY(10); 
	 
	        // Set the title 
	        p.setTitle("Performance Comparison for the Pasta Experiment"); 
	 
	        // Create the graph object 
	        Graph graph = new Graph(p); 
	 
	        // Add a CandleStick plot to the graph's function list 
	        graph.functions.add(new BarChartPlot(labels, high, means, lows)); 
	        //graph.functions.add(new CandleStickPlot(labels, high, means, lows)); 
	 
	        return graph; 
	 
	    } 
	 
	    public static Graph getExampleGraph2() { 
	 
	        PlotSettings p = new PlotSettings(-2, 2, -1, 1); 
	        p.setPlotColor(Color.RED); 
	        p.setGridSpacingX(0.5); 
	        p.setGridSpacingY(0.5); 
	        p.setTitle("Two functions being rendered together"); 
	        Graph graph = new Graph(p); 
	        graph.functions.add(new Circle1()); 
	        graph.functions.add(new SineWave()); 
	 
	        return graph; 
	    }
	    
	    public static Graph getExampleGraph3() { 
	   	 
	        PlotSettings p = new PlotSettings(0, 16, 0, 20); 
	        p.setPlotColor(Color.RED); 
	        p.setGridSpacingX(2); 
	        p.setGridSpacingY(2); 
//	        p.setTitle("Função linear"); 
	        Graph graph = new Graph(p); 
	        graph.functions.add(new ContinuousFunctionPlotter() {
				
				@Override
				public String getName() {
					return "TESTE";
				}
				
				@Override
				public double getY(double arg0) {
					return 20;
				}
			}); 
	 
	        return graph; 
	    } 

}
