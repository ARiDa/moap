package arida.ufc.br.moap.rec.slopeone;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import arida.ufc.br.moap.rec.spi.SimilarityFunction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class Deviation<U, I> extends SimilarityFunction {

    private RecommendationSet<U, I> dataset;
    private Map<I, Map<I, Double>> deviations;

    /**
     * @param dataset
     */
    public Deviation(RecommendationSet<U, I> dataset) {
        this.dataset = dataset;
    }

    /**
     * @param item_i
     * @param item_j
     * @return
     */
    private void deviation(I item_i, I item_j) {
        int card = 0;
        double dev_i_j = 0.0;
        double dev_j_i = 0.0;
        if (!this.deviations.get(item_i).containsKey(item_j)) {
            for (U user : dataset.getUsers()) {
                Map<I, Double> items = dataset.getItemRatings(user);
                if (items.containsKey(item_i) && items.containsKey(item_j)) {
                    dev_i_j += (items.get(item_i) - items.get(item_j));
                    dev_j_i += (items.get(item_j) - items.get(item_i));
                    card++;
                }
            }

            if (card > 0) {
                double final_dev_i_j = (double) dev_i_j / card;
                double final_dev_j_i = (double) dev_j_i / card;

                this.deviations.get(item_i).put(item_j, final_dev_i_j);
                this.deviations.get(item_j).put(item_i, final_dev_j_i);
//            return final_dev_i_j;
            } else {
                this.deviations.get(item_i).put(item_j, 0.0);
                this.deviations.get(item_j).put(item_i, 0.0);
            }
        }
//        return 0.0;
    }

    /**
     * @return
     */
    public Map<I, Map<I, Double>> computeDeviation() {
//        Set<I> items = this.dataset.getItems();
//        int item_count = 0;


//        item_count = items.size();
//		this.deviations = new double[item_count][item_count];


//		I[] array_items = (I[]) items.toArray();
        this.deviations = new HashMap<I, Map<I, Double>>();
//		for(int i = 0;i<item_count;i++){
//			I item_i = array_items[i];
//			Map<I,Double> dev_i = new HashMap<I, Double>();
//			for(int j=0; j<item_count;j++){
//				I item_j = array_items[j];
//				if(i!=j){
//					double dev = deviation(item_i, item_j);
//					dev_i.put(item_j, dev);
//					
//				}
//			}
//			deviations.put(item_i, dev_i);
//		}

        Set<U> users = this.dataset.getUsers();
        for (U user : users) {
            Set<I> user_items = this.dataset.getItems(user);
            I[] array_items = (I[]) user_items.toArray();
            for (int i = 0; i < array_items.length; i++) {
                I item_i = array_items[i];

                if (!this.deviations.containsKey(item_i)) {
                    this.deviations.put(item_i, new HashMap<I, Double>());
                }

                Map<I, Double> dev_i = this.deviations.get(item_i);

                for (int j = 0; j < array_items.length; j++) {
                    I item_j = array_items[j];
                    if (!this.deviations.containsKey(item_j)) {
                        this.deviations.put(item_j, new HashMap<I, Double>());
                    }
                    Map<I, Double> dev_j = this.deviations.get(item_j);

                    deviation(item_i, item_j);
//                            dev_i.put(item_j, dev);

                }
//                deviations.put(item_i, dev_i);
            }
        }
//        System.out.println(deviations);
        return deviations;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
