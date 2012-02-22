package org.simplesql.om.key;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * Represents a KeyColumn from a byte array. Is used to edit, truncate and
 * retrieve a key.
 * 
 */
public abstract class KeyColumnValue {

	public static final KeyColumnValueOrderComparator ORDER_COMPARATOR = new KeyColumnValueOrderComparator();

	protected byte[] key;
	protected int bufferFrom, bufferLen;

	protected final int from, len;
	protected final int order;

	protected final String name;

	public KeyColumnValue(String name, int order, byte[] key, int from,
			int len, int bufferFrom, int bufferLen) {
		super();
		this.name = name;
		this.order = order;
		this.key = key;
		this.bufferFrom = bufferFrom;
		this.bufferLen = bufferLen;
		this.from = from;
		this.len = len;
	}

	public String getName() {
		return name;
	}

	public void setKey(byte[] key, int from, int len) {
		this.key = key;
		this.bufferFrom = from;
		this.bufferLen = len;
	}

	public void fill(byte b) {
		int start = bufferFrom + from;
		Arrays.fill(key, start, start + len, b);
	}

	public int getOrder() {
		return order;
	}

	public int getFrom() {
		return from;
	}

	public int getLen() {
		return len;
	}

	public int getBufferFrom() {
		return bufferFrom;
	}

	public int getBufferLen() {
		return bufferLen;
	}

	public abstract void setByteValue(byte[] value, int from);

	public abstract byte[] readByteValue();

	public abstract Object getObjectValue();

	public abstract int readInt();

	public abstract long readLong();

	public abstract double readDouble();

	public abstract String readString();

	public abstract void write(long n);

	public abstract void write(double n);

	public abstract void write(int n);

	public void write(Object n) {

		if (n instanceof Number)
			write((Number) n);
		else if (n instanceof Boolean)
			write( ((Boolean) n).booleanValue() );
		else
			write(n.toString());

	}

	public abstract void write(Number n);

	/**
	 * Should convert the String value to the correct typed value before
	 * converting to bytes.
	 * 
	 * @param colValue
	 */
	public abstract void write(String colValue);

	public abstract void write(boolean bool);

	public boolean readBoolean() {
		return false;
	}

	public static final class KeyColumnValueOrderComparator implements
			Comparator<KeyColumnValue> {

		@Override
		public int compare(KeyColumnValue k1, KeyColumnValue k2) {
			final int o1 = k1.order;
			final int o2 = k2.order;

			if (o1 < o2)
				return -1;
			else if (o2 > o1)
				return 1;
			else
				return 0;
		}

	}

}
