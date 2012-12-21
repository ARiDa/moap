/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appmoapimporter;

/**
 *
 * @author franzejr
 */
public class APPMOAPImporter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Object> tables = new ArrayList<Object>();
       
        PostgresqlProvider provider = new PostgresqlProvider();
        provider.setConnection("user", "pass","port","database");
       
        tables = provider.getTables();
       
        for(Object o : tables){
            System.out.println(o.getTriple());
        }
    }
}
