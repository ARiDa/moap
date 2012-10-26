/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author igobrilhante
 */
public class XYBarChart extends ApplicationFrame{
    private XYBarDataset dataset;
    private JFreeChart chart;
    private String axis;
    public XYBarChart(String title,String axis,XYBarDataset dataset){
        super(title);
        this.dataset = dataset;
        this.axis = axis;
    }
    
    public void createPlot(){
        chart = ChartFactory.createXYBarChart(this.getTitle(),
                this.axis,
                rootPaneCheckingEnabled,
                null,
                dataset,
                PlotOrientation.VERTICAL,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled);
        
        ChartLayout.setChartLayout(chart);
        ChartLayout.setXYPlotLayout(chart.getXYPlot());
        
        ChartPanel chartPanel = new ChartPanel(chart, true, true, true, true, true);
        setContentPane(chartPanel);



        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
