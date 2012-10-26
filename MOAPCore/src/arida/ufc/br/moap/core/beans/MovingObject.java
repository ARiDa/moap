package arida.ufc.br.moap.core.beans;

/**
 * @author igobrilhante
 *
 * @param <T>
 *
 * User Structure
 * @param <S>
 */
public class MovingObject {
    
    private String id;
    
    public MovingObject(String id) {
        this.id = id;
    }
    

    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MovingObject other = (MovingObject) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return "Moving Object " + id;
    }
    
}
