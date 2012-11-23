package org.simplesql.parser.tree;

import org.simplesql.data.BooleanCell;
import org.simplesql.data.ByteCell;
import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.DynamicCell;
import org.simplesql.data.FloatCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.ShortCell;
import org.simplesql.data.StringCell;

public class TERM {

	public enum TYPE {
		INTEGER(1, IntCell.class), LONG(2, LongCell.class), BOOLEAN(1,
				BooleanCell.class), DOUBLE(3, DoubleCell.class), FLOAT(4,
				FloatCell.class), SHORT(5, ShortCell.class), BYTE(6,
				ByteCell.class), STRING(7, StringCell.class), UKNOWN(8,
				DynamicCell.class), AGGREGATE_TOP(9, DynamicCell.class), AGGREGATE_COUNT(
				10, LongCell.class), AGGREGATE_SUM(11, DoubleCell.class);

		int weight;
		Class<? extends Cell> cellType;

		TYPE(int w, Class<? extends Cell> cellType) {
			this.weight = w;
			this.cellType = cellType;
		}

		public TYPE max(TYPE t) {
			return (t.weight > weight) ? t : this;
		}

		public Class<? extends Cell> getCellType() {
			return cellType;
		}

	}

	/**
	 * The name of the variable used or via the "as" keyword
	 */
	String assignedName;
	TYPE type;
	Object value;

	public TERM(TYPE type) {
		super();
		this.type = type;
	}

	public TERM(TYPE type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public TYPE getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getAssignedName() {
		return assignedName;
	}

	public void setAssignedName(String assignedName) {
		this.assignedName = assignedName;
	}

}
