package arida.ufc.br.moap.rec.evaluation;

import arida.ufc.br.moap.rec.location.beans.RecommendationSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * 
 * @author igobrilhante
 * @param <U>
 * @param <E>
 */
public class SurprisingGain<U,E> {
	private Logger logger = Logger.getLogger(SurprisingGain.class);
	private Set<E> relevant;
	private Set<E> retrieved1;
	private Set<E> retrieved2;
	private RecommendationSet<U, E> dataset;
	
        /**
         * 
         * @param dataset
         */
        public SurprisingGain(RecommendationSet<U, E> dataset){
		this.dataset = dataset;
	}
	
        /**
         * 
         */
        public SurprisingGain(){}
	
	private int getGain(Set<E> retrieved){
		int gain = 0;
		
		Set<E> dif = new HashSet<E>(retrieved);
		dif.removeAll(relevant);
		gain = dif.size();
		
		return gain;
	}
	
        /**
         * 
         * @param relevant
         * @param retrieved
         * @return
         */
        public int getGain(Set<E> relevant,Set<E> retrieved){
		int gain = 0;
		
		Set<E> dif = new HashSet<E>(retrieved);
		dif.removeAll(relevant);
		gain = dif.size();
		
		return gain;
	}
	
        /**
         * 
         * @param user
         * @param recommendations
         * @return
         */
        public int getNumberOfSurprisingItems(U user,Set<E> recommendations){
		Set<E> surprisingSet = new HashSet<E>();
		int n = 0;
		for(E item : recommendations){
			boolean c1 = condition1(user,item);
//			boolean c2 = condition2(user, item);
//			logger.info("User "+user+" item "+item+" c1:"+c1+" c2:"+c2);
			if( c1 ){
				surprisingSet.add(item);
			}
		}
		n = surprisingSet.size();
		return n;
	}
	
        /**
         * 
         * @param user
         * @param item
         * @return
         */
        public boolean condition1(U user,E item){
		Set<E> u_items = this.dataset.getItems(user);
		// Search for a user v
//		System.out.println("u: "+u_items);
		for(U v : this.dataset.getUsers()){
			// v is not u
			if(!v.equals(user)){
				Set<E> v_items = this.dataset.getItems(v); // v PoIs
//				System.out.println("v: "+v_items);
				// v has visited PoI item
				if(v_items.contains(item)){
					Set<E> intersection_u_v = new HashSet<E>(v_items);
					intersection_u_v.retainAll(u_items);
					// u and v share PoIs
					if(!intersection_u_v.isEmpty()){
						// Search for a user w that does not share PoIs with u, but he has visited item. So, w
						// and v share at least item with each other
						for(U w : this.dataset.getUsers()){
							//w is not v or u
							if(!w.equals(v) && !w.equals(user)){
								Set<E> w_items = this.dataset.getItems(w); // w PoIs
//								System.out.println("w: "+w_items);
								// w contains item, hence w shares PoI with v
								if(w_items.contains(item)){
									Set<E> intersection_u_w = new HashSet<E>(w_items);
									intersection_u_w.retainAll(u_items);
									// u and w do not share items with each other
									if(intersection_u_w.isEmpty()){
										return true;
									}
								}
							}
						}
					}
				}
			}
		}

		return false;
	}
	
        /**
         * 
         * @param user
         * @param item
         * @return
         */
        public boolean condition2(U user,E item){
		Set<E> u_items = this.dataset.getItems(user); // u PoIs
		// Search for users v
		for(U v : this.dataset.getUsers()){
			// v is not u
			if(!v.equals(user)){
				Set<E> v_items = this.dataset.getItems(v); // v PoIs
				// v visited item
				if(v_items.contains(item)){
					Set<E> intersection_u_v = new HashSet<E>(v_items);
					intersection_u_v.retainAll(u_items);
					// u and v do not share PoIs with each other
					if(intersection_u_v.isEmpty()){
						// Search for a user w that share PoIs with u and v. However, w has not visited item
						for(U w : this.dataset.getUsers()){
							// w is not v or u
							if(!w.equals(v) && !w.equals(user)){
								Set<E> w_items = this.dataset.getItems(w);
								// w has not visited item
								if(!w_items.contains(item)){
									Set<E> intersection_w_v = new HashSet<E>(w_items);
									intersection_w_v.retainAll(v_items);
									
									Set<E> intersection_w_u = new HashSet<E>(w_items);
									intersection_w_u.retainAll(u_items);
									// w shares PoIs with u
									if(!intersection_w_u.isEmpty()){
										// w shares PoIs with v
										if(!intersection_w_v.isEmpty()){
											return true;
										}
										
									}
								}
							}
						}
					}
				}	
			}
			
		}
		
		return false;
	}
}
