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

	public SimpleColumnDef(Class<?> javaType, String name, Cell<?> cell) {
		super();
		this.javaType = javaType;
		this.name = name;
		this.cell = cell;

		isNumber = Number.class.isAssignableFrom(javaType);

	}

	public final Class<?> getJavaType() {
		return javaType;
	}

	public final String getName() {
		return name;
	}

	public final Cell<?> getCell() {
		return cell;
	}

	public final boolean isNumber() {
		return isNumber;
	}

	@Override
	public final byte getByteMax() {
		Object max = cell.getMax();
		if (max instanceof String)
			return (byte) Character.MAX_VALUE * -1;
		else
			return Byte.MAX_VALUE;
	}

	@Override
	public final byte getByteMin() {
		Object min = cell.getMin();
		if (min instanceof String)
			return (byte) Character.MIN_VALUE;
		else
			return Byte.MIN_VALUE;
	}

	@Override
	public final Object getMax() {
		return cell.getMax();
	}

	@Override
	public final Object getMin() {
		return cell.getMin();
	}

}
