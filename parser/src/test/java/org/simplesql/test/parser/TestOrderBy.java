package org.simplesql.test.parser;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.IntCell;
import org.simplesql.data.StringCell;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

/**
 * Test the following: Order by without ASC or DESC specified.<br/>
 * Order by with DESC specified.<br/>
 * Order by with As key word.<br/>
 * Order by Group parameters.<br/>
 * Order by Group poarameters with DESC Specified.<br/>
 * 
 * 
 */
public class TestOrderBy extends TestCase {

	/**
	 * Test variables with only one bound defined
	 */
	@Test
	public void testSimpleOrderBySelect() {

		String str = "SELECT c, b, a FROM mytbl ORDER BY a,b";

		TableDef tableDef = new SimpleTableDef("mytbl", new SimpleColumnDef(
				Integer.class, "a", new IntCell()), new SimpleColumnDef(
				Integer.class, "b", new IntCell()), new SimpleColumnDef(
				String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);

		SQLExecutor exec = compiler.compile(tableDef, str);

		assertEquals(AggregateStore.ORDER.ASC, exec.getOrder());
		Set<String> orderByColumns = exec.getSelectOrderByColumns();
		Set<String> groupOrderByColumns = exec.getGroupOrderByColumns();

		// check that the group order is empty
		assertEquals(0, groupOrderByColumns.size());
		assertTrue(orderByColumns.contains("a"));
		assertTrue(orderByColumns.contains("b"));

		// the order for a,b,c in the select statement is put in reverse such
		// that
		// we expect indexes c=0, b=1, a=2
		// order is a,b so we expect indexes [2,1]

		int[] selectOrderIndexes = exec.getSelectOrderIndexes();
		assertEquals(2, selectOrderIndexes.length);
		assertEquals(2, selectOrderIndexes[0]);
		assertEquals(1, selectOrderIndexes[1]);

		// no order on a group by statement was specified so we expect null
		// here.
		assertNull(exec.getGroupOrderIndexes());
	}

	/**
	 */
	@Test
	public void testSimpleOrderBySelectAsKeyword() {

		String str = "SELECT c, b AS y, a AS x FROM mytbl ORDER BY x,y";

		TableDef tableDef = new SimpleTableDef("mytbl", new SimpleColumnDef(
				Integer.class, "a", new IntCell()), new SimpleColumnDef(
				Integer.class, "b", new IntCell()), new SimpleColumnDef(
				String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);

		SQLExecutor exec = compiler.compile(tableDef, str);

		assertEquals(AggregateStore.ORDER.ASC, exec.getOrder());
		Set<String> orderByColumns = exec.getSelectOrderByColumns();
		Set<String> groupOrderByColumns = exec.getGroupOrderByColumns();

		// check that the group order is empty
		assertEquals(0, groupOrderByColumns.size());
		assertTrue(orderByColumns.contains("x"));
		assertTrue(orderByColumns.contains("y"));

		// the order for a,b,c in the select statement is put in reverse such
		// that
		// we expect indexes c=0, b=1, a=2
		// order is a,b so we expect indexes [2,1]

		int[] selectOrderIndexes = exec.getSelectOrderIndexes();
		assertEquals(2, selectOrderIndexes.length);
		assertEquals(2, selectOrderIndexes[0]);
		assertEquals(1, selectOrderIndexes[1]);

		// no order on a group by statement was specified so we expect null
		// here.
		assertNull(exec.getGroupOrderIndexes());
	}

	/**
	 */
	@Test
	public void testComplexOrderBySelectAsKeyword() {

		String str = "SELECT c, b+1 AS y, a+b AS x FROM mytbl ORDER BY x,y";

		TableDef tableDef = new SimpleTableDef("mytbl", new SimpleColumnDef(
				Integer.class, "a", new IntCell()), new SimpleColumnDef(
				Integer.class, "b", new IntCell()), new SimpleColumnDef(
				String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);

		SQLExecutor exec = compiler.compile(tableDef, str);

		assertEquals(AggregateStore.ORDER.ASC, exec.getOrder());
		Set<String> orderByColumns = exec.getSelectOrderByColumns();
		Set<String> groupOrderByColumns = exec.getGroupOrderByColumns();

		// check that the group order is empty
		assertEquals(0, groupOrderByColumns.size());
		assertTrue(orderByColumns.contains("x"));
		assertTrue(orderByColumns.contains("y"));

		// the order for a,b,c in the select statement is put in reverse such
		// that
		// we expect indexes c=0, b=1, a=2
		// order is a,b so we expect indexes [2,1]

		int[] selectOrderIndexes = exec.getSelectOrderIndexes();
		assertEquals(2, selectOrderIndexes.length);
		assertEquals(2, selectOrderIndexes[0]);
		assertEquals(1, selectOrderIndexes[1]);

		// no order on a group by statement was specified so we expect null
		// here.
		assertNull(exec.getGroupOrderIndexes());
	}

	/**
	 */
	@Test
	public void testComplexOrderByGroupAsKeyword() {

		String str = "SELECT c, b+1 AS y, a+b AS x FROM mytbl GROUP BY a,b,c ORDER BY a,c";

		TableDef tableDef = new SimpleTableDef("mytbl", new SimpleColumnDef(
				Integer.class, "a", new IntCell()), new SimpleColumnDef(
				Integer.class, "b", new IntCell()), new SimpleColumnDef(
				String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);

		SQLExecutor exec = compiler.compile(tableDef, str);

		assertEquals(AggregateStore.ORDER.ASC, exec.getOrder());
		Set<String> orderByColumns = exec.getSelectOrderByColumns();
		Set<String> groupOrderByColumns = exec.getGroupOrderByColumns();

		// check that the group order is empty
		assertEquals(0, orderByColumns.size());
		assertTrue(groupOrderByColumns.contains("a"));
		assertTrue(groupOrderByColumns.contains("c"));

		// the order for a,b,c the group a,b,c
		// we expect indexes a=0, b=1, c=2
		// order is a,b so we expect indexes [0,2]

		int[] groupOrderIndexes = exec.getGroupOrderIndexes();
		assertEquals(2, groupOrderIndexes.length);
		assertEquals(0, groupOrderIndexes[0]);
		assertEquals(2, groupOrderIndexes[1]);

		// no order on a group by statement was specified so we expect null
		// here.
		assertNull(exec.getSelectOrderIndexes());
	}

	/**
	 */
	@Test
	public void testComplexOrderBySelectGroupAsKeyword() {

		String str = "SELECT c, b+1 AS y, a+b AS x FROM mytbl GROUP BY a,b ORDER BY a,x";

		TableDef tableDef = new SimpleTableDef("mytbl", new SimpleColumnDef(
				Integer.class, "a", new IntCell()), new SimpleColumnDef(
				Integer.class, "b", new IntCell()), new SimpleColumnDef(
				String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);

		SQLExecutor exec = compiler.compile(tableDef, str);

		assertEquals(AggregateStore.ORDER.ASC, exec.getOrder());
		Set<String> selectOrderByColumns = exec.getSelectOrderByColumns();
		Set<String> groupOrderByColumns = exec.getGroupOrderByColumns();

		// check that the group order is empty
		assertEquals(1, selectOrderByColumns.size());

		assertTrue(groupOrderByColumns.contains("a"));
		assertTrue(selectOrderByColumns.contains("x"));

		// the order for a,b,c the group a,b
		// we expect indexes a=0, b=1
		// order is a,b so we expect indexes [0,1]

		int[] groupOrderIndexes = exec.getGroupOrderIndexes();
		assertEquals(1, groupOrderIndexes.length);
		assertEquals(0, groupOrderIndexes[0]);

		// expect indexes c=0,y=1,x=2
		int[] selectOrderIndexes = exec.getSelectOrderIndexes();
		assertEquals(1, selectOrderIndexes.length);
		assertEquals(2, selectOrderIndexes[0]);

	}

}
