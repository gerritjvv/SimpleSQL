package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.util.Bytes;

public final class FloatCell implements Cell<Number> {

	float val = 0.0F;

	String name;

	public FloatCell() {

	}

	public FloatCell(float val) {
		super();
		this.val = val;
	}

	public FloatCell(float val, String name) {
		super();
		this.val = val;
		this.name = name;
	}

	public void readFields(DataInput in) throws IOException {
		val = in.readFloat();
	}

	public void write(DataOutput out) throws IOException {
		out.writeFloat(val);
	}

	@Override
	public void inc() {
		val++;
	}

	@Override
	public void inc(int val) {
		this.val += val;
	}

	@Override
	public void inc(long val) {
		this.val += val;
	}

	@Override
	public void inc(float val) {
		this.val += val;
	}

	@Override
	public float getFloatValue() {
		return val;
	}

	@Override
	public long getLongValue() {
		return (long) val;
	}

	@Override
	public int getIntValue() {
		return (int) val;
	}

	@Override
	public Number getData() {
		return val;
	}

	@Override
	public void setData(Number dat) {
		val = (dat == null) ? 0F : dat.floatValue();
	}

	@Override
	public void inc(Counter counter) {
		val += counter.getFloatValue();
	}

	@Override
	public Cell<Number> copy(boolean resetToDefaults) {
		return new FloatCell((resetToDefaults) ? 0.0F : val);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return Float.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return Float.MIN_VALUE;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeFloat(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		val = in.readFloat();
	}

	@Override
	public int compareTo(Cell<Number> cell) {
		float d = cell.getFloatValue();
		if (val < d)
			return -1;
		else if (val > d)
			return 1;
		else
			return 0;
	}

	@Override
	public final void putHash(HashCodeBuilder builder) {
		builder.append(val);
	}

	@Override
	public int byteLength() {
		return 4;
	}

	@Override
	public int write(byte[] arr, int from) {
		Bytes.writeBytes(Float.floatToIntBits(val), arr, from);
		return 4;
	}

	@Override
	public int read(byte[] arr, int from) {
		val = Float.intBitsToFloat(Bytes.readInt(arr, from));
		return 4;
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.FLOAT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Float.floatToIntBits(val);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		FloatCell other = (FloatCell) obj;
		if (Float.floatToIntBits(val) != Float.floatToIntBits(other.val))
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
	public void inc(double val) {
		this.val += val;
	}

	@Override
	public void inc(short val) {
		this.val += val;
	}

	@Override
	public void inc(byte val) {
		this.val += val;
	}

	@Override
	public byte getByteValue() {
		return (byte) val;
	}

	@Override
	public double getDoubleValue() {
		return (double) val;
	}

	@Override
	public short getShortValue() {
		return (short) val;
	}

	@Override
	public int getDefinedWidth() {
		return 4;
	}

	@Override
	public Class<?> getJavaType() {
		return float.class;
	}

}
