package org.simplesql.parser;

import java.lang.reflect.InvocationTargetException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;
import org.simplesql.data.Cell;
import org.simplesql.data.Key;
import org.simplesql.data.KeyParser;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.StringCell;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.parser.tree.TreeJavaConvert;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.TableDef;

/**
 * 
 * Implements The SQLCompiler interface.<br/>
 * Using Antlr3 generated SQL classes and Janino ExpressionEvaluator.<br/>
 * 
 */
public class SimpleSQLCompiler implements SQLCompiler {

	@Override
	public SQLExecutor compile(TableDef tableDef, String sql) {
		try {
			Object[][] nameTypes = columnNameTypes(tableDef);
			String[] columnNames = (String[]) nameTypes[0];
			Class<?>[] columnTypes = (Class<?>[]) nameTypes[1];

			SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
			SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
			parser.setTreeAdaptor(new SELECTTreeAdaptor());

			SELECT select = parser.statement().ret;
			TreeJavaConvert converter = new TreeJavaConvert(select);

			ExpressionEvaluator eval = new ExpressionEvaluator(
					"new org.simplesql.data.Cell[]{"
							+ converter.getSelectExpressions() + "}",
					Object[].class, columnNames, columnTypes);

			KeyParser keyParser = (converter.getGroupByExpressions() == null) ? new NoKeyParser()
					: new SimpleKeyParser(converter, columnNames, columnTypes);
			
			return new SimpleSQLExecutor(tableDef, eval, keyParser);

		} catch (Throwable t) {
			CompilerException excp = new CompilerException(t.toString(), t);
			excp.setStackTrace(t.getStackTrace());
			throw excp;
		}

	}

	/**
	 * Return an Object array of len == 2.<br/>
	 * index[0] = String[] == names<br/>
	 * index[1] = Class[] == types<br/>
	 * 
	 * @param tableDef
	 * @return
	 */
	private static final Object[][] columnNameTypes(TableDef tableDef) {

		ColumnDef[] defs = tableDef.getColumnDefs();
		int len = defs.length;
		String[] names = new String[len];
		Class<?>[] types = new Class<?>[len];

		for (int i = 0; i < len; i++) {
			ColumnDef def = defs[i];
			names[i] = def.getName();
			types[i] = def.getJavaType();
		}

		return new Object[][] { names, types };
	}

	/**
	 * Returns a single key with name equal '_';
	 * 
	 * 
	 */
	static public class NoKeyParser implements KeyParser {

		static final Key key = new SimpleCellKey(new Cell[] { new StringCell("_") });

		@Override
		public Key makeKey(Object[] data) {
			return key;
		}

	}

	/**
	 * 
	 * Create Key's from evaluating the Object[] data with the Group By
	 * expression.
	 * 
	 */
	static public class SimpleKeyParser implements KeyParser {

		final ExpressionEvaluator evalKeyCreator;

		public SimpleKeyParser(TreeJavaConvert converter, String[] columnNames,
				Class[] columnTypes) throws CompileException, ParseException,
				ScanException {
			evalKeyCreator = new ExpressionEvaluator("new "
					+ SimpleCellKey.class.getName() + "("
					+ converter.getSelectExpressions() + ")", Object[].class,
					columnNames, columnTypes);

		}

		@Override
		public final Key makeKey(final Object[] data) {
			try {
				return (Key) evalKeyCreator.evaluate(data);
			} catch (InvocationTargetException e) {
				RuntimeException exc = new RuntimeException(e.toString(), e);
				exc.setStackTrace(e.getStackTrace());
				throw exc;
			}
		}

	}

}
