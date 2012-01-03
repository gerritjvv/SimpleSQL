package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class SELECT {

	/**
	 * Holds the SELECT a,b,d ... part
	 */
	final List<EXPRESSION> selects = new ArrayList<EXPRESSION>();
	final List<LOGICAL> where = new ArrayList<LOGICAL>();
	final List<EXPRESSION> groupBy = new ArrayList<EXPRESSION>();
	final List<EXPRESSION> orderBy = new ArrayList<EXPRESSION>();

	int limit = Integer.MAX_VALUE;
	String table;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void table(String table) {
		this.table = table;
	}

	public void select(EXPRESSION expr) {
		selects.add(expr);
	}

	public void where(LOGICAL logical) {
		where.add(logical);
	}

	public void groupBy(EXPRESSION expr) {
		groupBy.add(expr);
	}

	public void orderBy(EXPRESSION expr) {
		orderBy.add(expr);
	}

	public void limit(String limit) {
		this.limit = Integer.parseInt(limit);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<EXPRESSION> getSelects() {
		return selects;
	}

	public List<LOGICAL> getWhere() {
		return where;
	}

	public List<EXPRESSION> getGroupBy() {
		return groupBy;
	}

	public List<EXPRESSION> getOrderBy() {
		return orderBy;
	}

	public void visit(Visitor visitor) {
		visitor.table(table);
		visitor.limit(limit);

		for (int i = 0; i < selects.size(); i++) {
			visitor.select(i, selects.get(i));
		}

		for (int i = 0; i < groupBy.size(); i++) {
			visitor.groupBy(i, groupBy.get(i));
		}

		for (int i = 0; i < orderBy.size(); i++) {
			visitor.orderBy(i, orderBy.get(i));
		}

		for (int i = 0; i < where.size(); i++) {
			visitor.where(i, where.get(i));
		}

	}

	public static interface Visitor {

		void table(String name);

		void limit(int limit);

		void select(int i, EXPRESSION expr);

		void where(int i, LOGICAL logical);

		void groupBy(int i, EXPRESSION expr);

		void orderBy(int i, EXPRESSION expr);

	}

}
