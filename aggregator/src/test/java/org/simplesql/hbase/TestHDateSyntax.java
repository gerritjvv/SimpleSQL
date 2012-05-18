package org.simplesql.hbase;

import static org.junit.Assert.assertTrue;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.apache.hadoop.hbase.client.Result;
import org.codehaus.janino.ClassBodyEvaluator;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.ScriptEvaluator;
import org.junit.Test;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.projection.ProjectionLexer;
import org.simplesql.om.projection.ProjectionParser;
import org.simplesql.om.projection.ProjectionParser.projection_return;
import org.simplesql.om.util.ProjectionKeyUtil;
import org.simplesql.schema.TableDef;

/**
 * 
 * Test that the syntax is as expected from table to java conversion.
 * 
 */
public class TestHDateSyntax {


	@Test
	public void test1() throws Throwable {

		TableDef def = getTable("TABLE mytable (a LONG KEY, b LONG KEY)");
//		HDataParser pars
		ClassBodyEvaluator eval = new ClassBodyEvaluator();
		eval.setParentClassLoader(this.getClass().getClassLoader());
		eval.cook("import org.apache.hadoop.hbase.client.*; public class MyClass implements org.simplesql.hbase.HDataParser{ public Object[] parse(Result res, byte[] key){ return null;} }");
		Class cls = eval.getClazz();
		
		System.out.println("CLS: " + cls);
		byte[] b = new byte[]{115,109,121,102,97,109};
//		
//		new ExpressionEvaluator("int i  = 0;\n new Object[]{1}", 
//				Object.class, new String[]{"res"}, new Class[]{Result.class});
//		
		
		
//		Object[] d = new Object[]{1};
//		ScriptEvaluator script = HDataSyntax.createExpr(def);
//		assertTrue(true);

	}
	
	@Test
	public void test2() throws Throwable {

		TableDef def = getTable("TABLE mytable (a LONG KEY, b LONG KEY, 1 f FLOAT, s m STRING 100, hello thisiscol SHORT,s s BYTE)");

		String java = HDataSyntax.createJavaSyntax(def);
		
		System.out.println(java);
		
		HDataSyntax.createExpr(def);
		assertTrue(true);

	}

	private TableDef getTable(String str) throws RecognitionException {

		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(str));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn = parser.projection();
		Projection projection = preturn.builder.build();

		return ProjectionKeyUtil.createTableDefNoIds(projection);
	}

	
	static interface HDataParser{
		Object[] parse(Result res, byte[] key);
	}
	
}
