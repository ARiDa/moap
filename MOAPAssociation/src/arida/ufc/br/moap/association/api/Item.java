package arida.ufc.br.moap.association.api;

/**
 * @author igobrilhante
 * This class represents an item, which is an entity that carries a value as its string representation.
 */
/**
 * @author igobrilhante
 *
 */
public class Item implements Comparable<Item> {
	
	/**
	 * String representation of the item value
	 */
	private String stringValue;
	
	/**
	 * Object value of the item 
	 */
	private Object objectValue;
	/**
	 * Frequency of the item
	 */
	private int frequency;
	
	public Item(String value){
		this.stringValue = value;
		this.frequency = 0;
	}
	
	public Item(String stringValue,Object objectValue){
		this.stringValue = stringValue;
		this.objectValue = objectValue;
	}
	
	public String getStringValue(){
		return this.stringValue;
	}
	
	public Object getObjectValue(){
		return this.objectValue;
	}
	
	public void setFrequency(int f){
		this.frequency = f;
	}
	
	public int getFrequency(){
		return this.frequency;
	}

	/**
	 * Compare two items according to their string representation. This is useful for lexicographical ordering
	 */
	@Override
	public int compareTo(Item o) {
		// TODO Auto-generated method stub
		return this.stringValue.compareToIgnoreCase(o.getStringValue());
	}
	
}
