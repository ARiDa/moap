package arida.ufc.br.moap.function.sim;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arida.ufc.br.moap.function.api.ISimilarityFunction;

/**
 * 
 * @author igobrilhante
 */
public class CosineSimilarity<T> implements ISimilarityFunction<Map<T, Double>> {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double evaluate(Map<T, Double> obj1, Map<T, Double> obj2) {
      double sim = 0.0;
      
      Set<T> s1 = obj1.keySet();
      Set<T> s2 = obj2.keySet();
      Set<T> intersection = new HashSet<T>(s1);
      intersection.retainAll(s2);
      if(intersection.size()>0){
          double a = 0;
          double b = 0;
          double c = 0;
          
          for(T o : intersection){
              a += (obj1.get(o)*obj2.get(o));
          }
          
          for(T o : s1){
              b += Math.pow(obj1.get(o), 2);
          }
          b = Math.sqrt(b);
          
          for(T o : s2){
              c += Math.pow(obj2.get(o), 2);
          }
          c = Math.sqrt(c);
          
          sim = a/(b*c);
      }
      
      return sim;
	}


    
//    @Override
//    public double getSimilarity(Object o1, Object o2){
//        double sim = 0.0;
//        
//        Set s1 = this.dataset.getItemRatings(o1).keySet();
//        Set s2 = this.dataset.getItemRatings(o2).keySet();
//        Set intersection = new HashSet(s1);
//        intersection.retainAll(s2);
//        if(intersection.size()>0){
//            double a = 0;
//            double b = 0;
//            double c = 0;
//            
//            for(Object o : intersection){
//                a += (this.dataset.getRating(o1, o)*this.dataset.getRating(o2, o));
//            }
//            
//            for(Object o : this.dataset.getItems(o1)){
//                b += Math.pow(this.dataset.getRating(o1, o), 2);
//            }
//            b = Math.sqrt(b);
//            
//            for(Object o : this.dataset.getItems(o2)){
//                c += Math.pow(this.dataset.getRating(o2, o), 2);
//            }
//            c = Math.sqrt(c);
//            
//            sim = a/(b*c);
//        }
//        
//        return sim;
//    }
}
