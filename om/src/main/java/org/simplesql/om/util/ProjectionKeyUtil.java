package org.simplesql.om.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.simplesql.data.BooleanCell;
import org.simplesql.data.ByteCell;
import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.FloatCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.RangeGroups;
import org.simplesql.data.ShortCell;
import org.simplesql.data.StringCell;
import org.simplesql.data.VariableRange;
import org.simplesql.data.VariableRanges;
import org.simplesql.om.ClientInfoTemplate.Column;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.key.DefaultKeys;
import org.simplesql.om.key.KeyColumnValue;
import org.simplesql.om.key.KeyWriterReader;
import org.simplesql.om.key.ProjectionColumnType;
import org.simplesql.om.key.ProjectionColumnsComparator;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

/**
 * 
 * Help with keys
 * 
 */
public class ProjectionKeyUtil {

	/**
	 * Helper method to create the KeyColumnValue of type long for the hits
	 * record<br/>
	 * 
	 * @return KeyColumnValue backed by a 8 byte array.
	 */
	public static final KeyColumnValue getHitsKeyColumnValue() {
		return DefaultKeys.createHitsKeyColumnValue();
	}

	/**
	 * Helper method to create the KeyColumnValue of type long for a DateKey<br/>
	 * Dates can be stored as long values which are 8 bytes.<br/>
	 * 
	 * @return KeyColumnValue backed by a 8 byte array.
	 */
	public static final KeyColumnValue getDateKeyColumnValue() {
		return DefaultKeys.createDateKeyColumnValue();
	}

	/**
	 * Get the Key's KeyColumnValue(s) as per the projection's columns.
	 * 
	 * @param offset
	 * @param projection
	 * @return Map of KeyColumnValues key = column name
	 */
	public static final Map<String, KeyColumnValue> getKeyColumnValueMap(
			final int offset, byte[] key, int bufferFrom, int bufferLen,
			Projection projection) {

		Column[] colArr = projection.getColumnList().toArray(new Column[0]);

		Arrays.sort(colArr, ProjectionColumnsComparator.DEFAULT);

		final int len = colArr.length;
		int pos = offset;

		final Map<String, KeyColumnValue> map = new HashMap<String, KeyColumnValue>();

		for (int i = 0; i < len; i++) {
			final Column column = colArr[i];

			ProjectionColumnType type = ProjectionColumnType.valueOf(column
					.getType().toUpperCase());
			KeyColumnValue keyColumnValue;

			if (type == ProjectionColumnType.STRING) {
				keyColumnValue = type.getColumnValue(column.getName(),
						column.getOrder(), key, pos, column.getWidth(),
						bufferFrom, bufferLen);
			} else {
				keyColumnValue = type.getColumnValue(column.getName(),
						column.getOrder(), key, pos, bufferFrom, bufferLen);
			}

			// for each column
			// base on the type we use the ProjectionColumnType to get the
			// correct
			// KeyColumnValue
			map.put(column.getName(), keyColumnValue);

			// key column value len represents the width of the type.
			// using the GPB Column Def will only return correct width if the
			// width was explicitely specified.
			pos += keyColumnValue.getLen();
		}

		// these values to not participate in the key itself
		map.put("date", getDateKeyColumnValue());
		map.put("hits", getHitsKeyColumnValue());

		return map;
	}

	/**
	 * Create's a key in bytes from the projection's column width(s) + the
	 * projection id
	 * 
	 * @param projection
	 * @return byte[]
	 */
	public static final byte[] createByteKey(Projection projection) {
		// calculate widths
		int width = totalKeyWidth(projection)
				+ ProjectionColumnType.INT.getWidth()
				+ ProjectionColumnType.LONG.getWidth();
		return new byte[width];
	}

