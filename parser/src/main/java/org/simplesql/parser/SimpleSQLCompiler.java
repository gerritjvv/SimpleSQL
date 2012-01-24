package org.simplesql.parser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

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
import org.simplesql.data.SimpleObjectKey;
import org.simplesql.data.SimpleStringKey;
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

	final ExecutorService execService;

	public SimpleSQLCompiler(ExecutorService execService) {
		super();
		this.execService = execService;
	}

	@Override
	public SQLExecutor compile(TableDefLoader loader, String sql) {
		try {

			SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
			SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
			parser.setTreeAdaptor(new SELECTTreeAdaptor());

			SELECT select = parser.statement().ret;

			String tableName = select.getTable();

			return compile(loader.load(tableName), select);
		} catch (Throwable t) {
			CompilerException excp = new CompilerException(t.toString(), t);
			excp.setStackTrace(t.getStackTrace());
			throw excp;
		}

	}

	@Override
	public SQLExecutor compile(TableDef tableDef, String sql) {
		try {

			SQLLexer lexer = new SQLLexer(new ANTLRStringStream(sql));
			SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
			parser.setTreeAdaptor(new SELECTTreeAdaptor());

			SELECT select = parser.statement().ret;

			return compile(tableDef, select);
		} catch (Throwable t) {
			CompilerException excp = new CompilerException(t.toString(), t);
			excp.setStackTrace(t.getStackTrace());
			throw excp;
		}

	}

	private SQLExecutor compile(TableDef tableDef, SELECT select) {
		try {
			Object[][] nameTypes = columnNameTypes(tableDef);
			String[] columnNames = (String[]) nameTypes[0];
			Class<?>[] columnTypes = (Class<?>[]) nameTypes[1];

			TreeJavaConvert converter = new TreeJavaConvert(select);

			ExpressionEvaluator eval = new ExpressionEvaluator(
					"new org.simplesql.data.Cell[]{"
							+ converter.getSelectExpressions() + "}",
					Object[].class, columnNames, columnTypes);

			// Create key based on group by.
			String groupByExpressions = converter.getGroupByExpressions();

			KeyParser keyParser = (groupByExpressions == null || groupByExpressions
					.trim().isEmpty()) ? new NoKeyParser()
					: new SimpleKeyParser(converter, columnNames, columnTypes);

			String whereExpressions = converter.getWhereExpressions();

			WhereFilter whereFilter = (whereExpressions == null || whereExpressions
					.trim().isEmpty()) ? new AlwaysTrueWhereFilter()
					: new SimpleWhereFilter(converter, columnNames, columnTypes);

			return new SimpleSQLExecutor(select.getRangeGroups(), select
					.getVariables().toArray(new String[0]), execService,
					tableDef, eval, keyParser, whereFilter,
					select.getTransforms());

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
	 * Returns a new key for each call to makeKey;
	 * 
	 * 
	 */
	static public class NoKeyParser implements KeyParser {

		final AtomicInteger id = new AtomicInteger(0);

		@Override
		public Key makeKey(Object[] data) {
			return new SimpleStringKey(String.valueOf(id.getAndIncrement()));
		}

	}

	/**
	 * 
	 * Always return truer
	 * 
	 */
	static public class AlwaysTrueWhereFilter implements WhereFilter {

		@Override
		public boolean include(Object[] data) {
			return true;
		}

		@Override
		public void write(DataOutput dataOut) throws IOException {

		}

		@Override
		public void read(DataInput dataIn) throws IOException {

		}

	}

	/**
	 * 
	 * Implements a WhereFilter by evaluating the Where Expression using an
	 * ExpressionEvaluator.
	 * 
	 */
	static public class SimpleWhereFilter implements WhereFilter {

		private static final Pattern SPLIT_COMMA = Pattern.compile(",");

		ExpressionEvaluator evalBool;

		String[] columnNames;
		Class[] columnTypes;
		String whereExpressions;

		/**
		 * Should only be used with reflection and in conjunction with the read
		 * and write methods.
		 */
		public SimpleWhereFilter() {

		}

		@SuppressWarnings("rawtypes")
		public SimpleWhereFilter(TreeJavaConvert converter,
				String[] columnNames, Class[] columnTypes)
				throws CompileException, ParseException, ScanException {

			String whereExpressions = converter.getWhereExpressions();

			evalBool = new ExpressionEvaluator(whereExpressions, Boolean.class,
					columnNames, columnTypes);

			this.columnNames = columnNames;
			this.columnTypes = columnTypes;
			this.whereExpressions = whereExpressions;
		}

		@Override
		public boolean include(Object[] data) {
			try {
				return (Boolean) evalBool.evaluate(data);
			} catch (InvocationTargetException e) {
				RuntimeException exc = new RuntimeException(e.toString(), e);
				exc.setStackTrace(e.getStackTrace());
				throw exc;
			}
		}

		@Override
		public void write(DataOutput dataOut) throws IOException {

			final byte[] columns = Arrays.toString(columnNames).getBytes(
					"UTF-8");
			final byte[] types = Arrays.toString(columnTypes).getBytes("UTF-8");
			final byte[] whereBytes = whereExpressions.getBytes("UTF-8");

			dataOut.write(columns.length);
			dataOut.write(columns);
			dataOut.write(types.length);
			dataOut.write(types);
			dataOut.write(whereBytes.length);
			dataOut.write(whereBytes);

		}

		@Override
		public void read(DataInput dataIn) throws IOException {

			String columnStr = readString(dataIn);
			String typesStr = readString(dataIn);
			String whereStr = readString(dataIn);

			// deserialize columns
			this.columnNames = SPLIT_COMMA.split(columnStr);

			// deserialize types
			String[] typesArr = SPLIT_COMMA.split(typesStr);
			ClassLoader cls = Thread.currentThread().getContextClassLoader();

			columnTypes = new Class[typesArr.length];
			for (int i = 0; i < typesArr.length; i++) {
				try {
					columnTypes[i] = cls.loadClass(typesArr[i]);
				} catch (ClassNotFoundException e) {
					rethrow(e);
				}
			}

			// save where str and compile expression
			whereExpressions = whereStr;
			try {
				evalBool = new ExpressionEvaluator(whereExpressions,
						Boolean.class, columnNames, columnTypes);
			} catch (Throwable t) {
				rethrow(t);
			}
		}

		private static final void rethrow(Throwable t) {
			RuntimeException rte = new RuntimeException(t.toString(), t);
			rte.setStackTrace(t.getStackTrace());
			throw rte;
		}

		private static final String readString(DataInput dataIn)
				throws IOException {
			int len = dataIn.readInt();
			byte[] bytes = new byte[len];
			dataIn.readFully(bytes, 0, len);

			return new String(bytes, "UTF8");
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

			String str = "new " + SimpleCellKey.class.getName() + "( new "
					+ Cell.class.getName() + "[]{"
					+ converter.getGroupByExpressions() + "})";

			evalKeyCreator = new ExpressionEvaluator("new "
					+ SimpleObjectKey.class.getName() + "( new Object[]{"
					+ converter.getGroupByExpressions() + "})", Key.class,
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
