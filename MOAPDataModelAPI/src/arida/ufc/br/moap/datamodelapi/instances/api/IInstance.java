/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.api;

/**
 *
 * @author igobrilhante
 * 
 * Defines the needed structure of an instance
 */
public interface IInstance {
    
    /**
     * All instance must have its model defined
     * 
     * @return the correspondent model for the instance
     */
    public IInstancesBasedModel getModel();
    
    /**
     * 
     * @param columnIdx
     * @return Object for a given column index
     */
    public Object getValue(int columnIdx);
    
    /**
     * 
     * @param columnName
     * @return Object for a given column name
     */
    public Object getvalue(String columnName);
    
    /**
     * Set a value in the column index
     * 
     * @param columnIdx
     * @param value 
     */
    public void setValue(int columnIdx,Object value);
    
    /**
     * Set a value in the column name
     * 
     * @param columnName
     * @param value 
     */
    public void setValue(String columnName,Object value);
    
    
    
}
