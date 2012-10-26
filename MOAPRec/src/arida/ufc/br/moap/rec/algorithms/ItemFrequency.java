package arida.ufc.br.moap.rec.algorithms;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * 
 * @author igobrilhante
 */
public class ItemFrequency {
    /**
     * 
     * @param <U>
     * @param <I>
     * @param dataset
     * @return
     */
    public static <U,I> Map<I, Integer> getItemFrequency(RecommendationSet<U, I> dataset){
		TreeMap<I, Integer> frequency = new TreeMap<I, Integer>();
		
		for(U user: dataset.getUsers()){
			Set<I> items = dataset.getItems(user);
			for(I item : items){
				if(frequency.containsKey(item)){
					int count = frequency.get(item);
					count = count + 1;
					frequency.put(item, count);
				}
				else{
					frequency.put(item, 1);
				}
			}
		}
		return frequency;
	}
}
