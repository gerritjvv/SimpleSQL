package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.util.Bytes;

public final class LongCell implements Cell<Number> {

	long val = 0L;

	String name;

	public LongCell() {
	}

	public LongCell(long val) {
		super();
		this.val = val;
	}

	public LongCell(long val, String name) {
		super();
		this.val = val;
		this.name = name;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		val = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(val);
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
	public void inc(double val) {
		this.val += val;
	}

	@Override
	public double getDoubleValue() {
		return val;
	}

	@Override
	public long getLongValue() {
		return val;
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
			val = 0L;
		val = dat.longValue();
	}

	@Override
	public void inc(Counter counter) {
		val += counter.getLongValue();
	}

	@Override
	public Cell<Number> copy(boolean resetToDefaults) {
		return new LongCell((resetToDefaults) ? 0L : val);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return Long.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return Long.MIN_VALUE;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeLong(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		val = in.readLong();
	}

	@Override
	public int compareTo(Cell<Number> cell) {
		long v = cell.getLongValue();
		if (val < v)
			return -1;
		else if (val > v)
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
		return 8;
	}

	@Override
	public int write(byte[] arr, int from) {
		Bytes.writeBytes(val, arr, from);
		return 8;
	}

	@Override
	public int read(byte[] arr, int from) {
		val = Bytes.readLong(arr, from);
		return 8;
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.LONG;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (val ^ (val >>> 32));
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
		LongCell other = (LongCell) obj;
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
	public void inc(float val) {
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
	public float getFloatValue() {
		return (float)val;
	}

	@Override
	public short getShortValue() {
		return (short)val;
	}

	@Override
	public int getDefinedWidth() {
		return 8;
	}

	@Override
	public Class<?> getJavaType() {
		return long.class;
	}

}
