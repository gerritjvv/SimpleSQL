package org.simplesql.om.data;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import krati.core.segment.MappedSegmentFactory;
import krati.store.DynamicDataStore;
import krati.util.IndexedIterator;

import org.apache.commons.configuration.Configuration;
import org.mortbay.log.Log;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;
import org.simplesql.table.TableRepo;
import org.simplesql.util.Bytes;

/**
 * 
 * Use Krati to store the table definitions
 * 
 */
public class KratiTableRepo implements TableRepo {

	public static final String REPO_DIR = "repo.dir";

	DynamicDataStore store;

	public KratiTableRepo() throws Exception {

	}

	@Override
	public TableDef getTable(String name) {
		byte[] defData = store.get(Bytes.toBytes(name));
		return (defData == null) ? null : new SimpleTableDef().merge(defData);
	}

	@Override
	public void setTable(TableDef def) {
		try {
			store.put(Bytes.toBytes(def.getName()), def.serialize());
		} catch (Exception e) {
			rethrow(e);
		}
	}

	@Override
	public void removeTable(String name) {
		try {
			store.delete(Bytes.toBytes(name));
		} catch (Exception e) {
			rethrow(e);
		}
	}

	private void rethrow(Exception e) {
		RuntimeException rte = new RuntimeException(e.toString(), e);
		rte.setStackTrace(e.getStackTrace());
		throw rte;
	}

	@Override
	public Iterator<String> getTables() {

		final IndexedIterator<byte[]> it = store.keyIterator();

		return new Iterator<String>() {

			@Override
			public void remove() {
			}

			@Override
			public String next() {
				byte[] bts = it.next();
				return (bts == null) ? null : Bytes.readString(bts, 0,
						bts.length);
			}

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}
		};
	}

	@Override
	public void close() {

		if (store != null) {
			try {
				store.close();
			} catch (IOException e) {
				rethrow(e);
			}
		}

	}

	@Override
	public void init(Configuration conf) {
		File storeDir = new File(conf.getString("store.dir",
				"/tmp/kratitablerepo"));

		if (!storeDir.exists()) {
			storeDir.mkdirs();
		}

		if (!(storeDir.exists() || storeDir.canWrite())) {
			throw new RuntimeException("Cannot write to " + storeDir
					+ " or it does not exist");
		}

		int capacity = (int) (1 * 1.5);
		try {
			store = new DynamicDataStore(storeDir, capacity, /* capacity */
			100, /* update batch size */
			5, /* number of update batches required to sync indexes.dat */
			10, /* segment file size in MB */
			new MappedSegmentFactory());

			Log.info("Using " + storeDir.getAbsolutePath() + " for storage");

		} catch (Exception e) {
			rethrow(e);
		}

	}

	@Override
	public Iterator<String> iterator() {
		return getTables();
	}

}
