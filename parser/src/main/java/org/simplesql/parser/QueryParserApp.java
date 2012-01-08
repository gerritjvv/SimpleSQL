package org.simplesql.parser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.antlr.runtime.tree.CommonTree;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DataSource;
import org.simplesql.data.IntCell;
import org.simplesql.data.Key;
import org.simplesql.data.StringCell;
import org.simplesql.data.TransformFunction;
import org.simplesql.data.impl.HashMapAggregateStore;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

public class QueryParserApp {

	public static void main(String[] args) throws Throwable {
		String str = "SELECT 1, b, a*0.5/2, \"STR\",c , COUNT(1) FROM table WHERE a>=1 AND b<5 GROUP BY a, b";
		
		TableDef tableDef = new SimpleTableDef("mytbl", 
				new SimpleColumnDef(Integer.class, "a", new IntCell()),
				new SimpleColumnDef(Integer.class, "b", new IntCell()),
				new SimpleColumnDef(String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);
		
		SQLExecutor exec = compiler.compile(tableDef, str);
		
		Object[][] data = new Object[][]{
			
				new Object[]{ 1, 2, "hi"},
				new Object[]{ 1, 3, "hi there"},
				new Object[]{ 1, 3, "hi there"}
				
		};
		
		AggregateStore store = new HashMapAggregateStore(exec.getTransforms().toArray(new TransformFunction[0]));
		
		final List<Object[]> dataList = Arrays.asList(data);
		
		
		exec.pump(new DataSource() {
			
			@Override
			public Iterator<Object[]> iterator() {
					return dataList.iterator();
			}
			
			@Override
			public long getEstimatedSize() {
				return dataList.size();
			}
			
			
		}, store, null);
		
		execService.shutdown();
		
		store.write(new DataSink() {
			
			@Override
			public boolean fill(Key key, Cell<?>[] data) {
				System.out.println(key.asString().hashCode() + " : " + Arrays.toString(data));
				return true;
			}
		});
		

	}


}
