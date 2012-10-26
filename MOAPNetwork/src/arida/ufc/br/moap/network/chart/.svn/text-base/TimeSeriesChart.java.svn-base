/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author igobrilhante
 */
public class TimeSeriesChart extends ApplicationFrame {

    private XYDataset dataset;
    private JFreeChart chart;
    private String axis;
    private String value;

    public TimeSeriesChart(String title,String axis,String value, XYDataset dataset) {
        super(title);
        this.axis = axis;
        this.value = value;
        this.dataset = dataset;
    }

    public void createPlot() {
        chart = ChartFactory.createTimeSeriesChart(this.getTitle(),
                this.axis,
                this.value,
                dataset,
                rootPaneCheckingEnabled,
                rootPaneCheckingEnabled,
                false);
                this.chart.setBorderVisible(false);
//        this.chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);

        ChartLayout.setXYPlotLayout(chart.getXYPlot());
        ChartLayout.setXYLineAndShapeRenderer(chart.getXYPlot());
        ChartLayout.setChartLayout(chart);
        DateAxis axis2 = (DateAxis) chart.getXYPlot().getDomainAxis();
        
        axis2.setDateFormatOverride(new SimpleDateFormat("E"));
//        axis2.setRange(ValueAxis.DEFAULT_RANGE);
//        this.chart.getXYPlot().getRenderer().setBaseSeriesVisible(false);
        ChartPanel chartPanel = new ChartPanel(chart, true, true, true, true, true);
        setContentPane(chartPanel);



        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
