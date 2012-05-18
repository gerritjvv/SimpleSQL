package org.simplesql.schema;

import org.simplesql.data.Cell;
import org.simplesql.data.StringCell;
import org.simplesql.util.NumberUtil;

/**
 * 
 * Defines an immutable column definition
 * 
 * @param <T>
 */
public class SimpleColumnDef implements ColumnDef {

	Class<?> javaType;
	String name;
	Cell<?> cell;
	boolean isNumber;
	boolean isKey;
	String family;
	boolean isCounter;

	int width;
	int order;

	public SimpleColumnDef() {

	}

	public SimpleColumnDef(Class<?> javaType, String name, Cell<?> cell,
			boolean isKey, String family, boolean isCounter) {
		super();
		this.javaType = javaType;
		this.name = name;
		this.cell = cell;
		this.isKey = isKey;
		this.family = family;
		this.isCounter = isCounter;
		isNumber = NumberUtil.isNumber(javaType);
	}

	public SimpleColumnDef(Class<?> javaType, String name, Cell<?> cell) {
		this(javaType, name, cell, false, "", false);
	}

	
	public void setType(String type) {
		cell = Cell.SCHEMA.valueOf(type).newCell();
	}

	public int getWidth() {
		return cell.getDefinedWidth();
	}

	public void setWidth(int width) {
		if (cell instanceof StringCell) {
			((StringCell) cell).setWidth(width);
		}
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCell(Cell<?> cell) {
		this.cell = cell;
	}

	public void setNumber(boolean isNumber) {
		this.isNumber = isNumber;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public void setCounter(boolean isCounter) {
		this.isCounter = isCounter;
	}

	public String getFamily() {
		return family;
	}

	public boolean isCounter() {
		return isCounter;
	}

	public boolean isKey() {
		return isKey;
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
