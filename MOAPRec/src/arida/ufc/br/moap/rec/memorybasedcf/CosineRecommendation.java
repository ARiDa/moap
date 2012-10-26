package arida.ufc.br.moap.rec.memorybasedcf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import org.apache.log4j.Logger;

import arida.ufc.br.moap.rec.slopeone.WeightedSlopeOne;
import arida.ufc.br.moap.rec.spi.IRecommenderSystem;

/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <I>
 */
public class CosineRecommendation<U,I> implements IRecommenderSystem<U, I> {
	private Map<U, Map<I,Double>> dataset;
	private Map<U, Map<I,Double>> predictions;
	private double[] avg_ratings;
	private int[] item_frequency;
	private Map<U,Integer> userIndex;
	private Map<I,Integer> itemIndex;
	private double[][] similarities;
	private RecommendationSet<U, I> result;
	private Logger logger = Logger.getLogger(WeightedSlopeOne.class);
	
        /**
         * 
         * @param dataset
         */
        public CosineRecommendation(Map<U, Map<I,Double>> dataset){
		this.dataset = dataset;
		this.predictions = new HashMap<U, Map<I,Double>>();
	}
        
        public CosineRecommendation(RecommendationSet<U,I> dataset){
		this.dataset = dataset.getDataset();
		this.predictions = new HashMap<U, Map<I,Double>>();
	}
	
        /**
         * 
         * @param similarities
         */
        public void setSimilarities(double[][] similarities){
		this.similarities = similarities;
	}
	
        /**
         * 
         * @return
         */
        public Map<U, Map<I, Double>> getPredictions() {
		return predictions;
	}

        /**
         * 
         * @param predictions
         */
        public void setPredictions(Map<U, Map<I, Double>> predictions) {
		this.predictions = predictions;
	}

        /**
         * 
         */
        public void execute(){
		logger.info("Computing prediction");
		Set<I> item_set = new HashSet<I>();
		logger.info("Selecting item set");
		int c =0;
		userIndex = new HashMap<U, Integer>();
		for(U u : this.dataset.keySet()){
			Map<I,Double> user_items = dataset.get(u);
			for(I item : user_items.keySet()){
				item_set.add(item);
			}
			userIndex.put(u, c);
			c++;
		}
		item_frequency = new int[item_set.size()];
		c = 0;
		this.itemIndex = new HashMap<I, Integer>();
		for(I item : item_set){
			item_frequency[c] = 0; 
			itemIndex.put(item, c);
			c++;
		}
		
		for(U u : this.dataset.keySet()){
			Map<I,Double> user_items = dataset.get(u);
			for(I item : user_items.keySet()){
				int index = itemIndex.get(item);
				item_frequency[index] = item_frequency[index] + 1; 
			}
		}
		

		logger.info("Initializing structures");
		int userCount = this.dataset.keySet().size();
		this.avg_ratings = new double[userCount];
		for(int i=0;i<userCount;i++){
			avg_ratings[i] = -1;
		}
		this.similarities = new double[userCount][userCount];
		for(int i=0;i<userCount;i++){
			for(int j=0;j<userCount;j++){
				similarities[i][j] = -2.0;
			}
		}
		logger.info("Executing for each user: "+userCount);
		for(U user : dataset.keySet()){
//			logger.info("User: "+user);
			Set<I> user_items = dataset.get(user).keySet();
			Map<I, Double> user_prediction = new HashMap<I, Double>();
			for(I item : item_set){ // for all item in the dataset
				if(!user_items.contains(item)){ // if the user has not had this item yet
					double pred = getPrediction(user, item);
//					System.out.println("Pred "+pred+" for "+user +" item "+item);
					if(pred > 0.0)
						user_prediction.put(item, pred);
				}
			}
			predictions.put(user, user_prediction);
		}
		
		this.result = new RecommendationSet<U, I>(this.predictions);
//		System.out.println(correlations);
	}
	
	private double inverseUserFrequency(I item){
		double f = 0.0;
		int n = this.dataset.keySet().size();
		int index = this.itemIndex.get(item);
		int n_i = item_frequency[index];
		
		f = Math.log(n/n_i);
		
		return f;
		
	}
	
