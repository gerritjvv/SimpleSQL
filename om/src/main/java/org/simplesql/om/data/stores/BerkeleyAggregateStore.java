package org.simplesql.om.data.stores;

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
import org.simplesql.om.data.stores.berkeley.DBManager;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;

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
	SecondaryDatabase secDb;
	CellKeyDataCreator secKeyCreator;

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
	int[] dataCellIndexes;

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

	}

	private final OrderedCursor getCursor() {
		if (secDb == null) {
			// if the secondary db was not used, open a normal cursor
			return ORDER.ASC.equals(order) ? new AscCursor(db.openCursor(null,
					null)) : new DescCursor(db.openCursor(null, null));
		} else {
			// else use the secondary database
			return ORDER.ASC.equals(order) ? new AscCursor(secDb.openCursor(
					null, null)) : new DescCursor(secDb.openCursor(null, null));
		}
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

			// we also open the secondary database here.
			// passing in the cellIndexes and the dataCellIndexes
			// we only create a secondary index if
			if (!(cellIndexes == null && dataCellIndexes == null)) {
				secKeyCreator = new CellKeyDataCreator(cellIndexes,
						dataCellIndexes, keyWrapper, dataWrapper);
				secDb = dbManager.openSecondaryDatabase(db, secKeyCreator);
			}

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

			final byte[] keybytes = keyWrapper.getBytes(key.getCells());
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
				entry.apply(dataWrapper.readFrom(rowData.getData(), 0));
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
		final CellsByteWrapper cursorKeyWrapper = (secKeyCreator == null) ? keyWrapper
				: secKeyCreator.getSecondaryKeyWrapper();
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
				return new DataEntry(new SimpleCellKey(
						cursorKeyWrapper.readFrom(itKey.getData(), 0)),
						dataWrapper.readFrom(itData.getData(), 0), functions);
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
		final CellsByteWrapper cursorKeyWrapper = (secKeyCreator == null) ? keyWrapper
				: secKeyCreator.getSecondaryKeyWrapper();
		try {

			while ((row++ < limit)
					&& cursor.getNext(itKey, itData).equals(
							OperationStatus.SUCCESS)) {
				keys.add(new SimpleCellKey(cursorKeyWrapper.readFrom(
						itKey.getData(), 0)));
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

		// the cursor will be opened depending on the ORDER used.
		// either ASC or DESC
		final OrderedCursor cursor = getCursor();
		final DatabaseEntry itKey = new DatabaseEntry();
		final DatabaseEntry itData = new DatabaseEntry();
		final CellsByteWrapper cursorKeyWrapper = (secKeyCreator == null) ? keyWrapper
				: secKeyCreator.getSecondaryKeyWrapper();

		try {
			int rows = 0;
			while (cursor.getNext(itKey, itData)
					.equals(OperationStatus.SUCCESS)) {
				if (rows++ < limit)
					sink.fill(
							new SimpleCellKey(cursorKeyWrapper.readFrom(
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

		if (secDb != null)
			secDb.close();
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

	/**
	 * 
	 * Create's a secondary database key based on the keyCellIndexes and
	 * datCellIndexes.<br/>
	 * All keys are sorted in the berkeley database due to the BTree
	 * implementation it uses, which if we only needed to order/sort by key
	 * there is not need for comparators and secondary indexes, we need to be
	 * able to sort/order by data i.e. information not in the key, and or
	 * including the key. The only way to accomplish this is with a secondary
	 * key.
	 * <p/>
	 * <b>SCHEMA</b><br/>
	 * The schema is created as follows: keyCells, dataCells so that if the
	 * keyCells point to {String, int} and the data cells point to {long} the
	 * schema is {String, int, long}
	 * 
	 */
	static class CellKeyDataCreator implements SecondaryKeyCreator {

		final int keyCellIndexes[];
		final int datCellIndexes[];
		final int keyLen;
		final int datLen;
		final int resultLen;

		final CellsByteWrapper keyWrapper, dataWrapper, secKeyWrapper;
		@SuppressWarnings("rawtypes")
		final Cell[] resultCells;

		/**
		 * 
		 * @param keyCells
		 *            can be null
		 * @param datCells
		 *            can be null
		 * @param keyWrapper
		 * @param dataWrapper
		 *            can be null
		 */
		public CellKeyDataCreator(int[] keyCells, int[] datCells,
				CellsByteWrapper keyWrapper, CellsByteWrapper dataWrapper) {
			super();
			this.keyCellIndexes = keyCells;
			this.datCellIndexes = datCells;
			this.keyWrapper = keyWrapper;
			this.dataWrapper = dataWrapper;

			// create the result key schema from the merged schemas of the key
			// and the data
			Cell.SCHEMA[] schemas;
			if (keyCells == null && datCells != null) {
				schemas = new Cell.SCHEMA[datCells.length];
				addSchema(datCells, dataWrapper, 0, schemas);
			} else if (datCells == null && keyCells != null) {
				schemas = new Cell.SCHEMA[keyCells.length];
				addSchema(keyCells, keyWrapper, 0, schemas);
			} else {
				schemas = new Cell.SCHEMA[keyCells.length + datCells.length];
				addSchema(keyCells, keyWrapper, 0, schemas);
				addSchema(datCells, dataWrapper, keyCells.length, schemas);

			}

			// precreate the result key wrapper and cell array.
			secKeyWrapper = new CellsByteWrapper(schemas);

			keyLen = (keyCellIndexes == null) ? 0 : keyCellIndexes.length;
			datLen = (datCellIndexes == null) ? 0 : datCellIndexes.length;
			resultLen = keyLen + datLen;
			resultCells = new Cell[resultLen];
		}

		/**
		 * helper method to add schemas
		 * 
		 * @param indexes
		 * @param wrapper
		 * @param from
		 * @param schemas
		 */
		private static final void addSchema(final int[] indexes,
				final CellsByteWrapper wrapper, final int from,
				final Cell.SCHEMA[] schemas) {
			final int len = indexes.length;
			for (int i = 0; i < len; i++) {
				schemas[i + from] = wrapper.schemaAt(indexes[i]);
			}

		}

		public CellsByteWrapper getSecondaryKeyWrapper() {
			return secKeyWrapper;
		}

		/**
		 * We create the secondary indexes based on the keyCellIndexes and the
		 * dataCellIndexes.
		 */
		@Override
		public boolean createSecondaryKey(SecondaryDatabase db,
				DatabaseEntry key, DatabaseEntry dat, DatabaseEntry secKey) {

			if (keyCellIndexes != null) {
				final Cell[] keyCells = keyWrapper.readFrom(key.getData(), 0);
				for (int i = 0; i < keyLen; i++) {
					// get the keycells as specified in the key cell indexes
					resultCells[i] = keyCells[keyCellIndexes[i]];
				}
			}

			if (datCellIndexes != null) {
				final Cell[] datCells = dataWrapper.readFrom(dat.getData(), 0);
				for (int i = keyLen; i < resultLen; i++) {
					// get the data cells as specified in the data cell indexes
					resultCells[i] = datCells[datCellIndexes[i]];
				}
			}

			// set the secondary key
			secKey.setData(secKeyWrapper.getBytes(resultCells));

			return true;
		}

	}

}
