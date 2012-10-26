package arida.ufc.br.moap.core.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Annotations {
	private Map<String, Annotation> annotations;

	public Annotations(){
		this.annotations = new HashMap<String, Annotation>();
	}
	
	public List<Annotation> getAnnotations() {
		return (List<Annotation>) annotations.values();
	}

	public void setAnnotations(List<Annotation> annotations) {
		for(Annotation a : annotations){
			this.annotations.put(a.getName(), a);
		}
	}
	
	public void addAnnotation(String name,AnnotationType type,Object value){
		this.annotations.put(name, new Annotation(name, type, value));
	}
	
	public Annotation getAnnotation(int index){
		return  ((List<Annotation>) annotations.values()).get(index);
	}
	
	public Annotation getAnnotation(String index){
		return this.annotations.get(index);
	}
        
    @Override
        public String toString(){
            return this.annotations.toString();
        }

	
}
