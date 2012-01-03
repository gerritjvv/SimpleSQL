package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class FUNCTION extends TERM {

	String name;

	List<EXPRESSION> args = new ArrayList<EXPRESSION>();

	public FUNCTION() {
		super(TYPE.UKNOWN);
	}

	public void name(String name) {
		this.name = name;
	}

	public void expression(EXPRESSION expr) {
		args.add(expr);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EXPRESSION> getArgs() {
		return args;
	}

	public void setArgs(List<EXPRESSION> args) {
		this.args = args;
	}

}
