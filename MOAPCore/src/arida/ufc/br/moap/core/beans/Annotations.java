package arida.ufc.br.moap.core.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arida.ufc.br.moap.core.spi.Type;

public class Annotations {
	private Map<String, Annotation> annotations;

	public Annotations(){
		this.annotations = new HashMap<String, Annotation>();
	}
	
	public List<Annotation> getAnnotations() {
		List<Annotation> list = new ArrayList<Annotation>();
		list.addAll(annotations.values());
		return list;
	}

	public void setAnnotations(List<Annotation> annotations) {
		for(Annotation a : annotations){
			this.annotations.put(a.getName(), a);
		}
	}
	
	public void addAnnotation(String name,Type type,Object value){
		this.annotations.put(name, new Annotation(name, type, value));
	}
	
	public Annotation getAnnotation(int index){
		List<Annotation> list = new ArrayList<Annotation>();
		list.addAll(annotations.values());
		
		return  list.get(index);
	}
	
	public Annotation getAnnotation(String index){
		return this.annotations.get(index);
	}
        
    @Override
        public String toString(){
            return this.annotations.toString();
        }

	
}
