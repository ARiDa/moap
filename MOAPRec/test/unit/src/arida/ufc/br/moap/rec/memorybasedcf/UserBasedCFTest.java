/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.rec.memorybasedcf;

import arida.ufc.br.moap.rec.function.similarity.PearsonSimilarity;
import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class UserBasedCFTest {
    
    public UserBasedCFTest() {
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
     * Test of getRecommendation method, of class UserBasedCF.
     */


    /**
     * Test of execute method, of class UserBasedCF.
     */
    @Test
    public void testExecute() {
        
        System.out.println("execute");
        
        RecommendationSet<String,String> rec = new RecommendationSet<String, String>();
        rec.addUser("u1");
        rec.addUserItem("u1","i1", 4.0);
        rec.addUserItem("u1","i3", 5.0);
        rec.addUserItem("u1","i4", 5.0);
        
        rec.addUser("u2");
        rec.addUserItem("u2", "i1", 4.0);
        rec.addUserItem("u2", "i2", 2.0);
        rec.addUserItem("u2", "i3", 1.0);
        
                rec.addUser("u3");
        rec.addUserItem("u3", "i1", 3.0);
        rec.addUserItem("u3", "i3", 2.0);
        rec.addUserItem("u3", "i4", 4.0);
        
                rec.addUser("u4");
        rec.addUserItem("u4", "i1", 4.0);
        rec.addUserItem("u4", "i2", 4.0);
        
                rec.addUser("u5");
        rec.addUserItem("u5", "i1", 2.0);
        rec.addUserItem("u5", "i2", 1.0);
        rec.addUserItem("u5", "i3", 3.0);
        rec.addUserItem("u5", "i4", 5.0);
        
        SimilarityFunction sf = new PearsonSimilarity(rec);
        sf.execute();
       
        
        UserBasedCF instance = new UserBasedCF(rec, sf);
        instance.execute();
        System.out.println(instance.getRecommendation().getDataset());
        // {u2={i4=2.341075904623409}, u1={i2=3.946914190211329}, u4={}, u3={i2=1.7746528064785732}, u5={}}
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
