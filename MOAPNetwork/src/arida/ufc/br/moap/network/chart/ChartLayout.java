/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import arida.ufc.br.moap.network.color.ColorUtils;
//import com.sun.j3d.utils.geometry.Sphere;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Renderer;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.util.ShapeUtilities;

/**
 *
 * @author igobrilhante
 */
public class ChartLayout {
    private static String font ="Times";
    
    public static synchronized void setXYPlotLayout(XYPlot plot){
        
        plot.setForegroundAlpha(0.85F);
//        plot.getDomainAxis().setAxisLineVisible(false);
        plot.getDomainAxis().setTickLabelPaint(Color.BLACK);
        plot.getDomainAxis().setTickLabelFont(new Font(font, Font.PLAIN, 24));
        plot.getRangeAxis().setTickLabelFont(new Font(font, Font.PLAIN, 24));
        plot.getRangeAxis().setTickLabelPaint(Color.BLACK);
//        plot.getDomainAxis().setAxisLineVisible(false);
        
//        r.setBaseStroke(new BasicStroke(4.0f));
        plot.setBackgroundPaint(new Color(235, 235, 235));
//        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlineStroke(new BasicStroke(1.2f));
        plot.setRangeGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlineStroke(new BasicStroke(1.2f));
        plot.setDomainGridlinePaint(Color.white);

        plot.getDomainAxis().setLabelFont(new Font(font, Font.BOLD, 24));
//         plot.getDomainAxis().setLabel(axis);
         plot.getDomainAxis().setLabelPaint(Color.black);
         
      
         plot.getRangeAxis().setLabelFont(new Font(font, Font.BOLD, 24));
         plot.getRangeAxis().setLabelPaint(Color.black);
         
                 List<Color> colors = ColorUtils.colorTheme1(plot.getSeriesCount());
                int i = 0;
        int div = 5;
        while(i<plot.getSeriesCount()){
            plot.getRenderer().setSeriesStroke(i, new BasicStroke(2.5f));
            Shape shape= null;
            if(i%div == 0){
                shape = new Ellipse2D.Double(-8, -8, 16, 16);
            }
            if(i%div == 1){
                shape = ShapeUtilities.createRegularCross(8, 8);
            }
            if(i%div == 2){
                
                shape = ShapeUtilities.createDiamond(9f);
            }
            if(i%div == 3){
                shape = ShapeUtilities.createUpTriangle(9f);
            }
            if(i%div == 4){
                shape = ShapeUtilities.createDownTriangle(9f);
            }
            plot.getRenderer().setSeriesShape(i, shape);
            plot.getRenderer().setSeriesPaint(i, colors.get(i));
            i++;
        }

         
         
         
////        chart.getLegend().setPosition(RectangleEdge.RIGHT);
    }
    
    public static synchronized void setChartLayout(JFreeChart chart){
        chart.getLegend().setBorder(0.0, 0.0, 0.0, 0.0);
        chart.setBorderVisible(false);
        chart.getLegend().setItemFont(new Font(font, Font.PLAIN, 20));
        chart.getLegend().setItemPaint(Color.black);
        chart.getTitle().setFont(new Font(font, Font.BOLD, 24));
        chart.getLegend().setVisible(false);
    }
    
    public static synchronized void setXYBar(XYPlot plot){
        XYBarRenderer renderer = (XYBarRenderer)plot.getRenderer();
        
        
    }
    
    public static synchronized void setXYLineAndShapeRenderer(XYPlot plot){
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setBaseFillPaint(Color.white);
//        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setBaseOutlineStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f));
        
        renderer.setBaseStroke(new BasicStroke(4.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f));
        renderer.setBaseOutlinePaint(Color.black);
        renderer.setUseOutlinePaint(true);
//        renderer.setBaseLegendTextPaint(Color.BLACK);
//        renderer.setBaseLegendTextFont(new Font("Arial", Font.BOLD, 16));
        

        

            float [] Dashes1 = {3.0F, 3.0F, 3.0F, 3.0F};
            float [] Dashes2 = {15.0F, 15.0F, 15.0F, 15.0F};
            float [] Dashes3 = {1.0F, 1.0F, 1.0F, 1.0F};
         int   i = 0;
        Stroke[] strokes = {new BasicStroke(5f),
                            new BasicStroke (5.0F, BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,10.0F, Dashes1, 0.F),
                            new BasicStroke (5.0F, BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,10.0F, Dashes2, 0.F),
                            new BasicStroke (5.0F, BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,10.0F, Dashes3, 2.F)
                            };
        while(i<plot.getSeriesCount()){
            renderer.setSeriesStroke(i, strokes[i%strokes.length]);
            
            i++;
        }

    }
    
        public static synchronized void setHistogramLayout(XYPlot plot){
        plot.setForegroundAlpha(1F);
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setBase(0.0);
//        renderer.setBaseStroke(new BasicStroke(0.1f));
        renderer.setBaseShape(ShapeUtilities.createRegularCross(0.3f, 0.3f));
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBaseFillPaint(Color.lightGray);
        renderer.setBasePaint(Color.lightGray);
//        renderer.set
//        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setBaseOutlineStroke(new BasicStroke(2f));
        renderer.setShadowVisible(false);
//        renderer.setBaseStroke(new BasicStroke(4.2f));
        renderer.setBaseOutlinePaint(Color.lightGray);
        renderer.setSeriesFillPaint(0, Color.lightGray);
        
//        renderer.setBaseLegendTextPaint(Color.BLACK);
//        renderer.setBaseLegendTextFont(new Font("Arial", Font.BOLD, 16));
        

    }
}
