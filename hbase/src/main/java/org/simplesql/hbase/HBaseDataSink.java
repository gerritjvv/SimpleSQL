package org.simplesql.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTableInterfaceFactory;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.util.Bytes;
import org.simplesql.data.Cell;
import org.simplesql.data.DefaultDataSink;
import org.simplesql.data.Key;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.key.KeyWriterReader;
import org.simplesql.om.projection.ProjectionLexer;
import org.simplesql.om.projection.ProjectionParser;
import org.simplesql.om.projection.ProjectionParser.projection_return;
import org.simplesql.om.util.ExceptionUtil;

/**
 * 
 * Provides a framework for writing HBaseDataSink(s)
 *
 */
public abstract class HBaseDataSink extends DefaultDataSink{

	
	final HTablePool pool;
	final byte[] tblName;
	final Projection keyProjection;
	
	final KeyWriterReader kwr;
	
	HTableInterface currentable;
	
	final List<Row> rows = new ArrayList<Row>(100);
	
	public HBaseDataSink(String keySchema, HTablePool tablePool, String tblName) {
		super();
		this.pool = tablePool;
		this.tblName = Bytes.toBytes(tblName);
		
		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(
				keySchema));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn;
		try {
			preturn = parser.projection();
		} catch (RecognitionException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
		
		keyProjection = preturn.builder.build();
		kwr = new KeyWriterReader(keyProjection);
		
	}

	@Override
	public void open() {
		currentable = pool.getTable(tblName);
	}

	@Override
	public void flush() {
		try {
			currentable.batch(rows);
			currentable.flushCommits();
		} catch (IOException e) {
			ExceptionUtil.throwRuntime(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}

	@Override
	public void close() {
		try {
			currentable.close();
		} catch (IOException e) {
			ExceptionUtil.throwRuntime(e);
		}
	}

	public boolean fill(Key key, Cell<?>[] data) {
		rows.add(createRow(kwr, key, data));
		
		return true;
	}
	
		
	abstract Row createRow(KeyWriterReader kwr, Key key, Cell<?>[] data);

	/**
	 * Utility method to return a TableFactory that will set auto flush to false for each table
	 * @return HTableInterfaceFactory
	 */
	public static final HTableInterfaceFactory getTableFactory(){
		return new HBaseTableFactory();
	}
	
	private static class HBaseTableFactory implements  HTableInterfaceFactory {

		public HTableInterface createHTableInterface(Configuration conf,
				byte[] name) {
			HTable tbl = null;
			try {
				tbl = new HTable(conf, name);
			} catch (IOException e) {
				ExceptionUtil.throwRuntime(e);
			}
			
			tbl.setAutoFlush(false);
			return tbl;
		}

		public void releaseHTableInterface(HTableInterface tbl)
				throws IOException {
			tbl.close();
		}
		
	}
	
	
}
