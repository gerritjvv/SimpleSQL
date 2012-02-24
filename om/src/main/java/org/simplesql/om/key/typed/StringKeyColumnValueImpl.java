package org.simplesql.om.key.typed;

import org.simplesql.om.key.KeyColumnValueImpl;
import org.simplesql.util.Bytes;

/**
 * 
 * Presents a Long value
 */
public class StringKeyColumnValueImpl extends KeyColumnValueImpl {

	public StringKeyColumnValueImpl(String name, int order, byte[] key,
			int from, int width, int bufferFrom, int bufferLen) {
		super(name, order, key, from, width, bufferFrom, bufferLen);
	}

	@Override
	public Object getObjectValue() {
		return readString();
	}

	@Override
	public int readInt() {
		return Integer.parseInt(readString());
	}

	@Override
	public long readLong() {
		return Long.parseLong(readString());
	}

	@Override
	public double readDouble() {
		return Double.parseDouble(readString());
	}

	@Override
	public String readString() {
		return Bytes.readString(key, from + bufferFrom, len);
	}

	@Override
	public void write(int i) {
		write(String.valueOf(i));
	}

	@Override
	public void write(long l) {
		write(String.valueOf(l));
	}

	@Override
	public void write(double l) {
		write(String.valueOf(l));
	}

}
