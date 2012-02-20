package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * Combines the Cell values into a single String. The single String is used to
 * calculate hashcode and equality.
 * 
 */
public class SimpleCellKey implements Key {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String str;
	Cell[] cells;

	/**
	 * To be used only for serialization
	 */
	public SimpleCellKey() {

	}

	public SimpleCellKey(String val) {
		this(new StringCell(val));
	}

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
		return str.equals(((Key) key).asString());
	}

	@Override
	public int compareTo(Key key) {
		return str.compareTo(key.asString());
	}

	public String toString() {
		return "SimpleCellKey(" + str + ")";
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		int len = cells.length;
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			out.writeObject(cells[0]);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException {
		int len = in.readInt();
		cells = new Cell[len];
		StringBuilder buff = new StringBuilder();
		try {
			for (int i = 0; i < len; i++) {
				Cell cell = (Cell) in.readObject();
				buff.append(cell.getData().toString());
				cells[i] = cell;
			}
		} catch (ClassNotFoundException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
		this.str = buff.toString();
	}
}