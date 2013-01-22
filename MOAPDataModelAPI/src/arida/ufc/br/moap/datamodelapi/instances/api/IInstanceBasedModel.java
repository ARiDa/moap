/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.datamodelapi.instances.api;

import arida.ufc.br.moap.core.spi.IDataModel;
import java.util.Collection;

/**
 *
 * @author igobrilhante
 */
public interface IInstanceBasedModel extends IDataModel<IInstance> {
    
    
    public void addAttribute(String name,AttributeType type,Object defaultValue);
    
    public void addAttribute(IAttribute attribute);
    
    public IAttribute getAttribute(String attributeName);
    
    public IAttribute getAttribute(int attribureIdx);
    
    public void addInstances(Collection<IInstance> instances);
    
    public void addInstance(IInstance instance);
    
    public IInstance getInstance(int i);
    
    public boolean hasAttribute(int attributeIdx);
    
    public boolean hasAttribute(String attributeName);
    
    public boolean hasAttribute(IAttribute attribute);
    
    public int attributesCount();
    
    public int instancesCount();
    
    
    
    
}
