package org.simplesql.data;

import java.io.Serializable;

import com.google.common.hash.Hasher;

public interface Cell<T> extends Counter, Serializable, Comparable<Cell<T>> {

	enum SCHEMA {
		INT(IntCell.class), DOUBLE(DoubleCell.class), LONG(LongCell.class), BOOLEAN(
				BooleanCell.class), DYNAMIC(DynamicCell.class), STRING(
				StringCell.class);

		@SuppressWarnings("rawtypes")
		Class cellCls;

		@SuppressWarnings("rawtypes")
		SCHEMA(Class cellCls) {
			this.cellCls = cellCls;
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

	}

	String getName();

	T getData();

	SCHEMA getSchema();

	void setData(T dat);

	Cell<T> copy(boolean resetToDefaults);

	Object getMax();

	Object getMin();

	Hasher putHash(Hasher hasher);

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

}
