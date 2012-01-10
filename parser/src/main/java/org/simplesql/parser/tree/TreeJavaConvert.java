package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.simplesql.data.Cell;
import org.simplesql.data.RangeGroups;
import org.simplesql.data.VariableRange;
import org.simplesql.data.VariableRanges;
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
	 * Holds the variables used. In a select query the variables correspond to
	 * the table columns.
	 */
	final Set<String> variables = new HashSet<String>();

	final RangeGroups rangeGroups = new RangeGroups();

	/**
	 * Visits the SELECT and all of its children.<br/>
	 * The strings for where, select, order and group will be created as valid
	 * Java expressions.
	 * 
	 * @param select
	 */
	public TreeJavaConvert(SELECT select) {

		final ExpressionPrinter selectExpressions = new ExpressionPrinter(
				new StringBuilder(), variables);
		final ExpressionPrinter groupByExpressions = new ExpressionPrinter(
				new StringBuilder(), variables);
		final ExpressionPrinter orderByExpressions = new ExpressionPrinter(
				new StringBuilder(), variables);

		final LogicalPrinter logicalPrinter = new LogicalPrinter(
				new StringBuilder(), rangeGroups, variables);

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

	public RangeGroups getRangeGroups() {
		return rangeGroups;
	}

	public Set<String> getVariables() {
		return variables;
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

		final RangeGroups rangeGroups;

		final Set<String> variables;

		public LogicalPrinter(StringBuilder buff, RangeGroups rangeGroups,
				Set<String> variables) {
			this.rangeGroups = rangeGroups;
			this.buff = buff;
			this.variables = variables;
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

			rangeGroups.nextGroup();

		}

		@Override
		public void relation(EXPRESSION e1, OP op, EXPRESSION e2) {
			buff.append("(");

			Set<String> leftVariables = new HashSet<String>();

			ExpressionPrinter leftPrinter = new ExpressionPrinter(buff,
					leftVariables);

			e1.visit(leftPrinter);

			String opStr;
			if (op.equals(RELATION.OP.EQ))
				opStr = "==";
			else
				opStr = op.toString();

			buff.append(")").append(opStr);
			buff.append("(");

			Set<String> rightVariables = new HashSet<String>();
			ExpressionPrinter rightPrinter = new ExpressionPrinter(buff,
					rightVariables);

			e2.visit(rightPrinter);

			buff.append(")");

			variables.addAll(leftVariables);
			variables.addAll(rightVariables);

			System.out.println("LV.size: " + leftVariables.size() + ", " + leftPrinter.isComplex());
			System.out.println("RV.size: " + rightVariables.size() + ", " + rightPrinter.isComplex() );
			
			// check for ranges
			if (((leftVariables.size() + rightVariables.size()) == 1)
					&& !(leftPrinter.isComplex() || rightPrinter.isComplex())) {
				// here we can calculate a range
				// we have either left or right with variable on one side and a
				// value on the other.
				// and some values that are none complex in terms of there are
				// no functions and no multiplications

				String varName;
				VariableRange range;
				Object upper, lower;
				if (leftVariables.size() == 1) {
					varName = leftVariables.iterator().next();
					upper = leftPrinter.getMax();
					lower = leftPrinter.getMin();
				} else {
					varName = rightVariables.iterator().next();
					upper = leftPrinter.getMax();
					lower = leftPrinter.getMin();
				}

				range = new VariableRange(upper instanceof Number, upper, lower);
				rangeGroups.setVariable(varName, range);

			}

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

		boolean complex = false;

		public ExpressionPrinter(StringBuilder buff, Set<String> variables) {
			this.buff = buff;
			unaryPrinter = new UnaryPrinter(buff, this, variables);
		}

		public Object getMax() {
			return unaryPrinter.getMax();
		}

		public Object getMin() {
			return unaryPrinter.getMin();
		}

		/**
		 * An expression is considered complex if it has anything other than a
		 * variable or constant in it.
		 * 
		 * @return
		 */
		public boolean isComplex() {
			return complex;
		}

		public void addString(String str) {
			buff.append(str);
		}

		public void addComma() {
			complex = true;
			buff.append(",");
		}

		public String toString() {
			return buff.toString();
		}

		@Override
		public void plus() {
			complex = true;
			buff.append(" + ");
		}

		@Override
		public void minus() {
			complex = true;
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
			complex = true;
			buff.append(" * ");
		}

		@Override
		public void divide() {
			complex = true;
			buff.append(" / ");
		}

		@Override
		public void mod() {
			complex = true;
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

		Object min = Integer.MAX_VALUE;
		Object max = Integer.MIN_VALUE;

		/**
		 * Holds the variables used. In a select query the variables correspond
		 * to the table columns.
		 */
		final Set<String> variables;

		public UnaryPrinter(StringBuilder buff, EXPRESSION.Visitor exprVisitor,
				Set<String> variables) {
			this.buff = buff;
			this.exprVisitor = exprVisitor;
			this.variables = variables;
		}

		public Object getMax() {
			return max;
		}

		public Object getMin() {
			return min;
		}

		public String toString() {
			return buff.toString();
		}

		@Override
		public void term(EXPRESSION expression) {
			// range calculations are no supported for complex expressions
			max = Integer.MAX_VALUE;
			min = Integer.MIN_VALUE;

			expression.visit(exprVisitor);
		}

		@Override
		public void term(FUNCTION f) {
			// range calculations are no supported for complex expressions

			if (f.isAggregateFunction()) {
				for (EXPRESSION arg : f.getArgs()) {
					arg.visit(exprVisitor);
				}
			} else {
				buff.append(SQLFunctions.class.getName()).append(".");
				buff.append(f.name).append("(");

				for (EXPRESSION arg : f.getArgs()) {
					arg.visit(exprVisitor);
				}

				buff.append(")");

			}
		}

		@Override
		public void term(DOUBLE d) {

			double val = d.getVal();

			if (max instanceof Number) {
				max = Math.max(((Number) max).doubleValue(), val);
			}

			if (min instanceof Number) {
				min = Math.min(((Number) min).doubleValue(), val);
			}

			buff.append(val);
		}

		@Override
		public void term(INTEGER i) {
			int val = i.getVal();

			if (max instanceof Number) {
				max = Math.max(((Number) max).intValue(), val);
			}

			if (min instanceof Number) {
				min = Math.min(((Number) min).intValue(), val);
			}

			buff.append(val);
		}

		@Override
		public void term(STRING s) {
			String val = s.getVal();
			max = val;
			min = val;
			buff.append(s.getVal());
		}

		@Override
		public void term(VARIABLE v) {
			String name = v.getName();

			buff.append(name);
			variables.add(name);
		}

	}

}
