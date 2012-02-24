package org.simplesql.data.impl;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.CellsByteWrapper;
import org.simplesql.data.DataEntry;
import org.simplesql.data.DataEntryBuilder;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.TransformFunction;
import org.simplesql.data.impl.berkeley.DBManager;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.OperationStatus;

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

	final TransformFunction[] functions;

	// Byte Key Values

	CellsByteWrapper keyWrapper;
	CellsByteWrapper dataWrapper;

	DataEntryBuilder builder;
	SimpleCellKey currentKey;
	DataEntry currentDataEntry;

	DatabaseEntry rowDataKey, rowData;

	// ORDER AND LMIT VALUES

	int limit = Integer.MAX_VALUE;
	int rowCount = 0;

	int[] cellIndexes;
	ORDER order = ORDER.ASC;

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

		cellIndexes = new int[] { 0 };

	}

	private final OrderedCursor getCursor() {
		return ORDER.ASC.equals(order) ? new AscCursor(
				db.openCursor(null, null)) : new DescCursor(db.openCursor(null,
				null));
	}

	/**
	 * Add the cells for the key. This method will also apply the
	 * TranformFunction(s).
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean put(Key key, Cell[] cells) {

		if (keyWrapper == null) {
			keyWrapper = new CellsByteWrapper(key.getCells());
			dataWrapper = new CellsByteWrapper(cells);
		}

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

			byte[] keybytes = keyWrapper.getBytes(key.getCells());
			rowDataKey = new DatabaseEntry(keybytes);
			rowData = new DatabaseEntry();

			OperationStatus opStatus = db.get(null, rowDataKey, rowData, null);

			if (!opStatus.equals(OperationStatus.SUCCESS)) {
				// key not found

				if (builder == null) {
					builder = new DataEntryBuilder(cells, functions);
				}

				entry = builder.create(cellKey);

				rowData.setData(dataWrapper.getBytes(cells));
				// put data entry
				db.put(null, rowDataKey, rowData);

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
				rowData.setData(dataWrapper.getBytes(currentDataEntry
						.getCells()));
				db.put(null, rowDataKey, rowData);
			}

			dirty = false;

		}

	}

	@Override
	public DataEntry get(Key key) {
		flushCurrent();

		DatabaseEntry itKey = new DatabaseEntry(keyWrapper.getBytes(key
				.getCells()));
		DatabaseEntry itData = new DatabaseEntry();

		if (db.get(null, itKey, itData, null).equals(OperationStatus.SUCCESS)) {
			return new DataEntry(key,
					dataWrapper.readFrom(itData.getData(), 0), functions);
		} else {
			return null;
		}

	}

	@Override
	public Iterator<DataEntry> iterator() {
		flushCurrent();
		final OrderedCursor cursor = getCursor();
		final DatabaseEntry itKey = new DatabaseEntry();
		final DatabaseEntry itData = new DatabaseEntry();
		final int l = limit;
		// we wrap the CellTuple iterator into a DataEntry Iterator
		return new Iterator<DataEntry>() {
			int row = 0;

			@Override
			public boolean hasNext() {
				if ((row++ < l)
						&& cursor.getNext(itKey, itData).equals(
								OperationStatus.SUCCESS)) {
					return true;
				} else {
					cursor.close();
					return false;
				}
			}

			@Override
			public DataEntry next() {
				return new DataEntry(new SimpleCellKey(keyWrapper.readFrom(
						itKey.getData(), 0)), dataWrapper.readFrom(
						itData.getData(), 0), functions);
			}

			@Override
			public void remove() {

			}

		};
	}

	/**
	 * Scans the whole database and add keys to a Set. This method should only
	 * be called for testing and debugging.
	 */
	@Override
	public Set<? extends Key> keys() {
		Set<SimpleCellKey> keys = new TreeSet<SimpleCellKey>();

		final OrderedCursor cursor = getCursor();

		final DatabaseEntry itKey = new DatabaseEntry();
		final DatabaseEntry itData = new DatabaseEntry();
		itData.setPartial(true);
		itData.setPartialLength(0);
		int row = 0;
		try {

			while ((row++ < limit)
					&& cursor.getNext(itKey, itData).equals(
							OperationStatus.SUCCESS)) {
				keys.add(new SimpleCellKey(keyWrapper.readFrom(itKey.getData(),
						0)));
			}

		} finally {
			cursor.close();
		}

		return keys;
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
		// final Set<Entry<SimpleCellKey, CellTuple>> values = map.entrySet();
		//
		// for (Entry<SimpleCellKey, CellTuple> entry : values) {
		// sink.fill(entry.getKey(), entry.getValue().getCells());
		// }

		final OrderedCursor cursor = getCursor();
		final DatabaseEntry itKey = new DatabaseEntry();
		final DatabaseEntry itData = new DatabaseEntry();
		try {
			int rows = 0;
			while (cursor.getNext(itKey, itData)
					.equals(OperationStatus.SUCCESS)) {
				if (rows++ < limit)
					sink.fill(
							new SimpleCellKey(keyWrapper.readFrom(
									itKey.getData(), 0)),
							dataWrapper.readFrom(itData.getData(), 0));
				else
					break;
			}
		} finally {
			cursor.close();
		}
	}

	@Override
	public void close() {
		flushCurrent();
		// db is temporary, i.e. on close the db is removed.
		db.close();
	}

	@Override
	public void setOrderKeyBy(int[] cellIndexes,
			org.simplesql.data.AggregateStore.ORDER order) {
		this.order = order;
		this.cellIndexes = cellIndexes;
	}

	/**
	 * 
	 * Define an ordered cursor
	 * 
	 */
	static interface OrderedCursor {
		OperationStatus getNext(DatabaseEntry key, DatabaseEntry data);

		void close();
	}

	/**
	 * Gets data in the Ascending order
	 * 
	 */
	static class AscCursor implements OrderedCursor {
		final Cursor cursor;

		public AscCursor(Cursor cursor) {
			super();
			this.cursor = cursor;
		}

		public void close() {
			cursor.close();
		}

		public OperationStatus getNext(DatabaseEntry key, DatabaseEntry data) {
			return cursor.getNext(key, data, null);
		}
	}

	/**
	 * Get data in the Desc order
	 * 
	 */
	static class DescCursor implements OrderedCursor {
		final Cursor cursor;

		public DescCursor(Cursor cursor) {
			super();
			this.cursor = cursor;
		}

		public void close() {
			cursor.close();
		}

		public OperationStatus getNext(DatabaseEntry key, DatabaseEntry data) {
			return cursor.getPrev(key, data, null);
		}
	}
}
