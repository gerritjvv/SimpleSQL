package org.simplesql.test.data.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.StringCell;
import org.simplesql.data.impl.HashMapAggregateStore;
import org.simplesql.funct.COUNT;
import org.simplesql.funct.PassThroughTransform;
import org.simplesql.funct.SUM;

/**
 * 
 * Test the Map implementation of AggregateStore
 * 
 */
public class HashMapAggregateStoreTest extends TestCase {

	@Test
	public void testAddToWithCountSumTransform() {
		
		/**
		 * 
		 * COUNT IntCell start=10 expect 10
		 * COUNT LongCell start=10 expect 10
		 * SUM DoubleCell start=0.02 expect 0.2
		 * 
		 * StringCell("Test");
		 */
		HashMapAggregateStore map = new HashMapAggregateStore(new COUNT(0),
				new COUNT(1), new SUM(2), new PassThroughTransform(3));


		int rows = 10;
		List<String> keys = new ArrayList<String>();
		keys.add("A");
		keys.add("B");
		keys.add("C");
		keys.add("D");

		for (String key : keys) {
			for (int i = 0; i < rows; i++) {
				map.put(key, getCells());
			}
		}


		for(String key: keys){
			Cell[] cells = map.get(key).getCells();
			
			assertEquals(10, cells[0].getIntValue());
			assertEquals(10, cells[1].getLongValue());
			assertEquals(0.2F, (float)cells[2].getDoubleValue());
			assertEquals("Test", cells[3].getData());
		}

	}

	@Test
	public void testAddToWithLimit() {

		HashMapAggregateStore map = new HashMapAggregateStore(null);
		map.setLimit(2);

		int rows = 10;
		List<String> keys = new ArrayList<String>();
		keys.add("A");
		keys.add("B");
		keys.add("C");
		keys.add("D");

		for (String key : keys) {
			for (int i = 0; i < rows; i++) {
				map.put(key, getCells());
			}
		}

		System.out.println(map.keys());
		assertEquals(2, map.keys().size());

		for (String key : Arrays.asList("A", "B")) {
			assertEquals(4, map.get(key).getCells().length);
		}

		for (String key : Arrays.asList("C", "D")) {
			assertNull(map.get(key));
		}

	}

	@Test
	public void testAddToMapNoFunctions() {

		HashMapAggregateStore map = new HashMapAggregateStore(null);

		int rows = 10;
		Set<String> keys = new HashSet<String>();
		keys.add("A");
		keys.add("B");
		keys.add("C");
		keys.add("D");

		for (String key : keys) {
			for (int i = 0; i < rows; i++) {
				map.put(key, getCells());
			}
		}

		assertEquals(keys, map.keys());
		for (String key : keys) {
			assertEquals(4, map.get(key).getCells().length);
		}

	}

	private Cell[] getCells() {

		Cell[] cells = new Cell[4];
		int i = 0;

		cells[i++] = new IntCell(10);
		cells[i++] = new LongCell(10);
		cells[i++] = new DoubleCell(0.02);
		cells[i++] = new StringCell("Test");

		return cells;
	}

}
