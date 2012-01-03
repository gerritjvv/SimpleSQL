package org.simplesql.parser.tree;

import org.simplesql.data.Cell;
import org.simplesql.funct.SQLFunctions;
import org.simplesql.parser.tree.RELATION.OP;

/**
 * 
 * Converts a SELECT to separate java expressions.
 * 
 */
public class TreeJavaConvert {

	final String whereExpressions;
	final String selectExpressions;
	final String orderByExpressions;
	final String groupByExpressions;

	/**
	 * Visits the SELECT and all of its children.<br/>
	 * The strings for where, select, order and group will be created as valid
	 * Java expressions.
	 * 
	 * @param select
	 */
	public TreeJavaConvert(SELECT select) {

		final ExpressionPrinter selectExpressions = new ExpressionPrinter(
				new StringBuilder());
		final ExpressionPrinter groupByExpressions = new ExpressionPrinter(
				new StringBuilder());
		final ExpressionPrinter orderByExpressions = new ExpressionPrinter(
				new StringBuilder());

		final LogicalPrinter logicalPrinter = new LogicalPrinter(
				new StringBuilder());

		select.visit(new SELECT.Visitor() {

			@Override
			public void where(int i, LOGICAL logical) {
				logical.visit(logicalPrinter);
			}

			@Override
			public void table(String name) {
			}

			@Override
			public void select(int i, EXPRESSION expr) {
				if (i != 0)
					selectExpressions.addComma();

				// we encapsulate each type with a new <Type>Cell
				Class<? extends Cell> cellType = expr.getCellType();

				selectExpressions.addString("new "
						+ cellType.getCanonicalName() + "(");
				expr.visit(selectExpressions);
				selectExpressions.addString(")");

			}

			@Override
			public void orderBy(int i, EXPRESSION expr) {
				if (i != 0)
					orderByExpressions.addComma();

				expr.visit(orderByExpressions);
			}

			@Override
			public void limit(int limit) {
			}

			@Override
			public void groupBy(int i, EXPRESSION expr) {
				if (i != 0)
					groupByExpressions.addComma();

				expr.visit(groupByExpressions);
			}
		});

		this.whereExpressions = logicalPrinter.toString();
		this.selectExpressions = selectExpressions.toString();
		this.groupByExpressions = groupByExpressions.toString();
		this.orderByExpressions = orderByExpressions.toString();
	}

	public String getWhereExpressions() {
		return whereExpressions;
	}

	public String getSelectExpressions() {
		return selectExpressions;
	}

	public String getOrderByExpressions() {
		return orderByExpressions;
	}

	public String getGroupByExpressions() {
		return groupByExpressions;
	}

	/**
	 * 
	 * Visit's the Logical relations
	 * 
	 */
	static class LogicalPrinter implements LOGICAL.Visitor, RELATION.Visitor {
		final StringBuilder buff;
		final ExpressionPrinter exprPrinter;

		public LogicalPrinter(StringBuilder buff) {
			this.buff = buff;
			exprPrinter = new ExpressionPrinter(buff);
		}

		public String toString() {
			return buff.toString();
		}

		@Override
		public void relation(RELATION rel) {
			rel.visit(this);
		}

		@Override
		public void and() {
			buff.append(" && ");
		}

		@Override
		public void or() {
			buff.append(" || ");
		}

		@Override
		public void relation(EXPRESSION e1, OP op, EXPRESSION e2) {
			buff.append("(");

			e1.visit(exprPrinter);

			buff.append(")").append(op);
			buff.append("(");

			e2.visit(exprPrinter);

			buff.append(")");

		}

	}

	/**
	 * 
	 * Visits each EXPRESSION and MULT
	 * 
	 * 
	 */
	static class ExpressionPrinter implements EXPRESSION.Visitor, MULT.Visitor {

		final StringBuilder buff;
		UnaryPrinter unaryPrinter;

		public ExpressionPrinter(StringBuilder buff) {
			this.buff = buff;
			unaryPrinter = new UnaryPrinter(buff, this);
		}

		public void addString(String str) {
			buff.append(str);
		}

		public void addComma() {
			buff.append(",");
		}

		public String toString() {
			return buff.toString();
		}

		@Override
		public void plus() {
			buff.append(" + ");
		}

		@Override
		public void minus() {
			buff.append(" - ");
		}

		@Override
		public void mult(MULT mult) {
			mult.visit(this);
		}

		@Override
		public void unary(UNARY unary) {
			unary.visit(unaryPrinter);
		}

		@Override
		public void mult() {
			buff.append(" * ");
		}

		@Override
		public void divide() {
			buff.append(" / ");
		}

		@Override
		public void mod() {
			buff.append(" mod ");
		}

	}

	/**
	 * 
	 * Visits the Unary TERM and for each term its sub parts.
	 * 
	 */
	static class UnaryPrinter implements UNARY.Visitor {

		final StringBuilder buff;
		final EXPRESSION.Visitor exprVisitor;

		public UnaryPrinter(StringBuilder buff, EXPRESSION.Visitor exprVisitor) {
			this.buff = buff;
			this.exprVisitor = exprVisitor;
		}

		public String toString() {
			return buff.toString();
		}

		@Override
		public void term(EXPRESSION expression) {
			expression.visit(exprVisitor);
		}

		@Override
		public void term(FUNCTION f) {
			buff.append(SQLFunctions.class.getName()).append(".");
			buff.append(f.name).append("(");

			for (EXPRESSION arg : f.getArgs()) {
				arg.visit(exprVisitor);
			}

			buff.append(")");

		}

		@Override
		public void term(DOUBLE d) {
			buff.append(d.getVal());
		}

		@Override
		public void term(INTEGER i) {
			buff.append(i.getVal());
		}

		@Override
		public void term(STRING s) {
			buff.append(s.getVal());
		}

		@Override
		public void term(VARIABLE v) {
			buff.append(v.getName());
		}

	}

}
