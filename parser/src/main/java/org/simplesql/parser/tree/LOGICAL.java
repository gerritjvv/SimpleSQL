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

		public static OP parse(String id) {
			if (id.equals(AND.val))
				return AND;
			else if (id.equals(OR.val))
				return OR;
			else if (id.equals(JAVA_AND.val))
				return JAVA_AND;
			else
				return JAVA_OR;
		}

	}

	List<Object> children = new ArrayList<Object>();

	public void relation(RELATION rel) {
		children.add(rel);
	}

	public void logical(String op) {
		children.add(OP.parse(op));
	}

	public void visit(Visitor visitor) {

		for (Object child : children) {
			if (child instanceof RELATION)
				visitor.relation((RELATION) child);
			else {
				OP op = (OP) child;
				if (op.equals(OP.AND) || op.equals(OP.JAVA_AND))
					visitor.and();
				else
					visitor.or();
			}
		}

	}

	static interface Visitor {

		void relation(RELATION rel);

		void and();

		void or();

	}

}
