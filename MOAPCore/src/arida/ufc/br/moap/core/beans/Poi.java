package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.IAnnotable;
import java.util.HashSet;
import java.util.Set;

public class Poi implements IAnnotable, Comparable<Poi> {

    private String id;
    private String label;
    private LatLonPoint point;
    private Set<Category> categories;
    private Annotations annotations;

    public Poi(String id){
        this.id = id;
        this.annotations = new Annotations();
        this.categories = new HashSet<Category>();
    }
    
    public String getId() {
        return id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public LatLonPoint getPoint() {
        return point;
    }

    public void setPoint(LatLonPoint point) {
        this.point = point;
    }
    
    public void setPoint(double lon, double lat) {
        this.point = new LatLonPoint(lon, lat);
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public Annotations getAnnotations() {
        return this.annotations;
    }
    
    @Override
    public String toString(){
        return this.id;
    }

    @Override
    public int compareTo(Poi t) {
        return id.compareTo(t.id);
    }
}
