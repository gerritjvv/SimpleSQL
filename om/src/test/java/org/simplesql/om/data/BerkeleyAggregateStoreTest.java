package org.simplesql.om.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DefaultDataSink;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.Key;
import org.simplesql.data.LongCell;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.StringCell;
import org.simplesql.funct.COUNT;
import org.simplesql.funct.PassThroughTransform;
import org.simplesql.funct.SUM;
import org.simplesql.om.data.stores.BerkeleyAggregateStore;
import org.simplesql.om.data.stores.berkeley.DBManager;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * 
 * Test the Map implementation of AggregateStore
 * 
 */
public class BerkeleyAggregateStoreTest extends TestCase {

	static DBManager dbManager;

	public void testOrderByDataDescLimit() {
		/**
		 * 
		 * COUNT IntCell start=10 expect 10 COUNT LongCell start=10 expect 10
		 * SUM DoubleCell start=0.02 expect 0.2
		 * 
		 * StringCell("Test");
		 */
		setup();
		BerkeleyAggregateStore map = new BerkeleyAggregateStore(dbManager,
				new COUNT(0), new COUNT(1), new SUM(2),
				new PassThroughTransform(3));

		map.setOrderKeyBy(null, org.simplesql.data.AggregateStore.ORDER.DESC);
		// we order by data that is accumulated as the query proceeds,
		// i.e. the order is constantly changing.
		map.setOrderByData(new int[] { 2 });

		int limit = 10;

		// get the top 10
		map.setLimit(limit);

		int rows = 10;
		for (int i = 0; i < rows; i++) {
			// add the same key twice
			map.put(new SimpleCellKey(new IntCell(i), new DoubleCell(i + 2)),
					getCells(i, i, i));
			map.put(new SimpleCellKey(new IntCell(i), new DoubleCell(i + 2)),
					getCells(i, i, i));

		}

		final AtomicInteger counter = new AtomicInteger(0);
		final AtomicDouble keyCounter = new AtomicDouble(198);
		// we expect the keys with values at index 0: values 100-95
		map.write(new DefaultDataSink() {

			@Override
			public boolean fill(Key key, Cell<?>[] data) {
				// ensure that the key that the ordering was done on is the data
				// key
				assertEquals(key.getCells()[0], data[2]);

				counter.getAndIncrement();

				// check that values follow 198, 196
				assertEquals(keyCounter.getAndAdd(-2D),
						data[2].getDoubleValue());
				return true;
			}
		});

		assertEquals(limit, counter.get());
	}

	public void testOrderByKeyAscLimit() {
		/**
		 * 
		 * COUNT IntCell start=10 expect 10 COUNT LongCell start=10 expect 10
		 * SUM DoubleCell start=0.02 expect 0.2
		 * 
		 * StringCell("Test");
		 */
		setup();
		BerkeleyAggregateStore map = new BerkeleyAggregateStore(dbManager,
				new COUNT(0), new COUNT(1), new SUM(2),
				new PassThroughTransform(3));

		map.setOrderKeyBy(new int[] { 0 }, org.simplesql.data.AggregateStore.ORDER.ASC);


		// get the top 10
		map.setLimit(10);

		int rows = 10;
		for (int i = 0; i < rows; i++) {
			// add the same key twice
			map.put(new SimpleCellKey(new IntCell(i), new DoubleCell(i + 2)),
					getCells());
			map.put(new SimpleCellKey(new IntCell(i), new DoubleCell(i + 2)),
					getCells());

		}

		final AtomicInteger counter = new AtomicInteger(0);

		// we expect the keys with values at index 0: values 100-95
		map.write(new DefaultDataSink() {

			@Override
			public boolean fill(Key key, Cell<?>[] data) {
				assertEquals(counter.getAndIncrement(),
						key.getCells()[0].getIntValue());
				return true;
			}
		});

		// check that only 10 rows were read
		assertEquals(10, counter.get());
	}

