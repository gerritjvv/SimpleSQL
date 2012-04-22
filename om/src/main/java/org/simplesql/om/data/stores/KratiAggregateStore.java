package org.simplesql.om.data.stores;

import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import krati.core.segment.MappedSegmentFactory;
import krati.core.segment.SegmentFactory;
import krati.store.DataStore;
import krati.store.DynamicDataStore;

import org.apache.commons.configuration.Configuration;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.Cell.SCHEMA;
import org.simplesql.data.CellsByteWrapper;
import org.simplesql.data.CellsUtil;
import org.simplesql.data.DataEntry;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.TransformFunction;
import org.simplesql.util.Bytes;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * 
 * Use the Berkeley db to store DataEntry(s) per key.<br/>
 * 
 * Thread Safe:<br/>
 * This class is not thread safe.
 * 
 * @param <T>
 */
public class KratiAggregateStore<T> implements AggregateStore<T> {

	public enum CONF {
		UPDATE_BATCH, UPDATE_BATCHES, KEY_COUNT, SEGMENT_SIZE, CACHE_SIZE
	}

	final String dbName;

	final TransformFunction[] functions;

	// Byte Key Values

	CellsByteWrapper keyWrapper;
	CellsByteWrapper dataWrapper;

	final Cache<Key, DataEntry> cache;
	SimpleCellKey currentKey;
	DataEntry currentDataEntry;

	final int updateBatch;
	final int updateBatches;
	final int segmentSize;
	final int keyCount;

	// ORDER AND LMIT VALUES

	int limit = Integer.MAX_VALUE;
	int rowCount = 0;

	int[] cellIndexes;
	int[] dataCellIndexes;

	ORDER order = ORDER.ASC;

	final SCHEMA[] schemas;

	TreeMap<Key, byte[]> keyIndex;

	DataStore<byte[], byte[]> dataStore;

	/**
	 * 
	 * @param functions
	 *            TransformFunction(s) that will be applied to each row put.
	 * @throws Exception
	 */
	public KratiAggregateStore(Configuration conf, File storeDir,
			SCHEMA[] schemas, TransformFunction... functions) throws Exception {
		super();

		this.schemas = schemas;
		// helps keep code clean from null pointers.
		if (functions == null) {
			functions = new TransformFunction[0];
		}

		this.functions = functions;

		// create a unique name for the db
		dbName = "aggStore_" + System.nanoTime();

		keyCount = conf.getInt(CONF.KEY_COUNT.toString(), 1);
		updateBatch = conf.getInt(CONF.UPDATE_BATCH.toString(), 10000);
		updateBatches = conf.getInt(CONF.UPDATE_BATCHES.toString(), 5);
		segmentSize = conf.getInt(CONF.SEGMENT_SIZE.toString(), 128);

		cache = CacheBuilder.newBuilder()
				.maximumSize(conf.getInt(CONF.CACHE_SIZE.toString(), 100000))
				.concurrencyLevel(4)
				.removalListener(new RemovalListener<Key, DataEntry>() {

					@Override
					public void onRemoval(
							RemovalNotification<Key, DataEntry> notf) {
						saveToStore(notf.getKey(), notf.getValue());
					}

				}).build();

		dataStore = createDataStore(keyCount, storeDir);
	}

	/**
	 * Add the cells for the key. This method will also apply the
	 * TranformFunction(s).
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean put(Key key, Cell[] cells) {

		DataEntry entry = get(key);
		if (entry == null) {
			// add to index
			final byte[] index = Bytes.toBytes(System.nanoTime());
			keyIndex.put(key, index);

			// add to store
			try {
				dataStore.put(index, getDataWrapper().getBytes(cells));
			} catch (Exception e) {
				RuntimeException rte = new RuntimeException(e.toString(), e);
				rte.setStackTrace(e.getStackTrace());
				throw rte;
			}

			// add to cache
			entry = new DataEntry(key, cells, functions);
			cache.put(key, entry);
		} else {

			// data applied is only available in memory
			// we need to flush to the data store
			// this is done as the RemoveListener
			entry.apply(cells);

		}

		return true;
	}

	private void saveToStore(Key key, DataEntry value) {
		// get index position
		final byte[] index = keyIndex.get(key);
		try {
			dataStore.put(index, getDataWrapper().getBytes(value.getCells()));
		} catch (Exception e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

	@Override
	public DataEntry get(Key key) {

		// search in cache first
		DataEntry dataEntry = cache.getIfPresent(key);

		// if not in cache
		if (dataEntry == null) {

			if (keyIndex == null) {
				keyIndex = new TreeMap<Key, byte[]>(getComparator());
			}

			// get index position
			final byte[] index = keyIndex.get(key);

			// if in index get the data.
			if (index != null) {
				final byte[] data = dataStore.get(index);
				final CellsByteWrapper wrapper = getDataWrapper();
				dataEntry = new DataEntry(key, CellsUtil.copyOf(
						wrapper.readFrom(data, 0), true), functions);

			}

		}

		return dataEntry;
	}

	private final Comparator<Key> getComparator() {
		return (order == org.simplesql.data.AggregateStore.ORDER.ASC) ? new AscOrderedComparator()
				: new DescOrderedComparator();
	}

	private final CellsByteWrapper getDataWrapper() {
		if (dataWrapper == null) {
			dataWrapper = new CellsByteWrapper(schemas);
		}
		return dataWrapper;
	}

	@Override
	public Iterator<DataEntry> iterator() {
		return null;
	}

	/**
	 * Scans the whole database and add keys to a Set. This method should only
	 * be called for testing and debugging.
	 */
	@Override
	public Set<? extends Key> keys() {
		return null;
	}

	@Override
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public void write(DataSink sink) {

		for (Key key : keyIndex.keySet()) {

			sink.fill(key, get(key).getCells());

		}

	}

	@Override
	public void close() {
		// empty cache to storage
		cache.invalidateAll();
	}

	/**
	 * In the case of the Berkeley Aggregate Store the data is already ordered
	 * by key.<br/>
	 * The cell indexes for ordering is ignored.
	 */
	@Override
	public void setOrderKeyBy(int[] cellIndexes,
			org.simplesql.data.AggregateStore.ORDER order) {
		this.order = order;
		this.cellIndexes = cellIndexes;
	}

	@Override
	public void setOrderByData(int[] cellIndexes) {
		this.dataCellIndexes = cellIndexes;
	}

	/**
	 * 
	 * Define an ordered cursor
	 * 
	 */
	static interface OrderedCursor extends Comparator<Key> {

	}

	protected DataStore createDataStore(int keyCount, File storeDir)
			throws Exception {
		int capacity = (int) (keyCount * 1.5);
		return new DynamicDataStore(storeDir, capacity, /* capacity */
		updateBatch, /* update batch size */
		updateBatches, /* number of update batches required to sync indexes.dat */
		segmentSize, /* segment file size in MB */
		createSegmentFactory());
	}

	/**
	 * Creates a segment factory. Subclasses can override this method to provide
	 * a specific segment factory such as ChannelSegmentFactory and
	 * MappedSegmentFactory.
	 * 
	 * @return the segment factory.
	 */
	protected SegmentFactory createSegmentFactory() {
		return new MappedSegmentFactory();
	}

	static class DescOrderedComparator implements OrderedCursor {

		@Override
		public int compare(Key o1, Key o2) {
			return o1.compareTo(o2);
		}

	}

	static class AscOrderedComparator implements OrderedCursor {

		@Override
		public int compare(Key o1, Key o2) {
			return o2.compareTo(o1);
		}

	}
}
