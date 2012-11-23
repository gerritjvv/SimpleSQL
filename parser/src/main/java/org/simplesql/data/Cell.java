package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.parser.tree.TERM;

public interface Cell<T> extends Counter, Serializable, Comparable<Cell<T>> {

	enum SCHEMA {
		INT(IntCell.class, TERM.TYPE.INTEGER), DOUBLE(DoubleCell.class,
				TERM.TYPE.DOUBLE), LONG(LongCell.class, TERM.TYPE.LONG), BOOLEAN(
				BooleanCell.class, TERM.TYPE.BOOLEAN), DYNAMIC(
				DynamicCell.class, null), STRING(StringCell.class,
				TERM.TYPE.STRING), FLOAT(FloatCell.class, TERM.TYPE.FLOAT), SHORT(
				ShortCell.class, TERM.TYPE.SHORT), BYTE(ByteCell.class,
				TERM.TYPE.BYTE);

		@SuppressWarnings("rawtypes")
		final Class cellCls;
		final TERM.TYPE treeType;

		@SuppressWarnings("rawtypes")
		SCHEMA(Class cellCls, TERM.TYPE treeType) {
			this.cellCls = cellCls;
			this.treeType = treeType;
		}

		@SuppressWarnings("rawtypes")
		public Cell newCell() {
			try {
				return (Cell) cellCls.newInstance();
			} catch (InstantiationException e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			} catch (IllegalAccessException e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			}
		}

		public TERM.TYPE getTreeType() {
			return treeType;
		}

	}

	int getDefinedWidth();

	String getName();

	T getData();

	SCHEMA getSchema();

	void setData(T dat);

	Cell<T> copy(boolean resetToDefaults);

	Object getMax();

	Object getMin();

	void putHash(HashCodeBuilder builder);

	int byteLength();

	/**
	 * 
	 * @param arr
	 * @param from
	 * @return the number of bytes writen
	 */
	int write(byte[] arr, int from);

	/**
	 * 
	 * @param arr
	 * @param from
	 * @return the number of bytes read
	 */
	int read(byte[] arr, int from);

	public void readFields(DataInput in) throws IOException;

	public void write(DataOutput out) throws IOException;

	public Class<?> getJavaType();

}
