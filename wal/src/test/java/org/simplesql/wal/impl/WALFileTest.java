package org.simplesql.wal.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.simplesql.wal.EachClosure;
import org.simplesql.wal.impl.WALFile;

/**
 * 
 * Test simple file write WAL.
 * 
 */
public class WALFileTest {

	private final String[] data = getData();

	@Test
	public void testReadWAL() throws IOException {

		WALFile wal = createWAL("target/test/WALFileTest/testReadWAL");

		for (String d : data) {
			wal.write(d);
		}

		wal.flush();
		wal.close();

		// Test Hour File
		File file = wal.getFile();
		final String[] fileData = FileUtils.readLines(file).toArray(
				new String[0]);

		wal.eachLineFrom(10, new EachClosure<String>() {

			@Override
			public void call(String line, int index) {
				assertEquals(fileData[index], line);
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSingleMessageWrite() throws IOException {

		WALFile wal = createWAL("target/test/WALFileTest/testSingleMessageWrite");

		for (String d : data) {
			wal.write(d);
		}

		wal.flush();
		wal.close();

		// Test Hour File
		File file = wal.getFile();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
		format.format(new Date());
		Date date = new Date();
		String fileName = "request." + format.format(date) + ".log";

		assertEquals(file.getName(), fileName);

		String[] fileData = FileUtils.readLines(file).toArray(new String[0]);

		Arrays.sort(data);
		Arrays.sort(fileData);

		assertEquals(data, fileData);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMultiMessageWrite() throws IOException {

		WALFile wal = createWAL("target/test/WALFileTest/testSingleMessageWrite");

		wal.write(data);

		wal.flush();
		wal.close();

		// Test Hour File
		File file = wal.getFile();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
		format.format(new Date());
		Date date = new Date();
		String fileName = "request." + format.format(date) + ".log";

		assertEquals(file.getName(), fileName);

		String[] fileData = FileUtils.readLines(file).toArray(new String[0]);

		Arrays.sort(data);
		Arrays.sort(fileData);

		assertEquals(data, fileData);
	}

	private static String[] getData() {
		String[] lines = new String[100];
		for (int i = 0; i < lines.length; i++)
			lines[i] = "testline_" + i;

		return lines;
	}

	private static WALFile createWAL(String baseDir) throws IOException {

		File file = new File(baseDir);
		if (file.exists())
			FileUtils.deleteDirectory(file);

		file.mkdirs();

		return new WALFile(baseDir);
	}

}
