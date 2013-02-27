///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package arida.ufc.br.moap.function.sim;
//
//
//import arida.ufc.br.moap.function.beans.DefaultDataset;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author igobrilhante
// */
//public class CosineSimilarityTest {
//    
//    public CosineSimilarityTest() {
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
//    @Test
//    public void test(){
//               System.out.println("getSimilarity");
//        DefaultDataset dataset = new DefaultDataset();
//        dataset.addUser(1);
//        dataset.addUserItem(1, 1,2.0);
//        dataset.addUserItem(1, 2,1.0);
//        dataset.addUserItem(1, 4,2.0);
//        dataset.addUserItem(1, 6,1.0);
//        dataset.addUserItem(1, 7,1.0);
//        dataset.addUserItem(1, 8,1.0);
//        
//        dataset.addUser(2);
//        dataset.addUserItem(2, 1,2.0);
//        dataset.addUserItem(2, 2,1.0);
//        dataset.addUserItem(2, 3,1.0);
//        dataset.addUserItem(2, 4,1.0);
//        dataset.addUserItem(2, 5,1.0);
//        dataset.addUserItem(2, 7,1.0);
//        dataset.addUserItem(2, 8,1.0);
//        
//        
//        Object o1 = 1;
//        Object o2 = 2;
//        CosineSimilarity instance = new CosineSimilarity(dataset);
//        double expResult = 0.822;
//        double result = instance.getSimilarity(o1, o2);
//        System.out.println("Result: "+result);
//        assertEquals(expResult, result, 0.01);
//    }
//}
