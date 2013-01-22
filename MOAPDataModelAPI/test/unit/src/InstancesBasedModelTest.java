/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import arida.ufc.br.moap.datamodelapi.instances.api.AttributeType;
import arida.ufc.br.moap.datamodelapi.instances.api.IInstance;
import arida.ufc.br.moap.datamodelapi.instances.imp.Instance;
import arida.ufc.br.moap.datamodelapi.instances.imp.InstancesBasedModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author igobrilhante
 */
public class InstancesBasedModelTest {

    public InstancesBasedModelTest() {
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void example() {

        /*
         * Defining the Model
         */
        InstancesBasedModel model = new InstancesBasedModel();
        model.addAttribute("age", AttributeType.INT, -1);
        model.addAttribute("name", AttributeType.STRING, "");
        model.addAttribute("weight", AttributeType.FLOAT, 0f);

        /*
         * Creating instances
         */
        Instance i1 = new Instance(model);
        i1.setValue(0, 24);
        i1.setValue(1, "Igo");
        i1.setValue(2, 79.4f);

        // Setting only one attribute
        Instance i2 = new Instance(model);
        i2.setValue(1, "Fulano");

        // Setting two attributes
        Instance i3 = new Instance(model);
        i3.setValue(1, "Cicrano");
        i3.setValue(2, 80f);


        /*
         * Adding instances to the model
         */
        model.addInstance(i1);
        model.addInstance(i2);
        model.addInstance(i3);

        /*
         * Priting Instances
         */

        for (IInstance instance : model.getInstances()) {

            int attCount = model.attributesCount();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < attCount; i++) {
                sb.append(instance.getValue(i));
                sb.append("\t");
            }

            System.out.println(sb);

        }



    }

    /*
     * Test wrong types
     */
    @Test
    public void test() {
        /*
         * Defining the Model
         */
        InstancesBasedModel model = new InstancesBasedModel();
        model.addAttribute("age", AttributeType.INT, -1);
        model.addAttribute("name", AttributeType.STRING, "");
        model.addAttribute("weight", AttributeType.FLOAT, 0f);

        /*
         * Creating instances
         */
        Instance i1 = new Instance(model);
        i1.setValue(0, 24);
        i1.setValue(1, "Igo");
        // Setting a wrong value for an Attribute Float
        try {
            i1.setValue(2, "wrong");
            assert false;
        } catch (Exception e) {
            assert true;
        }
    }
}
