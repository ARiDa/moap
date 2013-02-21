package arida.ufc.br.moap.association.api;


import java.util.Collection;

import arida.ufc.br.moap.core.algorithm.spi.AbstractAlgorithm;
import arida.ufc.br.moap.datamodelapi.imp.ListModelImpl;
import arida.ufc.br.moap.datamodelapi.instances.api.IInstancesBasedModel;

/**
 * @author igobrilhante
 *
 * Abstract class that defines the capabilities of an association rules algorithm. 
 * It receives IInstancesBasedModel as input and results a ListModelImpl<IAssociationRule>
 */
public abstract class AssociationRulesAlgorithm extends AbstractAlgorithm<IInstancesBasedModel, ListModelImpl<IAssociationRule>> {
	
	/**
	 * @return index of the item attribute. It corresponds to the attribute that will be the item of the transactions
	 */
	public abstract int getItemAttributeIndex();
	
	/**
	 * return index of the transaction attribute index. Transaction index is the unique identifier of the transaction, e.g. user id.
	 */
	public abstract int getTransactionAttributeIndex();
	
	/**
	 * @return transaction format. Simple, each line for a item, or List, all items of a single transaction per line
	 */
	public abstract TransactionFormat getTransactionFormat();
	
	/**
	 * @return number of transactions
	 */
	public abstract int getTransactionsCount();
	
	/**
	 * @param item
	 * @return collection of rules with a given item
	 */
	public abstract Collection<IAssociationRule> getAssociationRules(Item item);
	
	/**
	 * @param items
	 * @param and, if all the items should be in the association rules
	 * @return collection of rules with a given list of items
	 */
	public abstract Collection<IAssociationRule> getAssociationRules(Collection<Item> items,boolean and);
	
	/**
	 * @return number os rules found
	 */
	public abstract int getAssociationRulesCount();
	
	/**
	 * @return number of items
	 */
	public abstract int getItemsCount();
	
	/**
	 * @return support
	 */
	public abstract float getSupport();
	
	/**
	 * @return confidence
	 */
	public abstract float getConfidence();

}
