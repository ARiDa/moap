///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package arida.ufc.br.moap.importer.csv.imp;
//
//import arida.ufc.br.moap.core.imp.Parameters;
//import arida.ufc.br.moap.datamodelapi.imp.TrajectoryModelImpl;
//import arida.ufc.br.moap.datamodelapi.spi.ITrajectoryModel;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author igobrilhante
// */
//public class RawTrajecotyGPXImporterTest {
//    
//    public RawTrajecotyGPXImporterTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of buildImport method, of class RawTrajecotyGPXImporter.
//     */
//    @Test
//    public void testBuildImport() {
//        System.out.println("buildImport");
//        ITrajectoryModel dataModel = new TrajectoryModelImpl();
//        Parameters parameters = new Parameters();
//        parameters.addParam(RawTrajecotyGPXImporter.PARAMETER_FILE, "/Users/igobrilhante/Documents/GPS Data/20120519.gpx");
//        RawTrajecotyGPXImporter instance = new RawTrajecotyGPXImporter();
//        instance.buildImport(dataModel, parameters);
//        
//        System.out.println("Trajectories: "+dataModel.getTrajectoryCount());
//        System.out.println("Trajectory: "+dataModel.getTrajectory(0));
//        System.out.println("Trajectory Annotation: "+dataModel.getTrajectory(0).getAnnotations());
//        System.out.println("Points: "+dataModel.getTrajectory(0).getPoints());
//        System.out.println("Point Annotation: "+dataModel.getTrajectory(0).getPoint(0).getAnnotations());
//        // TODO review the generated test code and remove the default call to fail.
//        
//    }
//}
