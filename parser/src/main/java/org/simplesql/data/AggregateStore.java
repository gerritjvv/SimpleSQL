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
	boolean put(String key, Cell[] cells);

	DataEntry get(String key);

	Iterator<DataEntry> iterator();

	Set<String> keys();

	
	void setLimit(int limit);

	int getLimit();

	void write(DataSink sink);

}