	public void testOrderByKeyDescLimit() {
		/**
		 * 
		 * COUNT IntCell start=10 expect 10 COUNT LongCell start=10 expect 10
		 * SUM DoubleCell start=0.02 expect 0.2
		 * 
		 * StringCell("Test");
		 */
		setup();
		BerkeleyAggregateStore map = new BerkeleyAggregateStore(dbManager,
				new COUNT(0), new COUNT(1), new SUM(2),
				new PassThroughTransform(3));

		map.setOrderKeyBy(new int[] { 0 }, org.simplesql.data.AggregateStore.ORDER.DESC);

		// get the top 10
		map.setLimit(10);

		int rows = 10;
		for (int i = 0; i < rows; i++) {
			// add the same key twice
			map.put(new SimpleCellKey(new IntCell(i), new DoubleCell(i + 2)),
					getCells());
			map.put(new SimpleCellKey(new IntCell(i), new DoubleCell(i + 2)),
					getCells());

		}

		final AtomicInteger counter = new AtomicInteger(rows - 1);

		// we expect the keys with values at index 0: values 100-95
		map.write(new DefaultDataSink() {

			@Override
			public boolean fill(Key key, Cell<?>[] data) {
				assertEquals(counter.getAndDecrement(),
						key.getCells()[0].getIntValue());
				return true;
			}
		});

		// check that only 10 rows were read
		assertEquals(counter.get(), 89);
	}

	// @SuppressWarnings("rawtypes")
	@SuppressWarnings("rawtypes")
	@Test
	public void testAddToWithCountSumTransform() {

		/**
		 * 
		 * COUNT IntCell start=10 expect 10 COUNT LongCell start=10 expect 10
		 * SUM DoubleCell start=0.02 expect 0.2
		 * 
		 * StringCell("Test");
		 */
		setup();
		BerkeleyAggregateStore map = new BerkeleyAggregateStore(dbManager,
				new COUNT(0), new COUNT(1), new SUM(2),
				new PassThroughTransform(3));

		int rows = 10;
		List<Key> keys = new ArrayList<Key>();
		keys.add(new SimpleCellKey("A"));
		keys.add(new SimpleCellKey("B"));
		keys.add(new SimpleCellKey("C"));
		keys.add(new SimpleCellKey("D"));

		for (Key key : keys) {
			for (int i = 0; i < rows; i++) {
				map.put(key, getCells());
			}
		}

		for (Key key : keys) {
			Cell[] cells = map.get(key).getCells();

			assertEquals(10, cells[0].getIntValue());
			assertEquals(10, cells[1].getLongValue());
			assertEquals(0.2F, (float) cells[2].getDoubleValue());
			assertEquals("Test", cells[3].getData());
		}

	}

	@Test
	public void testAddToWithLimit() {

		setup();
		BerkeleyAggregateStore map = new BerkeleyAggregateStore(dbManager, null);

		map.setLimit(2);

		int rows = 10;
		List<Key> keys = new ArrayList<Key>();
		keys.add(new SimpleCellKey("A"));
		keys.add(new SimpleCellKey("B"));
		keys.add(new SimpleCellKey("C"));
		keys.add(new SimpleCellKey("D"));

		for (Key key : keys) {
			for (int i = 0; i < rows; i++) {
				map.put(key, getCells());
			}
		}

		System.out.println(map.keys());
		assertEquals(2, map.keys().size());

		for (String key : Arrays.asList("A", "B")) {
			assertEquals(4, map.get(new SimpleCellKey(key)).getCells().length);
		}

		// for (String key : Arrays.asList("C", "D")) {
		// assertNull(map.get(new SimpleCellKey(key)));
		// }

	}

	@Test
	public void testAddToMapNoFunctions() {
		setup();
		BerkeleyAggregateStore map = new BerkeleyAggregateStore(dbManager, null);
		try {
			int rows = 10;
			Set<Key> keys = new HashSet<Key>();
			keys.add(new SimpleCellKey("A"));
			keys.add(new SimpleCellKey("B"));
			keys.add(new SimpleCellKey("C"));
			keys.add(new SimpleCellKey("D"));

			for (Key key : keys) {
				for (int i = 0; i < rows; i++) {
					map.put(key, getCells());
				}
			}

			assertEquals(keys, map.keys());
			for (Key key : keys) {
				assertEquals(4, map.get(key).getCells().length);
			}
		} finally {
			map.close();
		}
	}

	static boolean isSetup = false;

	@BeforeClass
	public static void setup() {
		if (isSetup)
			return;

		dbManager = new DBManager(new File(
				"target/test/BerkeleyAggregateStoreTest/" + System.nanoTime()
						+ "/db"));
	}

	@AfterClass
	public static void shutdown() {
		dbManager.close();
	}

	private Cell[] getCells(int a, long b, double c) {

		Cell[] cells = new Cell[4];
		int i = 0;

		cells[i++] = new IntCell(a);
		cells[i++] = new LongCell(b);
		cells[i++] = new DoubleCell(c);
		cells[i++] = new StringCell("Test");

		return cells;
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
