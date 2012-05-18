package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class INSERT {

	String table;
	List<String> columns = new ArrayList<String>();
	String input;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void addColumn(String column) {
		columns.add(column);
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

}
