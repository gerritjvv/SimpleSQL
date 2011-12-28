package org.simplesql.funct;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;

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

	final KeyParser keyParser;
	final AggregateStore store;

	public GroupBy(KeyParser keyParser, AggregateStore store) {
		super();
		this.keyParser = keyParser;
		this.store = store;
	}

	public boolean fill(Cell[] cells) {

		return store.put(keyParser.makeKey(cells), cells);

	}

	public void write(DataSink sink){
		store.write(sink);
	}
	
	/**
	 * 
	 * Interface that create's keys from an input set
	 * 
	 * @param <T>
	 */
	public static interface KeyParser {

		String makeKey(Cell[] cells);

	}

}
