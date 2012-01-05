package org.simplesql.data;


/**
 * 
 * Combines the Cell values into a single String. The single String is used
 * to calculate hashcode and equality.
 * 
 */
public class SimpleKey implements Key, Comparable<SimpleKey>{

	final String str;
	final Cell[] cells;

	public SimpleKey(Cell[] cells) {
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
	public boolean equals(Key key) {
		return str.equals(key.asString());
	}

	@Override
	public int compareTo(SimpleKey key) {
		return str.compareTo(key.asString());
	}

}