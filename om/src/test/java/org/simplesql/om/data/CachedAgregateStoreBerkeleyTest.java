package org.simplesql.om.data;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.Key;
import org.simplesql.data.LongCell;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.StringCell;
import org.simplesql.om.data.stores.BerkeleyAggregateStore;
import org.simplesql.om.data.stores.CachedAggregateStore;
import org.simplesql.om.data.stores.berkeley.DBManager;

public class CachedAgregateStoreBerkeleyTest {

	static DBManager dbManager;
	static boolean isSetup = false;

	@Test
	public void testAddToMapNoFunctions() throws InterruptedException {
		setup();
		CachedAggregateStore map = new CachedAggregateStore(
				new BerkeleyAggregateStore(dbManager, null),
				Executors.newCachedThreadPool(), 500);

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
			
			
			Thread.sleep(2000);

			assertEquals(keys, map.keys());
			for (Key key : keys) {
				assertEquals(4, map.get(key).getCells().length);
			}
		} finally {
			map.close();
		}
	}

	@BeforeClass
	public static void setup() {
		if (isSetup)
			return;

		dbManager = new DBManager(new File(
				"target/test/CachedAgregateStoreBerkeleyTest/"
						+ System.nanoTime() + "/db"));
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
