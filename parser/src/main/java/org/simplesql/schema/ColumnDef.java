package org.simplesql.schema;

import org.simplesql.data.Cell;

/**
 * 
 * Defines a column interface and methods
 *
 */
public interface ColumnDef<T> {

	Class<? extends T> getJavaType();
	
	Cell<? extends T> getCell();
	
	boolean isNumber();
	
	String getName();
		
}
