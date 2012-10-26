/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.evaluation;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.slopeone.WeightedSlopeOne;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class NoveltyTest {
    
    public NoveltyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getNolvety method, of class Novelty.
     */
    @Test
    public void testGetNolvety() {
        
        System.out.println("getNolvety");

        RecommendationSet<String,String> dataset = new RecommendationSet<String, String>();
        dataset.addUser("igo");
        dataset.addUserItem("igo", "a", 1.0);
        dataset.addUserItem("igo", "b", 1.0);
        dataset.addUserItem("igo", "c", 1.0);
        
        dataset.addUser("hugo");
        dataset.addUserItem("hugo", "b", 1.0);
        dataset.addUserItem("hugo", "d", 1.0);
        dataset.addUserItem("hugo", "e", 1.0);
        
        dataset.addUser("iza");
        dataset.addUserItem("iza", "e", 1.0);
        dataset.addUserItem("iza", "f", 1.0);
        dataset.addUserItem("iza", "a", 1.0);
        
        
        WeightedSlopeOne<String,String> ws1 = new WeightedSlopeOne<String, String>(dataset);
        ws1.execute();
        
        System.out.println("Recommendations: "+ws1.getRecommendation());
        
        Novelty instance = new Novelty(dataset, ws1.getRecommendation());
        double expResult = 1.2516291673878228;
        double result = instance.getNolvety();
        System.out.println("Novelty: "+result);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
