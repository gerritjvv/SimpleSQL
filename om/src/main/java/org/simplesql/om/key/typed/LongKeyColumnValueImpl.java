package org.simplesql.om.key.typed;

import org.simplesql.om.key.KeyColumnValueImpl;
import org.simplesql.om.util.Bytes;

/**
 * 
 * Presents a Long value
 */
public class LongKeyColumnValueImpl extends KeyColumnValueImpl {

	public LongKeyColumnValueImpl(String name, int order, byte[] key, int from,
			int width, int bufferFrom, int bufferLen) {
		super(name, order, key, from, width, bufferFrom, bufferLen);
	}

	@Override
	public Object getObjectValue() {
		return readLong();
	}

	@Override
	public int readInt() {
		return (int) readLong();
	}

	@Override
	public long readLong() {
		return Bytes.readLong(key, from + bufferFrom);
	}

	@Override
	public double readDouble() {
		return (double) readLong();
	}

	@Override
	public String readString() {
		return String.valueOf(readLong());
	}

	@Override
	public void write(int i) {
		write((long) i);
	}

	@Override
	public void write(double l) {
		write((long) l);
	}

	public void write(Number n) {
		write(n.longValue());
	}

	@Override
	public void write(String colValue) {
		write(Long.parseLong(colValue));
	}

}
