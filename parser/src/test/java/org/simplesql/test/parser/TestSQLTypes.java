package org.simplesql.test.parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.simplesql.data.BooleanCell;
import org.simplesql.data.LongCell;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

public class TestSQLTypes {

	
	/**
	 * Test variables with only one bound defined
	 */
	@Test
	public void testBoolean(){
		
	String str = "SELECT a,b,c FROM mytbl WHERE a = TRUE AND b = TRUE AND c = FALSE and c = false";
		
		TableDef tableDef = new SimpleTableDef("mytbl", 
				new SimpleColumnDef(Boolean.class, "a", new BooleanCell()),
				new SimpleColumnDef(Boolean.class, "b", new BooleanCell()),
				new SimpleColumnDef(Boolean.class, "c", new BooleanCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);
		SQLExecutor exec = compiler.compile(tableDef, str);
		
	}
	

	/**
	 * Test variables with only one bound defined
	 */
	@Test
	public void testLong(){
		
	String str = "SELECT a,b,c FROM mytbl WHERE a = " + Long.MAX_VALUE + " AND b = TRUE AND c = FALSE and c = false";
		
		TableDef tableDef = new SimpleTableDef("mytbl", 
				new SimpleColumnDef(Long.class, "a", new LongCell()),
				new SimpleColumnDef(Boolean.class, "b", new BooleanCell()),
				new SimpleColumnDef(Boolean.class, "c", new BooleanCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);
		SQLExecutor exec = compiler.compile(tableDef, str);
		
	}

	
}
