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
 * Accepts as argument an Object.<br/>
 * The value is incremented and changed depending on the type.<br/>
 * E.g. if the value is an Integer and incremented with a double value, then the
 * value will change to type double.<br/>
 * 
 */
public final class DynamicCell implements Cell<String> {

	enum TYPE {
		INT((byte) 0), LONG((byte) 1), DOUBLE((byte) 2), BOOLEAN((byte) 3), STRING(
				(byte) 4), FLOAT((byte) 5), SHORT((byte) 6), BYTE((byte) 7);

		byte id;

		TYPE(byte b) {
			this.id = b;
		}

	}

	String name;
	Object val;

	public DynamicCell() {

	}

	public DynamicCell(Object val) {
		super();
		this.val = (val == null) ? "" : val;
	}

	public DynamicCell(Object val, String name) {
		super();
		this.val = (val == null) ? "" : val;
		this.name = name;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		val = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF((val == null) ? "" : val.toString());
	}

	@Override
	public void inc() {
		inc(1);
	}

	@Override
	public void inc(int val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Integer) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else if (Float.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else if (Short.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Short) this.val) + val;
		else if (Byte.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Byte) this.val) + val;
		else
			this.val = ((Integer) this.val) + val;

	}

	@Override
	public void inc(long val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Integer) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else if (Float.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else if (Short.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Short) this.val) + val;
		else if (Byte.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Byte) this.val) + val;
		else
			this.val = Long.valueOf(((Integer) this.val) + val); // here we need
																	// to
																	// type to
																	// Long
	}

	@Override
	public void inc(double val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf(((Double) this.val).doubleValue() + val);
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			this.val = Integer
					.valueOf((int) (((Integer) this.val).intValue() + val));
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf(((Double) this.val).doubleValue() + val); // change
																				// type
																				// to
		else if (Float.class.isAssignableFrom(this.val.getClass()))
			this.val = new Float(((Float) this.val).floatValue() + val);
		else if (Short.class.isAssignableFrom(this.val.getClass()))
			this.val = Short
					.valueOf((short) (((Short) this.val).shortValue() + val));
		else if (Byte.class.isAssignableFrom(this.val.getClass()))
			this.val = Byte
					.valueOf((byte) (((Byte) this.val).byteValue() + val));
		else
			this.val = Double.valueOf(((Integer) this.val) + val); // here we
																	// need
																	// to change
																	// the
																	// type to
																	// Double
	}

	@Override
	public void inc(float val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf(((Double) this.val).doubleValue() + val);
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			this.val = Integer
					.valueOf((int) (((Integer) this.val).intValue() + val));
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf(((Double) this.val).doubleValue() + val); // change
																				// type
																				// to
		else if (Float.class.isAssignableFrom(this.val.getClass()))
			this.val = new Float(((Float) this.val).floatValue() + val);
		else if (Short.class.isAssignableFrom(this.val.getClass()))
			this.val = Short
					.valueOf((short) (((Short) this.val).shortValue() + val));
		else if (Byte.class.isAssignableFrom(this.val.getClass()))
			this.val = Byte
					.valueOf((byte) (((Byte) this.val).byteValue() + val));
		else
			this.val = Double.valueOf(((Integer) this.val) + val); // here we
																	// need
																	// to change
																	// the
																	// type to
																	// Double
	}

	@Override
	public void inc(short val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf(((Double) this.val).doubleValue() + val);
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			this.val = Integer
					.valueOf((int) (((Integer) this.val).intValue() + val));
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf(((Double) this.val).doubleValue() + val); // change
																				// type
																				// to
		else if (Float.class.isAssignableFrom(this.val.getClass()))
			this.val = new Float(((Float) this.val).floatValue() + val);
		else if (Short.class.isAssignableFrom(this.val.getClass()))
			this.val = Short
					.valueOf((short) (((Short) this.val).shortValue() + val));
		else if (Byte.class.isAssignableFrom(this.val.getClass()))
			this.val = Byte
					.valueOf((byte) (((Byte) this.val).byteValue() + val));
		else
			this.val = Double.valueOf(((Integer) this.val) + val); // here we
																	// need
																	// to change
																	// the
																	// type to
																	// Double
	}

	@Override
	public void inc(byte val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = Double.valueOf((double) ((Double) this.val)
					.doubleValue() + val);
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = Long
					.valueOf((long) (((Long) this.val).longValue() + val)); // change
																			// type
																			// to
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			this.val = Integer.valueOf((int) (((Integer) this.val)).intValue()
					+ val); // Double
		else if (Float.class.isAssignableFrom(this.val.getClass()))
			this.val = new Float(
					(float) (((Float) this.val).floatValue() + val));
		else if (Short.class.isAssignableFrom(this.val.getClass()))
			this.val = Short
					.valueOf((short) (((Short) this.val).shortValue() + val));
		else if (Byte.class.isAssignableFrom(this.val.getClass()))
			this.val = Byte
					.valueOf((byte) (((Byte) this.val).byteValue() + val));
		else
			this.val = Integer.valueOf(((Integer) this.val).intValue() + val);
	}

	@Override
	public byte getByteValue() {
		return (byte) ((Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.intValue() : 0);
	}

	@Override
	public float getFloatValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.floatValue() : 0F;
	}

	@Override
	public short getShortValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.shortValue() : (short) 0;
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
		if (Double.class.isAssignableFrom(val.getClass())
				|| double.class.isAssignableFrom(val.getClass())) {
			inc(counter.getDoubleValue());
		} else if (Long.class.isAssignableFrom(val.getClass())
				|| long.class.isAssignableFrom(val.getClass())) {
			inc(counter.getLongValue());
		} else if (Integer.class.isAssignableFrom(val.getClass())
				|| int.class.isAssignableFrom(val.getClass())) {
			inc(counter.getIntValue());
		} else if (Float.class.isAssignableFrom(val.getClass())
				|| float.class.isAssignableFrom(val.getClass())) {
			inc(counter.getFloatValue());
		} else if (Short.class.isAssignableFrom(val.getClass())
				|| short.class.isAssignableFrom(val.getClass())) {
			inc(counter.getShortValue());
		} else if (Byte.class.isAssignableFrom(val.getClass())
				|| byte.class.isAssignableFrom(val.getClass())) {
			inc(counter.getByteValue());
		}
	}

	@Override
	public Cell<String> copy(boolean resetToDefaults) {

		if (Double.class.isAssignableFrom(val.getClass())
				|| double.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Double) val).doubleValue();
			return new DynamicCell((resetToDefaults) ? 0D : copy);
		} else if (Long.class.isAssignableFrom(val.getClass())
				|| long.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Long) val).longValue();
			return new DynamicCell((resetToDefaults) ? 0L : copy);
		} else if (Integer.class.isAssignableFrom(val.getClass())
				|| int.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Integer) val).intValue();
			return new DynamicCell((resetToDefaults) ? 0 : copy);
		} else if (Boolean.class.isAssignableFrom(val.getClass())
				|| boolean.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Boolean) val).booleanValue();
			return new DynamicCell((resetToDefaults) ? false : copy);
		} else if (Float.class.isAssignableFrom(val.getClass())
				|| float.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Float) val).floatValue();
			return new DynamicCell((resetToDefaults) ? false : copy);
		} else if (Short.class.isAssignableFrom(val.getClass())
				|| short.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Short) val).shortValue();
			return new DynamicCell((resetToDefaults) ? false : copy);
		} else if (Byte.class.isAssignableFrom(val.getClass())
				|| byte.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Byte) val).byteValue();
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
		if (Double.class.isAssignableFrom(this.val.getClass())
				|| double.class.isAssignableFrom(val.getClass()))
			max = Double.MAX_VALUE;
		else if (Long.class.isAssignableFrom(this.val.getClass())
				|| long.class.isAssignableFrom(val.getClass()))
			max = Long.MAX_VALUE;
		else if (Integer.class.isAssignableFrom(this.val.getClass())
				|| int.class.isAssignableFrom(val.getClass()))
			max = Integer.MAX_VALUE;
		else if (Boolean.class.isAssignableFrom(this.val.getClass())
				|| boolean.class.isAssignableFrom(val.getClass()))
			max = Integer.MAX_VALUE;
		else if (Float.class.isAssignableFrom(this.val.getClass())
				|| float.class.isAssignableFrom(val.getClass()))
			max = Float.MAX_VALUE;
		else if (Short.class.isAssignableFrom(this.val.getClass())
				|| short.class.isAssignableFrom(val.getClass()))
			max = Short.MAX_VALUE;
		else if (Byte.class.isAssignableFrom(this.val.getClass())
				|| byte.class.isAssignableFrom(val.getClass()))
			max = Byte.MAX_VALUE;
		else {
			max = (char) 128;
		}

		return max;
	}

	@Override
	public Object getMin() {
		Object min = null;
		if (Double.class.isAssignableFrom(this.val.getClass())
				|| double.class.isAssignableFrom(val.getClass()))
			min = Double.MIN_VALUE;
		else if (Long.class.isAssignableFrom(this.val.getClass())
				|| long.class.isAssignableFrom(val.getClass()))
			min = Long.MIN_VALUE;
		else if (Integer.class.isAssignableFrom(this.val.getClass())
				|| int.class.isAssignableFrom(val.getClass()))
			min = Integer.MIN_VALUE;
		else if (Boolean.class.isAssignableFrom(this.val.getClass())
				|| boolean.class.isAssignableFrom(val.getClass()))
			min = Integer.MIN_VALUE;
		else if (Float.class.isAssignableFrom(this.val.getClass())
				|| float.class.isAssignableFrom(val.getClass()))
			min = Float.MIN_VALUE;
		else if (Short.class.isAssignableFrom(this.val.getClass())
				|| short.class.isAssignableFrom(val.getClass()))
			min = Short.MIN_VALUE;
		else if (Byte.class.isAssignableFrom(this.val.getClass())
				|| byte.class.isAssignableFrom(val.getClass()))
			min = Byte.MIN_VALUE;
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
	public final void putHash(HashCodeBuilder builder) {
		builder.append(getData());
	}

	@Override
	public int byteLength() {
		if (Double.class.isAssignableFrom(val.getClass())
				|| double.class.isAssignableFrom(val.getClass())) {
			return 9;
		} else if (Long.class.isAssignableFrom(val.getClass())
				|| long.class.isAssignableFrom(val.getClass())) {
			return 9;
		} else if (Integer.class.isAssignableFrom(val.getClass())
				|| int.class.isAssignableFrom(val.getClass())) {
			return 5;
		} else if (Boolean.class.isAssignableFrom(val.getClass())
				|| boolean.class.isAssignableFrom(val.getClass())) {
			return 2;
		} else if (Float.class.isAssignableFrom(val.getClass())
				|| float.class.isAssignableFrom(val.getClass())) {
			return 5;
		} else if (Short.class.isAssignableFrom(val.getClass())
				|| short.class.isAssignableFrom(val.getClass())) {
			return 5;
		} else if (Byte.class.isAssignableFrom(val.getClass())
				|| byte.class.isAssignableFrom(val.getClass())) {
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
		} else if (Float.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.FLOAT.id;
			Bytes.writeBytes(Float.floatToIntBits(getFloatValue()), arr,
					from + 1);
			return 5;
		} else if (Short.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.SHORT.id;
			Bytes.writeBytes((int) getShortValue(), arr, from + 1);
			return 5;
		} else if (Byte.class.isAssignableFrom(val.getClass())) {
			arr[from] = TYPE.BYTE.id;
			Bytes.writeBytes(getByteValue(), arr, from + 1);
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
		} else if (type == TYPE.FLOAT.id) {
			val = Float.intBitsToFloat(Bytes.readInt(arr, from + 1));
			return 2;
		} else if (type == TYPE.SHORT.id) {
			val = (short) Bytes.readInt(arr, from + 1);
			return 2;
		} else if (type == TYPE.BYTE.id) {
			val = arr[from];
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getDefinedWidth() {
		return 0;
	}

	@Override
	public Class<?> getJavaType() {
		return val.getClass();
	}

}
