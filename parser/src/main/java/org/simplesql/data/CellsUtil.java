package org.simplesql.data;

public class CellsUtil {

	/**
	 * Makes a unique copy of each element inside the array. (Deep copy is not
	 * supported).
	 * 
	 * @param cells
	 * @return
	 */
	public static final Cell[] copyOf(Cell[] cells) {

		int len = cells.length;
		Cell[] ncells = new Cell[cells.length];

		for (int i = 0; i < len; i++) {
			ncells[i] = cells[i].copy();
		}

		return ncells;
	}

}
