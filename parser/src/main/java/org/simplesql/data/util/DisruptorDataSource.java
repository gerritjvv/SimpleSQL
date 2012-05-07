package org.simplesql.data.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.simplesql.data.DataSource;

import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.Sequencer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * Reads each line from the standard input, splits by the separator and passes
 * the split string through the SelectTransform. This processes is repeated via
 * the SplitIterator for each line in from the standard input.
 * 
 */
public class DisruptorDataSource implements DataSource {

	final String sep;

	final SelectTransform transform;

	public DisruptorDataSource(SelectTransform transform, String sep) {
		super();

		this.transform = transform;
		this.sep = sep;
	}

	public Iterator<Object[]> iterator(InputStream in) {
		return new DisruptorIt(sep, transform, new BufferedInputStream(in),
				Executors.newCachedThreadPool());

	}

	public Iterator<Object[]> iterator() {
		return new DisruptorIt(sep, transform, new BufferedInputStream(
				System.in), Executors.newCachedThreadPool());

	}

	public long getEstimatedSize() {
		return 0;
	}

	/**
	 * Blocking queue for events
	 */
	private static class Processor implements EventProcessor {

		private final AtomicBoolean running = new AtomicBoolean(true);
		private final RingBuffer<StrEvent> ringBuffer;
		private final SequenceBarrier sequenceBarrier;
		private final Sequence sequence = new Sequence(
				Sequencer.INITIAL_CURSOR_VALUE);

		public Processor(ExecutorService execService) {
			super();

			ringBuffer = new RingBuffer<StrEvent>(StrEventFactory.DEFAULT,
					4096, ClaimStrategy.Option.SINGLE_THREADED,
					WaitStrategy.Option.YIELDING);
			sequenceBarrier = ringBuffer.newBarrier();
			ringBuffer.setGatingSequences(sequence);

			execService.submit(this);
		}

		public void publish(String str) {

			long seq = ringBuffer.next();

			ringBuffer.get(seq).str = str;
			ringBuffer.publish(seq);

		}

		@Override
		public void run() {
			while (running.get()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}

		final List<String> lines = new ArrayList<String>();

		/**
		 * Returns the next available string
		 * 
		 * @return
		 * @throws AlertException
		 * @throws InterruptedException
		 */
		public String getNext() throws AlertException, InterruptedException {

			if (lines.size() == 0) {
				long nextSequence = sequence.get() + 1L;
				final long availableSequence = sequenceBarrier
						.waitFor(nextSequence);
				while (nextSequence <= availableSequence) {
					StrEvent event = ringBuffer.get(nextSequence);
					nextSequence++;
					lines.add(event.str);
				}
				sequence.set(nextSequence - 1L);
			}

			return (lines.size() > 0) ? lines.remove(0) : null;

		}

		@Override
		public Sequence getSequence() {
			return sequence;
		}

		@Override
		public void halt() {
			running.set(false);
			sequenceBarrier.alert();
		}

	}

	private static class DisruptorIt implements Iterator<Object[]> {

		final int ringSize = 4096;
		final AtomicBoolean shouldStop = new AtomicBoolean(false);
		final AtomicBoolean hasError = new AtomicBoolean(false);
		final AtomicReference<Throwable> errorReference = new AtomicReference<Throwable>();
		final AtomicInteger processed = new AtomicInteger();

		final SelectTransform transform;
		final String sep;

		/**
		 * Build Disruptor for async reading from data source and writing to the
		 * thread exec.
		 */
		final Disruptor<Event> disruptor;
		final RingBuffer<Event> ringBuffer;

		Processor processor;

