package org.simplesql.parser.tree;

public class DOUBLE extends NUMBER {

	final double val;

	public DOUBLE(String val) {
		super(TYPE.DOUBLE);
		this.val = Double.parseDouble(val);
	}

	public double getVal() {
		return val;
	}

}
