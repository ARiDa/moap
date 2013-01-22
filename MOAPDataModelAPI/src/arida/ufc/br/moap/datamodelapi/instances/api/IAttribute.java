/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.api;

/**
 *
 * @author igobrilhante
 */
public interface IAttribute {
    
    public String getName();
    public Object getDefaultValue();
    public AttributeType getType();
}
