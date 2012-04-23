package org.simplesql.om.data.stores;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.Cell.SCHEMA;
import org.simplesql.data.TransformFunction;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.data.StorageManager;
import org.simplesql.parser.SQLExecutor;

public class KratiStoreManager implements StorageManager {

	final File storeDir;
	final Cell.SCHEMA[] schemas;

	final Configuration conf;

	public KratiStoreManager(Configuration conf, File storeDir, SCHEMA[] schemas) {
		super();
		this.conf = conf;
		this.storeDir = storeDir;
		this.schemas = schemas;
	}

	@Override
	public AggregateStore newAggregateStore(Projection projection,
			SQLExecutor exec) {
		KratiAggregateStore store;
		try {
			store = new KratiAggregateStore(conf, storeDir, schemas, exec
					.getTransforms().toArray(new TransformFunction[0]));
		} catch (Exception e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

		store.setLimit(exec.getLimit());
		store.setOrderKeyBy(exec.getGroupOrderIndexes(), exec.getOrder());
		store.setOrderByData(exec.getSelectOrderIndexes());

		return store;
	}

	@Override
	public void close() {
	}

}
