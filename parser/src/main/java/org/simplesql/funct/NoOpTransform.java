package org.simplesql.funct;

import org.simplesql.data.Cell;
import org.simplesql.data.TransformFunction;

/**
 * 
 * Does not perform any operation
 * 
 * @param <Cell>
 */
public class NoOpTransform implements TransformFunction {

	@Override
	public final void apply(Cell[] cells, Cell[] input) {
	}

}
