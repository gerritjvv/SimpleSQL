package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.simplesql.data.Cell.SCHEMA;

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
	int hashCode = -1;

	SCHEMA[] schemas;
	
	
	/**
	 * To be used only for serialization
	 */
	public SimpleCellKey() {

	}

	public SimpleCellKey(String val) {
		this(new StringCell(val));
	}

	/**
	 * Receives and array of objects and wraps them into the correct cell.
	 * 
	 * @param vals
	 */
	public SimpleCellKey(Object... vals) {

		final int len = vals.length;
		cells = new Cell[len];

		for (int i = 0; i < len; i++) {
			final Object val = vals[i];
			final Cell cell;
			if (val instanceof Integer) {
				cell = new IntCell((Integer) val);
			} else if (val instanceof Long) {
				cell = new LongCell((Long) val);
			} else if (val instanceof Double) {
				cell = new DoubleCell((Double) val);
			} else if (val instanceof Boolean) {
				cell = new BooleanCell((Boolean) val);
			} else if (val instanceof String) {
				cell = new StringCell((String) val);
			} else {
				cell = new DynamicCell(val);
			}

			cells[i] = cell;
		}

	}

	public SimpleCellKey(Cell... cells) {
		this.cells = cells;
	}

	public SCHEMA[] getSchemas(){
		
		if(schemas == null){
			final int len = cells.length;
			schemas = new SCHEMA[len];
			
			for(int i = 0; i < len; i++){
				schemas[i] = cells[i].getSchema();
			}
		}
		
		return schemas;
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
		
		if(hashCode == -1){
			final HashFunction hashFunct = Hashing.goodFastHash(10);
			final Hasher hasher = hashFunct.newHasher();
			final int len = cells.length;
			for (int i = 0; i < len; i++)
				cells[i].putHash(hasher);

			hashCode = hasher.hash().hashCode();
		}
		
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