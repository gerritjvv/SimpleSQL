package org.simplesql.hbase;

import static org.junit.Assert.assertTrue;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.TableDef;

/**
 * 
 * Test that the syntax is as expected from table to java conversion.
 * 
 */
public class TestHDataSyntax {

	@Test
	public void test1() throws Throwable {

		// TableDef def = getTable("TABLE mytable (a LONG KEY, b LONG KEY)");
		// HDataParser pars
		// ClassBodyEvaluator eval = new ClassBodyEvaluator();
		// eval.setParentClassLoader(this.getClass().getClassLoader());
		// eval.cook("import org.apache.hadoop.hbase.client.*; public class MyClass implements org.simplesql.hbase.HDataParser{ public Object[] parse(Result res, byte[] key){ return null;} }");
		// Class cls = eval.getClazz();
		//
		// System.out.println("CLS: " + cls);
		// byte[] b = new byte[]{115,109,121,102,97,109};
		//
		// new ExpressionEvaluator("int i  = 0;\n new Object[]{1}",
		// Object.class, new String[]{"res"}, new Class[]{Result.class});
		//

		// Object[] d = new Object[]{1};
		// ScriptEvaluator script = HDataSyntax.createExpr(def);
		// assertTrue(true);

	}

	@Test
	public void test2() throws Throwable {

		// TableDef def =
		// getTable("CREATE TABLE mytable (a LONG KEY, b LONG KEY, 1 f FLOAT, s m STRING 100, hello thisiscol SHORT,s s BYTE)");
		//
		//
		// HDataParser parser = HDataSyntax.createReadParser(def,
		// Arrays.asList("a", "b", "s"));

		TableDef def = getTable("CREATE TABLE testselect (fam1 k1 INT KEY, fam1 k2 STRING 2 KEY=true, fam1 count INT, fam1 temp FLOAT)");
		HDataParser parser = HDataSyntax.createReadParser(def,
				new String[]{"k1", "k2", "count", "temp"});
		assertTrue(true);

	}

	private TableDef getTable(String str) throws RecognitionException {
		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(str));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());
		parser.create();

		return parser.tableDef;
	}

}
