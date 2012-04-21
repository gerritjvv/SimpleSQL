package org.simplesql.om.data;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.Key;
import org.simplesql.data.LongCell;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.StringCell;
import org.simplesql.funct.COUNT;
import org.simplesql.funct.PassThroughTransform;
import org.simplesql.funct.SUM;
import org.simplesql.om.data.stores.KratiAggregateStore;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * 
 * Test the Map implementation of AggregateStore
 * 
 */
public class KratiAggregateStoreTest extends TestCase {

	public void testOrderByDataDescLimit() throws Exception {
		/**
		 * 
		 * COUNT IntCell start=10 expect 10 COUNT LongCell start=10 expect 10
		 * SUM DoubleCell start=0.02 expect 0.2
		 * 
		 * StringCell("Test");
		 */

		org.simplesql.data.Cell.SCHEMA[] schemas;

		Cell[] cells = getCells(1, 1, 1);
		int len = cells.length;
		schemas = new org.simplesql.data.Cell.SCHEMA[len];

		for (int i = 0; i < len; i++) {
			schemas[i] = cells[i].getSchema();
		}

		KratiAggregateStore map = new KratiAggregateStore(1, new File(
				"target/test/KratiAggregateStoreTest"), schemas, new COUNT(0),
				new COUNT(1), new SUM(2), new PassThroughTransform(3));

		map.setOrderKeyBy(null, org.simplesql.data.AggregateStore.ORDER.DESC);
		// we order by data that is accumulated as the query proceeds,
		// i.e. the order is constantly changing.
		map.setOrderByData(new int[] { 2 });

		int limit = 10;

		// get the top 10
		map.setLimit(limit);

		int rows = 100;
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
		map.write(new DataSink() {

			@Override
			public boolean fill(Key key, Cell<?>[] data) {
				// ensure that the key that the ordering was done on is the data
				// key

				System.out.println("key: " + key.asString() + " data: "
						+ Arrays.toString(data));
				 assertEquals(key.getCells()[0].getIntValue(), data[2].getIntValue());

				 counter.getAndIncrement();

				// check that values follow 198, 196
//				 assertEquals(keyCounter.getAndAdd(-2D),
//				 data[2].getDoubleValue());
				return true;
			}
		});

		// assertEquals(limit, counter.get());
	}

	static boolean isSetup = false;

	@BeforeClass
	public static void setup() {
	}

	@AfterClass
	public static void shutdown() {
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
