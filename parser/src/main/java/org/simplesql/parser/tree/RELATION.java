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
		
		public static OP parse(String id){
			if(id.equals(SMALLER_THAN.val))
				return SMALLER_THAN;
			else if(id.equals(BIGGER_THAN.val))
				return BIGGER_THAN;
			else if(id.equals(SMALLER_EQ_THAN.val))
				return SMALLER_EQ_THAN;
			else if(id.equals(BIGGER_EQ_THAN.val))
				return BIGGER_EQ_THAN;
			else if(id.equals(NOT_EQ.val))
				return NOT_EQ;
			else
				return EQ;
		}
		
	}
	
	final EXPRESSION e1;
	final EXPRESSION e2;
	final OP op;
	
	public RELATION(EXPRESSION e1, String op, EXPRESSION e2) {
		super();
		this.e1 = e1;
		this.op = OP.parse(op);
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
	
	public void visit(Visitor visitor){
		visitor.relation(e1, op, e2);
	}
	
	public static interface Visitor{
		
		void relation(EXPRESSION e1, OP op, EXPRESSION e2);
		
	}
	
}
