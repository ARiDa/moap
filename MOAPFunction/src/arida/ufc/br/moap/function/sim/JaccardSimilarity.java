/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.function.sim;

import arida.ufc.br.moap.function.api.ISimilarityFunction;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author igobrilhante
 */
public class JaccardSimilarity<T> implements ISimilarityFunction<Set<T>> {

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double evaluate(Set<T> arg0, Set<T> arg1) {
		
		HashSet<T> inter = new HashSet<T>(arg0);
		inter.retainAll(arg1);
		
		int interSize = inter.size();
		int set1Size = arg0.size();
		int set2Size = arg1.size();
		
//		HashSet<T> union = new HashSet<T>(arg0);
//		union.addAll(arg1);
		int unionSize = set1Size + set2Size - interSize;

		return (double)interSize/unionSize;
	}
}
