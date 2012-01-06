package org.simplesql.parser.tree;

import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.DynamicCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.StringCell;

public class TERM {

	enum TYPE {
		INTEGER(1, IntCell.class), LONG(2, LongCell.class), DOUBLE(3,
				DoubleCell.class), STRING(4, StringCell.class), UKNOWN(5,
				DynamicCell.class), AGGREGATE_TOP(6, DynamicCell.class), AGGREGATE_COUNT(
				7, DynamicCell.class), AGGREGATE_SUM(8, DynamicCell.class);

		int wieght;
		Class<? extends Cell> cellType;

		TYPE(int w, Class<? extends Cell> cellType) {
			this.wieght = w;
			this.cellType = cellType;
		}

		public TYPE max(TYPE t) {
			return (t.wieght > wieght) ? t : this;
		}

		public Class<? extends Cell> getCellType() {
			return cellType;
		}

	}

	TYPE type;

	public TERM(TYPE type) {
		super();
		this.type = type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public TYPE getType() {
		return type;
	}

}
