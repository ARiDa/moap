package arida.ufc.br.moap.rec.evaluation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import arida.ufc.br.moap.rec.algorithms.TopK;

/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class NormalizedPrecision<U,I extends Comparable> {
	private Map<U,Map<I,Double>> retrievedSet;
	private Map<U,Map<I,Double>> relevantSet;
	private Map<U, Map<I, Double>> trainingSet;
	private Logger logger = Logger.getLogger(NormalizedPrecision.class);
	
        /**
         * 
         * @param retrievedSet
         * @param relevantSet
         * @param trainingSet
         */
        public NormalizedPrecision(Map<U, Map<I, Double>> retrievedSet,
			Map<U, Map<I, Double>> relevantSet, Map<U, Map<I, Double>> trainingSet ) {
		this.retrievedSet = retrievedSet; // Retrieved Set
		this.relevantSet = relevantSet; // Test Set
		this.trainingSet = trainingSet ; // Trainning Set
	}

	private double intersection(Set<I> relevantSet, Set<I> retrievedSet){
		Set<I> intersection = new HashSet<I>(retrievedSet);
		intersection.retainAll(relevantSet);
//		logger.info("Intersection: "+intersection.size());
		return (double)intersection.size();
	}
	
	private double difference(Set<I> relevantSet, Set<I> retrievedSet){
		
		Set<I> minus = new HashSet<I>(relevantSet);
		minus.removeAll(retrievedSet);
//		logger.info("Difference: "+minus.size());
		return (double)minus.size();
	}
	
        /**
         * 
         * @param k
         * @return
         */
        public double normalizedPrecisionK(int k){
		Set<U> entities = retrievedSet.keySet();
//		System.out.println(entities);
		double intersection = 0.0,difference =0.0;
		double np =0.0;
		int n = 0;
		for(U entity : entities){
//			logger.info(entity.toString());
			if(relevantSet.containsKey(entity)){
				
				Set<I> entity_relevantSet = relevantSet.get(entity).keySet(); // Test Set
				Set<I> setV = new HashSet<I>(this.trainingSet.get(entity).keySet());
				setV.addAll(entity_relevantSet);
//				System.out.println(entity_relevantSet);
//				System.out.println(setV);
				Set<I> topkSet = TopK.getTopK(retrievedSet.get(entity),k).keySet(); // Top K for the user
//				System.out.println(topk.getTopK(k));
//				System.out.println(topkSet);
				intersection = intersection + intersection(setV,topkSet);
				difference = difference + Math.min(k,entity_relevantSet.size());
//				System.out.println(intersection);
//				System.out.println(difference);
				//				difference = (double)Math.min(k,entity_relevantSet.size());
//				if(difference > 0){
//					np = np + (intersection/difference);
//					n++;
//				}
				
			}
			
		}
		if(difference != 0)
			np = intersection/difference;
//		if(n > 0)
//			np = np/n;
		return np;
	}
}
