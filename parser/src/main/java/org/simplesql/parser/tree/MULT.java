package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

import org.simplesql.parser.tree.TERM.TYPE;

public class MULT {

	enum OP {
		PRODUCT("*"), DIVIDE("/"), MOD("mod");

		final String val;

		OP(String val) {
			this.val = val;
		}

		public String toString() {
			return val;
		}

	}

	TYPE type = TYPE.INTEGER;

	List<Object> children = new ArrayList<Object>();

	
	/**
	 * An expression is considered complex if it contains anything other than a simple constant or variable.
	 */
	boolean complex = false;
	
	/**
	 * Only used if complex == false.
	 * This means that only one UNARY exist.
	 */
	UNARY.TYPE unaryType = UNARY.TYPE.MIXED;
	Object unaryValue;
	
	public void unary(UNARY unary) {

		// get the highest order type
		type = unary.term.type.max(type);
		
		children.add(unary);
		
		//set to complex if the unary is a functions, expression or other than a CONSTANT or Variable.
		unaryType = unary.getType();
		unaryValue = unary.term.getValue();
		
		if(!complex){
			complex = unaryType == UNARY.TYPE.MIXED || children.size() > 1;

		}
		
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public void mult() {
		children.add(OP.PRODUCT);
	}

	public void divide() {
		children.add(OP.DIVIDE);
	}

	public void mod() {
		children.add(OP.MOD);
	}

	public boolean isComplex(){
		return complex;
	}
	
	public void visit(Visitor visitor) {

		for (Object child : children) {
			if (child instanceof UNARY)
				visitor.unary((UNARY) child);
			else {
				OP op = (OP) child;
				if (op.equals(OP.PRODUCT))
					visitor.mult();
				else if (op.equals(OP.DIVIDE))
					visitor.divide();
				else
					visitor.mod();
			}
		}
	}

	public static interface Visitor {

		void unary(UNARY unary);

		void mult();

		void divide();

		void mod();

	}

}
