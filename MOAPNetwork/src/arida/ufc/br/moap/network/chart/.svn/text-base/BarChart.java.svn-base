/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author igobrilhante
 */
public class BarChart extends ApplicationFrame {
    private Map<Object,Map<Object,Double>> data;
    private DefaultCategoryDataset dataset;
    private JFreeChart chart;
    private String yaxis;
    private String xaxis;
    
    public BarChart(String title,String yaxis,String xaxis,Map data){
        super(title);
        this.yaxis = yaxis;
        this.xaxis = xaxis;
        this.data = data;
    }
    
    public void createPlot(){
         dataset = new DefaultCategoryDataset();
         
         for(Object category : data.keySet()){
//             System.out.println(category.toString()+" "+data.get(category));
            Map<Object,Double> aux = data.get(category);
            for(Object object : aux.keySet()){
                dataset.setValue(aux.get(object), category.toString(), object.toString());
            }
            
         }
         
         chart = ChartFactory.createBarChart( this.getTitle(), // chart title 
                 yaxis, // domain axis label 
                 xaxis, // range axis label
                    dataset, // data 
                    PlotOrientation.HORIZONTAL, // orientation 
                    true, // include legend 
                    true, // tooltips? 
                    false // URLs?
         );
                 this.chart.setBorderVisible(false);
        this.chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);
//         TextTitle tt = new TextTitle(this.getTitle(), new Font("Arial", Font.BOLD, 28));
//         chart.setTitle(tt);
//         chart.setBackgroundPaint(Color.);
         
         CategoryPlot plot = (CategoryPlot)chart.getPlot();
//         plot.setBackgroundPaint(Color.getHSBColor(237f, 18f, 100f));
//         plot.setDomainGridlinePaint(Color.white);
//         plot.setRangeGridlinePaint(Color.white);
        
        plot.setBackgroundPaint(new Color(235, 235, 235));
//        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlineStroke(new BasicStroke(1.2f));
        plot.setRangeGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlineStroke(new BasicStroke(1.2f));
        plot.setDomainGridlinePaint(Color.white);
         
         
         plot.getDomainAxis().setLabelFont(new Font("Arial", Font.BOLD, 16));
         plot.getDomainAxis().setLabel(xaxis);
         plot.getDomainAxis().setLabelPaint(Color.black);
         
      
         plot.getRangeAxis().setLabelFont(new Font("Arial", Font.BOLD, 16));
         plot.getRangeAxis().setLabelPaint(Color.black);
         plot.getRangeAxis().setLabel(yaxis);
         
         BarRenderer renderer = (BarRenderer)plot.getRenderer();
//         renderer.setSeriesPaint(0, new Color(128, 128, 128));
         renderer.setDrawBarOutline(false);
         renderer.setShadowVisible(false);
//         renderer.setBaseItemLabelFont(new Font("Arial", Font.BOLD, 16));
//         renderer.setBaseItemLabelPaint(Color.black);
         
         
         ChartPanel chartPanel = new ChartPanel(chart, true,true,true,true,true);
//         chartPanel.setPreferredSize(new Dimension(800, 600)); 
         setContentPane(chartPanel);
         this.pack();
         RefineryUtilities.centerFrameOnScreen(this);
         this.setVisible(true);
    }
    
    
    

}
