package arida.ufc.br.moap.rec.slopeone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;

import org.apache.log4j.Logger;

import arida.ufc.br.moap.rec.spi.IRecommenderSystem;

/**
 * @author igobrilhante
 *
 * @param <U> User
 * @param <I> Item
 */
public class WeightedSlopeOne<U,I> implements IRecommenderSystem<U, I> {
	private Map<I, Map<I,Double>> dev;
	private Map<U, Map<I,Double>> dataset;
	private Map<U, Map<I,Double>> predictions;
	private RecommendationSet<U, I> result;
        private RecommendationSet<U, I> recSet;
	private int[][] setSij;
	private Map<I,Integer> itemIndex;
	private Logger logger = Logger.getLogger(WeightedSlopeOne.class);
	
	/**
	 * @param dataset
	 */
        @Deprecated
	public WeightedSlopeOne(Map<U, Map<I,Double>> dataset){
		logger.info("Weighted Slope One");
		predictions = new HashMap<U, Map<I,Double>>();
		this.dataset = dataset;
	}
        
        /**
         * 
         * @param dataset
         */
        public WeightedSlopeOne(RecommendationSet<U,I> dataset){
                logger.info("Weighted Slope One");
		predictions = new HashMap<U, Map<I,Double>>();
		this.dataset = dataset.getDataset();
                this.recSet = dataset;
        }
	
	/**
	 * @param user
	 * @param item_i
	 * @return double Prediction for a given user and item_i
	 */
	private double slopeOne(U user, I item_i){
		Map<I,Double> items = this.dataset.get(user);

		double weights = 0.0;
//		Map<I,Double> predictions = new HashMap<I, Double>();
		double prediction = 0.0;
		Map<I,Double> dev_i = dev.get(item_i);
//		System.out.println(user);
		for(I item_j : items.keySet()){ // compare the recommended item i with the user's items
			
			if(!item_i.equals(item_j)){
                            if(dev_i.containsKey(item_j)){
				int cad = setSij(item_i, item_j);
//                                System.out.println("I: "+item_i+" J:"+item_j);
				prediction += (double)(dev_i.get(item_j) + items.get(item_j))*cad;
				weights += cad;
                            }
			}
		}
		
		if(prediction != 0.0 && weights != 0.0){
			prediction = (double)prediction/weights;
		}
//		System.out.println(item_i+" pred "+prediction);
		
		
		return prediction;
	}
	
	
	/**
	 * @return Map<U, Map<I,Double>> Set of users and their prediction for items
	 */
	public Map<U, Map<I,Double>> computePredictions(){
		logger.info("Computing prediction");
		Set<I> item_set = new HashSet<I>();
		logger.info("Selecting item set");
		for(U u : this.dataset.keySet()){
			Map<I,Double> user_items = dataset.get(u);
			for(I item : user_items.keySet()){
				item_set.add(item);
			}
		}
		int c =0;
		itemIndex = new HashMap<I, Integer>();
		for(I item : item_set){
			itemIndex.put(item, c);
			c++;
		}
		
		int itemCount = item_set.size();
		this.setSij = new int[itemCount][itemCount];
		for(int i=0;i<itemCount;i++){
			for(int j=0;j<itemCount;j++){
				this.setSij[i][j] = -1;
			}
		}
		
		logger.info("Computing deviations");
		arida.ufc.br.moap.rec.slopeone.Deviation<U, I> deviations = new Deviation<U, I>(recSet);
		this.dev = deviations.computeDeviation();
		
		logger.info("Prediction for each user u_i: "+this.dataset.keySet().size());
		for(U user : dataset.keySet()){
//			logger.info("User: "+user);
			Set<I> user_items = dataset.get(user).keySet();
			Map<I, Double> user_prediction = new HashMap<I, Double>();
			for(I item : item_set){ // for all item in the dataset
				if(!user_items.contains(item)){ // if the user has not had this item yet
					double pred = slopeOne(user, item);
					if(pred > 0.0)
						user_prediction.put(item, pred);
				}
			}
			predictions.put(user, user_prediction);
		}
//		System.out.println(setSij);
		return predictions;
	}
	
	private int setSij(I item_i, I item_j) {
		int cad = 0;
		int iIndex = itemIndex.get(item_i);
		int jIndex = itemIndex.get(item_j);
		if(setSij[iIndex][jIndex]==-1){
			for (U user : dataset.keySet()) {
				Set<I> items = dataset.get(user).keySet();
				if (items.contains(item_i) && items.contains(item_j)) {
					cad++;
				}
			}
			setSij[iIndex][jIndex] = cad;
			setSij[jIndex][iIndex] = cad;
		}
		else{
			cad = setSij[iIndex][jIndex];
		}
			
			
//		System.out.println("Cad "+item_i+" "+item_j+" = "+cad);
		return cad;
	}

        /**
         * 
         */
        @Override
	public void execute() {
		// TODO Auto-generated method stub
		this.predictions = this.computePredictions();
		this.result = new RecommendationSet<U, I>(this.predictions);
	}

        /**
         * 
         * @return
         */
        @Override
	public RecommendationSet<U, I> getRecommendation() {
		// TODO Auto-generated method stub
		
		return this.result;
	}
	
}
