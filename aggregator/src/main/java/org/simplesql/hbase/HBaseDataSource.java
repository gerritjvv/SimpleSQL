package org.simplesql.hbase;

import java.util.Iterator;

import org.apache.hadoop.hbase.client.ResultScanner;
import org.simplesql.data.DataSource;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.TableDef;

public class HBaseDataSource implements DataSource {

	final HDataParser parser;
	final ResultScanner scanner;

	public HBaseDataSource(ResultScanner scanner, TableDef schema,
			SELECT select, SQLExecutor exec) {
		this.scanner = scanner;
		try {
			final Object[][] nameTypes = SimpleSQLCompiler.columnNameTypes(select.getVariables(), schema);
			final String[] columnNames = (String[]) nameTypes[0];
			
			parser = HDataSyntax.createReadParser(schema, columnNames);
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
