package org.simplesql.test.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.StringCell;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.SimpleTableDef;

public class TestCreateTable {

	@Test
	public void testTableCreate() throws RecognitionException{
		
		String sql = "CREATE TABLE mytable (col INT KEY, col2 STRING 100 KEY=true, col3 LONG, col4 LONG COUNTER) ENGINE HBASE";
		
		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		parser.create();
		
		SimpleTableDef tbl = parser.tableDef;
		System.out.println("TBL: " + tbl);
		assertNotNull(tbl);
		assertEquals("mytable", tbl.getName());
		assertEquals("HBASE", tbl.getEngine());
		
		ColumnDef col = tbl.getColumnDef("col");
		assertEquals(IntCell.class, col.getCell().getClass());
		assertEquals(true, col.isKey());
		assertEquals(false, col.isCounter());
		
		col = tbl.getColumnDef("col2");
		assertEquals(StringCell.class, col.getCell().getClass());
		assertEquals(true, col.isKey());
		assertEquals(100, col.getCell().getDefinedWidth());
		assertEquals(false, col.isCounter());
		
		col = tbl.getColumnDef("col3");
		assertEquals(LongCell.class, col.getCell().getClass());
		assertEquals(false, col.isKey());
		assertEquals(false, col.isCounter());
		
		col = tbl.getColumnDef("col4");
		assertEquals(LongCell.class, col.getCell().getClass());
		assertEquals(false, col.isKey());
		assertEquals(true, col.isCounter());
	}
}
