package org.simplesql.data.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataEntry;
import org.simplesql.data.DataEntryBuilder;
import org.simplesql.data.DataSink;
import org.simplesql.data.TransformFunction;

/**
 * 
 * Use a ConcurrentHashMap to store DataEntry(s) per key.
 * 
 * @param <T>
 */
public class HashMapAggregateStore<T> implements AggregateStore<T> {

	final Map<String, DataEntry> map = new ConcurrentHashMap<String, DataEntry>();

	final TransformFunction[] functions;

	int limit = Integer.MAX_VALUE;
	int rowCount = 0;

	DataEntryBuilder builder;

	/**
	 * 
	 * @param functions
	 *            TransformFunction(s) that will be applied to each row put.
	 */
	public HashMapAggregateStore(TransformFunction... functions) {
		super();

		// helps keep code clean from null pointers.
		if (functions == null) {
			functions = new TransformFunction[0];
		}

		this.functions = functions;
	}

	/**
	 * Add the cells for the key. This method will also apply the
	 * TranformFunction(s).
	 */
	@Override
	public boolean put(String key, Cell[] cells) {

		// here we should increment and add top to any values required

		DataEntry entry = map.get(key);

		if (entry == null) {

			if (builder == null) {
				builder = new DataEntryBuilder(cells, functions);
			}

			if (rowCount++ >= limit) {
				// check for limit on key
				// this implementation expects that all keys are delivered in
				// order.
				return false;
			}

			entry = builder.create();

			map.put(key, entry);

		}

		entry.apply(cells);

		return true;
	}

	@Override
	public DataEntry get(String key) {
		return map.get(key);
	}

	@Override
	public Iterator<DataEntry> iterator() {
		return map.values().iterator();
	}

	@Override
	public Set<String> keys() {
		return map.keySet();
	}

	@Override
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public void write(DataSink sink) {

		Collection<DataEntry> values = map.values();

		for (DataEntry entry : values) {
			entry.write(sink);
		}

	}

}
