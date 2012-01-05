package org.simplesql.data;

import java.util.Iterator;
import java.util.Set;

/**
 * 
 * Implements the notion of storing aggregate data
 * 
 */
public interface AggregateStore<T> extends Iterable<DataEntry> {

	/**
	 * @param key
	 * @param cells
	 * @return boolean true if data was accepted, false if limit was reached
	 *         etc.
	 */
	boolean put(Key key, Cell[] cells);

	DataEntry get(Key key);

	Iterator<DataEntry> iterator();

	Set<Key> keys();

	
	void setLimit(int limit);

	int getLimit();

	void write(DataSink sink);

}
