package org.simplesql.om.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.Executors;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.key.KeyWriterReader;
import org.simplesql.om.projection.ProjectionLexer;
import org.simplesql.om.projection.ProjectionParser;
import org.simplesql.om.projection.ProjectionParser.projection_return;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.schema.TableDef;
import org.simplesql.util.Bytes;

/**
 * Test the key range settings of the ProjectionKeyUtil class
 * 
 * 
 */
public class TestProjectionKeyUtil {

	@Test
	public void testStartEndKey() throws Throwable {

		long clientId = 99;
		int projectionId = 88;

		Projection projection = createProjection(clientId, projectionId);

		final SQLCompiler compiler = new SimpleSQLCompiler(
				Executors.newCachedThreadPool());
		final SQLExecutor sqlExec = compiler.compile(
				ProjectionKeyUtil.createTableDef(projection),
				"SELECT col FROM table WHERE col=10 AND mycol=\"AA\" ");

		final KeyWriterReader startKey = new KeyWriterReader(projection);
		final KeyWriterReader endKey = new KeyWriterReader(projection);

		TableDef tableDef = ProjectionKeyUtil.createTableDef(projection);

		// set the start key and end key values in bytes
		ProjectionKeyUtil.setKeyRanges(tableDef, sqlExec, startKey, endKey,
				projection);

		assertTrue(Bytes.compareTo(startKey.getKey(), endKey.getKey()) < 0);
		Object[] startValues = startKey.getValues();
		Object[] endValues = endKey.getValues();

		System.out.println("Start: " + Arrays.toString(startValues));
		System.out.println("End: " + Arrays.toString(endValues));

		// test for value types
		assertTrue(startValues[0] instanceof Long);
		assertTrue(startValues[1] instanceof Integer);
		assertTrue(startValues[2] instanceof Integer);
		assertTrue(startValues[3] instanceof String);
		assertTrue(startValues[4] instanceof Boolean);
		assertTrue(startValues[5] instanceof Long);
		assertTrue(startValues[6] instanceof Long);

		assertTrue(endValues[0] instanceof Long);
		assertTrue(endValues[1] instanceof Integer);
		assertTrue(endValues[2] instanceof Integer);
		assertTrue(endValues[3] instanceof String);
		assertTrue(endValues[4] instanceof Boolean);
		assertTrue(endValues[5] instanceof Long);
		assertTrue(endValues[6] instanceof Long);

		System.out
				.println("StartCol: " + ((Integer) startValues[2]).intValue());
		System.out.println("EndCol: " + ((Integer) endValues[2]).intValue());
		// test for start end gap. The gap between integer values should be one.
		assertEquals(((Integer) endValues[2]).intValue()
				- ((Integer) startValues[2]).intValue(), 1);

		// test that the start is AA and end is B (B is one bigger than A)
		assertEquals(startValues[3], "AA");
		assertEquals(endValues[3], "B");

		// test that the clientId and projectionId are the same
		assertEquals(clientId, startValues[0]);
		assertEquals(projectionId, startValues[1]);
		assertEquals(clientId, endValues[0]);
		assertEquals(projectionId, endValues[1]);

	}

	private Projection createProjection(long clientId, int projectionId)
			throws Throwable {

		String projectionStr = "TABLE mytable (col INT, mycol STRING 200, flag BOOLEAN )";

		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(
				projectionStr));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn = parser.projection();
		return preturn.builder.setClientId(clientId).setId(projectionId)
				.build();

	}

}
