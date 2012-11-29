package arida.ufc.br.moap.stop.imp;

import arida.ufc.br.moap.core.beans.MovingObject;
import arida.ufc.br.moap.core.beans.Trajectory;
import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
import arida.ufc.br.moap.importer.csv.imp.RawTrajectoryCSVImporter;
import java.util.List;
import org.joda.time.Interval;
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
     * The execute method execute the StayPoint Algorithm
     */
    @Test
    public void testExecute() {
        
        //Importing the file
        //There is a file inside the folder data
        RawTrajectoryCSVImporter importer = new RawTrajectoryCSVImporter();
        TrajectoryModelImpl model = new TrajectoryModelImpl();
        Parameters parameters = new Parameters();
        //You must to set the path
        parameters.addParam(RawTrajectoryCSVImporter.PARAMETER_FILE, "/Users/franzejr/NetBeansProjects/moap/MOAPStop/test/unit/src/data/paradas.csv");
        try {
            importer.buildImport(model, parameters);
            System.out.println("Trajectories: "+model.getTrajectoryCount());
            System.out.println("Moving Object Count: "+model.getMovingObjectCount());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Adding parameters to the algorithm
        parameters.addParam("timeThreshold", 300000.0);
        parameters.addParam("spatialThreshold", 0.003);
        
        //Initializing a StayPoint Algorithm
        StayPointDetection s = new StayPointDetection();
        ITrajectoryModel<StayPoint,Interval> result = s.execute(model, parameters);
        System.out.println("RESULTADO:" +result.getTrajectoryCount());
        
        
        //Here we can use the model as we wish
        for(MovingObject mo : result.getMovingObjects()){
            System.out.println("MOVING OBJECT ID:"+mo.toString());
        }
        
        
        for (Trajectory t : result.getTrajectories()) {
            System.out.println("STAY POINT: " + t.getPoint(0));
            System.out.println("PONTOS:"+t.getPoints());
            System.out.println(t.getMovingObject().getId());

        }

    }
}
