package org.simplesql.data;

public class BooleanCell implements Cell<Number> {

	boolean val = false;

	public BooleanCell() {
	}

	public BooleanCell(boolean val) {
		super();
		this.val = val;
	}

	@Override
	public void inc() {
		val = !val;
	}

	@Override
	public void inc(int n) {
		inc();
	}

	@Override
	public void inc(long val) {
		inc();
	}

	@Override
	public void inc(double val) {
		inc();
	}

	@Override
	public double getDoubleValue() {
		return (val) ? 1 : 0;
	}

	@Override
	public long getLongValue() {
		return (val) ? 1 : 0;
	}

	@Override
	public int getIntValue() {
		return (val) ? 1 : 0;
	}

	@Override
	public Number getData() {
		return getIntValue();
	}

	@Override
	public void setData(Number dat) {
		if (dat == null)
			val = false;
		val = (dat.intValue() == 1);
	}

	@Override
	public void inc(Counter counter) {
		inc();
	}

	@Override
	public Cell<Number> copy(boolean resetToDefaults) {
		return new BooleanCell((resetToDefaults) ? false : true);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return true;
	}

	@Override
	public Object getMin() {
		return false;
	}

}
