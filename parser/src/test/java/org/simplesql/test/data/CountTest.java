package org.simplesql.test.data;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.IntCell;
import org.simplesql.funct.COUNT;

public class CountTest extends TestCase {

	@SuppressWarnings("rawtypes")
	@Test
	public void testCountInt() {

		Cell[] cells = new Cell[] { new IntCell(), new IntCell() };

		Cell[] data = new Cell[] { new IntCell(), new IntCell(10) };

		COUNT sum = new COUNT(1);

		sum.apply(cells, data);
		sum.apply(cells, data);

		assertEquals(2, cells[1].getIntValue());

	}

}
