package org.simplesql.parser.tree;

public class INTEGER extends NUMBER{

	final int val;

	public INTEGER(String val) {
		super();
		this.val = Integer.parseInt(val);
	}

	public int getVal() {
		return val;
	}
	
}
