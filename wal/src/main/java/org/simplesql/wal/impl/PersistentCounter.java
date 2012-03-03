package org.simplesql.wal.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.simplesql.wal.impl.DisruptorGPBWALTest.MyEvent;
import org.simplesql.wal.impl.DisruptorGPBWALTest.MyEventFactory;

import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * A persistent counter is a number that can be incremented and each increment
 * is persisted.<br/>
 * The easiest would be to put this into a database that does continuous updates
 * to a data structure,<br/>
 * but such complexities is an overkill and too slow for what a counter should
 * be used for.
 * <p/>
 * This implementation implements a RingBased File. i.e. The file is
 * preallocated with a set size and as a array containing a fixed number of
 * entries.<br/>
 * Two entries are supported, Counter Value (8bytes) and the Time Stamp (8
 * bytes). Counter updates are persisted by writing to the next available space
 * in the end of the array is reached the class will start writing at the
 * beginning of the array again.
 * <p/>
 * To help get the next available space in the array as quickly as possible the
 * disruptor framework is used.<br/>
 * I.e. the disruptor framework already contains a fixed ring buffer in which
 * sequence numbers are allocated,<br/>
 * this is extremely fast and the same sequence can be used for the next
 * available space in the file array.
 * <p/>
 * The most expensive operation in the whole process is syncing the edits to the
 * file system, we can do this by using the disruptor framework to on write
 * launch an event that will asynchronously do the file sync.
 * <p/>
 * Benchmark: On a simple benchmark test using Junit and milliseconds 1 million
 * increments were performed at 60k persecond if syncToOs is true.
 */
public class PersistentCounter {

	private static final Logger LOG = Logger.getLogger(PersistentCounter.class);

	static final int blockSize = 16; // contains 2 long bytes 1 for the counter
										// value and 1 for the timestamp
	static final int bufferLen = 4096; // this value is a power of two 2^16 and
										// gives 16 * 4096 = 65536 entries is
										// 64kb
										// of memory.

	final MappedByteBuffer buffer;
	final RandomAccessFile randFile;
	final FileChannel channel;
	final RingBuffer<CounterEvent> ringBuffer;
	final Disruptor<CounterEvent> disruptor;
	final boolean syncToOs;

	volatile long bufferedVal = 0;

	public PersistentCounter(ExecutorService executorService, File file) throws FileNotFoundException, IOException {
		this(executorService, file, false);
	}

	public PersistentCounter(ExecutorService executorService, File file,
			boolean syncToOs) throws FileNotFoundException, IOException {

		this.syncToOs = syncToOs;
		randFile = new RandomAccessFile(file, "rw");
		channel = randFile.getChannel();

		// load the whole file into memory
		buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, bufferLen
				* blockSize);
		buffer.load(); // load into memory

		disruptor = new Disruptor<CounterEvent>(new CounterEventFactory(),
				bufferLen, executorService,
				ClaimStrategy.Option.SINGLE_THREADED,
				WaitStrategy.Option.YIELDING);

		CounterEventHandler handler = new CounterEventHandler();
		disruptor.handleEventsWith(handler);
		disruptor.handleExceptionsWith(handler);

		ringBuffer = disruptor.start();

		here we need to read the file and load the counter value of the oldest entry.
	}

	public long inc(long val) {
		bufferedVal += val;

		long sequence = ringBuffer.next();
		CounterEvent event = ringBuffer.get(sequence);

		// write to buffer
		int index = event.index * blockSize;
		// write value
		try {
			buffer.putLong(index, bufferedVal);
			// write timestamp
			buffer.putLong(index + 8, System.nanoTime());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		ringBuffer.publish(sequence);

		return bufferedVal;
	}

	public void close() throws IOException {
		disruptor.halt();
		buffer.force();
		channel.close();
		randFile.close();
	}

	/**
	 * 
	 * Flushes the buffer
	 * 
	 */
	class CounterEventHandler implements EventHandler<CounterEvent>,
			ExceptionHandler {

		public void onEvent(CounterEvent event, long sequence,
				boolean endOfBatch) throws Exception {
			if (endOfBatch) {
				if (syncToOs)
					buffer.force();
			}
		}

		public void handle(Exception ex, long sequence, Object event) {
			LOG.error(ex.toString(), ex);
		}

	}

	/**
	 * 
	 * Create's events with sequenced indexes
	 * 
	 */
	static class CounterEventFactory implements EventFactory<CounterEvent> {
		int index = 0;

		public CounterEvent newInstance() {
			return new CounterEvent(index++);
		}

	}

	/**
	 * Each counter event contains a sequence
	 * 
	 */
	static class CounterEvent {
		final int index;

		public CounterEvent(int index) {
			super();
			this.index = index;
		}

	}

}
