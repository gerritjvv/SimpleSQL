package org.simplesql.schema;

import org.simplesql.data.Cell;

/**
 * 
 * Defines a column interface and methods
 * 
 */
public interface ColumnDef {

	Class<?> getJavaType();

	Cell<?> getCell();

	boolean isNumber();

	String getName();

	Object getMax();

	Object getMin();

	byte getByteMin();

	byte getByteMax();
	
	boolean isKey();
	
}
