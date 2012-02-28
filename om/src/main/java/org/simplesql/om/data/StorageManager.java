package org.simplesql.om.data;

import org.simplesql.data.AggregateStore;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.parser.SQLExecutor;

/**
 * 
 * Interface that abstracts the way in which storage implementations are
 * created.
 * 
 */
public interface StorageManager {

	/**
	 * Create's an AggregateStore for the specified Projection and SQLExecutor.
	 * 
	 * @param projection
	 * @param exec
	 * @return AggregateStore
	 */
	@SuppressWarnings("rawtypes")
	AggregateStore newAggregateStore(Projection projection, SQLExecutor exec);

	void close();

}
