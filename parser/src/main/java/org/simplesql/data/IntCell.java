package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IntCell implements Cell<Number> {

	int val = 0;

	public IntCell() {
	}

	public IntCell(int val) {
		super();
		this.val = val;
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

}
