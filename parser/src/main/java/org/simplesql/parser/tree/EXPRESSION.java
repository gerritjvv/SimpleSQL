package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class EXPRESSION extends TERM {

	List<Object> chidlren = new ArrayList<Object>();

	public void plus() {
		chidlren.add("+");
	}

	public void minus() {
		chidlren.add("-");
	}

	public void mult(MULT mult) {
		chidlren.add(mult);
	}

}
