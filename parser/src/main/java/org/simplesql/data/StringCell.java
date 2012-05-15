package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.util.Bytes;

/**
 * 
 * Store a String. All increment methods are ignored or zero is returned.
 * 
 */
public final class StringCell implements Cell<String> {

	String val;
	String name;

	public StringCell() {

	}

	public StringCell(String val) {
		super();
		this.val = val;
	}

	public StringCell(String val, String name) {
		super();
		this.val = val;
		this.name = name;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		val = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF((val == null) ? "" : val);
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
	public final void putHash(HashCodeBuilder builder) {
		builder.append(val);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringCell other = (StringCell) obj;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void inc(float val) {
	}

	@Override
	public void inc(short val) {
	}

	@Override
	public void inc(byte val) {
	}

	@Override
	public byte getByteValue() {
		return 0;
	}

	@Override
	public float getFloatValue() {
		return 0;
	}

	@Override
	public short getShortValue() {
		return 0;
	}

}
