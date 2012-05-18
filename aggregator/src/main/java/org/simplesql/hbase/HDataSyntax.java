package org.simplesql.hbase;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.codehaus.janino.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Parser.ParseException;
import org.codehaus.janino.Scanner.ScanException;
import org.codehaus.janino.ScriptEvaluator;
import org.simplesql.data.Cell;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.TableDef;

public class HDataSyntax {

	static final String importStr = "import java.lang.*; import java.util.*; import org.apache.hadoop.hbase.util.*; import org.apache.hadoop.hbase.client.*; import org.apache.hadoop.hbase.filter.*;\n";

	public static ScriptEvaluator createExpr(TableDef schema)
			throws CompileException, ParseException, ScanException {
		final String java = createJavaSyntax(schema);
		// ExpressionEvaluator.createFastScriptEvaluator(importStr + java,
		// Object.class, new String[] { "key", "res" });
		System.out.println(importStr + java +";");
		return new ScriptEvaluator(importStr + java , Object[].class,
				new String[] { "key", "res" }, new Class[] { byte[].class,
						Result.class });
		// return new ExpressionEvaluator(importStr + java,
		// Object.class, new String[] { "key", "res" }, new Class[] {
		// byte[].class, Result.class });
	}

	/**
	 * Assumes that two external variables res:Result and key:byte[] will be
	 * made available to the script.
	 * 
	 * @param schema
	 * @return
	 */
	public static final String createJavaSyntax(TableDef schema) {

		final StringBuilder buff = new StringBuilder(100);
		final StringBuilder header = new StringBuilder(100);

		int keyFrom = 0;
		final ColumnDef[] cols = schema.getColumnDefs();
		for (int i = 0; i < cols.length; i++) {
			final ColumnDef col = cols[i];

			if (i != 0)
				buff.append("\n,");

			final Cell cell = col.getCell();

			if (col.isKey()) {

				buff.append(getKeyRead("key", cell, keyFrom));
				keyFrom += cell.getDefinedWidth();

			} else {

				final String strFam = col.getFamily();
				if (strFam == null) {
					throw new RuntimeException("The column " + col.getName()
							+ " must have a family name");
				}

				final byte[] familyName = Bytes.toBytes(strFam);
				final byte[] name = Bytes.toBytes(col.getName());

				header.append("\nfinal KeyValue col").append(col.getName())
						.append(" = res.getColumnLatest(")
						.append(arrayToString(familyName)).append(", ")
						.append(arrayToString(name)).append(");");

				header.append("\nfinal byte[] arr").append(col.getName())
						.append(" = col").append(col.getName())
						.append(".getBuffer();");

				buff.append(getValueRead(col.getName(), cell));

			}
		}

		header.append("\nnew Object[]{");
		header.append(buff.toString());
		header.append("}");

		return header.toString();
	}

	private static final String arrayToString(byte[] bytes) {
		StringBuilder buff = new StringBuilder(bytes.length * 2);

		buff.append("new byte[]{");

		for (int i = 0; i < bytes.length; i++) {
			if (i != 0)
				buff.append(',');

			buff.append("(byte)").append(bytes[i]);
		}

		return buff.append("}").toString();
	}

	/**
	 * Write the java code that translates each type from bytes to its java
	 * type.
	 * 
	 * @param colName
	 *            the name of the column used to find the header arr$colName and
	 *            col$ColName
	 * @param cell
	 *            The cell of the current column
	 * @return String java string
	 */
	private static final String getValueRead(String colName, Cell cell) {
		final Cell.SCHEMA schema = cell.getSchema();

		if (schema.equals(Cell.SCHEMA.BOOLEAN))
			return "org.simplesql.util.Bytes.readBoolean(arr" + colName
					+ " , col" + colName + ".getValueOffset())";
		else if (schema.equals(Cell.SCHEMA.BYTE))
			return "arr" + colName + "[col" + colName + ".getValueOffset()]";
		else if (schema.equals(Cell.SCHEMA.DOUBLE))
			return "org.simplesql.util.Bytes.readDouble(arr" + colName
					+ " , col" + colName + ".getValueOffset())";
		else if (schema.equals(Cell.SCHEMA.LONG))
			return "org.simplesql.util.Bytes.readLong(arr" + colName + " , col"
					+ colName + ".getValueOffset())";
		else if (schema.equals(Cell.SCHEMA.INT))
			return "org.simplesql.util.Bytes.readInt(arr" + colName + " , col"
					+ colName + ".getValueOffset())";
		else if (schema.equals(Cell.SCHEMA.FLOAT))
			return "Float.intBitsToFloat(org.simplesql.util.Bytes.readInt(arr"
					+ colName + " , col" + colName + ".getValueOffset()))";
		else if (schema.equals(Cell.SCHEMA.BOOLEAN))
			return "org.simplesql.util.Bytes.readBoolean(arr" + colName
					+ " , col" + colName + ".getValueOffset())";
		else if (schema.equals(Cell.SCHEMA.SHORT))
			return "(short)((arr" + colName + "[col" + colName
					+ ".getValueOffset()] << 8) & 0xff) | (arr" + colName
					+ "[col" + colName + ".getValueOffset() + 1]  & 0xff))";
		else if (schema.equals(Cell.SCHEMA.STRING)) {
			int strlen = cell.getDefinedWidth();
			return "org.simplesql.util.Bytes.readString(arrName" + colName
					+ ", col" + colName + ".getValueOffset(), Math.min("
					+ strlen + ", col" + colName + ".getValueLength()))";
		} else {
			throw new RuntimeException("Cell type: " + schema
					+ " not supported ");
		}

	}

	private static final String getKeyRead(String arrName, Cell cell, int from) {
		final Cell.SCHEMA schema = cell.getSchema();

		if (schema.equals(Cell.SCHEMA.BOOLEAN))
			return "org.simplesql.util.Bytes.readBoolean(" + arrName + " , "
					+ from + ")";
		else if (schema.equals(Cell.SCHEMA.BYTE))
			return "key[from]";
		else if (schema.equals(Cell.SCHEMA.DOUBLE))
			return "org.simplesql.util.Bytes.readDouble(" + arrName + ", "
					+ from + ")";
		else if (schema.equals(Cell.SCHEMA.LONG))
			return "org.simplesql.util.Bytes.readLong(" + arrName + ", " + from
					+ ")";
		else if (schema.equals(Cell.SCHEMA.INT))
			return "org.simplesql.util.BytesInt(" + arrName + ", " + from + ")";
		else if (schema.equals(Cell.SCHEMA.BOOLEAN))
			return "org.simplesql.util.Bytes.readBoolean(" + arrName + ", "
					+ from + ")";
		else if (schema.equals(Cell.SCHEMA.FLOAT))
			return "Float.intBitsToFloat(org.simplesql.util.Bytes.readInt("
					+ arrName + ", " + from + "))";
		else if (schema.equals(Cell.SCHEMA.SHORT))
			return "(short)((" + arrName + "[" + from + "] << 8) & 0xff) | ("
					+ arrName + "[" + from + 1 + "]  & 0xff))";
		else if (schema.equals(Cell.SCHEMA.STRING)) {
			final int strlen = cell.getDefinedWidth();
			return "org.simplesql.util.Bytes.readString(" + arrName + ", "
					+ from + ", " + strlen + ")";
		} else {
			throw new RuntimeException("Cell type: " + schema
					+ " not supported ");
		}

	}

	public static byte readByte(byte[] arr, int from) {
		return arr[from];
	}

}
