package org.simplesql.wal.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestPersistentCounter {

	@Test
	public void testIncGet() throws Throwable {

		File dir = new File("target/test/testPersistentCounterIncr1");
		dir.mkdirs();
		File file = new File(dir, "counter.txt");
		file.delete();
		file.createNewFile();

		PersistentCounter counter = new PersistentCounter(
				Executors.newCachedThreadPool(), file, false);

		long start = System.currentTimeMillis();

		int len = 1000000;

		for (int i = 0; i < len; i++) {
			counter.inc(1);
		}

		long end = System.currentTimeMillis();

		System.out.println("Time taken: " + (end - start));
		counter.close();

		//check that we can re-read the value
		PersistentCounter counter2 = new PersistentCounter(
				Executors.newCachedThreadPool(), file);

		assertEquals(len, counter.getValue());
		assertEquals(counter.getValue(), counter2.getValue());

		counter2.close();
	}

}
