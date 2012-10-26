package test.np;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import recommender.evaluation.IREvaluation;
import recommender.evaluation.NormalizedPrecision;

public class IREvaluationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Map<String,Double>> dataset = new HashMap<String, Map<String,Double>>();
		Map<String,Double> trainingSet = new HashMap<String, Double>();
		trainingSet.put("t", 1.0);
		trainingSet.put("g", 2.0);
		trainingSet.put("u", 2.0);
		dataset.put("u1", trainingSet);
		
		Map<String,Map<String,Double>> u1 = new HashMap<String, Map<String,Double>>();
		
		Map<String,Double> retrieved = new HashMap<String, Double>();
		retrieved.put("a", 1.0);
		retrieved.put("b", 2.0);
		retrieved.put("z", 2.0);
		u1.put("u1", retrieved);
		
		Map<String,Double> relevant = new HashMap<String, Double>();
		relevant.put("a", 1.0);
		relevant.put("b", 2.0);
		relevant.put("e", 3.0);
		relevant.put("f", 4.0);
		
		Map<String,Map<String,Double>> u2 = new HashMap<String, Map<String,Double>>();
		u2.put("u1", relevant);
		int k = 4;
		IREvaluation<String, String> ir = new IREvaluation<String, String>(u2, u1);
		System.out.println("p "+ir.precisionK(k));
		System.out.println("r "+ir.recallK(k));
		
		NormalizedPrecision<String, String> np = new NormalizedPrecision<String, String>(u1, u2,dataset);
		System.out.println("np "+np.normalizedPrecisionK(k));
		
	}

}
