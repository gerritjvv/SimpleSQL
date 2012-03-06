package org.simplesql.om.wal.impl;

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
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.RequestTemplate;
import org.simplesql.om.RequestTemplate.Request;
import org.simplesql.om.event.RequestEvent;
import org.simplesql.wal.impl.DisruptorGPBWAL;
import org.simplesql.wal.impl.GPBWALImpl;
import org.simplesql.wal.impl.WALFile;

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

	private final Request[] data = getData();
	WALFile walFile = null;

	static final EventFactory<RequestEvent> EVENT_FACTORY = new EventFactory<RequestEvent>() {

		@Override
		public RequestEvent newInstance() {
			return new RequestEvent(0);
		}

	};

	@SuppressWarnings("deprecation")
	@Test
	public void testMultiMessageWrite() throws IOException,
			InterruptedException {
		int ringSize = 32;
		DisruptorGPBWAL wal = createWAL(
				"target/test/WALFileTest/testMultiMessageWrite", ringSize);

		ExecutorService executor = Executors.newCachedThreadPool();
		Disruptor<RequestEvent> disruptor = new Disruptor<RequestEvent>(
				EVENT_FACTORY, ringSize, executor,
				ClaimStrategy.Option.SINGLE_THREADED,
				WaitStrategy.Option.BUSY_SPIN);

		disruptor.handleEventsWith(wal);

		org.simplesql.om.ClientInfoTemplate.ClientInfo clientInfo = org.simplesql.om.ClientInfoTemplate.ClientInfo
				.newBuilder().setClientId(1).build();
		Projection projection = Projection.newBuilder()
				.setClientId(clientInfo.getClientId()).setName("Test").build();

		RingBuffer<RequestEvent> ringBuffer = disruptor.start();

		for (Request d : data) {
			long sequence = ringBuffer.next();
			RequestEvent event = ringBuffer.get(sequence);
			event.setRequest(d, clientInfo, projection);
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

	private static Request[] base64ToUTF(String[] base64)
			throws UnsupportedEncodingException, InvalidProtocolBufferException {
		Request[] text = new Request[base64.length];

		for (int i = 0; i < base64.length; i++) {
			text[i] = Request.newBuilder()
					.mergeFrom(Base64.decodeBase64(base64[i])).build();
		}
		return text;
	}

	private static Request[] getData() {
		Request[] lines = new Request[100];

		for (int i = 0; i < lines.length; i++) {

			lines[i] = RequestTemplate.Request.newBuilder()
					.setHost("myhost" + i).setIp("12.12.22.2").setUa("myua")
					.build();

		}

		return lines;
	}

	private static String[] messageToString(Request[] requests) {
		String[] str = new String[requests.length];
		for (int i = 0; i < str.length; i++)
			str[i] = requests[i].getHost();

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

}
