package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * Accepts as argument an Object.<br/>
 * The value is incremented and changed depending on the type.<br/>
 * E.g. if the value is an Integer and incremented with a double value, then the
 * value will change to type double.<br/>
 * 
 */
public class DynamicCell implements Cell<String> {

	Object val;

	public DynamicCell() {

	}

	public DynamicCell(Object val) {
		super();
		this.val = (val == null) ? "" : val;
	}

	@Override
	public void inc() {
		inc(1);
	}

	@Override
	public void inc(int val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else
			this.val = ((Integer) this.val) + val;

	}

	@Override
	public void inc(long val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Long) this.val) + val;
		else
			this.val = new Long(((Integer) this.val) + val); // here we need to
																// change the
																// type to Long
	}

	@Override
	public void inc(double val) {
		if (Double.class.isAssignableFrom(this.val.getClass()))
			this.val = ((Double) this.val) + val;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			this.val = new Double(((Long) this.val) + val); // change type to
															// Double
		else
			this.val = new Double(((Integer) this.val) + val); // here we need
																// to change the
																// type to
																// Double
	}

	@Override
	public double getDoubleValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.doubleValue() : 0D;
	}

	@Override
	public long getLongValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.longValue() : 0L;
	}

	@Override
	public int getIntValue() {
		return (Number.class.isAssignableFrom(val.getClass())) ? ((Number) val)
				.intValue() : 0;
	}

	@Override
	public String getData() {
		return val.toString();
	}

	@Override
	public void setData(String dat) {
		this.val = dat;
	}

	@Override
	public void inc(Counter counter) {
		if (Double.class.isAssignableFrom(val.getClass())) {
			inc(counter.getDoubleValue());
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			inc(counter.getLongValue());
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			inc(counter.getIntValue());
		}
	}

	@Override
	public Cell<String> copy(boolean resetToDefaults) {

		if (Double.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Double) val).doubleValue();
			return new DynamicCell((resetToDefaults) ? 0D : copy);
		} else if (Long.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Long) val).longValue();
			return new DynamicCell((resetToDefaults) ? 0L : copy);
		} else if (Integer.class.isAssignableFrom(val.getClass())) {
			Object copy = ((Integer) val).intValue();
			return new DynamicCell((resetToDefaults) ? 0 : copy);
		} else {
			Object copy = val;
			return new DynamicCell((resetToDefaults) ? "" : copy);
		}

	}

	public String toString() {
		return val.toString();
	}

	@Override
	public Object getMax() {

		Object max = null;
		if (Double.class.isAssignableFrom(this.val.getClass()))
			max = Double.MAX_VALUE;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			max = Long.MAX_VALUE;
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			max = Integer.MAX_VALUE;
		else {
			max = (char) 128;
		}

		return max;
	}

	@Override
	public Object getMin() {
		Object min = null;
		if (Double.class.isAssignableFrom(this.val.getClass()))
			min = Double.MIN_VALUE;
		else if (Long.class.isAssignableFrom(this.val.getClass()))
			min = Long.MIN_VALUE;
		else if (Integer.class.isAssignableFrom(this.val.getClass()))
			min = Integer.MIN_VALUE;
		else {
			min = (char) 0;
		}

		return min;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		try {
			val = in.readObject();
		} catch (ClassNotFoundException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}
}
