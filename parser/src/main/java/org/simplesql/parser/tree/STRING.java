package org.simplesql.parser.tree;

public class STRING extends TERM {

	final String val;

	public STRING(String val) {
		super();
		this.val = val;
	}

	public String getVal() {
		return val;
	}

}
