package org.simplesql.om.key.typed;

import org.simplesql.om.key.KeyColumnValueImpl;

/**
 * 
 * Presents a Boolean value
 */
public class BooleanKeyColumnValueImpl extends KeyColumnValueImpl {

	public BooleanKeyColumnValueImpl(String name, int order, byte[] key,
			int from, int width, int bufferFrom, int bufferLen) {
		super(name, order, key, from, width, bufferFrom, bufferLen);
	}

	@Override
	public Object getObjectValue() {
		return readBoolean();
	}

	@Override
	public int readInt() {
		return (readBoolean()) ? 1 : 0;
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
		return String.valueOf(readBoolean());
	}

	public void write(Number n) {
		write(n.intValue() == 1);
	}

	@Override
	public void write(String colValue) {
		write(Boolean.parseBoolean(colValue));
	}

	@Override
	public void write(long l) {
		write((int) l);
	}

	@Override
	public void write(double l) {
		write((int) l);
	}

	@Override
	public void write(int i) {
		write((i == 1));
	}

}
