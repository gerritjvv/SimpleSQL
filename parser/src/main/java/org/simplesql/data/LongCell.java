package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LongCell implements Cell<Number> {

	long val = 0L;

	public LongCell() {
	}

	public LongCell(long val) {
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

}
