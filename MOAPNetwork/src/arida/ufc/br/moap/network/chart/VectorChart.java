/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author igobrilhante
 */
public class VectorChart extends ApplicationFrame {
    private VectorSeriesCollection dataset;
    private JFreeChart chart;
    public VectorChart(String title, VectorSeriesCollection dataset){
        super(title);
        this.dataset = dataset;
    }
    
    public void createPlot(){
        
        chart = ChartFactory.createXYLineChart(
                this.getTitle(),
                "x",
                "y",
                dataset,
                PlotOrientation.HORIZONTAL,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled);
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(new VectorRenderer());
        
         ChartPanel chartPanel = new ChartPanel(chart, true,true,true,true,true);
//         chartPanel.setPreferredSize(new Dimension(800, 600)); 
         setContentPane(chartPanel);
         this.pack();
         RefineryUtilities.centerFrameOnScreen(this);
         this.setVisible(true);

    }
}
