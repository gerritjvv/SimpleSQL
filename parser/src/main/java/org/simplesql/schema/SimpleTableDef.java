package org.simplesql.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simplesql.data.Cell;
import org.simplesql.data.StringCell;
import org.simplesql.schema.TableTemplate.Column;
import org.simplesql.schema.TableTemplate.Table;
import org.simplesql.schema.TableTemplate.Table.Builder;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * Immutable TableDef implementation.
 * 
 */
public class SimpleTableDef implements TableDef {

	final List<ColumnDef> columnDefs = new ArrayList<ColumnDef>(10);
	String name;
	final Map<String, ColumnDef> columnMap = new HashMap<String, ColumnDef>();
	String engine = "";

	public SimpleTableDef() {

	}

	public SimpleTableDef(final String name, final ColumnDef... columnDefs) {
		this.name = name;

		for (final ColumnDef def : columnDefs) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public byte[] serialize() {
		final Builder builder = TableTemplate.Table.newBuilder();
		builder.setName(name);
		builder.setEngine(engine);

		for (ColumnDef col : columnDefs) {
			final org.simplesql.schema.TableTemplate.Column.Builder colBuilder = TableTemplate.Column
					.newBuilder();
			colBuilder.setName(col.getName());
			if (col.getFamily() != null)
				colBuilder.setFamily(col.getFamily());
			colBuilder.setIsCounter(col.isCounter());
			colBuilder.setIskey(col.isKey());

			colBuilder.setType(col.getCell().getSchema().name());

			final Cell cell = col.getCell();
			colBuilder.setWidth(cell.getDefinedWidth());
			builder.addColumn(colBuilder.build());

		}

		return builder.build().toByteArray();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public TableDef merge(final byte[] arr) {
		try {
			final Table table = TableTemplate.Table.newBuilder().mergeFrom(arr)
					.build();

			name = table.getName();
			engine = table.getEngine();

			columnDefs.clear();
			columnMap.clear();

			for (final Column col : table.getColumnList()) {
				final SimpleColumnDef colDef = new SimpleColumnDef();
				colDef.setName(col.getName());
				colDef.setFamily(col.getFamily());
				colDef.setCounter(col.getIsCounter());
				colDef.setKey(col.getIskey());

				final Cell cell = Cell.SCHEMA.valueOf(col.getType()).newCell();
				if (cell instanceof StringCell) {
					((StringCell) cell).setWidth(col.getWidth());
				}

				colDef.setCell(cell);

				addColumn(colDef);
			}

		} catch (InvalidProtocolBufferException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

		return this;
	}
	
}
