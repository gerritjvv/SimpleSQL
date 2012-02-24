package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

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
	int hashCode;

	/**
	 * To be used only for serialization
	 */
	public SimpleCellKey() {

	}

	public SimpleCellKey(String val) {
		this(new StringCell(val));
	}

	public SimpleCellKey(Cell... cells) {

		this.cells = cells;
		HashFunction hashFunct = Hashing.goodFastHash(10);
		Hasher hasher = hashFunct.newHasher();
		int len = cells.length;
		for (int i = 0; i < len; i++)
			cells[i].putHash(hasher);

		hashCode = hasher.hash().hashCode();
	}

	@Override
	public String asString() {
		StringBuilder buff = new StringBuilder();
		for (Cell cell : cells) {
			buff.append(cell.getData().toString());
		}
		return buff.toString();
	}

	@Override
	public Cell[] getCells() {
		return cells;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object key) {
		return (compareTo((Key) key) == 0);
	}

	@Override
	public int compareTo(Key key) {
		
		Cell[] kcells = key.getCells();
		if (cells.length == kcells.length) {
			int c = 0;
			for (int i = 0; i < cells.length; i++) {
				c = cells[i].compareTo(kcells[i]);
				if (c != 0)
					break;
			}
			return c;
		} else {
			return -1;
		}
	}

	public String toString() {
		return "SimpleCellKey(" + asString() + ")";
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

	@Override
	public int compareAt(int i, Key key) {
		return cells[i].compareTo(key.cellAt(i));
	}

	@Override
	public Cell cellAt(int i) {
		return cells[i];
	}
}