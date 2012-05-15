package org.simplesql.schema;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Immutable TableDef implementation.
 * 
 */
public class SimpleTableDef implements TableDef {

	final ColumnDef[] columnDefs;
	final String name;
	final Map<String, ColumnDef> columnMap = new HashMap<String, ColumnDef>();
	
	public SimpleTableDef(String name, ColumnDef... columnDefs) {
		this.name = name;
		this.columnDefs = columnDefs;

		for (ColumnDef def : columnDefs)
			columnMap.put(def.getName(), def);
	}

	public ColumnDef getColumnDef(String name) {
		return columnMap.get(name);
	}

	public ColumnDef[] getColumnDefs() {
		return columnDefs;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getColumnCount() {
		return (columnDefs == null) ? 0 : columnDefs.length;
	}

}
