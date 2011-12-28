package org.simplesql.data;

/**
 * Aggregate functions should not store state, but rely on the prevCells,
 * currCells and return variables.
 */
public interface TransformFunction {

	/**
	 * An aggregate Function can apply aggregation or transformation functions
	 * by viewing the previous data row in comparison to the current row.
	 * 
	 * 
	 * @param cells
	 * @param input
	 */
	void apply(Cell[] cells, Cell[] input);

}
