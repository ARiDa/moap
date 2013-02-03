/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.imp;

import arida.ufc.br.moap.core.spi.Type;
import arida.ufc.br.moap.core.spi.IAttribute;

/**
 *
 * @author igobrilhante
 */
public class Attribute implements IAttribute {
    
    private final String name;
    private final Type type;
    private final Object defaultValue;

    public Attribute(String name,Type type,Object defaultValue){
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    
    public Attribute(String name,Type type){
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
    public Type getType() {
        return this.type;
    }
    
}
