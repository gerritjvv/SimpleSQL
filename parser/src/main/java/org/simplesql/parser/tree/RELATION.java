package org.simplesql.parser.tree;

public class RELATION {

	enum OP{
		SMALLER_THAN("<"),
		BIGGER_THAN(">"),
		SMALLER_EQ_THAN("<="), 
		BIGGER_EQ_THAN(">="),
		NOT_EQ("!="),
		EQ("=");
		
		final String val;
		
		OP(String val){
			this.val = val;
		}
		
		public String toString(){
			return val;
		}
	}
	
	final EXPRESSION e1;
	final EXPRESSION e2;
	final OP op;
	
	public RELATION(EXPRESSION e1, String op, EXPRESSION e2) {
		super();
		this.e1 = e1;
		this.op = OP.valueOf(op);
		this.e2 = e2;
	}

	public EXPRESSION getE1() {
		return e1;
	}

	public EXPRESSION getE2() {
		return e2;
	}

	public OP getOp() {
		return op;
	}
	
	
	
}
