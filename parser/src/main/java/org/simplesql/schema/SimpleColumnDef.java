package org.simplesql.schema;

import org.simplesql.data.Cell;

/**
 * 
 * Defines an immutable column definition
 *
 * @param <T>
 */
public class SimpleColumnDef implements ColumnDef {

	final Class<?> javaType;
	final String name;
	final Cell<?> cell;
	final boolean isNumber;

	public SimpleColumnDef(Class<?> javaType, String name,
			Cell<?> cell) {
		super();
		this.javaType = javaType;
		this.name = name;
		this.cell = cell;

		isNumber = Number.class.isAssignableFrom(javaType);

	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public String getName() {
		return name;
	}

	public Cell<?> getCell() {
		return cell;
	}

	public boolean isNumber() {
		return isNumber;
	}

	@Override
	public Object getMax() {
		return cell.getMax();
	}

	@Override
	public Object getMin() {
		return cell.getMin();
	}

}
