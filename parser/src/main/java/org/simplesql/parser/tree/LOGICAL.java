package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class LOGICAL {

	enum OP {
		AND("AND"), OR("OR"), JAVA_AND("&&"), JAVA_OR("||");

		final String val;

		OP(String val) {
			this.val = val;
		}

		public String toString() {
			return val;
		}

	}

	List<Object> children = new ArrayList<Object>();

	public void relation(RELATION rel) {
		children.add(rel);
	}

	public void logical(String op) {
		children.add(OP.valueOf(op));
	}

}
