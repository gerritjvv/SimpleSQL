package org.simplesql.data.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataEntry;
import org.simplesql.data.DataEntryBuilder;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;
import org.simplesql.data.TransformFunction;

/**
 * 
 * Use a ConcurrentHashMap to store DataEntry(s) per key.
 * 
 * @param <T>
 */
public class HashMapAggregateStore<T> implements AggregateStore<T> {

	private final ConstraintDescComparator DESC_COMP = new ConstraintDescComparator();
	private final ConstraintAscComparator ASC_COMP = new ConstraintAscComparator();

	TreeMap<Key, DataEntry> map = new TreeMap<Key, DataEntry>(ASC_COMP);

	final TransformFunction[] functions;

	int limit = Integer.MAX_VALUE;
	int rowCount = 0;

	ORDER order = ORDER.NONE;

	int[] orderCellIndexes;
	int[] orderDataCellIndexes;

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
		orderCellIndexes = new int[] { 0 };
	}

	/**
	 * Add the cells for the key. This method will also apply the
	 * TranformFunction(s).
	 */
	@Override
	public boolean put(Key key, Cell[] cells) {

		// here we should increment and add top to any values required

		DataEntry entry = map.get(key);

		if (entry == null) {

			if (builder == null) {
				builder = new DataEntryBuilder(cells, functions);
			}

			entry = builder.create(key);

			map.put(key, entry);

		}

		entry.apply(cells);

		// apply constraint;

		return true;
	}

	@Override
	public DataEntry get(Key key) {
		return map.get(key);
	}

	@Override
	public Iterator<DataEntry> iterator() {
		return map.values().iterator();
	}

	@Override
	public Set<? extends Key> keys() {
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
	public void setOrderKeyBy(int[] cellIndexes,
			org.simplesql.data.AggregateStore.ORDER order) {
		orderCellIndexes = cellIndexes;
		this.order = order;
		if (order.equals(ORDER.DESC)) {
			map = new TreeMap<Key, DataEntry>(DESC_COMP);
		} else {
			map = new TreeMap<Key, DataEntry>(ASC_COMP);
		}
	}

	@Override
	public void write(DataSink sink) {

		Collection<DataEntry> values = map.values();
		int rows = 0;
		for (DataEntry entry : values) {
			if (rows++ < limit)
				entry.write(sink);
			else
				break;
		}

	}

	@Override
	public void setOrderByData(int[] cellIndexes) {
		this.orderDataCellIndexes = cellIndexes;
	}

	@Override
	public void close() {

	}

	/**
	 * 
	 * Compare cells in DESC
	 * 
	 */
	private class ConstraintAscComparator implements Comparator<Key> {

		@Override
		public int compare(Key key1, Key key2) {
			// we compare by a cell index;
			final int[] indexes = orderCellIndexes;
			final int len = indexes.length;
			if (len < key2.getCells().length) {
				int c = 0;

				for (int i = 0; i < len; i++) {
					c = key1.compareAt(i, key2);
					if (c != 0)
						break;
				}

				if (c == 0)
					return key1.compareTo(key2);
				else
					return c;
			} else {
				return key1.compareTo(key2);
			}

		}

	}

	/**
	 * 
	 * Compare cells in DESC
	 * 
	 */
	private class ConstraintDescComparator extends ConstraintAscComparator {

		@Override
		public int compare(Key key1, Key key2) {
			return super.compare(key1, key2) * -1;
		}

	}

}
