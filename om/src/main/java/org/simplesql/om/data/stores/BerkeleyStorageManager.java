package org.simplesql.om.data.stores;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.TransformFunction;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.data.StorageManager;
import org.simplesql.om.data.stores.berkeley.DBManager;
import org.simplesql.parser.SQLExecutor;

/**
 * 
 * Create's instances of the BerkeleyAggregateStore
 * 
 */
public class BerkeleyStorageManager implements StorageManager {

	final DBManager dbManager;

	public BerkeleyStorageManager(DBManager dbManager) {
		super();
		this.dbManager = dbManager;
	}

	@Override
	public AggregateStore newAggregateStore(Projection projection,
			SQLExecutor exec) {

		// ---------------------------- Create DataStore in some way that is out
		// of this logic
		// store = StorageManager.getDataStore(sqlExec)
		// create the aggregate store
		AggregateStore store = new BerkeleyAggregateStore(dbManager, exec
				.getTransforms().toArray(new TransformFunction[0]));

		// Order by must be done on the resultant set after all transforms have
		// been performed to the data.
		// to be able to match order columns to the resultant data set even if
		// it was an expression,
		// the as keyword is required. also to know if a order column is data or
		// in the key we need to know
		// which columns were used in the key itself, we can infer this by
		// checking if it is in the select columns, then it is a data value.
		store.setLimit(exec.getLimit());
		store.setOrderKeyBy(exec.getGroupOrderIndexes(), exec.getOrder());
		store.setOrderByData(exec.getSelectOrderIndexes());

		// -------------------------------------- Create data stor

		return store;
	}

	public void close() {
		dbManager.close();
	}

}
