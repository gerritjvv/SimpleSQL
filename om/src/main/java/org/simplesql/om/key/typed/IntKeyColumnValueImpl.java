package org.simplesql.om.key.typed;

import org.simplesql.om.key.KeyColumnValueImpl;
import org.simplesql.om.util.Bytes;

/**
 * 
 * Presents a Integer value
 */
public class IntKeyColumnValueImpl extends KeyColumnValueImpl {

	public IntKeyColumnValueImpl(String name, int order, byte[] key, int from,
			int width, int bufferFrom, int bufferLen) {
		super(name, order, key, from, width, bufferFrom, bufferLen);
	}

	@Override
	public Object getObjectValue() {
		return readInt();
	}

	@Override
	public int readInt() {
		return Bytes.readInt(key, from+bufferFrom);
	}

	@Override
	public long readLong() {
		return readInt();
	}

	@Override
	public double readDouble() {
		return readInt();
	}

	@Override
	public String readString() {
		return String.valueOf(readInt());
	}

	public void write(Number n) {
		write(n.intValue());
	}

	@Override
	public void write(String colValue) {
		write(Integer.parseInt(colValue));
	}

	@Override
	public void write(long l) {
		write((int) l);
	}

	@Override
	public void write(double l) {
		write((int) l);
	}

}
