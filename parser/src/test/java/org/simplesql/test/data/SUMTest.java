package org.simplesql.test.data;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.IntCell;
import org.simplesql.funct.SUM;

public class SUMTest extends TestCase{

	@SuppressWarnings("rawtypes")
	@Test
	public void testSumInt(){
		
		Cell[] cells = new Cell[]{ new IntCell(), new IntCell() };
		
		Cell[] data = new Cell[]{ new IntCell(), new IntCell(10) };
		
		SUM sum = new SUM(1);
		
		sum.apply(cells, data);
		
		assertEquals(10, cells[1].getIntValue());
		
	}
	
	
	
}
