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
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import org.openide.util.Exceptions;

/**
 *
 * @author igobrilhante
 */
public class HistogramChart extends ApplicationFrame {
    private JFreeChart chart;
    private IntervalXYDataset dataset;
    private String xaxis;
    private String yaxis;
    private boolean lines = false;
    public HistogramChart(String title,String xaxis,String yaxis,IntervalXYDataset dataset){
        super(title);
        this.xaxis = xaxis;
        this.yaxis = yaxis;
        this.dataset = dataset;
    }
    public void createPlot(){
        this.chart = ChartFactory.createXYLineChart(this.getTitle(),
                this.xaxis,
                this.yaxis,
                this.dataset, PlotOrientation.VERTICAL, true, true, false);
//        this.chart.setBorderVisible(false);
//        this.chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);
//        // set a few custom plot features
        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setRangeGridlinePaint(Color.white);

        // set the plot's axes to display integers
//        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
//        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
//        domain.setTickLabelsVisible(true);
//        domain.setStandardTickUnits(ticks);
//        NumberAxis range = (NumberAxis) plot.getRangeAxis();
//        range.setStandardTickUnits(ticks);
//        range.setTickLabelsVisible(true);
        
        // render shapes and lines
        
//        XYLineAndShapeRenderer renderer =
//            new XYLineAndShapeRenderer(true, true);
//        plot.setRenderer(renderer);
//        renderer.setBaseShapesVisible(true);
//        renderer.setBaseShapesFilled(true);
        
//        NumberFormat format = NumberFormat.getNumberInstance();
//        format.setMaximumFractionDigits(2);
//        XYItemLabelGenerator generator =
//            new StandardXYItemLabelGenerator(
//                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
//                format, format);
//        XYBarRenderer renderer = new XYBarRenderer(2.0);
////        renderer.setBaseItemLabelGenerator(generator);
////        renderer.setBaseItemLabelsVisible(true);
// 
//        plot.setRenderer(renderer);
        
        ChartLayout.setChartLayout(chart);
        ChartLayout.setXYPlotLayout(plot);
//        ChartLayout.setHistogramLayout(plot);
//        if(lines)
            ChartLayout.setXYLineAndShapeRenderer(plot);
          
//        ChartLayout.setXYLineAndShapeRenderer(plot);
        
            
        
        ChartPanel chartPanel = new ChartPanel(chart, true, true, true, true, true);
        setContentPane(chartPanel);



        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
    
    public void setLines(boolean b){
        this.lines = b;
    }
    
    public JFreeChart getChart(){
        return chart;
    }
    

}
