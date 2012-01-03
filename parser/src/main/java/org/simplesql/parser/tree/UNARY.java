package org.simplesql.parser.tree;

/**
 * 
 * An item can be a TERM or Function
 * 
 */
public class UNARY {

	
	TERM term;

	public void term(TERM term) {
		this.term = term;
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
		else
			visitor.term((VARIABLE) term);
	}

	public static interface Visitor {

		void term(EXPRESSION expression);

		void term(FUNCTION f);

		void term(DOUBLE d);

		void term(INTEGER i);

		void term(STRING s);

		void term(VARIABLE v);
	}

}
