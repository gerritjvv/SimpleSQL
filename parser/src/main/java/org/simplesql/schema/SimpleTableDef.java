package org.simplesql.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Immutable TableDef implementation.
 * 
 */
public class SimpleTableDef implements TableDef {

	List<ColumnDef> columnDefs = new ArrayList<ColumnDef>(10);
	String name;
	Map<String, ColumnDef> columnMap = new HashMap<String, ColumnDef>();
	String engine = "";

	public SimpleTableDef() {

	}

	public SimpleTableDef(String name, ColumnDef... columnDefs) {
		this.name = name;

		for (ColumnDef def : columnDefs) {
			this.columnDefs.add(def);
			columnMap.put(def.getName(), def);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public void addColumn(ColumnDef def) {
		columnDefs.add(def);
		columnMap.put(def.getName(), def);
	}

	public ColumnDef getColumnDef(String name) {
		return columnMap.get(name);
	}

	public ColumnDef[] getColumnDefs() {
		return columnDefs.toArray(new ColumnDef[0]);
	}

	public String getName() {
		return name;
	}

	@Override
	public int getColumnCount() {
		return (columnDefs == null) ? 0 : columnDefs.size();
	}

}
