/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.IAnnotable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author igobrilhante
 */
public class RoI implements IAnnotable, Comparable<RoI> {
    
    private Annotations annotations;
    
    private final String id;
    private String label;
    private Set<Poi> elements;
    

    public RoI(String id){
        this.id = id;
        this.elements = new HashSet<Poi>();
    }

    public String getId() {
        return id;
    }

    
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Poi> getElements() {
        return elements;
    }

    public void setElements(Set<Poi> elements) {
        this.elements = new HashSet<Poi>(elements);
    }
    
    @Override
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override
    public int compareTo(RoI o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoI other = (RoI) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RoI{" + "id=" + id + ", label=" + label + '}';
    }
    
    
    
}
