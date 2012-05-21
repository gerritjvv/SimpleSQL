package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

public final class ShortCell implements Cell<Number> {

	short val = 0;

	String name;

	public ShortCell() {

	}

	public ShortCell(short val) {
		super();
		this.val = val;
	}

	public ShortCell(short val, String name) {
		super();
		this.val = val;
		this.name = name;
	}
	

	public void readFields(DataInput in) throws IOException {
		val = in.readShort();
	}

	public void write(DataOutput out) throws IOException {
		out.writeShort(val);
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
		if (dat == null)
			val = 0;
		val = dat.shortValue();
	}

	@Override
	public void inc(Counter counter) {
		val += counter.getShortValue();
	}

	@Override
	public Cell<Number> copy(boolean resetToDefaults) {
		return new ShortCell((resetToDefaults) ? 0 : val);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return Short.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return Short.MIN_VALUE;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeShort(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		val = in.readShort();
	}

	@Override
	public int compareTo(Cell<Number> cell) {
		float d = cell.getShortValue();
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
		Bytes.writeBytes(val, arr, from);
		return 4;
	}

	@Override
	public int read(byte[] arr, int from) {
		val = (short)Bytes.readInt(arr, from);
		return 4;
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.SHORT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = val;
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
		ShortCell other = (ShortCell) obj;
		if (val != other.val)
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
		return (byte)val;
	}

	@Override
	public double getDoubleValue() {
		return (double)val;
	}

	@Override
	public short getShortValue() {
		return val;
	}

	@Override
	public int getDefinedWidth() {
		return 2;
	}

	@Override
	public Class<?> getJavaType() {
		return short.class;
	}

}
