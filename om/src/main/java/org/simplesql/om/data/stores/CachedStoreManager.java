package org.simplesql.om.data.stores;

import org.simplesql.data.AggregateStore;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.data.StorageManager;
import org.simplesql.parser.SQLExecutor;

public class CachedStoreManager implements StorageManager {

	final StorageManager manager;

	public CachedStoreManager(StorageManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public AggregateStore newAggregateStore(Projection projection,
			SQLExecutor exec) {
		return manager.newAggregateStore(projection, exec);
	}

	@Override
	public void close() {
		manager.close();
	}

}
