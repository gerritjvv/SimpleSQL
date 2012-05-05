package org.simplesql.data.util;

import java.io.BufferedInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang3.StringUtils;
import org.simplesql.data.DataSource;
import org.simplesql.parser.SimpleSQLExecutor.DataEvent;

import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
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

	public Iterator<Object[]> iterator() {
		return new DisruptorIt(sep, transform, new BufferedInputStream(System.in), Executors.newCachedThreadPool());

	}

	public long getEstimatedSize() {
		return 0;
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

		final BlockingDeque<String> queue = new LinkedBlockingDeque<String>(
				1000);

		@SuppressWarnings({ "unchecked" })
		public DisruptorIt(final String sep, final SelectTransform selectTransform, final BufferedInputStream in,
				ExecutorService execService) {
			this.sep = sep;
			this.transform = selectTransform;
			
			disruptor = new Disruptor<Event>(EventFactory.DEFAULT, ringSize,
					execService, ClaimStrategy.Option.SINGLE_THREADED,
					WaitStrategy.Option.YIELDING);

			ringBuffer = disruptor.start();

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
					try{
						/**
						 * Get buffer and find first new line
						 */
						final byte[] buff = evt.buff;
						final int len = evt.len;
	
						int pos = 0;
						int n = ByteArrayUtil.findN(buff, pos);
						int prevN = 0;
	
						if (n < 0) {
							//ifno new line, add this buffer to the lineBytes array
							ByteArrayUtil.addTo(lineBytes, lineBytesFrom, buff, 0,
									len, 0.5F);
							lineBytesFrom += len;
						} else if (n < len) {
							//else put to line to queue
							queue.offerLast(
									new String(ByteArrayUtil.combine(buff, 0,
											n + 1, lineBytes, 0, lineBytesFrom)),
									10, TimeUnit.SECONDS);
							prevN = n + 1;
							//look for the next new line till the end of the buffer is reached.
							while ((n = ByteArrayUtil.findN(buff, prevN)) > 0 && prevN < len) {
								queue.offerLast(
										new String(Arrays.copyOfRange(buff, prevN,
												(n - prevN) )), 10, TimeUnit.SECONDS);
								prevN = n + 1;
							}
							
							//here we need to check if the end of the buffer has been reached
							//if not add the remaining to line bytes.
							if(prevN < len){
								ByteArrayUtil.addTo(lineBytes, lineBytesFrom, buff, prevN, len - prevN, 0.5F);
							}
							
						}
						
					}catch(Throwable t){
						shouldStop.set(true);
						hasError.set(true);
						errorReference.set(t);
					}
					
					processed.decrementAndGet();

				}

			});

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

							processed.incrementAndGet();
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
			if( processed.get() == 0){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					;
				}
				return (processed.get() == 0);
			}else{
				return true;
			}
		}

		@Override
		public Object[] next() {
			try {
				return (Object[])transform.transform(
						StringUtils.split(queue.pollFirst(10, TimeUnit.SECONDS), sep));
			} catch (Throwable e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
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
