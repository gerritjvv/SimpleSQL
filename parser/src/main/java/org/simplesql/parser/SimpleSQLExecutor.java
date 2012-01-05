package org.simplesql.parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.codehaus.janino.ExpressionEvaluator;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSource;
import org.simplesql.data.KeyParser;
import org.simplesql.schema.TableDef;

import com.lmax.disruptor.ClaimStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * 
 *
 */
public class SimpleSQLExecutor implements SQLExecutor {

	private static final Logger LOG = Logger.getLogger(SimpleSQLExecutor.class);

	final TableDef tableDef;
	final ExpressionEvaluator eval;
	final KeyParser keyParser;
	final ExecutorService execService;
	final WhereFilter whereFilter;
	
	public SimpleSQLExecutor(ExecutorService execService, TableDef tableDef, ExpressionEvaluator eval, KeyParser keyParser, WhereFilter whereFilter) {
		super();
		this.execService = execService;
		this.tableDef = tableDef;
		this.eval = eval;
		this.keyParser = keyParser;
		this.whereFilter = whereFilter;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void pump(DataSource source,
			final AggregateStore store, final Progress progressListener) {

		int ringSize = 4096;

		/**
		 * Control parameters
		 */
		final AtomicBoolean shouldStop = new AtomicBoolean(false);
		final AtomicBoolean hasError = new AtomicBoolean(false);
		final AtomicReference<Throwable> errorReference = new AtomicReference<Throwable>();
		final AtomicInteger count = new AtomicInteger();

		/**
		 * Build Disruptor for async reading from data source and writing to the
		 * thread exec.
		 */
		Disruptor<DataEvent> disruptor = new Disruptor<DataEvent>(
				DataEventFactory.DEFAULT, ringSize, execService,
				ClaimStrategy.Option.SINGLE_THREADED,
				WaitStrategy.Option.YIELDING);

		final ProgressContext progressContext = new ProgressContext();

		disruptor.handleEventsWith(new EventHandler<DataEvent>() {

			@Override
			public void onEvent(DataEvent dat, long item, boolean batch)
					throws Exception {
				
				
				try {
					//only process if the where filter evaluates to true
					if(!whereFilter.include(dat.dat)){
						return;
					}
						
					// evaluate data into cells
					final Cell[] cells = (Cell[]) eval.evaluate(dat.dat);
					
					// put the cells into the store
					if (!store.put(keyParser.makeKey(dat.dat), cells)) {

						shouldStop.set(true);

					}


				} catch (Throwable t) {
					
					shouldStop.set(true);
					hasError.set(true);
					errorReference.set(t);
				}finally{
					
					final int recordAt = count.incrementAndGet();
					progressContext.recordsCompleted = recordAt;
					
					//update progress
					if (progressListener != null) {
						try {
							progressListener.update(progressContext);
						} catch (Throwable t) {
							LOG.error(t.toString(), t);
						}
					}

				}

			}

		});

		RingBuffer<DataEvent> ringBuffer = disruptor.start();

		int recordsRead = 0;

		// read data and add to the disruptor queue.
		for (Object[] data : source) {
			if (hasError.get() || shouldStop.get())
				break;

			long seq = ringBuffer.next();
			DataEvent evt = ringBuffer.get(seq);
			evt.dat = data;
			ringBuffer.publish(seq);
			recordsRead++;
		}

		// throw any error if an error occurred during the async processing
		if (hasError.get()) {
			Throwable t = errorReference.get();
			if (t == null)
				t = new RuntimeException("Uknown Error");

			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			else {
				RuntimeException excp = new RuntimeException(t.toString(), t);
				excp.setStackTrace(t.getStackTrace());
				throw excp;
			}
		}

		// wait for async processing
		while (count.get() < recordsRead) {
			Thread.yield();
		}

		disruptor.halt();

	}

	public static class DataEventFactory implements EventFactory<DataEvent> {
		public static final DataEventFactory DEFAULT = new DataEventFactory();

		@Override
		public DataEvent newInstance() {
			return new DataEvent();
		}

	}

	public static class DataEvent {

		public Object[] dat;

	}

}
