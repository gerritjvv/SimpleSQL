package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * Combines cells into a serializable tuple
 */
public class CellTuple implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cell[] cells;

	/**
	 * To be used only for serialization
	 */
	public CellTuple() {

	}

	public CellTuple(Cell... cells) {
		this.cells = cells;
	}

	public int compareAt(int i, Cell cell) {
		return cells[i].compareTo(cell);
	}

	public Cell[] getCells() {
		return cells;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		int len = cells.length;
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			out.writeObject(cells[i]);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException {
		final int len = in.readInt();
		cells = new Cell[len];
		try {
			for (int i = 0; i < len; i++) {
				final Cell cell = (Cell) in.readObject();
				cells[i] = cell;
			}
		} catch (ClassNotFoundException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

}