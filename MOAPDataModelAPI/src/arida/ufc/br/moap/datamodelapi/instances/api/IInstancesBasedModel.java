/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.api;

import arida.ufc.br.moap.core.spi.Type;
import arida.ufc.br.moap.core.spi.IAttribute;
import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.Collection;

/**
 *
 * @author igobrilhante
 * 
 * This data model is based on instances (IInstance) managing a collection of instances as
 * well as a collection of attributes for which the instances are defined.
 * 
 * Each instance included in the model will have the same attributes as the model has. Therefore, the model controls
 * the attributes of the instances including the default values for each attribute
 * 
 * 
 */
public interface IInstancesBasedModel extends IDataModel<IInstance> {
    
    /**
     * Add a new attribute into the model
     * 
     * @param name
     * @param type
     * @param defaultValue 
     */
    public void addAttribute(String name,Type type,Object defaultValue);
    
    /**
     * Add a new attribute into the model
     * 
     * @param name
     * @param type 
     */
    public void addAttribute(String name,Type type);
    
    /**
     * Add a new attribute into the model
     * 
     * @param attribute 
     */
    public void addAttribute(IAttribute attribute);
    
    /**
     * Get an attribute from the model
     * 
     * @param attributeName
     * @return 
     */
    public IAttribute getAttribute(String attributeName);
    
    /**
     * Get an attribute from the model
     * 
     * @param attribureIdx
     * @return 
     */
    public IAttribute getAttribute(int attribureIdx);
    
    /**
     * Add a collection of instances into the model
     * @param instances 
     */
    public void addInstances(Collection<IInstance> instances);
    
    /**
     * Add an instance into the model
     * @param instance 
     */
    public void addInstance(IInstance instance);
    
    /**
     * Get an instance
     * @param i
     * @return 
     */
    public IInstance getInstance(int i);
    
    /**
     * Verify if the model has an attribute
     * 
     * @param attributeIdx
     * @return 
     */
    public boolean hasAttribute(int attributeIdx);
    
    /**
     * Verify if the model has an attribute
     * 
     * @param attributeName
     * @return 
     */
    public boolean hasAttribute(String attributeName);
    
    /**
     * Verify if the model has an attribute
     * 
     * @param attribute
     * @return 
     */
    public boolean hasAttribute(IAttribute attribute);
    
    /**
     * Get the number of attribute
     * 
     * @return number of attribute in the model
     */
    public int attributesCount();
    
    /**
     * Get the number instances
     * 
     * @return number of instances
     */
    public int instancesCount();
    
    public Collection<IAttribute> getAttributes();
    
    
    
    
}
