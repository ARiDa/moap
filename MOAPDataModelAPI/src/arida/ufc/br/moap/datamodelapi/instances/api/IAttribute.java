/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.api;

/**
 *
 * @author igobrilhante
 * 
 * Defines an interface for working with attributes
 */
public interface IAttribute {
    
    /**
     * 
     * @return name or label of the attribute
     */
    public String getName();
    /**
     * 
     * @return default value of the attribute
     */
    public Object getDefaultValue();
    /**
     * 
     * @return the type of the attribute
     */
    public AttributeType getType();
}
