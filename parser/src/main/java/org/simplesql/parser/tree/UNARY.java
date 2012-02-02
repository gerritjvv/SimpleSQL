package org.simplesql.parser.tree;

/**
 * 
 * An item can be a TERM or Function
 * 
 */
public class UNARY {

	enum TYPE {
		VARIABLE, CONSTANT, MIXED;
	}

	TERM term;
	TYPE type;

	public void term(TERM term) {
		this.term = term;
		if (term instanceof VARIABLE) {
			type = TYPE.VARIABLE;
		} else if (term instanceof NUMBER || term instanceof STRING) {
			type = TYPE.CONSTANT;
		} else {
			type = TYPE.MIXED;
		}
	}

	public TYPE getType() {
		return type;
	}

	public void visit(Visitor visitor) {

		if (term instanceof EXPRESSION)
			visitor.term((EXPRESSION) term);
		else if (term instanceof FUNCTION)
			visitor.term((FUNCTION) term);
		else if (term instanceof DOUBLE)
			visitor.term((DOUBLE) term);
		else if (term instanceof INTEGER)
			visitor.term((INTEGER) term);
		else if (term instanceof STRING)
			visitor.term((STRING) term);
		else if (term instanceof BOOLEAN)
			visitor.term((BOOLEAN) term);
		else
			visitor.term((VARIABLE) term);
	}

	public static interface Visitor {

		void term(EXPRESSION expression);

		void term(FUNCTION f);

		void term(BOOLEAN d);

		void term(DOUBLE d);

		void term(INTEGER i);

		void term(STRING s);

		void term(VARIABLE v);
	}

}
