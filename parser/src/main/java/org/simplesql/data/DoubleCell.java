package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

public final class DoubleCell implements Cell<Number> {

	double val = 0.0D;

	String name;

	public DoubleCell() {

	}

	public DoubleCell(double val) {
		super();
		this.val = val;
	}

	public DoubleCell(double val, String name) {
		super();
		this.val = val;
		this.name = name;
	}
	

	public void readFields(DataInput in) throws IOException {
		val = in.readDouble();
	}

	public void write(DataOutput out) throws IOException {
		out.writeDouble(val);
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
			val = 0L;
		val = dat.doubleValue();
	}

	@Override
	public void inc(Counter counter) {
		val += counter.getDoubleValue();
	}

	@Override
	public Cell<Number> copy(boolean resetToDefaults) {
		return new DoubleCell((resetToDefaults) ? 0.0D : val);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return Double.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return Double.MIN_VALUE;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeDouble(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		val = in.readDouble();
	}

	@Override
	public int compareTo(Cell<Number> cell) {
		double d = cell.getDoubleValue();
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
		return 8;
	}

	@Override
	public int write(byte[] arr, int from) {
		Bytes.writeBytes(val, arr, from);
		return 8;
	}

	@Override
	public int read(byte[] arr, int from) {
		val = Bytes.readDouble(arr, from);
		return 8;
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.DOUBLE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(val);
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
		DoubleCell other = (DoubleCell) obj;
		if (Double.doubleToLongBits(val) != Double.doubleToLongBits(other.val))
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
