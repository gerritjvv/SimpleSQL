package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

public class IntCell implements Cell<Number> {

	int val = 0;
	String name;

	public IntCell() {
	}

	public IntCell(int val) {
		super();
		this.val = val;
	}

	public IntCell(int val, String name) {
		super();
		this.val = val;
		this.name = name;
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
			val = 0;
		val = dat.intValue();
	}

	@Override
	public void inc(Counter counter) {
		val += counter.getIntValue();
	}

	@Override
	public Cell<Number> copy(boolean resetToDefaults) {
		return new IntCell((resetToDefaults) ? 0 : val);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return Integer.MIN_VALUE;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeInt(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		val = in.readInt();
	}

	public int byteLength() {
		return 4;
	}

	public int write(byte[] arr, int from) {
		Bytes.writeBytes(val, arr, from);
		return 4;
	}

	@Override
	public int read(byte[] arr, int from) {
		val = Bytes.readInt(arr, from);
		return 4;
	}

	@Override
	public int compareTo(Cell<Number> cell) {
		int v = cell.getIntValue();
		if (val < v)
			return -1;
		else if (val > v)
			return 1;
		else
			return 0;
	}

	@Override
	public Hasher putHash(Hasher hasher) {
		return hasher.putInt(val);
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.INT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + val;
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
		IntCell other = (IntCell) obj;
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

}
