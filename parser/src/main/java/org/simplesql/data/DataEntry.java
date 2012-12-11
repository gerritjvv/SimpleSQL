package org.simplesql.data;

/**
 * 
 * Contain the cells for each row entry. In case of an aggregate only one entry
 * for a key will exist.
 * 
 * @param <T>
 */
public class DataEntry {

	final private Cell[] cells;
	final private TransformFunction[] functions;
	final private int len;
	final private Key key;

	public DataEntry(Key key, Cell[] cells, TransformFunction[] functions) {
		this.key = key;
		this.cells = cells;
		this.functions = functions;
		this.len = (functions == null) ? 0 : functions.length;
	}

	public void apply(Cell[] data) {

		for (int i = 0; i < len; i++) {
			functions[i].apply(cells, data);
		}

	}

	public int size() {
		return (cells == null) ? 0 : cells.length;
	}

	public Cell[] getCells() {
		return cells;
	}

	public Key getKey() {
		return key;
	}

	public void write(DataSink sink) {
		sink.fill(key, cells);
	}

}
