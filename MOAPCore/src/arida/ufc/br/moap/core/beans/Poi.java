package arida.ufc.br.moap.core.beans;

import arida.ufc.br.moap.core.spi.IAnnotable;

public class Poi implements IAnnotable {

    private String id;
    private String label;
    private Annotations annotations;

    public Poi(String id){
        this.id = id;
        this.annotations = new Annotations();
    }
    
    public String getId() {
        return id;
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
}
