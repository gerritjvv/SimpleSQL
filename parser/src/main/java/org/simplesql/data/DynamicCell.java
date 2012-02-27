package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

/**
 * 
 * Accepts as argument an Object.<br/>
 * The value is incremented and changed depending on the type.<br/>
 * E.g. if the value is an Integer and incremented with a double value, then the
 * value will change to type double.<br/>
 * 
 */
public class DynamicCell implements Cell<String> {

	enum TYPE {
		INT((byte) 0), LONG((byte) 1), DOUBLE((byte) 2), BOOLEAN((byte) 3), STRING(
				(byte) 4);

		byte id;

		TYPE(byte b) {
			this.id = b;
		}

	}

	Object val;

	public DynamicCell() {

	}

	public DynamicCell(Object val) {
		super();
		this.val = (val == null) ? "" : val;
	}

	@Override
	public void inc() {
		inc(1);
	}

	@Override
	public void inc(int val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else
			this.val = ((Integer) this.val) + val;

	}

	@Override
	public void inc(long val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else
			this.val = new Long(((Integer) this.val) + val); // here we need to
																// change the
																// type to Long
	}

	@Override
	public void inc(double val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = new Double(((Long) this.val) + val); // change type to
															// Double
		else
			this.val = new Double(((Integer) this.val) + val); // here we need
																// to change the
																// type to
																// Double
	}

	@Override
	public double getDoubleValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.doubleValue() : 0D;
	}

	@Override
	public long getLongValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.longValue() : 0L;
	}

	public boolean getBooleanValue() {
		return ((Boolean) val).booleanValue();
	}

	@Override
	public int getIntValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.intValue() : 0;
	}

	@Override
	public String getData() {
		return val.toString();
	}

	@Override
	public void setData(String dat) {
		this.val = dat;
	}

	@Override
	public void inc(Counter counter) {
		if (Double.class.isAssignableFrom(val.getClass())) {
			inc(counter.getDoubleValue());
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			inc(counter.getLongValue());
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			inc(counter.getIntValue());
		}
	}

	@Override
	public Cell<String> copy(boolean resetToDefaults) {

		if (Double.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Double) val).doubleValue();
			return new DynamicCell((resetToDefaults) ? 0D : copy);
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Long) val).longValue();
			return new DynamicCell((resetToDefaults) ? 0L : copy);
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Integer) val).intValue();
			return new DynamicCell((resetToDefaults) ? 0 : copy);
		} else if (Boolean.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Boolean) val).booleanValue();
			return new DynamicCell((resetToDefaults) ? false : copy);
		} else {
			Object copy = val;
			return new DynamicCell((resetToDefaults) ? "" : copy);
		}

	}

	public String toString() {
		return val.toString();
	}

	@Override
	public Object getMax() {

		Object max = null;
		if (Double.class.isAssignableFrom(this.val.getClass()))
			max = Double.MAX_VALUE;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			max = Long.MAX_VALUE;
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			max = Integer.MAX_VALUE;
		else if (Boolean.class.isAssignableFrom(this.val.getClass()))
			max = Integer.MAX_VALUE;
		else {
			max = (char) 128;
		}

		return max;
	}

	@Override
	public Object getMin() {
		Object min = null;
		if (Double.class.isAssignableFrom(this.val.getClass()))
			min = Double.MIN_VALUE;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			min = Long.MIN_VALUE;
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			min = Integer.MIN_VALUE;
		else if (Boolean.class.isAssignableFrom(this.val.getClass()))
			min = Integer.MIN_VALUE;
		else {
			min = (char) 0;
		}

		return min;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		try {
			val = in.readObject();
		} catch (ClassNotFoundException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

	@Override
	public int compareTo(Cell<String> cell) {
		String v = cell.getData();
		if (val == null || v == null)
			return 0;
		else {
			return val.toString().compareTo(v);
		}
	}

	@Override
	public Hasher putHash(Hasher hasher) {
		if (Double.class.isAssignableFrom(val.getClass())) {
			return hasher.putDouble(getDoubleValue());
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			return hasher.putLong(getLongValue());
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			return hasher.putInt(getIntValue());
		} else if (Boolean.class.isAssignableFrom(val.getClass())) {
			return hasher.putBoolean(getIntValue() == 1);
		} else {
			return hasher.putString(getData());
		}
	}

	@Override
	public int byteLength() {
		if (Double.class.isAssignableFrom(val.getClass())) {
			return 9;
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			return 9;
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			return 5;
		} else if (Boolean.class.isAssignableFrom(val.getClass())) {
			return 2;
		} else {
			try {
				return getData().getBytes("UTF-8").length + 5;
			} catch (UnsupportedEncodingException e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			}
		}
	}

	@Override
	public int write(byte[] arr, int from) {
		if (Double.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.DOUBLE.id;
			Bytes.writeBytes(getDoubleValue(), arr, from + 1);
			return 9;
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.LONG.id;
			Bytes.writeBytes(getLongValue(), arr, from + 1);
			return 9;
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.INT.id;
			Bytes.writeBytes(getIntValue(), arr, from + 1);
			return 5;
		} else if (Boolean.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.BOOLEAN.id;
			Bytes.writeBytes(getBooleanValue(), arr, from + 1);
			return 2;
		} else {
			try {
				byte[] strbytes = getData().getBytes("UTF-8");
				arr[from] = TYPE.STRING.id;
				Bytes.writeBytes(strbytes.length, arr, from + 1);
				System.arraycopy(strbytes, 0, arr, from + 5, strbytes.length);
				return strbytes.length + 5;
			} catch (UnsupportedEncodingException e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			}
		}
	}

	@Override
	public int read(byte[] arr, int from) {
		byte type = arr[from];

		if (type == TYPE.DOUBLE.id) {
			val = Bytes.readDouble(arr, from + 1);
			return 9;
		} else if (type == TYPE.LONG.id) {
			val = Bytes.readLong(arr, from + 1);
			return 9;
		} else if (type == TYPE.INT.id) {
			val = Bytes.readInt(arr, from + 1);
			return 5;
		} else if (type == TYPE.BOOLEAN.id) {
			val = Bytes.readBoolean(arr, from + 1);
			return 2;
		} else if (type == TYPE.STRING.id) {
			try {
				int len = Bytes.readInt(arr, from + 1);
				byte[] strarr = new byte[len];
				System.arraycopy(arr, from + 5, strarr, 0, len);
				val = new String(strarr, "UTF-8");
				return strarr.length + 5;
			} catch (UnsupportedEncodingException e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			}
		} else {
			throw new RuntimeException("Unsupported Type: " + type);
		}
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.DYNAMIC;
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
		DynamicCell other = (DynamicCell) obj;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}

	
}
