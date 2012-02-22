package org.simplesql.om.key.typed;

import org.simplesql.om.key.KeyColumnValueImpl;
import org.simplesql.om.util.Bytes;

/**
 * 
 * Presents a Double value
 */
public class DoubleKeyColumnValueImpl extends KeyColumnValueImpl {

	public DoubleKeyColumnValueImpl(String name, int order, byte[] key,
			int from, int width, int bufferFrom, int bufferLen) {
		super(name, order, key, from, width, bufferFrom, bufferLen);
	}

	@Override
	public Object getObjectValue() {
		return readDouble();
	}

	@Override
	public int readInt() {
		return (int) readDouble();
	}

	@Override
	public long readLong() {
		return (long) readDouble();
	}

	@Override
	public double readDouble() {
		return Bytes.readDouble(key, from + bufferFrom);
	}

	@Override
	public String readString() {
		return String.valueOf(readDouble());
	}

	public void write(Number n) {
		write(n.doubleValue());
	}

	@Override
	public void write(String colValue) {
		write(Double.valueOf(colValue));
	}

	@Override
	public void write(int i) {
		write((double) i);
	}

	@Override
	public void write(long l) {
		write((double) l);
	}

}
