package org.simplesql.funct;

import org.simplesql.data.Cell;
import org.simplesql.data.TransformFunction;

/**
 * 
 * Overwrites the value
 * 
 * @param <Cell>
 */
public class PassThroughTransform implements TransformFunction {

	final int index;

	public PassThroughTransform(int index) {
		this.index = index;
	}

	@Override
	public final void apply(Cell[] cells, Cell[] input) {
		cells[index] = input[index];
	}
	
}
