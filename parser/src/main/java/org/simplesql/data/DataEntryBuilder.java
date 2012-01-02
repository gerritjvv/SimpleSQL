package org.simplesql.data;

/**
 * 
 * Create's a new DataEntry from a Cell array and functions.
 * 
 */
public class DataEntryBuilder {

	final Cell[] cells;
	final TransformFunction[] functions;

	public DataEntryBuilder(Cell[] cells, TransformFunction[] functions) {
		this.cells = cells;
		this.functions = functions;
	}

	public DataEntry create() {
		return new DataEntry(CellsUtil.copyOf(cells, true), functions);
	}

}
