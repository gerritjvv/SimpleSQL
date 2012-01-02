package org.simplesql.parser.tree;

public class DOUBLE extends NUMBER {

	final double val;

	public DOUBLE(String val) {
		super();
		this.val = Double.parseDouble(val);
	}

	public double getVal() {
		return val;
	}

}
