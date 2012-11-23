package org.simplesql.test.server.controllers;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.myrest.io.PlainText;
import org.simplesql.HBaseTestUtil;
import org.simplesql.hbase.HBaseTableEngine;
import org.simplesql.om.data.KratiTableRepo;
import org.simplesql.server.controllers.QueryController;
import org.simplesql.table.SimpleTableEngineManager;
import org.simplesql.table.TableEngineManager;
import org.simplesql.table.TableRepo;
import org.simplesql.util.SQLParserUtil;

public class TestQueryController {

	static HBaseTestUtil hbaseUtil;
	static HBaseAdmin admin;
	static TableRepo repo;
	static PropertiesConfiguration appConf;
	static TableEngineManager engineManager;

	@Test
	public void testSelect() throws Throwable {

		String tableName = "testselect";

		QueryController controller = new QueryController(engineManager, repo);
		HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
				HttpMethod.GET, "/query/select/" + tableName
						+ "?sql=\"SELECT k1, k2, count, temp FROM " + tableName
						+ "\"");
		HttpResponse resp = controller.select(request, tableName);

		assertEquals(resp.getStatus(), HttpResponseStatus.OK);
		PlainText txt = (PlainText) resp;
		String[] lines = StringUtils.split(txt.asString(), '\n');

		assertEquals(100, lines.length);

	}

	@BeforeClass
	public static void setup() throws Throwable {
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

		appConf.setProperty("engine.hbase", HBaseTableEngine.class.getName());
		engineManager = new SimpleTableEngineManager(appConf, repo);

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
				.parseTableDef("CREATE TABLE testselect (fam1 k1 INT KEY, fam1 k2 STRING 2 KEY=true, fam1 count INT, fam1 temp FLOAT) ENGINE hbase"));

	}

	private static final void createTable(HBaseAdmin admin, String tblName,
			HTableDescriptor desc) throws Throwable {
		if (admin.tableExists(tblName)) {
			admin.disableTable(tblName);
			admin.deleteTable(tblName);
		}

		admin.createTable(desc);

	}

	@AfterClass
	public static void shutdown() throws IOException {
		hbaseUtil.shutdown();
		repo.close();
	}

}
