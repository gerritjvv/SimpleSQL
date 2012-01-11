package org.simplesql.parser.tree;

import org.simplesql.data.VariableRange;

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

	/**
	 * If a range could not be calculated from the expression relation the range is set to null.<br/>
	 * 
	 */
	VariableRange range;
	
	public RELATION(EXPRESSION e1, String op, EXPRESSION e2) {
		super();
		this.e1 = e1;
		this.op = OP.parse(op);
		this.e2 = e2;
		
		if(!(e1.isComplex() || e2.isComplex())){
			
			if(e1.unaryType == UNARY.TYPE.CONSTANT && e2.unaryType == UNARY.TYPE.VARIABLE){
				Object val = e1.getValue();
				
				range = createRange(e2.getValue().toString(), this.op, val);
				
			}else if(e2.unaryType == UNARY.TYPE.CONSTANT && e1.unaryType == UNARY.TYPE.VARIABLE){
				Object val = e2.getValue();
				range = createRange(e1.getValue().toString(), this.op, val);
			}else{
				range = null;
			}
			
		}
		
	}

	private static final VariableRange createRange(String string, OP op, Object val) {
		System.out.println("Creating range with value: " + val);
		VariableRange range = new VariableRange(string, val instanceof Number);
		
		if(op == OP.BIGGER_EQ_THAN || op == OP.BIGGER_THAN){
			range.addBiggerEq(val);
		}else if(op == OP.SMALLER_EQ_THAN || op == OP.SMALLER_THAN){
			range.addSmallerEq(val);
		}else{
			range.addEq(val);
		}
		
		
		return range;
	}

	/**
	 * @return VariableRange null if no variable range could be calculated.
	 */
	public VariableRange getRange() {
		return range;
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
