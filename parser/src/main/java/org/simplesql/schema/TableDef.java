package org.simplesql.schema;

/**
 * 
 * Defines the concept of a Table.<br/>
 * A table is nothing more than a grouping columns. Each DataSource read with a<br/>
 * Table schema applied has its columns filtered as per the ColumnDefs.
 * 
 */
public interface TableDef {

	String getEngine();
	String getName();
	ColumnDef[] getColumnDefs();
	ColumnDef getColumnDef(String colName);
	int getColumnCount();
	
	byte[] serialize();
	TableDef merge(byte[] arr);
	
}
