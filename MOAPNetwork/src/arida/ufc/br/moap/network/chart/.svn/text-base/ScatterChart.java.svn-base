/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Locale;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.TickUnits;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Value;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author igobrilhante
 */
public class ScatterChart extends ApplicationFrame {
     private XYDataset dataset;
    private JFreeChart chart;
    private String xaxis;
    private String yaxis;
    private boolean logarithmicAxis = false;
    public ScatterChart(String title,String xaxis,String yaxis,XYDataset dataset){
        super(title);
        this.dataset = dataset;
        this.xaxis = xaxis;
        this.yaxis = yaxis;
    }
    
    public void createPlot(){
        chart = ChartFactory.createXYLineChart(this.getTitle(),
                this.xaxis,
                this.yaxis,
                dataset,
                PlotOrientation.VERTICAL,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled);
        
        XYPlot plot = chart.getXYPlot();
        if(logarithmicAxis){
            LogarithmicAxis logarithmicaxis = new LogarithmicAxis(xaxis);
            LogarithmicAxis logarithmicaxis1 = new LogarithmicAxis(yaxis);
            plot.setDomainAxis(logarithmicaxis);
            plot.setRangeAxis(logarithmicaxis1);
            chart.getXYPlot().getDomainAxis().setStandardTickUnits(LogAxis.createLogTickUnits(Locale.ENGLISH));
            chart.getXYPlot().getRangeAxis().setStandardTickUnits(LogAxis.createLogTickUnits(Locale.ENGLISH));
            
        }
        
        ChartLayout.setChartLayout(chart);
        ChartLayout.setXYPlotLayout(plot);
        ChartLayout.setXYLineAndShapeRenderer(plot);
        chart.getLegend().setVisible(false);

        ChartPanel chartPanel = new ChartPanel(chart, true, true, true, true, true);
        setContentPane(chartPanel);



        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
    
    public void setLogarithmicAxis(boolean bool){
        this.logarithmicAxis = bool;
    }
}
