/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.spi;

import java.math.BigDecimal;

/**
 *
 * @author igobrilhante
 * 
 * Defines types for being used in IAttribute
 */
public enum Type {
    DOUBLE(Double.class),
    INT(Integer.class),
    STRING(String.class),
    FLOAT(Float.class),
    BOOLEAN(Boolean.class),
    BIGDECIMAL(BigDecimal.class),
    LONG(Long.class);
    ;
    
    
    private final Class type;

    Type(Class type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.getSimpleName();
    }

    /**
     * The name of the enum constant.
     *
     * @return the name of the enum constant
     */
    public String getTypeString() {
        return super.toString();
    }

    /**
     * Returns the
     * <code>Class</code> the type is associated with.
     *
     * @return the <code>class</code> the type is associated with
     */
    public Class getType() {
        return type;
    }

}
