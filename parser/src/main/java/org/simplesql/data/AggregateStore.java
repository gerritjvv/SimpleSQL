package org.simplesql.data;

import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Implements the notion of storing aggregate data
 * 
 */
public interface AggregateStore<T> extends Iterable<DataEntry> {

	enum ORDER {
		DESC, ASC, NONE
	};

	/**
	 * @param key
	 * @param cells
	 * @return boolean true if data was accepted, false if limit was reached
	 *         etc.
	 */
	@SuppressWarnings("rawtypes")
	boolean put(Key key, Cell[] cells);

	DataEntry get(Key key);

	Iterator<DataEntry> iterator();

	Set<? extends Key> keys();

	/**
	 * Does ordering on the key i.e. anything inside the GROUP function.
	 * @param cellIndexes
	 * @param order
	 */
	void setOrderKeyBy(int[] cellIndexes, ORDER order);
	
	void setOrderByData(int[] cellIndexes, ORDER order);
	
	void setLimit(int limit);

	int getLimit();

	void write(DataSink sink);

	void close();

}