	/**
	 * Calculate the total key width of the projection
	 * 
	 * @param projection
	 * @return int
	 */
	public static final int totalKeyWidth(Projection projection) {

		int width = 0;

		List<Column> colList = projection.getColumnList();
		final int len = colList.size();
		int w = 0;
		for (int i = 0; i < len; i++) {
			Column column = colList.get(i);
			w = column.getWidth();
			if (w == 0) {

				String type = column.getType().toUpperCase();
				if (type.equals("INT"))
					w = 4;
				else if (type.equals("LONG") || type.equals("DOUBLE")
						|| type.equals("FLOAT") || type.equals("SHORT"))
					w = 8;
				else if (type.equals("BOOLEAN"))
					w = 1;
			}

			if (w < 1 && column.getType().toUpperCase().equals("STRING")) {
				w = 255;
			}
			width += w;
		}

		return width;
	}

	/**
	 * Converts a Projection Column to a Simple SQL Parser ColumnDef
	 * 
	 * @param column
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static final ColumnDef getColumnDef(Column column) {
		Cell cell;
		Class javaCls;
		boolean isKey = column.getKey();

		String strType = column.getType().toUpperCase();

		if (strType.equals("STRING")) {
			cell = new StringCell(column.getDefaultValue());
			javaCls = String.class;
		} else if (strType.equals("INT")) {
			String defVal = column.getDefaultValue().trim();
			try {
				cell = new IntCell((defVal.length() < 1) ? 0
						: Integer.parseInt(defVal));
			} catch (NumberFormatException nbe) {
				cell = new IntCell(0);
			}

			javaCls = int.class;
		} else if (strType.equals("LONG")) {
			String defVal = column.getDefaultValue().trim();
			try {
				cell = new LongCell((defVal.length() < 1) ? 0L
						: Long.parseLong(defVal));
			} catch (NumberFormatException nbe) {
				cell = new LongCell(0L);
			}

			javaCls = long.class;
		} else if (strType.equals("DOUBLE")) {

			String defVal = column.getDefaultValue().trim();

			try {
				cell = new DoubleCell((defVal.length() < 1) ? 0D
						: Double.parseDouble(defVal));
			} catch (NumberFormatException nbe) {
				cell = new DoubleCell(0D);
			}

			javaCls = double.class;
		} else if (strType.equals("BOOLEAN")) {

			String defVal = column.getDefaultValue().trim();

			try {
				cell = new BooleanCell((defVal.length() < 1) ? false
						: Boolean.parseBoolean(defVal));
			} catch (Exception nbe) {
				cell = new BooleanCell(false);
			}

			javaCls = boolean.class;
		}  else if (strType.equals("FLOAT")) {

			String defVal = column.getDefaultValue().trim();

			try {
				cell = new FloatCell((defVal.length() < 1) ? 0F
						: Float.parseFloat(defVal));
			} catch (Exception nbe) {
				cell = new BooleanCell(false);
			}

			javaCls = boolean.class;
		} else if (strType.equals("SHORT")) {

			String defVal = column.getDefaultValue().trim();

			try {
				cell = new ShortCell((defVal.length() < 1) ? 0
						: Short.parseShort(defVal));
			} catch (Exception nbe) {
				cell = new BooleanCell(false);
			}

			javaCls = boolean.class;
		} else if (strType.equals("BYTE")) {

			String defVal = column.getDefaultValue().trim();

			try {
				cell = new ByteCell((defVal.length() < 1) ? (byte) 0
						: Byte.parseByte(defVal));
			} catch (Exception nbe) {
				cell = new BooleanCell(false);
			}

			javaCls = boolean.class;
		} else {
			throw new RuntimeException("Type: " + strType + " is not supported");
		}

		return new SimpleColumnDef(javaCls, column.getName(), cell, isKey);
	}

	/**
	 * Transforms a Projection object to a TableDef
	 * 
	 * @param projection
	 * @return
	 */
	public static final TableDef createTableDef(Projection projection) {

		final int len = projection.getColumnCount();
		final ColumnDef cols[] = new ColumnDef[len + 4];
		int i = 0;
		for (; i < len; i++) {
			cols[i] = getColumnDef(projection.getColumn(i));
		}

		// clientId, projectionId, date, hits == 4
		cols[i++] = new SimpleColumnDef(Long.class, "clientId", new LongCell(
				projection.getClientId()));
		cols[i++] = new SimpleColumnDef(Integer.class, "projectionId",
				new IntCell(projection.getId()));
		cols[i++] = new SimpleColumnDef(Long.class, "date", new LongCell(
				projection.getDate()));
		cols[i++] = new SimpleColumnDef(Long.class, "hits", new LongCell(
				projection.getHits()));

		return new SimpleTableDef(projection.getName(), cols);

	}

