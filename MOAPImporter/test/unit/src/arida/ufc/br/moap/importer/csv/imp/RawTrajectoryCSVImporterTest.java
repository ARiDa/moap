/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.importer.csv.imp;

import arida.ufc.br.moap.core.imp.Parameters;
import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author igobrilhante
 */
public class RawTrajectoryCSVImporterTest {
    
    public RawTrajectoryCSVImporterTest() {
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

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        RawTrajectoryCSVImporter importer = new RawTrajectoryCSVImporter();
        TrajectoryModelImpl model = new TrajectoryModelImpl();
        Parameters parameters = new Parameters();
        parameters.addParam(RawTrajectoryCSVImporter.PARAMETER_FILE, "/home/franzejr/Desktop/teste2.csv");
        try {
            importer.buildImport(model, parameters);
            System.out.println("Trajectories: "+model.getTrajectoryCount());
            System.out.println("Moving Objects: "+model.getMovingObject("franze"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
