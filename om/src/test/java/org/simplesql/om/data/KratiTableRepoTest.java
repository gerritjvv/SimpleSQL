package org.simplesql.om.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

public class KratiTableRepoTest {

	File baseDir;
	Configuration conf;
	KratiTableRepo repo;

	/**
	 * Test that we can store and then retreive tables
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testStoreAndGet() throws Throwable {

		Map<String, TableDef> names = new Hashtable<String, TableDef>();
		int len = 100;
		for (int i = 0; i < len; i++) {
			TableDef tbl = getTable("tbl_" + i);
			repo.setTable(tbl);
			names.put("tbl_" + i, tbl);
		}

		for (int i = 0; i < len; i++) {
			TableDef tbl = repo.getTable("tbl_" + i);
			assertTrue(tbl != null);
			assertEquals("tbl_" + i, tbl.getName());
			TableDef tbl2 = names.get(tbl.getName());

			assertTrue(tbl2 != null);
			assertEquals(tbl.getName(), tbl2.getName());
			assertEquals(tbl.getColumnCount(), tbl2.getColumnCount());
		}

	}

	/**
	 * Test that we can add and then remove table entries
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testStoreAndRemove() throws Throwable {

		Map<String, TableDef> names = new Hashtable<String, TableDef>();
		int len = 100;
		for (int i = 0; i < len; i++) {
			TableDef tbl = getTable("storeremove_tbl_" + i);
			repo.setTable(tbl);
			names.put("storeremove_tbl_" + i, tbl);
		}

		repo.removeTable("storeremove_tbl_" + 5);
		repo.removeTable("storeremove_tbl_" + 8);

		assertNull(repo.getTable("storeremove_tbl_" + 5));
		assertNull(repo.getTable("storeremove_tbl_" + 8));

		// count tables
		int count = 0;
		for (String tblName : repo) {
			count++;
			assertFalse(("storeremove_tbl_" + 5).equals(tblName));
			assertFalse(("storeremove_tbl_" + 8).equals(tblName));
		}

		assertEquals(len - 2, count);

	}

	private static final TableDef getTable(String name)
			throws RecognitionException {

		String sql = "CREATE TABLE "
				+ name
				+ " (col INT KEY, col2 STRING 100 KEY=true, col3 LONG, col4 LONG COUNTER) ENGINE HBASE";

		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		parser.create();

		SimpleTableDef tbl = parser.tableDef;
		assert tbl != null;

		return tbl;
	}

	@After
	public void after() {
		repo.close();
	}

	@Before
	public void before() throws Exception {

		baseDir = new File("target/test/kratiTableRepoTest");
		if (!baseDir.exists())
			baseDir.mkdirs();
		else {
			FileUtils.deleteDirectory(baseDir);
			baseDir.mkdirs();
		}

		conf = new PropertiesConfiguration();
		conf.setProperty(KratiTableRepo.REPO_DIR, baseDir.getAbsolutePath());

		repo = new KratiTableRepo();
		repo.init(conf);

	}

}
