package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

/**
 * 
 * Store a String. All increment methods are ignored or zero is returned.
 * 
 */
public class StringCell implements Cell<String> {

	String val;

	public StringCell() {

	}

	public StringCell(String val) {
		super();
		this.val = val;
	}

	@Override
	public void inc() {
	}

	@Override
	public void inc(int val) {
	}

	@Override
	public void inc(long val) {
	}

	@Override
	public void inc(double val) {
	}

	@Override
	public double getDoubleValue() {
		return 0;
	}

	@Override
	public long getLongValue() {
		return 0;
	}

	@Override
	public int getIntValue() {
		return 0;
	}

	@Override
	public String getData() {
		return val;
	}

	@Override
	public void setData(String dat) {
		this.val = dat;
	}

	@Override
	public void inc(Counter counter) {

	}

	@Override
	public Cell<String> copy(boolean resetToDefaults) {
		return new StringCell((resetToDefaults) ? "" : val);
	}

	public String toString() {
		return val;
	}

	@Override
	public Object getMax() {
		return (char) Integer.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return (char) 0;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		if (val == null) {
			out.writeInt(0);
		} else {
			byte[] bts = val.getBytes("UTF-8");
			out.writeInt(bts.length);
			out.write(bts);
		}
	}

	private void readObject(ObjectInputStream in) throws IOException {
		int len = in.readInt();
		if (len > 0) {
			byte[] bts = new byte[len];
			int size = in.read(bts);
			val = new String(bts, 0, size);
		}
	}

	@Override
	public int compareTo(Cell<String> cell) {
		String v = cell.getData();
		if (val == null || v == null)
			return 0;
		else {
			return val.compareTo(v);
		}
	}

	@Override
	public Hasher putHash(Hasher hasher) {
		return hasher.putString(val);
	}

	@Override
	public int byteLength() {
		try {
			return val.getBytes("UTF-8").length + 4;
		} catch (UnsupportedEncodingException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

	}

	@Override
	public int write(byte[] arr, int from) {
		try {

			final byte[] strbytes = val.getBytes("UTF-8");
			int len = strbytes.length;
			Bytes.writeBytes(len, arr, from);

			System.arraycopy(strbytes, 0, arr, from + 4, len);
			return len + 4;

		} catch (UnsupportedEncodingException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

	@Override
	public int read(byte[] arr, int from) {
		try {

			final int len = Bytes.readInt(arr, from);
			final byte[] strbytes = new byte[len];
			System.arraycopy(arr, from + 4, strbytes, 0, len);
			val = new String(strbytes, "UTF-8");
			return len + 4;
		} catch (UnsupportedEncodingException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.STRING;
	}

}
