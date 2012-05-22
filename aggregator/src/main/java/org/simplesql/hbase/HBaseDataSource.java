package org.simplesql.hbase;

import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.simplesql.data.DataSource;
import org.simplesql.schema.TableDef;

public class HBaseDataSource implements DataSource {

	final HDataParser parser;
	final ResultScanner scanner;

	public HBaseDataSource(ResultScanner scanner, TableDef schema,
			Set<String> set) {
		this.scanner = scanner;
		try {
			parser = HDataSyntax.createReadParser(schema, set);
		} catch (Throwable t) {
			RuntimeException rte = new RuntimeException(t.toString(), t);
			rte.setStackTrace(t.getStackTrace());
			throw rte;
		}
	}

	@Override
	public Iterator<Object[]> iterator() {
		return new ResultScannerIterator(parser, scanner);
	}

	@Override
	public long getEstimatedSize() {
		return 0;
	}

}
