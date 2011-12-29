package org.simplesql.data;

import java.util.Arrays;

/**
 * 
 * Contain the cells for each row entry. In case of an aggregate only one entry
 * for a key will exist.
 * 
 * @param <T>
 */
public class DataEntry {

	Cell[] cells;
	final TransformFunction[] functions;
	final int len;

	public DataEntry(TransformFunction[] functions) {
		this.functions = functions;
		this.len = functions.length;
	}

	public void apply(Cell[] data) {
		if (cells == null)
			cells = CellsUtil.copyOf(data);

		for (int i = 0; i < len; i++) {
			functions[i].apply(cells, data);
		}

	}
	
	public int size(){
		return (cells == null) ? 0 : cells.length;
	}

	public Cell[] getCells() {
		return cells;
	}

	public void write(DataSink sink) {
		sink.fill(cells);
	}

}
