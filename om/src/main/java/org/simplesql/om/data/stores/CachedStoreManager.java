package org.simplesql.om.data.stores;

import java.util.concurrent.ExecutorService;

import org.simplesql.data.AggregateStore;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.data.StorageManager;
import org.simplesql.parser.SQLExecutor;

public class CachedStoreManager implements StorageManager {

	final StorageManager manager;
	final long expireAfterWriteMillis;
	final ExecutorService service;
	
	public CachedStoreManager(StorageManager manager,
			long expireAfterWriteMillis, ExecutorService service) {
		super();
		this.manager = manager;
		this.expireAfterWriteMillis = expireAfterWriteMillis;
		this.service = service;
	}

	@Override
	public AggregateStore newAggregateStore(Projection projection,
			SQLExecutor exec) {
		return new CachedAggregateStore(manager.newAggregateStore(projection, exec), service, expireAfterWriteMillis);
	}

	@Override
	public void close() {
		manager.close();
	}

}
