package org.simplesql.data.impl;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.CellTuple;
import org.simplesql.data.DataEntry;
import org.simplesql.data.DataEntryBuilder;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.TransformFunction;
import org.simplesql.data.impl.berkeley.DBManager;

import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.Database;

/**
 * 
 * Use the Berkeley db to store DataEntry(s) per key.<br/>
 * 
 * Thread Safe:<br/>
 * This class is not thread safe.
 * 
 * @param <T>
 */
public class BerkeleyAggregateStore<T> implements AggregateStore<T> {

	final String dbName;
	final DBManager dbManager;
	final Database db;
	final StoredMap<SimpleCellKey, CellTuple> map;

	final TransformFunction[] functions;

	int limit = Integer.MAX_VALUE;
	int rowCount = 0;

	DataEntryBuilder builder;
	SimpleCellKey currentKey;
	DataEntry currentDataEntry;

	boolean dirty = false;

	/**
	 * 
	 * @param functions
	 *            TransformFunction(s) that will be applied to each row put.
	 */
	public BerkeleyAggregateStore(DBManager dbManager,
			TransformFunction... functions) {
		super();

		// helps keep code clean from null pointers.
		if (functions == null) {
			functions = new TransformFunction[0];
		}

		this.functions = functions;
		this.dbManager = dbManager;

		// create a unique name for the db
		dbName = "aggStore_" + System.nanoTime();
		// open the db and create a map wrapper
		db = dbManager.openDatabase(dbName);
		map = dbManager.createMap(db);

	}

	/**
	 * Add the cells for the key. This method will also apply the
	 * TranformFunction(s).
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean put(Key key, Cell[] cells) {

		// here we should increment and add top to any values required
		final SimpleCellKey cellKey;

		if (!(key instanceof SimpleCellKey)) {
			throw new RuntimeException("Key: " + key + " not supported");
		} else {
			cellKey = (SimpleCellKey) key;
		}

		final DataEntry entry;

		if (currentKey != null && currentKey.equals(cellKey)) {
			// caching area, we expect the keys to be scanned in order. so
			// similar keys will be send it groups
			// keeping track of the currentDataEntry eliminates multiple db
			// round trips.
			entry = currentDataEntry;
		} else {

			flushCurrent();

			CellTuple tuple = map.get(cellKey);

			if (tuple == null) {

				if (builder == null) {
					builder = new DataEntryBuilder(cells, functions);
				}

				if (rowCount++ >= limit) {
					// check for limit on key
					// this implementation expects that all keys are delivered
					// in
					// order.
					return false;
				}

				entry = builder.create(cellKey);

				map.put(cellKey, new CellTuple(entry.getCells()));

			} else {
				// else create a DataEntry from the builder.
				entry = builder.create(cellKey);
			}
			// set cellKey and entry to current values
			currentKey = cellKey;
			currentDataEntry = entry;
		}

		// apply transforms on the data entry based on the cells
		dirty = true;
		entry.apply(cells);

		return true;
	}

	/**
	 * Commits the current key if any
	 */
	private final void flushCurrent() {
		if (dirty) {
			if (currentKey != null) {
				// save last current key and value
				map.put(currentKey, new CellTuple(currentDataEntry.getCells()));
			}

			dirty = false;

		}

	}

	@Override
	public DataEntry get(Key key) {
		flushCurrent();
		final CellTuple tuple = map.get((SimpleCellKey) key);
		return (tuple == null) ? null : new DataEntry(key, tuple.getCells(),
				functions);
	}

	@Override
	public Iterator<DataEntry> iterator() {
		flushCurrent();
		final Iterator<Entry<SimpleCellKey, CellTuple>> tupleIt = map
				.entrySet().iterator();

		// we wrap the CellTuple iterator into a DataEntry Iterator
		return new Iterator<DataEntry>() {

			@Override
			public boolean hasNext() {
				return tupleIt.hasNext();
			}

			@Override
			public DataEntry next() {
				Entry<SimpleCellKey, CellTuple> entry = tupleIt.next();
				return new DataEntry(entry.getKey(), entry.getValue()
						.getCells(), functions);
			}

			@Override
			public void remove() {

			}

		};
	}

	@Override
	public Set<? extends Key> keys() {
		return map.keySet();
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
		flushCurrent();
		final Set<Entry<SimpleCellKey, CellTuple>> values = map.entrySet();

		for (Entry<SimpleCellKey, CellTuple> entry : values) {
			sink.fill(entry.getKey(), entry.getValue().getCells());
		}

	}

	@Override
	public void close() {
		flushCurrent();
		// db is temporary, i.e. on close the db is removed.
		db.close();
	}

}
