package org.simplesql.funct;

import org.simplesql.data.TransformFunction;

/**
 * 
 * 
 * @param <Cell>
 */
public class SUM implements TransformFunction {

	final int index;

	public SUM(int index) {
		super();
		this.index = index;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void apply(org.simplesql.data.Cell[] cells,
			org.simplesql.data.Cell[] input) {

		cells[index].inc(input[index]);

	}

}
