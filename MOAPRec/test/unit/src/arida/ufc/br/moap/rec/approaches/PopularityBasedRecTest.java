/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.approaches;

import arida.ufc.br.moap.core.beans.Poi;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class PopularityBasedRecTest {
    
    public PopularityBasedRecTest() {
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
     * Test of getRecommendation method, of class PopularityBasedRec.
     */
//    @Test
//    public void testGetRecommendation() {
//        System.out.println("getRecommendation");
//        PopularityBasedRec instance = null;
//        RecommendationSet expResult = null;
//        RecommendationSet result = instance.getRecommendation();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of execute method, of class PopularityBasedRec.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
                // Poi Dataset
        
        Set<Poi> pois = new HashSet<Poi>();
        Poi p1 = new Poi("Ordones");
        List p1Categories = new ArrayList<Object>();
        p1Categories.add("brazilian restaurant");
        p1Categories.add("sushi restaurant");
        p1.getAnnotations().addAnnotation("categories", "list", p1Categories);
        
        Poi p2 = new Poi("Casa do Carneiro");
        List p2Categories = new ArrayList<Object>();
        p2Categories.add("brazilian restaurant");
        p2Categories.add("meat restaurant");
        p2.getAnnotations().addAnnotation("categories", "list", p2Categories);
        
        Poi p3 = new Poi("Pao De Acucar");
        List p3Categories = new ArrayList<Object>();
        p3Categories.add("supermarket");
        p3.getAnnotations().addAnnotation("categories", "list", p3Categories);
        
        Poi p4 = new Poi("Coco Bambu");
        List p4Categories = new ArrayList<Object>();
        p4Categories.add("typical restaurant");
        p4Categories.add("meat restaurant");
        p4.getAnnotations().addAnnotation("categories", "list", p4Categories);
        
        Poi p5 = new Poi("Forro no sitio");
        List p5Categories = new ArrayList<Object>();
        p5Categories.add("forro");
        p5.getAnnotations().addAnnotation("categories", "list", p5Categories);
        
        Poi p6 = new Poi("Mucuripe");
        List p6Categories = new ArrayList<Object>();
        p6Categories.add("forro");
        p6Categories.add("night blub");
        p6.getAnnotations().addAnnotation("categories", "list", p6Categories);
        
        pois.add(p1);
        pois.add(p2);
        pois.add(p3);
        pois.add(p4);
        pois.add(p5);
        pois.add(p6);
        // Recommendation
        
        RecommendationSet<String,String> dataset = new RecommendationSet<String, String>();
        dataset.addUser("igo");
        dataset.addUserItem("igo", p1.getId(), 1.0);
        dataset.addUserItem("igo", p2.getId(), 1.0);
        dataset.addUserItem("igo", p3.getId(), 1.0);
        
        dataset.addUser("hugo");
        dataset.addUserItem("hugo", p2.getId(), 1.0);
        dataset.addUserItem("hugo", p4.getId(), 1.0);
        dataset.addUserItem("hugo", p5.getId(), 1.0);
        
        dataset.addUser("iza");
        dataset.addUserItem("iza", p5.getId(), 1.0);
        dataset.addUserItem("iza", p6.getId(), 1.0);
        dataset.addUserItem("iza", p1.getId(), 1.0);
        PopularityBasedRec instance = new PopularityBasedRec(dataset);
        instance.execute();
        System.out.println(dataset.getDataset());
        System.out.println(instance.getRecommendation().getDataset());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
