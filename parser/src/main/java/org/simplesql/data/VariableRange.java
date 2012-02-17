package org.simplesql.data;

/**
 * 
 * Defines the Range of data required for a Variable
 * 
 */
public class VariableRange {

	final boolean numeric;
	Object upper = Integer.MAX_VALUE, lower = Integer.MIN_VALUE;
	final String variable;

	public VariableRange(String variable, boolean numeric) {
		this.variable = variable;
		this.numeric = numeric;
	}

	public VariableRange(String variable, boolean numeric, Object upper,
			Object lower) {
		super();
		this.variable = variable;
		this.numeric = numeric;

		this.upper = upper;
		this.lower = lower;
	}

	public void merge(VariableRange range) {
		if (numeric && range.isNumeric()) {

			Object rUpper = range.getUpper();
			Object rLower = range.getLower();

			addBiggerEq(rLower);
			addSmallerEq(rUpper);

		} else {
			upper = range.getUpper();
			lower = range.getLower();
		}

	}

	public String getVariable() {
		return variable;
	}

	public void setUpper(Object upper) {
		this.upper = upper;
	}

	public void setLower(Object lower) {
		this.lower = lower;
	}

	public boolean isNumeric() {
		return numeric;
	}

	public Object getUpper() {
		return upper;
	}

	public Object getLower() {
		return lower;
	}

	public void addSmallerEq(Object val) {

		if (upper instanceof Double) {
			double dval = ((Number) val).doubleValue();
			if (dval != Double.MAX_VALUE)
				dval++;

			upper = Math.min((Double) upper, dval);

		} else if (upper instanceof Integer) {
			int ival = ((Number) val).intValue();
			if (ival != Integer.MAX_VALUE)
				ival++;

			upper = Math.min((Integer) upper, ival);
		} else if (upper instanceof Float) {
			float fval = ((Float) val).floatValue();
			if (fval != Float.MAX_VALUE)
				fval++;

			upper = Math.min((Float) upper, fval);
		} else if (upper instanceof Long) {
			long lval = ((Number) val).longValue();
			if (lval != Long.MAX_VALUE)
				lval++;

			upper = Math.min((Long) upper, lval);
		} else
			throw new RuntimeException("Value type: " + val.getClass()
					+ " is not supported");

	}

	public void addBiggerEq(Object val) {

		if (lower instanceof Double)
			lower = Math.max((Double) lower, ((Number) val).doubleValue());
		else if (lower instanceof Integer)
			lower = Math.max((Integer) lower, ((Number) val).intValue());
		else if (lower instanceof Float)
			lower = Math.max((Float) lower, ((Float) val).floatValue());
		else
			lower = Math.max((Long) lower, ((Number) val).longValue());

	}

	public void addEq(Object val) {

		if (val instanceof Number) {
			addSmallerEq(val);
			addBiggerEq(val);
		} else if (val instanceof String) {
			lower = val;
			upper = increaseString(val.toString());
		} else if (val instanceof Boolean) {
			lower = val;
			upper = 2;
		} else {
			lower = val;
			upper = Integer.MAX_VALUE;
		}

	}

	/**
	 * This function will increase the string by one. is used in calculating
	 * upper ranges for strings.
	 * 
	 * @param str
	 * @return String
	 */
	private static final String increaseString(String str) {
		final int len = str.length();
		final StringBuilder buff = new StringBuilder(len);

		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			if (ch <= Character.MAX_VALUE) {
				// increase by one
				buff.append(((char) (ch + 1)));
				break;
			}
		}

		return buff.toString();
	}

}
