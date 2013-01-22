package arida.ufc.br.moap.datamodelapi.instances.imp;

import arida.ufc.br.moap.datamodelapi.instances.api.AttributeType;
import arida.ufc.br.moap.datamodelapi.instances.api.IInstance;
import arida.ufc.br.moap.datamodelapi.instances.api.IInstanceBasedModel;
import java.util.HashMap;

/**
 *
 * @author igobrilhante
 */
public class Instance implements IInstance {

    private final IInstanceBasedModel model;
    private final HashMap<Integer, Object> attributeIdx;
    private final HashMap<String, Integer> attributeColumnName;

    /**
     * Class constructor specifying the model will be used
     */
    public Instance(IInstanceBasedModel model) {
        this.model = model;
        this.attributeIdx = new HashMap<Integer, Object>();
        this.attributeColumnName = new HashMap<String, Integer>();

    }

    @Override
    public IInstanceBasedModel getModel() {
        return this.model;
    }
    
    /**
     * Returns an Object for a specified column Index.
     *
     * @param columnIdx column Name
     * @return Value
     */
    @Override
    public Object getValue(int columnIdx) {

        if (this.model.hasAttribute(columnIdx)) {
            Object value = this.attributeIdx.get(columnIdx);
            if (value == null) {
                return this.model.getAttribute(columnIdx).getDefaultValue();
            } else {
                return this.attributeIdx.get(columnIdx);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Column index out of bound");
        }

    }

    /**
     * Returns an Object for a specified column Name.
     *
     * @param columnName column Name
     * @return Value
     */
    @Override
    public Object getvalue(String columnName) {

        Integer idx = this.attributeColumnName.get(columnName);

        if (idx != null) {

            return getValue(idx);
        }
        throw new ArrayIndexOutOfBoundsException("Column index out of bound");

    }

    /**
     * Set The Value into model for a column by index
     *
     * @param columnIdx colum index
     * @param value value from object
     */
    @Override
    public void setValue(int columnIdx, Object value) {

        if (this.model.hasAttribute(columnIdx)) {

            AttributeType attributeType = this.model.getAttribute(columnIdx).getType();

            Class type = attributeType.getType();

            if (value.getClass().equals(type)) {
                this.attributeIdx.put(columnIdx, value);
                return;
            }

            throw new IllegalArgumentException("Value type " + value.getClass().getSimpleName() + " does not match to Column Type " + type.getSimpleName());

        } else {
            throw new ArrayIndexOutOfBoundsException("Column index out of bound");
        }

    }

    /**
     * Set The Value into model for a column by Column Name
     *
     * @param columnIdx colum index
     * @param value value from object
     */
    @Override
    public void setValue(String columnName, Object value) {

        Integer idx = this.attributeColumnName.get(columnName);
        if (idx != null) {
            this.setValue(idx, value);
        }
        throw new ArrayIndexOutOfBoundsException("Column index out of bound");

    }

    @Override
    public String toString() {
        return "Instance";
    }
}
