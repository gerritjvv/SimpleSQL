package org.simplesql.om.data;

import java.io.File;
import java.util.Iterator;

import krati.core.segment.MappedSegmentFactory;
import krati.store.DynamicDataStore;
import krati.util.IndexedIterator;

import org.simplesql.data.TableRepo;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;
import org.simplesql.util.Bytes;

/**
 * 
 * Use Krati to store the table definitions
 * 
 */
public class KratiTableRepo implements TableRepo {

	final DynamicDataStore store;

	public KratiTableRepo(File storeDir) throws Exception {
		int capacity = (int) (1 * 1.5);
		store = new DynamicDataStore(storeDir, capacity, /* capacity */
		100, /* update batch size */
		5, /* number of update batches required to sync indexes.dat */
		10, /* segment file size in MB */
		new MappedSegmentFactory());
	}

	@Override
	public TableDef getTable(String name) {
		byte[] defData = store.get(Bytes.toBytes(name));
		SimpleTableDef tbl = new SimpleTableDef();
		tbl.merge(defData);
		return tbl;
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

}