		@SuppressWarnings({ "unchecked" })
		public DisruptorIt(final String sep,
				final SelectTransform selectTransform,
				final BufferedInputStream in, ExecutorService execService) {
			this.sep = sep;
			this.transform = selectTransform;

			processor = new Processor(execService);

			disruptor = new Disruptor<Event>(EventFactory.DEFAULT, ringSize,
					execService, ClaimStrategy.Option.SINGLE_THREADED,
					WaitStrategy.Option.YIELDING);

			/**
			 * On each event in the event buffer, split into lines and push onto
			 * a queue.
			 */
			disruptor.handleEventsWith(new EventHandler<Event>() {

				byte[] lineBytes = new byte[1024];
				int lineBytesFrom = 0;

				@Override
				public void onEvent(Event evt, long seq, boolean endBatch)
						throws Exception {
					try {
						/**
						 * Get buffer and find first new line
						 */
						final byte[] buff = evt.buff;
						final int len = evt.len;

						int pos = 0;
						int n = ByteArrayUtil.findN(buff, pos);
						int prevN = 0;
						if (n < 0) {
							// ifno new line, add this buffer to the lineBytes
							// array
							ByteArrayUtil.addTo(lineBytes, lineBytesFrom, buff,
									0, len, 0.5F);
							lineBytesFrom += len;
						} else {
							// else put to line to queue
							processed.incrementAndGet();
							processor.publish(new String(ByteArrayUtil.combine(
									buff, 0, n, lineBytes, 0, lineBytesFrom)));
							prevN = n + 1;
							// look for the next new line till the end of the
							// buffer is reached.
							while ((n = ByteArrayUtil.findN(buff, prevN)) > 0
									&& prevN < len) {
								if (prevN > n - 1) {
									processed.incrementAndGet();
									processor.publish("");
								} else {
									processed.incrementAndGet();
									processor.publish(new String(Arrays
											.copyOfRange(buff, prevN, n)));
								}
								prevN = n + 1;
							}

							// here we need to check if the end of the buffer
							// has been reached
							// if not add the remaining to line bytes.
							if (prevN < len) {
								ByteArrayUtil.addTo(lineBytes, lineBytesFrom,
										buff, prevN, len - prevN - 1, 0.5F);
							}

						}

					} catch (Throwable t) {
						t.printStackTrace();
						shouldStop.set(true);
						hasError.set(true);
						errorReference.set(t);
					}

				}

			});

			ringBuffer = disruptor.start();

			/**
			 * We iterate over the input stream publishing byte buffers read to
			 * the ring buffer. Increment the value by one for each buffer read.
			 */
			execService.submit(new Runnable() {
				public void run() {
					try {
						do {
							final long evtId = ringBuffer.next();
							Event evt = ringBuffer.get(evtId);
							final int len = in.read(evt.buff);

							if (len < 1) {
								shouldStop.set(true);
								break;
							}
							evt.len = len;

							ringBuffer.publish(evtId);

						} while (shouldStop.get() == false
								&& hasError.get() == false);

					} catch (Throwable t) {
						shouldStop.set(true);
						hasError.set(true);
						errorReference.set(t);
					}
				}
			});

		}

		@Override
		public boolean hasNext() {
			boolean ret;
			if ((shouldStop.get() || hasError.get()) && processed.get() == 0) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					ret = false;
				}
				ret = !((shouldStop.get() || hasError.get()) && processed.get() == 0);
			} else {
				ret = true;
			}

			return ret;
		}

		@Override
		public Object[] next() {
			String[] split = null;
			try {

				
				split = StringUtils.split(processor.getNext(), sep);
				
				return (Object[]) transform.transform(split);

			} catch (Throwable e) {
				System.out.println("!!!!!!!!!1Split: " + Arrays.toString(split));
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			} finally {
				processed.decrementAndGet();
			}
		}

		@Override
		public void remove() {
		}

	}

	private static class EventFactory implements
			com.lmax.disruptor.EventFactory<Event> {
		static public final EventFactory DEFAULT = new EventFactory();

		@Override
		public Event newInstance() {
			return new Event();
		}

	}

	private static class StrEventFactory implements
			com.lmax.disruptor.EventFactory<StrEvent> {
		static public final StrEventFactory DEFAULT = new StrEventFactory();

		@Override
		public StrEvent newInstance() {
			return new StrEvent();
		}

	}

	private static class StrEvent {
		String str;
	}

	/**
	 * 
	 * Hold the buffer values read for 1024 bytes.
	 * 
	 */
	private static class Event {
		byte[] buff = new byte[1024];
		int len = 0;
	}

}
