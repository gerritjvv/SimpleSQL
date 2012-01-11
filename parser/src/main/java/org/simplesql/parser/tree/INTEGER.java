package org.simplesql.parser.tree;

public class INTEGER extends NUMBER{

	final int val;

	public INTEGER(String val) {
		super(TYPE.INTEGER);
		this.val = Integer.parseInt(val);
		setValue(this.val);
	}

	public int getVal() {
		return val;
	}
	
}
