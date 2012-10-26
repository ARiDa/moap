/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author igobrilhante
 */
public class ColorUtils {
    private static int alfa = 255;
    public static List<Color> createRandomColors(int size){
        ArrayList<Color> colors = new ArrayList<Color>();
        
            Random rand = new Random();
        for(int i=0;i<size;i++){
            Color color = null;
            while(color==null){
                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);
                
                color = new Color(r, g, b,alfa);
                if(colors.contains(color))
                    color = null;
                else
                    colors.add(color);
             }
        }
        
        return colors;
         
    }
    // 8 colors
    public static List<Color> colorTheme1(int size){
        Color[] theme1 = {
        // dark
        new Color( 255 ,   0  ,  0,alfa),//red
        new Color( 0 ,128  , 0, alfa),//green
        new Color(  0 ,  0, 255,alfa),//blue
        
        
        new Color(128 ,  0 ,128,alfa),//purple
        // light
        new Color( 128 ,  0 ,  0,alfa),//orange
        new Color( 255 , 69  , 0,alfa),//orange
        new Color(178 , 34 , 34,alfa),//red
        new Color(0 ,128, 128,alfa),//green
        new Color(70 ,130, 180,alfa),//blue
//        new Color(255, 165 ,  0,alfa),//orange
        new Color(255 , 127 ,  80,alfa)};//purple
        ArrayList<Color> colors = new ArrayList<Color>();
        

        for(int i=0;i<size;i++){
            Color color = theme1[i%theme1.length];
            colors.add(color);
        }
        
        return colors;
         
    }
    
        public static List<Color> colorThemeBlackWhite(int size){
        Color[] theme1 = {
        // dark
        new Color(0  , 0 ,  0,alfa),//red
        new Color( 220, 220 ,220, alfa),//green
        new Color( 47 , 79 , 79,alfa),//blue
        new Color( 192, 192 ,192,alfa),//orange
        new Color(169, 169, 169,alfa),//purple
        // light
        new Color( 128 ,128, 128,alfa)
         };//purple
        ArrayList<Color> colors = new ArrayList<Color>();
        

        for(int i=0;i<size;i++){
            Color color = theme1[i%theme1.length];
            colors.add(color);
        }
        
        return colors;
         
    }
     
        
    public static List<Color> colorThemeSingle(int size){
                    Color[] theme1 = {
        // rgb(102, 102, 102)
        new Color( 102 ,102, 102,alfa)
         };//purple
        ArrayList<Color> colors = new ArrayList<Color>();
        

        for(int i=0;i<size;i++){
            Color color = theme1[i%theme1.length];
            colors.add(color);
        }
        
        return colors;
    }
}
