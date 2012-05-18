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

	@Override
	public byte[] serialize() {
		Builder builder = TableTemplate.Table.newBuilder();
		builder.setName(name);
		builder.setEngine(engine);

		for (ColumnDef col : columnDefs) {
			org.simplesql.schema.TableTemplate.Column.Builder colBuilder = TableTemplate.Column
					.newBuilder();
			colBuilder.setName(col.getName());
			colBuilder.setFamily(col.getFamily());
			colBuilder.setIsCounter(col.isCounter());
			colBuilder.setIskey(col.isKey());

			Cell cell = col.getCell();
			colBuilder.setType(cell.getSchema().name());
			colBuilder.setWidth(cell.getDefinedWidth());
			colBuilder.setJavaType(col.getJavaType().getName());

			builder.addColumn(colBuilder.build());

		}

		return builder.build().toByteArray();

	}

	@Override
	public void merge(byte[] arr) {
		try {
			Table table = TableTemplate.Table.newBuilder().mergeFrom(arr)
					.build();

			name = table.getName();
			engine = table.getEngine();

			columnDefs.clear();
			columnMap.clear();

			for (Column col : table.getColumnList()) {
				SimpleColumnDef colDef = new SimpleColumnDef();
				colDef.setName(col.getName());
				colDef.setFamily(col.getFamily());
				colDef.setCounter(col.getIsCounter());
				colDef.setKey(col.getIskey());

				Cell cell = Cell.SCHEMA.valueOf(col.getType()).newCell();
				if (cell instanceof StringCell) {
					((StringCell) cell).setWidth(colDef.getWidth());
				}

				colDef.setCell(cell);
				colDef.setJavaType(Thread.currentThread()
						.getContextClassLoader().loadClass(col.getJavaType()));

				addColumn(colDef);
			}

		} catch (InvalidProtocolBufferException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		} catch (ClassNotFoundException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

	}
}
