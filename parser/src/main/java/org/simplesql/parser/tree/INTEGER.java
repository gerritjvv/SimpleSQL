package org.simplesql.parser.tree;

public class INTEGER extends NUMBER {

	Number val;

	public INTEGER(String val) {
		super(TYPE.INTEGER);
		try {
			this.val = Integer.parseInt(val);
		} catch (NumberFormatException nfe) {
			this.val = Long.parseLong(val);
		}

		setValue(this.val);
	}

	public Number getVal() {
		return val;
	}

}
