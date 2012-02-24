package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.simplesql.data.Cell.SCHEMA;

/**
 * 
 * Store's cells in a byte array
 * 
 */
public class CellsByteWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	SCHEMA[] schemas;

	public CellsByteWrapper() {

	}

	public CellsByteWrapper(SCHEMA... schemas) {
		this.schemas = schemas;
	}

	public CellsByteWrapper(Cell... cells) {
		schemas = new SCHEMA[cells.length];
		for (int i = 0; i < cells.length; i++) {
			schemas[i] = cells[i].getSchema();
		}
	}

	public void writeTo(byte[] bytes, int from, Cell... cells) {
		int index = from;
		for (int i = 0; i < cells.length; i++) {
			index += cells[i].write(bytes, index);
		}
	}

	public byte[] getBytes(Cell... cells) {

		int size = 0;
		for (int i = 0; i < cells.length; i++) {
			size += cells[i].byteLength();
		}
		byte[] array = new byte[size];
		int index = 0;
		for (int i = 0; i < cells.length; i++) {
			index += cells[i].write(array, index);
		}

		return array;

	}

	/**
	 * The byte array must have been written with Cells of the same schema as is
	 * defined in this object.
	 * 
	 * @param bytes
	 * @param from
	 * @return
	 */
	public Cell[] readFrom(byte[] bytes, int from) {
		int len = schemas.length;

		Cell[] cells = new Cell[len];
		int index = from;

		for (int i = 0; i < len; i++) {
			Cell cell = schemas[i].newCell();
			index += cell.read(bytes, index);
			cells[i] = cell;
		}

		return cells;
	}

	/**
	 * Persists the schema
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(schemas);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		try {
			schemas = (SCHEMA[]) in.readObject();
		} catch (ClassNotFoundException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

}
