/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.community;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igobrilhante
 */
public class Community<E> implements Comparable<Community<E>> {
       private String id;
       private List<E> members;

    public Community(String id) {
        this.id = id;
        this.members = new ArrayList<E>();
    }

    public Community(String id, List<E> members) {
        this.id = id;
        this.members = members;
    }

    public List<E> getMembers() {
        return members;
    }

    public void setMembers(List<E> members) {
        this.members = members;
    }

    public String getId() {
        return id;
    }
    
    @Override
    public String toString(){
        return "Community "+id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Community<E> other = (Community<E>) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    @Override
    public int compareTo(Community<E> t) {
        return this.id.compareTo(t.getId());
    }
    
    
    
       
}