	public static final Cell.SCHEMA[] createSCHEMA(TableDef def) {
		final ColumnDef[] cols = def.getColumnDefs();
		final int len = cols.length;

		final Cell.SCHEMA[] schemas = new Cell.SCHEMA[len];
		for (int i = 0; i < len; i++) {
			schemas[i] = cols[i].getCell().getSchema();
		}

		return schemas;
	}

	/**
	 * Transforms a Projection object to a TableDef without including the date
	 * or hits column.
	 * 
	 * @param projection
	 * @return
	 */
	public static final TableDef createTableDefNoIds(Projection projection) {

		final int len = projection.getColumnCount();
		final ColumnDef cols[] = new ColumnDef[len];
		int i = 0;
		for (; i < len; i++) {
			cols[i] = getColumnDef(projection.getColumn(i));
		}

		return new SimpleTableDef(projection.getName(), cols);

	}

	/**
	 * Set the start key end key values based on the Ranges detected by the
	 * SQLExecutor and the min max values for each column type.
	 * 
	 * @param tableDef
	 * @param sqlExec
	 * @param startKey
	 * @param endKey
	 * @param projection
	 */
	public static final void setKeyRanges(TableDef tableDef,
			SQLExecutor sqlExec, KeyWriterReader startKey,
			KeyWriterReader endKey, Projection projection) {
		if (sqlExec.getRangeGroups() != null) {

			RangeGroups rangeGroups = sqlExec.getRangeGroups();

			final ColumnDef[] colDefs = tableDef.getColumnDefs();
			int i = 0;
			Set<String> unsetCols = new TreeSet<String>();

			for (VariableRanges ranges : rangeGroups.getRanges()) {

				KeyWriterReader subStartKey = new KeyWriterReader(projection);
				KeyWriterReader subEndKey = new KeyWriterReader(projection);

				for (ColumnDef def : colDefs) {

					final String colName = def.getName();
					Object lower, upper = null;

					if (i == 0) {
						// only add the col names once
						unsetCols.add(colName);
					}

					VariableRange range = ranges.getRange(colName);
					if (range != null) {
						// if range found remove the colName from unsetCols
						unsetCols.remove(colName);
						lower = range.getLower();
						upper = range.getUpper();

						subStartKey.write(colName, lower);
						subEndKey.write(colName, upper);
					}

				}

				// only if we find more than one VariableRanges
				// else we set the start key and end key directly
				if (i++ != 0) {
					// set start key if sub key is smaller than current
					if (subStartKey.compareTo(startKey) < 0)
						startKey.setKey(subStartKey);

					// set end key if sub key is greater than current
					if (subEndKey.compareTo(endKey) > 0)
						endKey.setKey(subEndKey);

				} else {

					startKey.setKey(subStartKey);
					endKey.setKey(subEndKey);
				}

			}

			// set maximum values for unspecified endKey;
			for (String colName : unsetCols) {
				ColumnDef colDef = tableDef.getColumnDef(colName);
				endKey.fill(colName, colDef.getByteMax());
				// startKey.fill(colName, colDef.getByteMin());
			}

		} else {

			// else leave the key empty

			// // create default keys with min max for each column
			for (ColumnDef def : tableDef.getColumnDefs()) {
				final String colName = def.getName();
				startKey.fill(colName, def.getByteMin());
				endKey.fill(colName, def.getByteMax());
			}

		}

		final long clientId = projection.getClientId();
		final int projectionId = projection.getId();
		startKey.write(DefaultKeys.NAME_CLIENT_ID, clientId);
		startKey.write(DefaultKeys.NAME_PROJECTION_ID, projectionId);
		endKey.write(DefaultKeys.NAME_CLIENT_ID, clientId);
		endKey.write(DefaultKeys.NAME_PROJECTION_ID, projectionId);

	}

}
