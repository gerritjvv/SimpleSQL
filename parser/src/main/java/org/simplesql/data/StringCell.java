package org.simplesql.data;

/**
 * 
 * Store a String. All increment methods are ignored or zero is returned.
 * 
 */
public class StringCell implements Cell<String> {

	String val;

	public StringCell() {

	}

	public StringCell(String val) {
		super();
		this.val = val;
	}

	@Override
	public void inc() {
	}

	@Override
	public void inc(int val) {
	}

	@Override
	public void inc(long val) {
	}

	@Override
	public void inc(double val) {
	}

	@Override
	public double getDoubleValue() {
		return 0;
	}

	@Override
	public long getLongValue() {
		return 0;
	}

	@Override
	public int getIntValue() {
		return 0;
	}

	@Override
	public String getData() {
		return val;
	}

	@Override
	public void setData(String dat) {
		this.val = dat;
	}

	@Override
	public void inc(Counter counter) {

	}

	@Override
	public Cell<String> copy(boolean resetToDefaults) {
		return new StringCell((resetToDefaults) ? "" : val);
	}

	public String toString() {
		return val;
	}

	@Override
	public Object getMax() {
		return (char) Integer.MAX_VALUE;
	}

	@Override
	public Object getMin() {
		return (char) 0;
	}

}
