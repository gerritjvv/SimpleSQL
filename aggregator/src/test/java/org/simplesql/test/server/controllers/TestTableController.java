package org.simplesql.test.server.controllers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.simplesql.om.data.KratiTableRepo;
import org.simplesql.schema.TableDef;
import org.simplesql.server.controllers.TableController;
import org.simplesql.table.TableRepo;

public class TestTableController {

	static TableRepo repo;
	static PropertiesConfiguration appConf;

	@Test
	public void testTableCreate() throws UnsupportedEncodingException {
		TableController controller = new TableController(repo);

		HttpRequest request = new DefaultHttpRequest(
				HttpVersion.HTTP_1_1,
				HttpMethod.GET,
				"/query/select/testtable"// ENGINE hbase
						+ "?sql=\"CREATE TABLE testtable (id INT KEY) ENGINE hbase\"");

		HttpResponse resp = controller.create(request, "testtable");

		assertEquals(HttpResponseStatus.CREATED, resp.getStatus());

		TableDef tblDef = repo.getTable("testtable");

		assertEquals("testtable", tblDef.getName());
		assertEquals(1, tblDef.getColumnCount());

	}

	@Test
	public void testTableCreateDelete() throws UnsupportedEncodingException {
		TableController controller = new TableController(repo);

		HttpRequest request = new DefaultHttpRequest(
				HttpVersion.HTTP_1_1,
				HttpMethod.GET,
				"/query/select/testtable"// ENGINE hbase
						+ "?sql=\"CREATE TABLE testtable (id INT KEY) ENGINE hbase\"");

		HttpResponse resp = controller.create(request, "testtable");

		assertEquals(HttpResponseStatus.CREATED, resp.getStatus());

		TableDef tblDef = repo.getTable("testtable");

		assertEquals("testtable", tblDef.getName());
		assertEquals(1, tblDef.getColumnCount());

		// delete
		request = new DefaultHttpRequest(HttpVersion.HTTP_1_1,
				HttpMethod.DELETE, "/query/select/testtable");

		resp = controller.delete(request, "testtable");
		assertEquals(HttpResponseStatus.OK, resp.getStatus());

		tblDef = repo.getTable("testtable");
		assertNull(tblDef);

	}

	@BeforeClass
	public static void setup() throws Throwable {

		repo = new KratiTableRepo();

		appConf = new PropertiesConfiguration();

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
		repo.close();
	}

}
