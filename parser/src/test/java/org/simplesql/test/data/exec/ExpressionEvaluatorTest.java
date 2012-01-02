package org.simplesql.test.data.exec;

import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;
import org.junit.Test;

public class ExpressionEvaluatorTest extends TestCase{

	@Test
	public void testExpression() throws CompileException, ParseException, ScanException, InvocationTargetException{
		
		ExpressionEvaluator eval = new ExpressionEvaluator("a + new Integer(c)",
				int.class,
				new String[]{"a", "c"},
				new Class[]{int.class, int.class}
		);
	
			Object res = eval.evaluate(new Object[]{new Integer(1),new Integer(2)});
			
			System.out.println("Res:" + res);
			
			
	}
}
