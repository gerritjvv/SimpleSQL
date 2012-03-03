package org.simplesql.wal.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.simplesql.wal.EachClosure;
import org.simplesql.wal.event.SimpleEventTemplate.SimpleEvent;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

/**
 * 
 * Test simple writing Request GPB to the GPBWAL delagating to a WALFile
 * instance.
 * 
 */
public class GPBWALTest {

	private final SimpleEvent[] data = getData();
	WALFile walFile = null;

	@Test
	public void testReadWAL() throws IOException {

		GPBWALImpl wal = createWAL("target/test/GPBWALTest/testReadWAL");

		for (SimpleEvent d : data) {
			wal.write(d);
		}

		wal.flush();
		wal.close();

		// Test Hour File
		File file = walFile.getFile();
		final String[] fileData = messageToString(base64ToUTF(FileUtils
				.readLines(file).toArray(new String[0])));

		wal.eachLineFrom(10, SimpleEvent.newBuilder(), new EachClosure<Message>() {

			@Override
			public void call(Message t, int index) {
				assertEquals(fileData[index],
						messageToString(new SimpleEvent[] { (SimpleEvent) t })[0]);
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSingleMessageWrite() throws IOException {

		GPBWALImpl wal = createWAL("target/test/WALFileTest/testSingleMessageWrite");

		for (SimpleEvent d : data) {
			wal.write(d);
		}

		wal.flush();
		wal.close();

		// Test Hour File
		File file = walFile.getFile();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
		format.format(new Date());
		Date date = new Date();
		String fileName = "request." + format.format(date) + ".log";

		assertEquals(file.getName(), fileName);

		String[] fileData = messageToString(base64ToUTF(FileUtils.readLines(
				file).toArray(new String[0])));
		String[] strData = messageToString(data);

		Arrays.sort(strData);
		Arrays.sort(fileData);

		assertEquals(strData, fileData);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMultiMessageWrite() throws IOException {

		GPBWALImpl wal = createWAL("target/test/GPBWALFileTest/testSingleMessageWrite");

		wal.write(data);

		wal.flush();
		wal.close();

		// Test Hour File
		File file = walFile.getFile();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
		format.format(new Date());
		Date date = new Date();
		String fileName = "request." + format.format(date) + ".log";

		assertEquals(file.getName(), fileName);

		String[] fileData = messageToString(base64ToUTF(FileUtils.readLines(
				file).toArray(new String[0])));
		String[] strData = messageToString(data);
		Arrays.sort(strData);
		Arrays.sort(fileData);

		assertEquals(strData, fileData);
	}

	private static SimpleEvent[] base64ToUTF(String[] base64)
			throws UnsupportedEncodingException, InvalidProtocolBufferException {
		SimpleEvent[] text = new SimpleEvent[base64.length];

		for (int i = 0; i < base64.length; i++) {
			text[i] = SimpleEvent.newBuilder()
					.mergeFrom(Base64.decodeBase64(base64[i])).build();
		}
		return text;
	}

	private static SimpleEvent[] getData() {
		SimpleEvent[] lines = new SimpleEvent[100];

		for (int i = 0; i < lines.length; i++) {

			lines[i] = SimpleEvent.newBuilder()
				.setName(String.valueOf(i))
					.build();

		}

		return lines;
	}

	private static String[] messageToString(SimpleEvent[] requests) {
		String[] str = new String[requests.length];
		for (int i = 0; i < str.length; i++)
			str[i] = requests[i].getName();

		return str;
	}

	private GPBWALImpl createWAL(String baseDir) throws IOException {

		File file = new File(baseDir);
		if (file.exists())
			FileUtils.deleteDirectory(file);

		file.mkdirs();

		walFile = new WALFile(baseDir);
		return new GPBWALImpl(walFile);
	}

}
