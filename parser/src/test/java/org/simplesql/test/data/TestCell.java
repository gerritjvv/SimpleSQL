package org.simplesql.test.data;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.StringCell;

public class TestCell extends TestCase {

	@Test
	public void testInc() {

		Cell[] cells = new Cell[] { new IntCell(), new LongCell(),
				new DoubleCell() };

		for (Cell cell : cells) {

			int v = cell.getIntValue();
			cell.inc(10);

			assertEquals(v + 10, cell.getIntValue());

		}

	}

	public void testStringInc() {

		StringCell cell = new StringCell();
		cell.inc();

		assertEquals(0, cell.getIntValue());
	}

}
