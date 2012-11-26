package org.simplesql.om.aggregate;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.ChunkedIterator;
import org.simplesql.data.DataSink;
import org.simplesql.data.DataSource;
import org.simplesql.data.TransformFunction;
import org.simplesql.om.data.StorageManager;
import org.simplesql.om.data.stores.HashMapAggregateStore;
import org.simplesql.om.data.stores.KratiStoreManager;
import org.simplesql.om.util.ProjectionKeyUtil;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.TableDef;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * Continuous aggregate class concentrates on data sources that have data
 * streams, and need to perform partial aggregates.<br/>
 * On each partial aggregate the data sink is called.<br/>
 * 
 */
public class ChunkedProcessor {

	private static final Logger LOG = Logger.getLogger(ChunkedProcessor.class);

	final Configuration conf;
	final StorageManager storageManager;

	final ExecutorService execService = Executors.newCachedThreadPool();
	final ExecutorService mainService = Executors.newCachedThreadPool();

	final SQLCompiler compiler = new SimpleSQLCompiler(execService);
	final SQLExecutor exec;

	final TableDef tableDef;

	final AtomicBoolean shouldStop = new AtomicBoolean(false);
	final CountDownLatch waitForStop = new CountDownLatch(1);

	private Future<Long> future;

	public ChunkedProcessor(Configuration conf, String schemaDef, String sql)
			throws Throwable {
		super();
		this.conf = conf;

		tableDef = createSchema(schemaDef);

		final Cell.SCHEMA[] schema = ProjectionKeyUtil.createSCHEMA(tableDef);

		storageManager = getStorageManager(conf, schema,
				new File(conf.getString("workingDir", "/tmp")));

		exec = compiler.compile(tableDef, sql);

	}

	public long stopWait() {

		shouldStop.set(true);
		try {
			waitForStop.await();

			mainService.shutdown();
			mainService.awaitTermination(10, TimeUnit.SECONDS);

			return future.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return 0L;
		} catch (ExecutionException e) {
			RuntimeException excp = new RuntimeException(e.toString(), e);
			excp.setStackTrace(e.getStackTrace());
			throw excp;
		}

	}

	public void runAsync(final DataSource dataSource, final DataSink sink,
			final int chunkSize) throws Throwable {

		future = mainService.submit(new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				try {
					return run(dataSource, sink, chunkSize);
				} catch (Throwable e) {
					RuntimeException excp = new RuntimeException(e.toString(),
							e);
					excp.setStackTrace(e.getStackTrace());
					throw excp;
				}
			}

		});

	}

	@SuppressWarnings("rawtypes")
	public long run(DataSource dataSource, final DataSink sink, int chunkSize)
			throws Throwable {

		if (chunkSize < 1)
			chunkSize = 1000;

		final AtomicLong eventCount = new AtomicLong();

		final Disruptor<WriteEvent> disruptor = createDisruptor(mainService,
				new EventHandler<ChunkedProcessor.WriteEvent>() {
					@Override
					public void onEvent(WriteEvent event, long index,
							boolean buffer) throws Exception {

						try {
							event.store.write(sink);
							event.store.close();
						} catch (Throwable t) {
							LOG.error(t.toString(), t);
						} finally {
							eventCount.incrementAndGet();
						}

					}
				});

		long i = 0;
		disruptor.start();
		try {

			final Iterator<Object[]> iterator = dataSource.iterator();

			
			
			while (!Thread.interrupted() && iterator.hasNext()) {
				
				final AggregateStore storage = new HashMapAggregateStore(exec
						.getTransforms().toArray(new TransformFunction[0]));

				//this data source will iterator over a chunk of iterator:Iterator
				final DataSource source = new DataSourceWrapper(
						new ChunkedIterator<Object[]>(iterator, chunkSize));

				exec.pump(source, storage, null);

				// async operation please see above out of the while loop in
				// which the anonymous class
				// will write to th event.store.write(sink)
				disruptor
						.publishEvent(new EventTranslator<ChunkedProcessor.WriteEvent>() {

							@Override
							public void translateTo(WriteEvent event,
									long sequence) {
								event.store = storage;
							}
						});

				// if (LOG.isDebugEnabled()) {
				// long end = System.currentTimeMillis() - start;
				// System.out.println("Aggregation " + i + " took " + end +
				// "ms");
				// }

				i++;
				
			}

			disruptor.shutdown();

			execService.shutdown();
			execService.awaitTermination(10, TimeUnit.SECONDS);

			disruptor.halt();

			System.out.println("Published: " + i + " events and processed: "
					+ eventCount.get());
		} finally {
			waitForStop.countDown();
		}

		return i;
	}

	@SuppressWarnings("unchecked")
	private static Disruptor<WriteEvent> createDisruptor(
			ExecutorService execService, EventHandler<WriteEvent> eventHandler) {

		Disruptor<WriteEvent> disruptor = new Disruptor<WriteEvent>(
				WriteEventFactory.DEFAULT, 4096, execService);

		disruptor.handleEventsWith(eventHandler);

		return disruptor;
	}

	private StorageManager getStorageManager(Configuration conf,
			Cell.SCHEMA[] schemas, File workingDir) throws Throwable {
		return new KratiStoreManager(conf.subset("krati"), workingDir, schemas);
	}

	private static final TableDef createSchema(String schemaDef) {
		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(schemaDef));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		try {
			parser.create();
		} catch (RecognitionException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

		return parser.tableDef;
	}

	static class WriteEventFactory implements EventFactory<WriteEvent> {
		static final WriteEventFactory DEFAULT = new WriteEventFactory();

		@Override
		public WriteEvent newInstance() {
			return new WriteEvent();
		}
	}

	static class WriteEvent {
		@SuppressWarnings("rawtypes")
		AggregateStore store;
	}

	static class DataSourceWrapper implements DataSource {

		final Iterator<Object[]> it;

		public DataSourceWrapper(Iterator<Object[]> it) {
			super();
			this.it = it;
		}

		@Override
		public Iterator<Object[]> iterator() {
			return it;
		}

		@Override
		public long getEstimatedSize() {
			return 0;
		}

	}

}
