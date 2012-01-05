package org.simplesql.schema;

import org.simplesql.data.Cell;

/**
 * 
 * Defines an immutable column definition
 *
 * @param <T>
 */
public class SimpleColumnDef<T> implements ColumnDef {

	final Class<? extends T> javaType;
	final String name;
	final Cell<? extends T> cell;
	final boolean isNumber;

	public SimpleColumnDef(Class<? extends T> javaType, String name,
			Cell<? extends T> cell) {
		super();
		this.javaType = javaType;
		this.name = name;
		this.cell = cell;

		isNumber = Number.class.isAssignableFrom(javaType);

	}

	public Class<? extends T> getJavaType() {
		return javaType;
	}

	public String getName() {
		return name;
	}

	public Cell<? extends T> getCell() {
		return cell;
	}

	public boolean isNumber() {
		return isNumber;
	}

}
