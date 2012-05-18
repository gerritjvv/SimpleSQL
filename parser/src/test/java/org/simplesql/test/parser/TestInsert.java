package org.simplesql.test.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.INSERT;
import org.simplesql.parser.tree.SELECTTreeAdaptor;

public class TestInsert {

	@Test
	public void testTableCreate() throws RecognitionException {

		String sql = "INSERT INTO mytable (col1, col2) STDIN";

		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		INSERT insert = parser.insert().insert;

		assertNotNull(insert);
		assertEquals("mytable", insert.getTable());

		List<String> cols = insert.getColumns();
		System.out.println("cols: " + cols);
		assertEquals("col1", cols.get(0));
		assertEquals("col2", cols.get(1));
		assertEquals("STDIN", insert.getInput());

	}
}
