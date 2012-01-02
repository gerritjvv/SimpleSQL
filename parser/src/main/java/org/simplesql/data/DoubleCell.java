package org.simplesql.data;

public class DoubleCell implements Cell<Number> {

	double val = 0.0D;

	public DoubleCell() {

	}

	public DoubleCell(double val) {
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
		return new DoubleCell((resetToDefaults)? 0.0D : val);
	}

	public String toString(){
		return String.valueOf(val);
	}
	
}
