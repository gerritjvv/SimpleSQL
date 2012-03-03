package org.simplesql.wal.impl;

import java.io.File;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestPersistentCounter {

	@Test
	public void testInc() throws Throwable {

		File dir = new File("target/test/testPersistentCounterIncr1");
		dir.mkdirs();
		File file = new File(dir, "counter.txt");
		file.createNewFile();

		PersistentCounter counter = new PersistentCounter(
				Executors.newCachedThreadPool(), file);

		long start = System.currentTimeMillis();

		int len = 1000000;

		for (int i = 0; i < len; i++) {
			counter.inc(i);
		}

		long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start));
		counter.close();
	}

}
