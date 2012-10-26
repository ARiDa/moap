package arida.ufc.br.moap.rec.evaluation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arida.ufc.br.moap.rec.algorithms.TopK;

/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class IREvaluation<U,I extends Comparable> {
	private Map<U,Map<I,Double>> relevant;
	private Map<U,Map<I,Double>> retrieved;
	
        /**
         * 
         * @param relevant
         * @param retrieved
         */
        public IREvaluation(Map<U,Map<I,Double>> relevant,Map<U,Map<I,Double>> retrieved){
		this.relevant = relevant;
		this.retrieved = retrieved;
	}
	
        /**
         * 
         * @param k
         * @return
         */
        public double precisionK(int k){
		double precision = 0.0;
		double a=0.0,b=0.0;
		int n = 0;
		for(U user : relevant.keySet()){
			if(retrieved.containsKey(user)){
				Set<I> intersection = new HashSet<I>(relevant.get(user).keySet());
				Set<I> topSet = TopK.getTopK(retrieved.get(user),k).keySet();
//				System.out.println(topSet);
				intersection.retainAll(topSet);
//				a += intersection.size();
//				b += topSet.size();
				a = intersection.size();
				b = topSet.size();
				if(b > 0){
					precision =  precision + (a/b);
					n++;
				}
			}
		}
//		precision = a/b;
		precision = precision/n;
		return precision;
	}
	
        /**
         * 
         * @param k
         * @return
         */
        public double recallK(int k){
		double recall = 0.0;
		double a=0.0,b=0.0;
		int n = 0;
		for(U user : relevant.keySet()){
			if(retrieved.containsKey(user)){
				Set<I> intersection = new HashSet<I>(relevant.get(user).keySet());
				Set<I> topSet = TopK.getTopK(retrieved.get(user),k).keySet();
//				System.out.println("R: "+retrieved.get(user)+" T: "+topSet.size());
				intersection.retainAll(topSet);
//				a += intersection.size();
//				b += relevant.get(user).keySet().size();
				a = intersection.size();
				b = relevant.get(user).keySet().size();
				if(b > 0){
					recall = recall + (a/b);
					n++;
				}
			}
		}
//		recall = a/b;
		recall = (double)recall/n;
		return recall;
	}
	
        /**
         * 
         * @param k
         * @return
         */
        public double fmeasureK(int k){
		double fmeasure = 0.0;
		double precision = precisionK(k);
		double recall = recallK(k);
		
		fmeasure = (2*precision*recall)/(precision+recall);
		
		return fmeasure;
	}
}
