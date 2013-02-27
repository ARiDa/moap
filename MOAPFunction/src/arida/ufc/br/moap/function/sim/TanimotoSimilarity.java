package arida.ufc.br.moap.function.sim;

import arida.ufc.br.moap.function.api.ISimilarityFunction;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author igobrilhante
 */
public class TanimotoSimilarity<T> implements ISimilarityFunction<Map<T,Double>> {


    
    public Double evaluate(Map<T,Double> map1,Map<T,Double> map2){
        double sim = 0.0;
        
        Set<T> union = new HashSet<T>(map1.keySet());
        union.addAll(map2.keySet());
        double a = 0.0;
        double b = 0.0;
        double c = 0.0;
        
        boolean c1 = false;
        boolean c2 = false;
        
        for(T item : union){
            c1 = map1.containsKey(item);
            c2 = map2.containsKey(item);
            if(c1 && c2){
                a += (map1.get(item)*map2.get(item));
            }
            if(c1){
                b += (map1.get(item)*map1.get(item));
            }
            if(c2){
                 c += (map2.get(item)*map2.get(item));
            }
        }
        
//        System.out.println(String.format("Sim %s/%s+%s-%s = %s", a,b,c,a,b+c - a));
        if(b+c - a > 0) { sim = (a)/(b+c - a); }
        
        return sim;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Tanimoto Similarity";
	}
}
