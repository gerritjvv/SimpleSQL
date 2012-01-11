package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

import org.simplesql.data.Cell;

public class EXPRESSION extends TERM {

	enum OP {
		PLUS("+"), MINUS("-");

		final String val;

		OP(String val) {
			this.val = val;
		}

		public String toString() {
			return val;
		}

		public static OP parse(String id) {
			if (id.equals(PLUS.val))
				return PLUS;
			else
				return MINUS;
		}

	}

	List<Object> children = new ArrayList<Object>();

	/**
	 * An expression is considered complex if it contains anything other than a
	 * simple constant or variable.
	 */
	boolean complex = false;
	/**
	 * Only used if complex == false. This means that only one UNARY exist.
	 */
	UNARY.TYPE unaryType = UNARY.TYPE.MIXED;

	public EXPRESSION() {
		super(TYPE.INTEGER);
	}

	public void plus() {
		children.add(OP.PLUS);
		complex = true;
	}

	public void minus() {
		children.add(OP.MINUS);
		complex = true;
	}

	public void mult(MULT mult) {
		// get the highest order type
		type = mult.type.max(type);
		children.add(mult);

		// if the expressions is only madeup of a
		// single constant or variable we propogate the
		// value and type upwards from the MULT and UNARY.
		if(!complex){
			complex = mult.isComplex();
			unaryType = mult.unaryType;
			setValue(mult.unaryValue);
		}
	}

	public boolean isComplex() {
		return complex;
	}

	/**
	 * Gets the Cell class based on the type
	 * 
	 * @return
	 */
	public Class<? extends Cell> getCellType() {
		return type.getCellType();
	}

	public void visit(Visitor visitor) {
		for (Object obj : children) {
			if (obj instanceof MULT)
				visitor.mult((MULT) obj);
			else if (obj.equals(OP.PLUS))
				visitor.plus();
			else
				visitor.minus();
		}
	}

	public static interface Visitor {

		void plus();

		void minus();

		void mult(MULT mult);

	}
}
