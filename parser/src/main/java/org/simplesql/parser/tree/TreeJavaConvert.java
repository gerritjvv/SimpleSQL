package org.simplesql.parser.tree;

import org.simplesql.data.Cell;
import org.simplesql.funct.SQLFunctions;
import org.simplesql.parser.tree.RELATION.OP;
import org.simplesql.parser.tree.TERM.TYPE;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.TableDef;

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
	
	final TableDef tableDef;
	

	/**
	 * Visits the SELECT and all of its children.<br/>
	 * The strings for where, select, order and group will be created as valid
	 * Java expressions.
	 * 
	 * @param select
	 */
	public TreeJavaConvert(SELECT select, final TableDef tableDef) {

		this.tableDef = tableDef;
		
		final ExpressionPrinter selectExpressions = new ExpressionPrinter(
				new StringBuilder());
		final ExpressionPrinter groupByExpressions = new ExpressionPrinter(
				new StringBuilder());
		final StringBuilder orderByExpressions = new StringBuilder();

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
				TYPE type = expr.getType();
				
				
				//do some extra checking here for types.
				if(type.equals(TYPE.UKNOWN) && expr.assignedName != null){
					//find the type from the schema
					ColumnDef colDef = tableDef.getColumnDef(expr.assignedName);
					if(colDef != null){
						
						if(!expr.complex){
							TYPE treeType = colDef.getCell().getSchema().getTreeType();
							if(treeType != null)
								expr.setType(treeType);
						}
						
					}
						
				}
				
				Class<? extends Cell> cellType = expr.getCellType();
				
				
				selectExpressions.addString("new "
						+ cellType.getCanonicalName() + "(");
				expr.visit(selectExpressions);
				selectExpressions.addString(",\"");
				selectExpressions.addString(expr.getAssignedName());
				selectExpressions.addString("\")");

			}

			@Override
			public void orderBy(int i, String expr) {
				if (i != 0)
					orderByExpressions.append(',');

				orderByExpressions.append(expr);
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

		ExpressionPrinter expressionPrinter;

		public LogicalPrinter(StringBuilder buff) {
			this.buff = buff;
			this.expressionPrinter = new ExpressionPrinter(buff);
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

			e1.visit(expressionPrinter);

			String opStr;
			if (op.equals(RELATION.OP.EQ))
				opStr = "==";
			else
				opStr = op.toString();

			buff.append(")").append(opStr);
			buff.append("(");

			e2.visit(expressionPrinter);

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
			buff.append(" % ");
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
			// range calculations are no supported for complex expressions

			if (f.isAggregateFunction()) {
				for (EXPRESSION arg : f.getArgs()) {
					arg.visit(exprVisitor);
				}
			} else {
				buff.append(SQLFunctions.class.getName()).append(".");
				buff.append(f.name).append("(");

				int i = 0;
				for (EXPRESSION arg : f.getArgs()) {
					if (i++ != 0)
						buff.append(',');

					arg.visit(exprVisitor);
				}

				buff.append(")");

			}
		}

		@Override
		public void term(DOUBLE d) {

			Number val = d.getVal();

			buff.append(val);
		}

		@Override
		public void term(INTEGER i) {
			Number val = i.getVal();

			buff.append(val);

			if (val instanceof Long)
				buff.append('L');
		}

		@Override
		public void term(STRING s) {
			buff.append('\"').append(s.getVal()).append('\"');
		}

		@Override
		public void term(VARIABLE v) {
			String name = v.getName();

			buff.append(name);
		}

		@Override
		public void term(BOOLEAN d) {
			buff.append(d.getVal());
		}

	}

}
