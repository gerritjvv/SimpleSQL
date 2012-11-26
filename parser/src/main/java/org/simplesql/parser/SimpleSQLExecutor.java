package org.simplesql.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import org.simplesql.data.RangeGroups;
import org.simplesql.data.TransformFunction;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.TableDef;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * Pumps data through a DataStore, not much execution is done in here, most of
 * the execution is left to the DataStore itself.
 * 
 */
public class SimpleSQLExecutor implements SQLExecutor {

	private static final Logger LOG = Logger.getLogger(SimpleSQLExecutor.class);

	final TableDef tableDef;
	final ExpressionEvaluator eval;
	final KeyParser keyParser;
	final ExecutorService execService;
	final WhereFilter whereFilter;

	final List<TransformFunction> transforms;
	final Set<String> columnsUsed;
	final RangeGroups rangeGroups;

	final Set<String> selectOrderByColumns;
	final int limit;

	final AggregateStore.ORDER order;

	int[] groupOrderIndexes, selectOrderIndexes;

	Set<String> groupOrderByColumns;

	public SimpleSQLExecutor(ExecutorService execService, TableDef tableDef,
			ExpressionEvaluator eval, KeyParser keyParser,
			WhereFilter whereFilter, List<TransformFunction> transforms,
			SELECT select) {
		super();
		this.rangeGroups = select.getRangeGroups();
		this.columnsUsed = select.getVariables();
		this.execService = execService;
		this.tableDef = tableDef;
		this.eval = eval;
		this.keyParser = keyParser;
		this.whereFilter = whereFilter;
		this.transforms = transforms;
		this.selectOrderByColumns = select.getSelectOrderBy();
		this.groupOrderByColumns = select.getGroupOrderBy();

		this.limit = select.getLimit();

		order = (select.getOrder().equals(SELECT.ORDER.DESC)) ? AggregateStore.ORDER.DESC
				: AggregateStore.ORDER.ASC;

		int[] tmpKeyOrderIndexes = select.getGroupOrderIndexes();
		if (tmpKeyOrderIndexes != null) {
			groupOrderIndexes = Arrays.copyOf(tmpKeyOrderIndexes,
					select.getGroupOrderIndexesLen());
		}

		int[] tmpDataOrderIndexes = select.getSelectOrderIndexes();
		if (tmpDataOrderIndexes != null) {
			selectOrderIndexes = Arrays.copyOf(tmpDataOrderIndexes,
					select.getSelectOrderIndexesLen());
		}

	}

	public int[] getGroupOrderIndexes() {
		return groupOrderIndexes;
	}

	public int[] getSelectOrderIndexes() {
		return selectOrderIndexes;
	}

	/**
	 * Get the order direction that was specified by the select order by
	 * statement
	 * 
	 * @return
	 */
	public AggregateStore.ORDER getOrder() {
		return order;
	}

	public int getLimit() {
		return limit;
	}

	public RangeGroups getRangeGroups() {
		return rangeGroups;
	}

	/**
	 * Returns the columns that was used in the query
	 */
	public Set<String> getColumnsUsed() {
		return columnsUsed;
	}

	public List<TransformFunction> getTransforms() {
		return transforms;
	}

	@Override
	public WhereFilter getWhereFilter() {
		return whereFilter;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void pump(DataSource source, final AggregateStore store,
			final Progress progressListener) {

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
				DataEventFactory.DEFAULT, ringSize, execService);

		final ProgressContext progressContext = new ProgressContext();

		disruptor.handleEventsWith(new EventHandler<DataEvent>() {

			List<CellPut> cellList = new ArrayList<CellPut>();

			@Override
			public void onEvent(DataEvent dat, long item, boolean batch)
					throws Exception {

				try {
					// only process if the where filter evaluates to true
					if (!whereFilter.include(dat.dat)) {
						return;
					}

					// evaluate data into cells
					final Cell[] cells = (Cell[]) eval.evaluate(dat.dat);

					cellList.add(new CellPut(cells, dat.dat));

					if (batch) {

						final List<CellPut> prevList = cellList;
						cellList = new ArrayList<CellPut>();

						final int len = prevList.size();
						for (int i = 0; i < len; i++) {
							final CellPut cellPut = prevList.get(i);
							if (!store.put(keyParser.makeKey(cellPut.dat),
									cellPut.cells))
								shouldStop.set(true);
						}

					}

				} catch (Throwable t) {

					shouldStop.set(true);
					hasError.set(true);
					errorReference.set(t);
				} finally {

					final int recordAt = count.incrementAndGet();
					progressContext.recordsCompleted = recordAt;

					// update progress
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

		final RingBuffer<DataEvent> ringBuffer = disruptor.start();

		// read data and add to the disruptor queue.
		final Iterator<Object[]> it = source.iterator();
		while (it.hasNext()) {
			if (hasError.get() || shouldStop.get())
				break;

			long seq = ringBuffer.next();
			final DataEvent evt = ringBuffer.get(seq);
			evt.dat = it.next();
			ringBuffer.publish(seq);
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

		disruptor.shutdown();
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

	@Override
	public Set<String> getSelectOrderByColumns() {
		return selectOrderByColumns;
	}

	@Override
	public Set<String> getGroupOrderByColumns() {
		return groupOrderByColumns;
	}

	private static class CellPut {

		@SuppressWarnings("rawtypes")
		Cell[] cells;
		Object[] dat;

		@SuppressWarnings("rawtypes")
		public CellPut(Cell[] cells, Object[] dat) {
			super();
			this.cells = cells;
			this.dat = dat;
		}

	}

}
