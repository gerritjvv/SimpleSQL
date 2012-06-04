package org.simplesql.hbase;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simplesql.HBaseTestUtil;
import org.simplesql.om.data.KratiTableRepo;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.table.TableEngine.SELECT_OUTPUT;
import org.simplesql.table.TableRepo;
import org.simplesql.util.SQLParserUtil;

/**
 * 
 * Test the select, insert methods of the HBaseTableEngine.
 * 
 */
public class TestHBaseTableEngine {

	static HBaseTestUtil hbaseUtil;
	static HBaseAdmin admin;
	static TableRepo repo;
	static PropertiesConfiguration appConf;

	@Test
	public void testSelect() throws Throwable {

		// create test table

		byte[] colFam1 = Bytes.toBytes("fam1");
		HTableDescriptor desc = new HTableDescriptor("testselect");
		desc.addFamily(new HColumnDescriptor(colFam1));

		// ensure the table is created newly
		createTable(admin, "testselect", desc);

		HTable tbl = new HTable(hbaseUtil.getConf(), "testselect");
		tbl.setAutoFlush(false);

		// key is embedded [4 byte int, 2 chars]
		// columns are count:int, temp:float

		// insert a few values
		int rows = 100;
		for (int i = 0; i < rows; i++) {

			// create key
			byte[] key = new byte[6];
			Bytes.putInt(key, 0, i);
			Bytes.putBytes(key, 4, ("i" + i).getBytes(), 0, 2);

			Put put = new Put(key);
			// add column data
			put.add(colFam1, Bytes.toBytes("count"), Bytes.toBytes(i));
			put.add(colFam1, Bytes.toBytes("temp"),
					Bytes.toBytes((float) (i / 3)));

			tbl.put(put);

		}
		// ensure that all of the data has been written
		tbl.flushCommits();

		// here we create the table definition in the repository
		repo.setTable(SQLParserUtil
				.parseTableDef("CREATE TABLE testselect (fam1 k1 INT KEY, fam1 k2 STRING 2 KEY=true, fam1 count INT, fam1 temp FLOAT)"));

		HBaseTableEngine engine = new HBaseTableEngine();
		engine.init(appConf, repo);

		final SQLCompiler compiler = new SimpleSQLCompiler(
				Executors.newCachedThreadPool());

		final AtomicInteger i = new AtomicInteger(0);

		engine.select(compiler, SQLParserUtil
				.parseSelect("SELECT k1, k2, count, temp FROM testselect"),
				new SELECT_OUTPUT() {

					@Override
					public void write(Object[] values) {
						System.out.println(Arrays.toString(values));
						i.incrementAndGet();
					}
				});

		engine.close();

		assertEquals(100, i.get());

	}

	/**
	 * If the table exists its deleted and then recreated.
	 * 
	 * @param admin
	 * @param tblName
	 * @param desc
	 * @throws Throwable
	 */
	private static final void createTable(HBaseAdmin admin, String tblName,
			HTableDescriptor desc) throws Throwable {
		if (admin.tableExists(tblName)) {
			admin.disableTable(tblName);
			admin.deleteTable(tblName);
		}

		admin.createTable(desc);

	}

	@BeforeClass
	public static void setup() throws Exception {
		if (hbaseUtil != null)
			return;

		hbaseUtil = new HBaseTestUtil();
		hbaseUtil.start();

		admin = new HBaseAdmin(hbaseUtil.getConf());

		repo = new KratiTableRepo();

		appConf = new PropertiesConfiguration();

		Configuration hbaseConf = hbaseUtil.getConf();
		for (Entry<String, String> e : hbaseConf) {
			appConf.setProperty(e.getKey(), e.getValue());
		}

		String repoDir = "target/test/testHbaseTableEngine/repo";
		File file = new File(repoDir);
		if (file.exists()) {
			FileUtils.deleteDirectory(file);
		}

		file.mkdirs();

		appConf.setProperty(KratiTableRepo.REPO_DIR, repoDir);
		repo.init(appConf);

	}

	@AfterClass
	public static void shutdown() throws IOException {
		hbaseUtil.shutdown();
		repo.close();
	}

}
