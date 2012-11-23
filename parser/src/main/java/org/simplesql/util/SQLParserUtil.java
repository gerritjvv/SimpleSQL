package org.simplesql.util;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.INSERT;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.TableDef;

/**
 * 
 * Helper class to parse sql
 * 
 */
public class SQLParserUtil {

	
	public static TableDef parseTableDef(String tableDef) {

		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(tableDef));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		try {
			parser.create();
		} catch (RecognitionException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

		return parser.tableDef;

	}

	public static SELECT parseSelect(String sql) {

		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		try {
			parser.statement();
		} catch (RecognitionException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

		return parser.select;

	}

	public static INSERT parseInsert(String sql) {

		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		try {
			parser.insert();
		} catch (RecognitionException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}

		return parser.insert;

	}

}
