/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.imp;

import arida.ufc.br.moap.datamodelapi.instances.api.AttributeType;
import arida.ufc.br.moap.datamodelapi.instances.api.IAttribute;

/**
 *
 * @author igobrilhante
 */
public class Attribute implements IAttribute {
    
    private final String name;
    private final AttributeType type;
    private final Object defaultValue;

    public Attribute(String name,AttributeType type,Object defaultValue){
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    
    public Attribute(String name,AttributeType type){
        this.name = name;
        this.type = type;
        this.defaultValue = null;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public AttributeType getType() {
        return this.type;
    }
    
}
