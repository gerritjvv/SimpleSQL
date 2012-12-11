package org.simplesql.data;

/**
 * 
 * Create's a new DataEntry from a Cell array and functions.
 * 
 */
public class DataEntryBuilder {

	final private Cell[] cells;
	final private TransformFunction[] functions;

	public DataEntryBuilder(Cell[] cells, TransformFunction[] functions) {
		this.cells = cells;
		this.functions = functions;
	}

	public final DataEntry create(Key key) {
		return new DataEntry(key, CellsUtil.copyOf(cells, true), functions);
	}

}
