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

}
