package org.simplesql.om.key;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.simplesql.util.Bytes;

/**
 * 
 * 
 *
 */
public class KeyColumnValueImpl extends KeyColumnValue {

	public KeyColumnValueImpl(String name, int order, byte[] key, int from,
			int width, int bufferFrom, int bufferLen) {
		super(name, order, key, from, width, bufferFrom, bufferLen);
	}

	@Override
	public void setByteValue(byte[] value, int srcFrom) {
		// automatically trim the value if the value length is larger than
		// allowed
		// for the column
		int vlen = value.length;
		if (vlen < len) {
			System.arraycopy(value, srcFrom, key, from + bufferFrom, vlen);
		} else {
			System.arraycopy(value, srcFrom, key, from + bufferFrom, len);
		}
	}

	public byte[] getBuffer(){
		return key;
	}
	
	@Override
	public byte[] readByteValue() {
		return Arrays.copyOfRange(key, from + bufferFrom, from + len);
	}

	/**
	 * Returns a String value. Use implementations that are more type aware to
	 * get the exact typed data.
	 */
	public Object getObjectValue() {
		try {
			return new String(readByteValue(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean readBoolean(){
		return Bytes.readBoolean(key, from + bufferFrom);
	}
	
	@Override
	public int readInt() {
		return Bytes.readInt(key, from + bufferFrom);
	}

	@Override
	public long readLong() {
		return Bytes.readLong(key, from + bufferFrom);
	}

	@Override
	public double readDouble() {
		return Bytes.readDouble(key, from + bufferFrom);
	}

	@Override
	public String readString() {
		return Bytes.readString(key, from + bufferFrom, len);
	}

	public void write(int i) {
		Bytes.writeBytes(i, key, from + bufferFrom);
	}

	public void write(long l) {
		Bytes.writeBytes(l, key, from + bufferFrom);
	}
	
	public void write(boolean b){
		Bytes.writeBytes(b, key, from + bufferFrom);
	}
	
	public void write(double l) {
		Bytes.writeBytes(l, key, from + bufferFrom);
	}

	@Override
	public void write(String colValue) {
		Bytes.writeBytes(colValue, key, from + bufferFrom, len);
	}

	@Override
	public void write(Number n) {
		write(n.doubleValue());
	}

}
