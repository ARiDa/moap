/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unit.src.arida.ufc.br.moap.function.sim;


import java.util.HashMap;
import java.util.Map;

import arida.ufc.br.moap.function.sim.CosineSimilarity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class CosineSimilarityTest {
    
    public CosineSimilarityTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test(){
               System.out.println("getSimilarity");
        Map<String, Double> map1 =new HashMap<String, Double>();
        map1.put("1", 1.0);
        map1.put("2", 2.0);

        Map<String, Double> map2 =new HashMap<String, Double>();
        map2.put("1", 1.0);
        map2.put("2", 2.0);
        
        
        CosineSimilarity<String> instance = new CosineSimilarity<String>();
        double expResult = 1.0;
        double result = instance.evaluate(map1, map2);
        System.out.println("Result: "+result);
        assertEquals(expResult, result, 0.0000001);
    }
}
