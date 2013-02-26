/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.functions.spatial;

import arida.ufc.br.moap.core.beans.LatLonPoint;
import arida.ufc.br.moap.core.beans.Pair;
import java.util.Collection;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class MinimimBoundingBoxTest {
    
    public MinimimBoundingBoxTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }



    /**
     * Test of evaluate method, of class MinimimBoundingBox.
     */
    @Test
    public void testEvaluate() {
        System.out.println("evaluate mbb 1");
        Collection<LatLonPoint> collection = new HashSet<LatLonPoint>();
        
        collection.add(new LatLonPoint(0.0, 0.0));
        collection.add(new LatLonPoint(1.0, 1.0));
        
        MinimimBoundingBox instance = new MinimimBoundingBox();
        Pair expResult = new Pair(new LatLonPoint(0.0, 0.0), new LatLonPoint(1.0, 1.0));
        Pair result = instance.evaluate(collection);
        assertEquals(expResult, result);
        
        System.out.println("MBB: "+result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
        @Test
    public void testEvaluate2() {
        System.out.println("evaluate mbb 2");
        Collection<LatLonPoint> collection = new HashSet<LatLonPoint>();
        
        collection.add(new LatLonPoint(0.0, 0.0));
        collection.add(new LatLonPoint(1.0, 1.0));
        collection.add(new LatLonPoint(1.0, 2.0));
        collection.add(new LatLonPoint(3.0, 4.0));
        
        MinimimBoundingBox instance = new MinimimBoundingBox();
        Pair expResult = new Pair(new LatLonPoint(0.0, 0.0), new LatLonPoint(3.0, 4.0));
        Pair result = instance.evaluate(collection);
        assertEquals(expResult, result);
        System.out.println("MBB: "+result);
        // TODO review the generated test code and remove the default call to fail.
    }
        
    @Test
    public void testEvaluateExtent() {
        System.out.println("evaluate area of mbb 1");


        MinimimBoundingBox instance = new MinimimBoundingBox();
        
        Haversine distance = new Haversine();
        
        double d1 = distance.distance(new LatLonPoint(0.0, 0.0), new LatLonPoint(0.0, 1.0));
        double d2 = distance.distance(new LatLonPoint(0.0, 0.0), new LatLonPoint(1.0, 0.0));
        

        double expResult = d1 * d2;
        System.out.println("Area: "+expResult);
        
        double result = instance.evaluate(new LatLonPoint(0.0, 0.0), new LatLonPoint(1.0, 1.0));
        
        assertTrue(expResult == result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
        @Test
    public void testEvaluateExtent2() {
        System.out.println("evaluate area of mbb 2");


        MinimimBoundingBox instance = new MinimimBoundingBox();
        
        Haversine distance = new Haversine();
        
        double d1 = distance.distance(new LatLonPoint(0.0, 0.0), new LatLonPoint(0.0, 6.0));
        double d2 = distance.distance(new LatLonPoint(0.0, 0.0), new LatLonPoint(3.0, 0.0));
        

        double expResult = d1 * d2;
        System.out.println("Area: "+expResult);
        
        double result = instance.evaluate(new LatLonPoint(0.0, 0.0), new LatLonPoint(3.0, 6.0));
        
        assertTrue(expResult == result);
        // TODO review the generated test code and remove the default call to fail.
    }
}
