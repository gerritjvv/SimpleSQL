package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.simplesql.data.RangeGroups;
import org.simplesql.data.TransformFunction;
import org.simplesql.funct.COUNT;
import org.simplesql.funct.PassThroughTransform;
import org.simplesql.funct.SUM;

public class SELECT {

	public enum ORDER {
		DESC, ASC
	};

	/**
	 * Holds the SELECT a,b,d ... part
	 */
	final List<EXPRESSION> selects = new ArrayList<EXPRESSION>();
	final List<LOGICAL> where = new ArrayList<LOGICAL>();
	final List<EXPRESSION> groupBy = new ArrayList<EXPRESSION>();

	ORDER order = ORDER.ASC;
	/**
	 * Holds the order by variables
	 */
	final Set<String> selectOrderBy = new HashSet<String>();
	final Set<String> groupOrderBy = new HashSet<String>();

	int[] selectOrderIndexes;
	int selectOrderIndexesLen;
	int[] groupOrderIndexes;
	int groupOderderIndexesLen;

	public Set<String> variables = new HashSet<String>();
	public final RangeGroups rangeGroups = new RangeGroups();

	Map<String, EXPRESSION> selectExpressionsMap = new HashMap<String, EXPRESSION>();
	Map<String, EXPRESSION> groupExpressionsMap = new HashMap<String, EXPRESSION>();

	/**
	 * Used to keep track of the index of each select expression e.g. select a,b
	 * will give a.index = 0, b.index = 1<br/>
	 * For internal use only
	 */
	private int selectIndexCounter;
	/**
	 * Same as selectIndexCounter but for group statements.<br/>
	 * For internal use only
	 */
	private int groupIndexCounter;

	/**
	 * Contain the identified transform functions for the select expressions.
	 */
	final List<TransformFunction> transforms = new ArrayList<TransformFunction>();

	int limit = Integer.MAX_VALUE;
	String table;

	public SELECT() {

	}

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
		selectExpressionsMap.put(expr.getAssignedName(), expr);

		final TERM.TYPE type = expr.getType();

		// choose the correct Transform Function
		if (type.equals(TERM.TYPE.AGGREGATE_COUNT)) {
			transforms.add(new COUNT(selects.indexOf(expr)));
		} else if (type.equals(TERM.TYPE.AGGREGATE_SUM)) {
			transforms.add(new SUM(selects.indexOf(expr)));
		} else if (type.equals(TERM.TYPE.AGGREGATE_TOP)) {
			throw new RuntimeException("TOP is not yet supported");
		} else {
			transforms.add(new PassThroughTransform(selects.indexOf(expr)));
		}

		expr.setIndex(selectIndexCounter++);
	}

	public void where(LOGICAL logical) {
		where.add(logical);
	}

	public void groupBy(EXPRESSION expr) {
		groupBy.add(expr);
		expr.setIndex(groupIndexCounter++);
		groupExpressionsMap.put(expr.getAssignedName(), expr);
	}

	public void orderBy(String varName) {

		EXPRESSION expr = null;
		if ((expr = groupExpressionsMap.get(varName)) != null) {
			groupOrderBy.add(varName);
			if (groupOrderIndexes == null)
				groupOrderIndexes = new int[groupIndexCounter];

			groupOrderIndexes[groupOderderIndexesLen++] = expr.getIndex();
		} else if ((expr = selectExpressionsMap.get(varName)) != null) {
			selectOrderBy.add(varName);

			if (selectOrderIndexes == null)
				selectOrderIndexes = new int[selectIndexCounter];

			selectOrderIndexes[selectOrderIndexesLen++] = expr.getIndex();
		} else {
			throw new RuntimeException(
					"The variable "
							+ varName
							+ " must exist either in the group by or in the select statement");
		}
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

	public void setVariables(Set<String> variables) {
		this.variables = variables;
	}

	public Set<String> getVariables() {
		return variables;
	}

	public RangeGroups getRangeGroups() {
		return rangeGroups;
	}

	public List<EXPRESSION> getSelects() {
		return selects;
	}

	public List<TransformFunction> getTransforms() {
		return transforms;
	}

	public List<LOGICAL> getWhere() {
		return where;
	}

	public List<EXPRESSION> getGroupBy() {
		return groupBy;
	}

	public Set<String> getSelectOrderBy() {
		return selectOrderBy;
	}

	public Set<String> getGroupOrderBy() {
		return groupOrderBy;
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

		int index = 0;
		for (String var : selectOrderBy) {
			visitor.orderBy(index++, var);
		}

		for (String var : groupOrderBy) {
			visitor.orderBy(index++, var);
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

		void orderBy(int i, String expr);

	}

	public ORDER getOrder() {
		return order;
	}

	public void setOrder(ORDER order) {
		this.order = order;
	}

	public int[] getSelectOrderIndexes() {
		return selectOrderIndexes;
	}

	public int getSelectOrderIndexesLen() {
		return selectOrderIndexesLen;
	}

	public int[] getGroupOrderIndexes() {
		return groupOrderIndexes;
	}

	public int getGroupOrderIndexesLen() {
		return groupOderderIndexesLen;
	}

}
