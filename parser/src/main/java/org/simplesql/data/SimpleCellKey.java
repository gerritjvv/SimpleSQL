package org.simplesql.data;


/**
 * 
 * Combines the Cell values into a single String. The single String is used to
 * calculate hashcode and equality.
 * 
 */
public class SimpleCellKey implements Key {

	final String str;
	final Cell[] cells;

	public SimpleCellKey(Cell... cells) {
		StringBuilder buff = new StringBuilder();
		for (Cell cell : cells) {
			buff.append(cell.getData().toString());
		}
		this.cells = cells;
		this.str = buff.toString();
	}

	@Override
	public String asString() {
		return str;
	}

	@Override
	public Cell[] getCells() {
		return cells;
	}

	@Override
	public int hashCode() {
		return str.hashCode();
	}

	@Override
	public boolean equals(Object key) {
		return str.equals(((Key)key).asString());
	}

	@Override
	public int compareTo(Key key) {
		return str.compareTo(key.asString());
	}

	public String toString() {
		return "SimpleCellKey(" + str + ")";
	}

}