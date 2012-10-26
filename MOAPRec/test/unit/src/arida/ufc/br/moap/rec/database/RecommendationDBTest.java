/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.database;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.Set;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class RecommendationDBTest {
    
    public RecommendationDBTest() {
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
     * Test of storeRecommendation method, of class RecommendationDB.
     */
//    @Test
//    public void testStoreRecommendation_RecommendationSet_String() throws Exception {
//        System.out.println("storeRecommendation");
//        RecommendationSet<U, I> dataset = null;
//        String table_name = "";
//        RecommendationDB.storeRecommendation(dataset, table_name);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of storeRecommendation method, of class RecommendationDB.
//     */
//    @Test
//    public void testStoreRecommendation_3args() throws Exception {
//        System.out.println("storeRecommendation");
//        RecommendationSet dataset = null;
//        int k = 0;
//        String table_name = "";
//        RecommendationDB.storeRecommendation(dataset, k, table_name);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of loadRecommendation method, of class RecommendationDB.
//     */
//    @Test
//    public void testLoadRecommendation_String() throws Exception {
//        System.out.println("loadRecommendation");
//        String table_name = "";
//        RecommendationSet expResult = null;
//        RecommendationSet result = RecommendationDB.loadRecommendation(table_name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of loadRecommendation method, of class RecommendationDB.
//     */
//    @Test
//    public void testLoadRecommendation_int_String() throws Exception {
//        System.out.println("loadRecommendation");
//        int top_k = 0;
//        String table_name = "";
//        RecommendationSet expResult = null;
//        RecommendationSet result = RecommendationDB.loadRecommendation(top_k, table_name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of loadPoiSet method, of class RecommendationDB.
     */
    @Test
    public void testLoadPoiSet() throws Exception {
        System.out.println("loadPoiSet");
        String poi_table = ""
                + " select clus as poi_id, category as poi_category "
                + " from foursquare.poifcluster_2p_200m_milan c, foursquare.poi_milan_f p "
                + " where st_x(p.object) = st_x(c.object) and st_y(p.object) = st_y(c.object)  "
                + " and clus = '0'"
                + " order by clus";
        Set expResult = null;
        Set result = RecommendationDB.loadPoiSet(poi_table);
        System.out.println(result);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
