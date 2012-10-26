package arida.ufc.br.moap.rec.algorithms;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.lang.Double;


/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class TopK<U,I> {
	private Map<I,Double> dataset;
	
        /**
         * 
         * @param <I>
         * @param dataset
         * @param k
         * @return
         */
        public synchronized static <I extends Comparable> SortedMap<I,Double> getTopK(Map<I,Double> dataset,int k){

		TreeSet<Map.Entry<I, Double>> tree = (TreeSet<Map.Entry<I, Double>>) entriesSortedByValues(dataset);
//		System.out.println("tree "+tree);
		if(tree.size() != dataset.size()){
			System.out.println("erro");
			return null;
		}
		SortedMap<I,Double> result = new TreeMap<I, Double>();
		int total = tree.size();
		int count = 0;
		
		while(count < k && count < total){
			Map.Entry<I, Double> item = tree.pollLast();
			result.put(item.getKey(),item.getValue() );
			count++;
		}
//		System.out.println(count);
		if(result.size()>k){
			return null;
		}
//		System.out.println(result);
		return result;
				
	}
        
        

        /**
         * 
         * @param <U>
         * @param <I>
         * @param dataset
         * @param k
         * @return
         */
        public  synchronized static <U,I extends Comparable> Map<U, SortedMap<I,Double>> getTopK(RecommendationSet<U, I> dataset,int k){
		Map<U, SortedMap<I,Double>> result = new HashMap<U, SortedMap<I,Double>>();
		
		for(U user : dataset.getUsers()){
			SortedMap<I,Double> set = getTopK(dataset.getDataset().get(user), k);
			result.put(user, set);
		}
		
		return result;
		
	}
	
        /**
         * 
         * @return
         */
        public String toString(){
		return this.dataset.toString();
	}
	
        /**
         * 
         * @param <K>
         * @param <V>
         * @param map
         * @return
         */
        public static <K extends Comparable,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override 
	            public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	            	int d = e1.getValue().compareTo(e2.getValue());
	            	if(d==0){
	            		d = e1.getKey().compareTo(e2.getKey());
	            	}
	                return d;
	            }
	        }
	    );
//	    System.out.println(map.entrySet().size());
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
	

}
