package org.simplesql.om.data.stores;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataEntry;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;

/**
 * 
 * Aggregate stores work fast with ordered data but performance degrade when
 * data is random (which is mostly the case).<br/>
 * To remedy this we can use a large cache that asynchronously writes to the
 * AggregateStore.
 * 
 */
public class CachedAggregateStore<T> implements AggregateStore<T>,
		RemovalListener<Key, DataEntry> {

	final AggregateStore<T> store;
	final LoadingCache<Key, DataEntry> cache;

	public CachedAggregateStore(final AggregateStore<T> store,
			ExecutorService service, long expireAfterWriteMillis) {
		super();
		this.store = store;
		cache = CacheBuilder
				.newBuilder()
				.maximumSize(100000)
				.expireAfterWrite(expireAfterWriteMillis, TimeUnit.MILLISECONDS)
				.removalListener(this)
				.build(new CacheLoader<Key, DataEntry>() {

					@Override
					public DataEntry load(Key key) throws Exception {
						return store.get(key);
					}

				});

	}

	@Override
	public boolean put(Key key, Cell[] cells) {
		cache.put(key, new DataEntry(key, cells, null));
		return true;
	}

	@Override
	public DataEntry get(Key key) {
		return cache.getUnchecked(key);
	}

	@Override
	public Iterator<DataEntry> iterator() {
		cache.cleanUp();
		return store.iterator();
	}

	@Override
	public Set<? extends Key> keys() {
		cache.cleanUp();
		return store.keys();
	}

	@Override
	public void setOrderKeyBy(int[] cellIndexes,
			org.simplesql.data.AggregateStore.ORDER order) {
		store.setOrderKeyBy(cellIndexes, order);
	}

	@Override
	public void setOrderByData(int[] cellIndexes) {
		store.setOrderByData(cellIndexes);
	}

	@Override
	public void setLimit(int limit) {
		store.setLimit(limit);
	}

	@Override
	public int getLimit() {
		return store.getLimit();
	}

	@Override
	public void write(DataSink sink) {
		cache.cleanUp();
		store.write(sink);
	}

	@Override
	public void close() {
		cache.cleanUp();
		store.close();
	}

	@Override
	public void onRemoval(RemovalNotification<Key, DataEntry> nt) {
		store.put(nt.getKey(), nt.getValue().getCells());
	}
	
}
