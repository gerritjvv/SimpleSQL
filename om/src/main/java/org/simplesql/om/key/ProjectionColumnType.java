package org.simplesql.om.key;

import org.simplesql.om.key.typed.BooleanKeyColumnValueImpl;
import org.simplesql.om.key.typed.DoubleKeyColumnValueImpl;
import org.simplesql.om.key.typed.IntKeyColumnValueImpl;
import org.simplesql.om.key.typed.LongKeyColumnValueImpl;
import org.simplesql.om.key.typed.StringKeyColumnValueImpl;

/**
 * 
 * Contains the different projection column types. <br/>
 * Width is only for numeric types, string == -1;
 */
public enum ProjectionColumnType {

	TABLENAME(20), STRING(-1), BYTE(1), SHORT(2), INT(4), BOOLEAN(1), LONG(8), DOUBLE(8);

	final int width;

	ProjectionColumnType(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}

	/**
	 * Returns a KeyColumnValue with the correct width set and of the correct
	 * type.
	 * 
	 * @param key
	 * @param from
	 * @return
	 */
	public KeyColumnValue getColumnValue(String name, int order, byte[] key,
			int from, int bufferFrom, int bufferLen) {
		return getColumnValue(name, order, key, from, width, bufferFrom,
				bufferLen);
	}

	/**
	 * Returns a KeyColumnValue with the correct width set and of the correct
	 * type.
	 * 
	 * @param name
	 *            column name
	 * @param key
	 * @param from
	 * @return
	 */
	public KeyColumnValue getColumnValue(String name, int order, byte[] key,
			int from, int width, int bufferFrom, int bufferLen) {
		if (this == ProjectionColumnType.INT) {
			return new IntKeyColumnValueImpl(name, order, key, from, width,
					bufferFrom, bufferLen);
		} else if (this == ProjectionColumnType.LONG) {
			return new LongKeyColumnValueImpl(name, order, key, from, width,
					bufferFrom, bufferLen);
		} else if (this == ProjectionColumnType.DOUBLE) {
			return new DoubleKeyColumnValueImpl(name, order, key, from, width,
					bufferFrom, bufferLen);
		} else if (this == ProjectionColumnType.BOOLEAN) {
			return new BooleanKeyColumnValueImpl(name, order, key, from, width,
					bufferFrom, bufferLen);
		} else {
			return new StringKeyColumnValueImpl(name, order, key, from, width,
					bufferFrom, bufferLen);
		}
	}
}
