package org.simplesql.funct;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;

/**
 * 
 * A GroupBy function has an AggregateStore and a KeyParser.<br/>
 * Each GroupBy should have keys that define how the grouping should be
 * performed.<br/>
 * Once the key is determined for a row, this row is inserted into an
 * AggregateStore.<br/>
 * 
 * The Aggergate store can determine how to most efficiently store the data and
 * apply any transformations if required.
 * 
 * @param <T>
 */
public class GroupBy implements DataSink {

	final AggregateStore store;

	public GroupBy(AggregateStore store) {
		super();
		this.store = store;
	}

	public boolean fill(Key key, Cell[] cells) {

		return store.put(key, cells);

	}

	public void write(DataSink sink) {
		store.write(sink);
	}

}
