package org.simplesql.data.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;
import org.simplesql.schema.ColumnDef;

/**
 * 
 * Used to read an array of string values into an array of Object.
 * 
 */
public class SelectTransform {

	final ExpressionEvaluator eval;

	public SelectTransform(ColumnDef[] defs, Set<String> colsUsed)
			throws CompileException, ParseException, ScanException {

		StringBuilder buff = new StringBuilder();
		final int len = defs.length;
		buff.append("new Object[").append("]{");

		Class<?>[] colTypes = new Class<?>[defs.length];

		int a = 0;
		for (int i = 0; i < defs.length; i++) {
			ColumnDef def = defs[i];
			Class<?> cls = def.getJavaType();

			if (colsUsed.contains(def.getName())) {
				if (i != 0)
					buff.append(",");

				if (def.isNumber() || Boolean.class.isAssignableFrom(cls))
					buff.append("new ").append(cls.getName()).append("(").append("input[")
							.append(i).append("])");
				else {
					buff.append("input[").append(i).append("]");
				}

			}

			colTypes[i] = cls;
		}

		buff.append("}");


		eval = new ExpressionEvaluator(buff.toString(), Object[].class,
				new String[] { "input" }, new Class[] { String[].class });

	}

	public Object[] transform(String[] input) throws InvocationTargetException {
		return (Object[]) eval.evaluate(new Object[]{ input } );
	}

}