	private double getPrediction(U user, I item){
//		logger.info("getPrediction");
		double p = 0.0;
		double cor = 0.0;
		double weight = 0.0;
		double v_rating = 0.0;
		double v_avg_r;
		double user_avg_r = getAverageRating(user);
		
		for(U v : dataset.keySet()){
			if(!v.equals(user)){
				Map<I,Double> v_items = this.dataset.get(v);
				if(v_items.containsKey(item)){
					v_rating = v_items.get(item);
					v_avg_r = getAverageRating(v,item);
					cor = CosineCorrelation(user, v);
					p = p + (double)((v_rating - v_avg_r)*cor);
					weight = weight + cor;
				}
			}
		}

		
		if(weight != 0.0){

			p = p/weight;
			p = user_avg_r + p;

//			System.out.println(4);
		}
		
		
		
		return p;
		
	}
	
	private double CosineCorrelation(U user1, U user2){
		double cor = 0.0;
//		logger.info("Pearson Correlation");
		int user1Index = this.userIndex.get(user1);
		int user2Index = this.userIndex.get(user2);
		
		if(similarities[user1Index][user2Index] != -2){
			cor = similarities[user1Index][user2Index];
		}
		else{
			Map<I,Double> user1_items = this.dataset.get(user1);
			Map<I,Double> user2_items = this.dataset.get(user2);
//			System.out.println("1: "+user1_items.keySet());
//			System.out.println("2: "+user2_items.keySet());
			Set<I> intersection = new HashSet<I>(user1_items.keySet());
			
			intersection.retainAll(user2_items.keySet());
//			System.out.println("i: "+intersection);
//			System.out.println("s "+intersection.size());
			
			double user1_avg = getAverageRating(user1);
			double user2_avg = getAverageRating(user2);
			if(!intersection.isEmpty()){
				double a = 0.0;
				for(I item : intersection){
					double user1_r = user1_items.get(item);
					double user2_r = user2_items.get(item);
					
//					a = a + ((user1_r - user1_avg)*(user2_r - user2_avg));
					a = a + ((user1_r )*(user2_r));
				}
				
				double b = 0.0;
				for(I item : user1_items.keySet()){
					double user1_r = user1_items.get(item);
//					b = b + (Math.pow((user1_r - user1_avg),2));
					b = b + (Math.pow((user1_r),2));
				}
				
				b = Math.sqrt(b);
				
				double c = 0.0;
				for(I item : user2_items.keySet()){
					double user2_r = user2_items.get(item);
//					c = c + (Math.pow((user2_r - user2_avg),2));
					c = c + (Math.pow((user2_r),2));
				}
			c = Math.sqrt(c);
				
			    double d = b*c;
			    if(d > 0){
			    	cor = (double)a/(d);
//			    	cor = cor * Math.pow((Math.abs(cor)),2.5-1);
//			    	System.out.println("Cor = "+a+" / "+d);
			    }
//				System.out.println("Cor: "+user1+" "+user2+" = "+cor);
			}
//			System.out.println(1);
			
			double cor_caseAmplification = cor;// * Math.pow(Math.abs(cor),2.5-1);
			
			similarities[user1Index][user2Index] = cor_caseAmplification;
			similarities[user2Index][user1Index] = cor_caseAmplification;
//			System.out.println(2);
			
		}
		
		return cor;
	}
	
	private double getAverageRating(U user){
		double avg = 0.0;
//		logger.info("Average Rating");
		int index = this.userIndex.get(user);
		if(this.avg_ratings[index]>-1){
			avg = this.avg_ratings[index];
		}
		else{
			Map<I,Double> items = this.dataset.get(user);
			int count = 0;
			for(I item : items.keySet()){
				avg = avg + items.get(item);
				count++;
			}
			avg = (double)avg/count;
			this.avg_ratings[index] = avg;
		}
		
		return avg;
	}
	
	private double getAverageRating(U user,I i){
		double avg = 0.0;
//		logger.info("Average Rating");
		Map<I,Double> items = this.dataset.get(user);
		int count = 0;
		for(I item : items.keySet()){
			if(!item.equals(i)){
				avg = avg + items.get(item);
				count++;
			}
		}
		if(count > 0)
			avg = (double)avg/count;
		
		
		return avg;
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
