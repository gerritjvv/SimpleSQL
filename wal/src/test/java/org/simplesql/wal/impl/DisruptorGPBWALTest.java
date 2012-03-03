package org.simplesql.wal.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.simplesql.wal.Event;
import org.simplesql.wal.event.SimpleEventTemplate.SimpleEvent;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * Test simple writing Request GPB to the GPBWAL delagating to a WALFile
 * instance.
 * 
 */
public class DisruptorGPBWALTest {

	private final SimpleEvent[] data = getData();
	WALFile walFile = null;

	@SuppressWarnings("deprecation")
	@Test
	public void testMultiMessageWrite() throws IOException,
			InterruptedException {
		int ringSize = 32;
		DisruptorGPBWAL wal = createWAL(
				"target/test/WALFileTest/testMultiMessageWrite", ringSize);

		ExecutorService executor = Executors.newCachedThreadPool();
		Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(
				new MyEventFactory(), ringSize, executor,
				ClaimStrategy.Option.SINGLE_THREADED,
				WaitStrategy.Option.BUSY_SPIN);

		disruptor.handleEventsWith(wal);

		RingBuffer<MyEvent> ringBuffer = disruptor.start();

		for (SimpleEvent d : data) {
			long sequence = ringBuffer.next();
			MyEvent event = ringBuffer.get(sequence);
			event.setData(d);
			ringBuffer.publish(sequence);
		}

		Thread.sleep(2000L);
		disruptor.halt();

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

			lines[i] = SimpleEvent.newBuilder().setName(String.valueOf(i))
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

	private DisruptorGPBWAL createWAL(String baseDir, int ringSize)
			throws IOException {

		File file = new File(baseDir);
		if (file.exists())
			FileUtils.deleteDirectory(file);

		file.mkdirs();

		walFile = new WALFile(baseDir);
		return new DisruptorGPBWAL(new GPBWALImpl(walFile), ringSize);
	}

	static class MyEventFactory implements EventFactory<MyEvent> {

		public MyEvent newInstance() {
			return new MyEvent();
		}

	}

	static class MyEvent implements Event<SimpleEvent> {

		SimpleEvent data;

		public void setData(SimpleEvent data) {
			this.data = data;
		}

		public SimpleEvent getData() {
			return data;
		}

	}

}
