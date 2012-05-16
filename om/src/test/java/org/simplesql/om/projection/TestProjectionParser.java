package org.simplesql.om.projection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.junit.Test;
import org.simplesql.om.ClientInfoTemplate.Column;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.projection.ProjectionParser.projection_return;

/**
 * 
 * Test creating projections via the ProjectionParser.
 * 
 */
public class TestProjectionParser {

	@Test
	public void testCreateMultipleColumns() throws Throwable {
		String str = "TABLE mytable (col INT, mycol STRING 200)";
		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(str));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn = parser.projection();
		Projection projection = preturn.builder.build();

		assertEquals("mytable", projection.getName());
		assertEquals(2, projection.getColumnCount());

		for (Column column : projection.getColumnList()) {
			if (column.getName().equals("col")) {
				assertEquals("INT", column.getType());
				assertEquals(0, column.getOrder());
			} else if (column.getName().equals("mycol")) {
				assertEquals("STRING", column.getType());
				assertEquals(200, column.getWidth());
				assertEquals(1, column.getOrder());
			} else {
				assertTrue(false);
			}
		}

	}

	@Test
	public void testCreate() throws Throwable {
		String str = "TABLE mytable (col LONG KEY)";
		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(str));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn = parser.projection();
		Projection projection = preturn.builder.build();

		assertEquals("mytable", projection.getName());
		assertEquals(1, projection.getColumnCount());

		for (Column column : projection.getColumnList()) {
			if (column.getName().equals("col")) {
				assertTrue(column.getKey());
				assertEquals("LONG", column.getType());
				assertEquals(0, column.getOrder());
			}
		}

	}
	
	@Test
	public void testCreateKeys() throws Throwable {
		String str = "TABLE mytable ( col LONG KEY, col1 STRING 100 KEY=true, col2 STRING 10 KEY=false )";
		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(str));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn = parser.projection();
		Projection projection = preturn.builder.build();

		assertEquals("mytable", projection.getName());
		assertEquals(3, projection.getColumnCount());

		for (Column column : projection.getColumnList()) {
			if (column.getName().equals("col")) {
				assertTrue(column.getKey());
				assertEquals("LONG", column.getType());
				assertEquals(0, column.getOrder());
			}else if (column.getName().equals("col1")) {
				assertTrue(column.getKey());
				assertEquals("STRING", column.getType());
				assertEquals(1, column.getOrder());
			}else if (column.getName().equals("col2")) {
				assertFalse(column.getKey());
				assertEquals("STRING", column.getType());
				assertEquals(2, column.getOrder());
			}
		}

	}

	@Test
	public void testCreateWrongSyntax() throws Throwable {
		System.out.println("Test Producing Errors");
		String str = "TABLE mytable col LONG)";
		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(str));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		parser.projection();

		assertTrue(parser.getNumberOfSyntaxErrors() > 0);

	}

}
