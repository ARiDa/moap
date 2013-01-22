/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.api;

/**
 *
 * @author igobrilhante
 */
public interface IInstance {
    
    public IInstanceBasedModel getModel();
    
    public Object getValue(int columnIdx);
    
    public Object getvalue(String columnName);
    
    public void setValue(int columnIdx,Object value);
    
    public void setValue(String columnName,Object value);
    
    
    
}
