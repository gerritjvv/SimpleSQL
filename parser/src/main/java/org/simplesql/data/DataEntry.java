package org.simplesql.data;

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
			cells = data;

		for (int i = 0; i < len; i++) {
			functions[i].apply(cells, data);
		}

	}

	public Cell[] getCells() {
		return cells;
	}

	public void write(DataSink sink) {
		sink.fill(cells);
	}

}
