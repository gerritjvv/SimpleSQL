package org.simplesql.wal.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class TestCheckPointServiceImpl {

	File baseDir;

	@Test
	public void testInc() throws IOException {

		CheckPointServiceImpl service = new CheckPointServiceImpl(baseDir);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			service.incCheckPoint("test", 1);
		}

		System.out.println("Time taken: "
				+ (System.currentTimeMillis() - start));

		CheckPointServiceImpl service2 = new CheckPointServiceImpl(baseDir);
		long chk = service2.getCheckPoint("test");

		assertEquals(100000, chk);
	}

	@Test
	public void testIncDifferentCounters() throws IOException {

		CheckPointServiceImpl service = new CheckPointServiceImpl(baseDir);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			for (int a = 0; a < 1000; a++)
				service.incCheckPoint("test" + i, 1);
		}

		System.out.println("Time taken: "
				+ (System.currentTimeMillis() - start));

		CheckPointServiceImpl service2 = new CheckPointServiceImpl(baseDir);
		for (int i = 0; i < 100; i++) {

			long chk = service2.getCheckPoint("test" + i);
			assertEquals(1000, chk);
		}

	}

	@Before
	public void setup() throws IOException {

		baseDir = new File("target/test/TestFileCheckPointService");

		if (baseDir.exists())
			FileUtils.deleteDirectory(baseDir);

		baseDir.mkdirs();
	}
}
