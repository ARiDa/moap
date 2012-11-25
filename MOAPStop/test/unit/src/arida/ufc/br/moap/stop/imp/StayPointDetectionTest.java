/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.stop.imp;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author franzejr
 */
public class StayPointDetectionTest {
    
    public StayPointDetectionTest() {
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
     * Test of getStops method, of class StayPointDetection.
     */
    @Test
    public void testGetStops() {
        System.out.println("getStops");
        StayPointDetection instance = new StayPointDetection();
        List expResult = null;
        List result = instance.getStops();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of execute method, of class StayPointDetection.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        ITrajectoryModel data = null;
        Parameters parameters = null;
        StayPointDetection instance = new StayPointDetection();
        ITrajectoryModel expResult = null;
        ITrajectoryModel result = instance.execute(data, parameters);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
