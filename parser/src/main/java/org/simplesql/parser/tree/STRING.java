package org.simplesql.parser.tree;

public class STRING extends TERM {

	final String val;

	public STRING(String val) {
		super(TYPE.STRING);
		this.val = val;
		setValue(val);
	}

	public String getVal() {
		return val;
	}

}