package arida.ufc.br.moap.association.api;

import java.util.Collection;

/**
 * @author igobrilhante
 * Interface to define an association rule
 */
public interface IAssociationRule {
	
	
	/**
	 * @return id of the rule. It could be only an order of te rule
	 */
	public int getId();
	
	
	/**
	 * @return the antecedent part of the rule
	 */
	public Collection<Item> getAntecedent();
	
	
	/**
	 * @return the consequent part of the rule
	 */
	public Collection<Item> getConsequent();
	
	
	/**
	 * @return support of the rule
	 */
	public float getSupport();
	
	
	/**
	 * @return confidence of the rule
	 */
	public float getConfidence();
	
	
	/**
	 * @return lift of the rule
	 */
	public float getLift();
	
	
	
}
