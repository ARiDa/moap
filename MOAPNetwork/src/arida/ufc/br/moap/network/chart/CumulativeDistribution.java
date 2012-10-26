/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.data.general.Series;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import weka.classifiers.meta.Bagging;

/**
 *
 * @author igobrilhante
 */
public class CumulativeDistribution {
    private List<Double> list;
    
    public CumulativeDistribution(List<Double> list){
        this.list = list;          
    }
    
    public void getCumulativeDistribution(String title,String xaxis,String yaxis){
        double total = list.size();
        Collections.sort(list);
        Map<Double,Integer> frequency = new HashMap<Double, Integer>();
        
        for(int i = 0; i< list.size(); i++){
            Double d = list.get(i);
            if(frequency.containsKey(d)){
                int f = frequency.get(d);
                frequency.put(d, f+1);
            }
            else{
                frequency.put(d, 1);
            }
        }
        double probability  = 0.0;
        XYSeries serie = new XYSeries("Communities");
        
        while(!frequency.isEmpty()){
            Double min = Double.MAX_VALUE;
            for(Double d : frequency.keySet()){
//                System.out.println(d);
                if(d<min){
                    min = d;
                }
            }
            
            probability = probability + ((double)frequency.get(min)/total);
            serie.add(min.doubleValue(), probability);
            System.out.println(min+" "+frequency.get(min)+" "+probability);
            frequency.remove(min);
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);
        
        ScatterChart chart = new ScatterChart(title, xaxis, yaxis, dataset);
        chart.createPlot();
    }
    
    public XYSeries getCumulativeDistribution(String label){
        double total = list.size();
        Collections.sort(list);
        Map<Double,Integer> frequency = new HashMap<Double, Integer>();
        
        for(int i = 0; i< list.size(); i++){
            Double d = list.get(i);
            if(frequency.containsKey(d)){
                int f = frequency.get(d);
                frequency.put(d, f+1);
            }
            else{
                frequency.put(d, 1);
            }
        }
        double probability  = 0.0;
        XYSeries serie = new XYSeries(label);
        
        while(!frequency.isEmpty()){
            Double min = Double.MAX_VALUE;
            for(Double d : frequency.keySet()){
//                System.out.println(d);
                if(d<min){
                    min = d;
                }
            }
            
            probability = probability + ((double)frequency.get(min)/total);
            serie.add(min.doubleValue(), probability);
            System.out.println(min+" "+frequency.get(min)+" "+probability);
            frequency.remove(min);
        }
        
        return serie;
    }
    
}
