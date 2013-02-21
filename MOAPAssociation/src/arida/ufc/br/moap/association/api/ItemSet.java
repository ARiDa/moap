package arida.ufc.br.moap.association.api;

import java.util.List;

/**
 * @author igobrilhante
 * This class represents an item set containing a set of items. 
 * The items should be lexicographically ordered
 *
 */
public class ItemSet {

	/**
	 * List of items. This list should be ordered
	 */
	private final List<Item> items;
	/**
	 * Support count of the item set. Support count is the number of transactions with this item set
	 */
	private final int supportCount;
	
	public ItemSet(List<Item> items,int supportCount){
		this.items = items;
		this.supportCount = supportCount;
	}
	
	/**
	 * @return
	 */
	public int getSupportCount(){
		return this.supportCount;
	}
	
	/**
	 * @return
	 */
	public List<Item> getItemSet(){
		return this.items;
	}
	
	
}
