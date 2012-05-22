package org.simplesql.hbase;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.configuration.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.Key;
import org.simplesql.data.RangeGroups;
import org.simplesql.data.TransformFunction;
import org.simplesql.data.VariableRange;
import org.simplesql.data.VariableRanges;
import org.simplesql.om.data.stores.KratiAggregateStore;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.tree.INSERT;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.TableDef;
import org.simplesql.table.TableEngine;
import org.simplesql.table.TableRepo;

/**
 * 
 * HBase table engine.
 * 
 */
public class HBaseTableEngine implements TableEngine {

	TableRepo repo;
	File workingDir;
	Configuration conf;
	HTablePool pool;

	@Override
	public void init(Configuration conf, TableRepo repo) {
		this.conf = conf;
		this.repo = repo;

		workingDir = new File(conf.getString("sql.storage.dir",
				"/tmp/sqlstorage"));
		workingDir.mkdirs();

		pool = new HTablePool(new org.apache.hadoop.conf.Configuration(), 100,
				new HTableInterfaceFactory() {

					@Override
					public void releaseHTableInterface(HTableInterface table)
							throws IOException {
						table.close();
					}

					@Override
					public HTableInterface createHTableInterface(
							org.apache.hadoop.conf.Configuration config,
							byte[] tableName) {
						try {
							return new HTable(tableName);
						} catch (IOException t) {
							RuntimeException rte = new RuntimeException(
									t.toString(), t);
							rte.setStackTrace(t.getStackTrace());
							throw rte;
						}
					}
				});
	}

	@Override
	public void close() {
		try {
			pool.close();
		} catch (IOException t) {
			RuntimeException rte = new RuntimeException(t.toString(), t);
			rte.setStackTrace(t.getStackTrace());
			throw rte;
		}
	}

	@Override
	public void select(SQLCompiler compiler, SELECT select, SELECT_OUTPUT output) {
		// create an

		TableDef table = repo.getTable(select.getTable());
		if (table == null)
			throw new RuntimeException("No table found for "
					+ select.getTable());

		// do select data here
		final SQLExecutor exec = compiler.compile(table, select);

		try {
			final File storeDir = new File(workingDir, "_" + System.nanoTime());

			final KratiAggregateStore store = new KratiAggregateStore(conf,
					storeDir, createSCHEMA(table), exec.getTransforms()
							.toArray(new TransformFunction[0]));

			HTableInterface htable = pool.getTable(Bytes.toBytes(table
					.getName()));

			// create start key and end key writers.
			Scan scan = new Scan();
			scan.setMaxVersions(1);

			// set only the families required in the query
			addFamilies(scan, exec.getColumnsUsed(), table);

			// set start and stop keys

			final int keyLen = columnKeyWidth(table);
			final byte[] startKey = new byte[keyLen];
			final byte[] endKey = new byte[keyLen];

			// setup start stop ranges for keys
			fillStartEndKeys(table, exec, startKey, endKey);

			scan.setStartRow(startKey);
			scan.setStopRow(endKey);

			ResultScanner scanner = htable.getScanner(scan);
			try {
				HBaseDataSource dataSource = new HBaseDataSource(scanner,
						table, exec.getColumnsUsed());
				exec.pump(dataSource, store, null);

				store.write(new DataSink() {

					@Override
					public boolean fill(Key key, Cell<?>[] data) {
						StringBuilder build = new StringBuilder();
						int i = 0;
						for (Cell cell : data) {
							if (i++ != 0)
								build.append(',');

							build.append(cell.getData());
						}

						System.out.println(build.toString());
						return true;
					}
				});

			} finally {
				scanner.close();
				htable.close();
			}

		} catch (Throwable t) {
			RuntimeException rte = new RuntimeException(t.toString(), t);
			rte.setStackTrace(t.getStackTrace());
			throw rte;
		}

	}

	private void fillStartEndKeys(TableDef table, SQLExecutor exec,
			byte[] startKey, byte[] endKey) {
		if (exec.getRangeGroups() != null) {

			RangeGroups rangeGroups = exec.getRangeGroups();

			final ColumnDef[] colDefs = table.getColumnDefs();
			int i = 0;
			int pos = 0;
			Set<String> unsetCols = new TreeSet<String>();

			for (VariableRanges ranges : rangeGroups.getRanges()) {

				final byte[] subStartKey = new byte[startKey.length];
				final byte[] subEndKey = new byte[startKey.length];

				for (ColumnDef def : colDefs) {

					if (!def.isKey())
						continue;

					final String colName = def.getName();
					Object lower, upper = null;

					if (i == 0) {
						// only add the col names once
						unsetCols.add(colName);
					}

					VariableRange range = ranges.getRange(colName);
					if (range != null) {
						// if range found remove the colName from unsetCols
						unsetCols.remove(colName);
						lower = range.getLower();
						upper = range.getUpper();

						Cell cell = def.getCell();
						cell.setData(lower);
						cell.write(subStartKey, pos);

						cell.setData(upper);
						cell.write(subEndKey, pos);

					}

					pos += def.getCell().getDefinedWidth();

				}

				// only if we find more than one VariableRanges
				// else we set the start key and end key directly
				if (i++ != 0) {
					// set start key if sub key is smaller than current
					if (Bytes.compareTo(subStartKey, startKey) < 0)
						startKey = subStartKey;

					// set end key if sub key is greater than current
					if (Bytes.compareTo(subEndKey, endKey) > 0)
						endKey = subEndKey;

				} else {
					startKey = subStartKey;
					endKey = subEndKey;
				}

			}

			// set maximum values for unspecified endKey;
			pos = 0;
			for (ColumnDef colDef : table.getColumnDefs()) {
				Cell cell = colDef.getCell();
				if (unsetCols.contains(colDef.getName())) {
					cell.setData(cell.getMax());
					cell.write(endKey, pos);
				}

				pos += cell.getDefinedWidth();
			}

		} else {

			// else leave the key empty

			// // create default keys with min max for each column
			int pos = 0;
			for (ColumnDef def : table.getColumnDefs()) {
				final String colName = def.getName();
				Cell cell = def.getCell();

				cell.setData(cell.getMax());
				cell.write(endKey, pos);
				cell.setData(cell.getMin());
				cell.write(startKey, pos);

				pos += cell.getDefinedWidth();

			}

		}

	}

	/**
	 * Add used column families
	 * 
	 * @param scan
	 * @param set
	 * @param table
	 */
	private void addFamilies(Scan scan, Set<String> set, TableDef table) {
		for (String col : set) {
			ColumnDef colDef = table.getColumnDef(col);
			scan.addFamily(Bytes.toBytes(colDef.getFamily()));
		}
	}

	private int columnKeyWidth(TableDef def) {
		ColumnDef[] cols = def.getColumnDefs();
		int width = 0;
		for (int i = 0; i < cols.length; i++) {
			if (cols[i].isKey())
				width += cols[i].getCell().getDefinedWidth();
		}
		return width;
	}

	@Override
	public void insert(INSERT insert) {
		// TODO Auto-generated method stub

	}

	private static final Cell.SCHEMA[] createSCHEMA(TableDef def) {
		final ColumnDef[] cols = def.getColumnDefs();
		final int len = cols.length;

		final Cell.SCHEMA[] schemas = new Cell.SCHEMA[len];
		for (int i = 0; i < len; i++) {
			schemas[i] = cols[i].getCell().getSchema();
		}

		return schemas;
	}

}
