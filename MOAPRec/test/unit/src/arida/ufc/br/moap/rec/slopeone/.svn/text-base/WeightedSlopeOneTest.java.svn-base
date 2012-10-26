/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.slopeone;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class WeightedSlopeOneTest {
    
    public WeightedSlopeOneTest() {
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
     * Test of computePredictions method, of class WeightedSlopeOne.
     */


    /**
     * Test of execute method, of class WeightedSlopeOne.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        RecommendationSet rec = new RecommendationSet();
        rec.addUser("John");
        rec.addUserItem("John", "Item 1", 5.0);
        rec.addUserItem("John", "Item 2", 3.0);
        rec.addUserItem("John", "Item 3", 2.0);
        
        rec.addUser("Mark");
        rec.addUserItem("Mark", "Item 1", 3.0);
        rec.addUserItem("Mark", "Item 2", 4.0);
        
        rec.addUser("Lucy");
        rec.addUserItem("Lucy", "Item 2", 2.0);
        rec.addUserItem("Lucy", "Item 3", 5.0);
        
        WeightedSlopeOne instance = new WeightedSlopeOne(rec);
        instance.execute();
        System.out.println(rec);
        System.out.println(instance.getRecommendation());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        
//        Mark{
//	Item 1	3.0
//	Item 2	4.0
//      }
//      Lucy{
//	Item 2	2.0
//	Item 3	5.0
//      }
//John{
//	Item 1	5.0
//	Item 2	3.0
//	Item 3	2.0
//}
//
//Mark{
//	Item 3	3.3333333333333335
//}
//John{
//}
//Lucy{
//	Item 1	4.333333333333333
//}
    }

    /**
     * Test of getRecommendation method, of class WeightedSlopeOne.
     */

}
