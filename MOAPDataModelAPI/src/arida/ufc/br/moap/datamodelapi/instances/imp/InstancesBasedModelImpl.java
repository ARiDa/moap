package arida.ufc.br.moap.datamodelapi.instances.imp;

import arida.ufc.br.moap.datamodelapi.instances.api.AttributeType;
import arida.ufc.br.moap.datamodelapi.instances.api.IAttribute;
import arida.ufc.br.moap.datamodelapi.instances.api.IInstance;
import arida.ufc.br.moap.datamodelapi.instances.api.IInstancesBasedModel;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author igobrilhante
 * 
 * Implementation of IInstancesBasedModel
 */
public class InstancesBasedModelImpl implements IInstancesBasedModel {

    public ArrayList<IInstance> instances;
    public ArrayList<IAttribute> attributes;

    public InstancesBasedModelImpl() {
        this.instances = new ArrayList();
        this.attributes = new ArrayList<IAttribute>();
    }

    @Override
    public void addAttribute(String name, AttributeType type, Object defaultValue) {
        
        if (name != null && type != null && defaultValue != null) {
            Attribute att = new Attribute(name, type, defaultValue);

            if (!this.attributes.contains(att)) {
                addAttribute(att);
            }
        }
        else{
            throw new IllegalArgumentException("Arguments cannot be null");
        }

    }
    
    @Override
    public void addAttribute(String name, AttributeType type) {
        
        if (name != null && type != null ) {
            Attribute att = new Attribute(name, type);

            if (!this.attributes.contains(att)) {
                this.addAttribute(att);
            }
        }
        else{
            throw new IllegalArgumentException("Arguments cannot be null");
        }

    }

    @Override
    public void addAttribute(IAttribute attribute) {
        
        this.attributes.add(attribute);
//        if(attribute!=null) {
//            this.attributes.add(attribute);
//        }
//        else {
//            throw new IllegalArgumentException("Arguments cannot be null");
//        }
    }

    @Override
    public IAttribute getAttribute(String attributeName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IAttribute getAttribute(int attribureIdx) {
        return attributes.get(attribureIdx);
    }

    @Override
    public void addInstances(Collection<IInstance> instances) {
        
        for(IInstance instance: instances){
            this.addInstance(instance);
        }
        
    }

    @Override
    public void addInstance(IInstance instance) {
        if(instance!=null){
            this.instances.add(instance);
        }
        else{
            throw new IllegalArgumentException("Instance cannot be null");
        }
    }

    @Override
    public IInstance getInstance(int i) {
        return this.instances.get(i);
    }

    @Override
    public boolean hasAttribute(int attributeIdx) {
        if(0<= attributeIdx && attributeIdx < this.attributes.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean hasAttribute(String attributeName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasAttribute(IAttribute attribute) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getName() {
        return "Intances Based Model";
    }

    @Override
    public Collection<IInstance> getInstances() {
        return this.instances;
    }

    @Override
    public int attributesCount() {
        return this.attributes.size();
    }

    @Override
    public int instancesCount() {
        return this.instances.size();
    }
}
