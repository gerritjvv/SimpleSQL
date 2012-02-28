package org.simplesql.om.data;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.simplesql.data.CellTuple;
import org.simplesql.data.IntCell;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.StringCell;
import org.simplesql.om.data.stores.berkeley.DBManager;

import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;

/**
 * 
 * Test the DBManager
 * 
 */
public class DatabaseManagerTest {

	/**
	 * Test that we can open a Database, create a Map, write and read from it.
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testDBCreate() throws Throwable {

		DBManager dbManager = new DBManager(new File(
				"target/test/databaseManagerTest/db"));
		try {
			Database db = dbManager.openDatabase("test");
			StoredMap<SimpleCellKey, CellTuple> map = dbManager.createMap(db);

			try {
				map.put(new SimpleCellKey(new IntCell(1)), new CellTuple(
						new StringCell("Hi"), new IntCell(1), new IntCell(2),
						new IntCell(3)));

				CellTuple tuple = map.get(new SimpleCellKey(new IntCell(1)));
				assertEquals(4, tuple.getCells().length);
				assertEquals("Hi", tuple.getCells()[0].getData());
				assertEquals(1, tuple.getCells()[1].getData());
				assertEquals(2, tuple.getCells()[2].getData());
				assertEquals(3, tuple.getCells()[3].getData());

			} finally {
				db.close();
			}
		} finally {
			dbManager.close();
		}

	}

}
