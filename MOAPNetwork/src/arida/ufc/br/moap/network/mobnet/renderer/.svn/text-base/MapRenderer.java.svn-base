/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.mobnet.renderer;

import org.jdesktop.swingx.JXMapKit;

/**
 *
 * @author igobrilhante
 */
public class MapRenderer {
    
    private static  MapRenderer instance;
    private static final JXMapKit map = new JXMapKit();;
    private MapRenderer(){
    }
    
    public static MapRenderer getInstance(){
        if(instance==null){
            return new MapRenderer();
        }
        return instance;
    }
    
    public JXMapKit getMap(){
        return map;
    }
}
