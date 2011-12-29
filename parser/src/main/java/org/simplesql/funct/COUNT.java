package org.simplesql.funct;

import org.simplesql.data.Cell;
import org.simplesql.data.TransformFunction;

/**
 * 
 * Count the number of rows i.e. the number of types apply was called.<br/>
 * The cell that changes is the cell at cells[index].
 * 
 * @param <Cell>
 */
public class COUNT implements TransformFunction {

	final int index;
	boolean started = false;

	public COUNT(int index) {
		super();
		this.index = index;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void apply(Cell[] cells, Cell[] input) {
		if (!started) {
			cells[index].setData(1);
			started = true;
		} else
			cells[index].inc();
	}

}
