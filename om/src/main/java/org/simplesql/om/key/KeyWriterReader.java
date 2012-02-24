package org.simplesql.om.key;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.util.ProjectionKeyUtil;
import org.simplesql.util.Bytes;

/**
 * 
 * Represents the writing of a single key with multiple values.<br/>
 * The key is made-up of :<br/>
 * [tableName][val][val]....
 */
public class KeyWriterReader implements Comparable<KeyWriterReader> {

	private static final Set<String> EMPTY_SET = new TreeSet<String>();

	static final String DATE_VARIABLE = "date";
	static final String HITS_VARIABLE = "hits";

	byte[] key;
	int from, len;

	final Map<String, KeyColumnValue> map;
	final KeyColumnValue[] orderdValues;

	final KeyColumnValue clientId;
	final KeyColumnValue projectionId;
	final boolean includeIds;

	final Projection projection;

	/**
	 * Uses a key byte array read externally.<br/>
	 * Sets KeyColumnValue instances for each position in the byte key array as
	 * per the Projection columns.
	 * 
	 * @param key
	 *            byte[] must have been created with the same format as this
	 *            class would do.
	 * @param projection
	 *            Projection
	 * @param includeIds
	 *            boolean if rtue the client and projection ids are included
	 *            otherwise they are ignored
	 */
	public KeyWriterReader(byte[] key, int bufferFrom, int bufferLen,
			Projection projection, boolean includeIds) {
		this.key = key;
		this.from = bufferFrom;
		this.len = bufferLen;
		this.projection = projection;
		this.includeIds = includeIds;

		map = ProjectionKeyUtil.getKeyColumnValueMap(
				((includeIds) ? ProjectionColumnType.LONG.getWidth()
						+ ProjectionColumnType.INT.getWidth() : 0), key,
				bufferFrom, bufferLen, projection);

		if (includeIds) {
			clientId = DefaultKeys.createClientIdKeyColumnValue(key,
					bufferFrom, bufferLen);
			projectionId = DefaultKeys.createProjectionIdKeyColumnValue(key,
					bufferFrom, bufferLen);

			map.put("clientId", clientId);
			map.put("projectionId", projectionId);

			writeId(projection);
		} else {
			clientId = null;
			projectionId = null;
		}

		orderdValues = map.values().toArray(new KeyColumnValue[0]);
		Arrays.sort(orderdValues, KeyColumnValue.ORDER_COMPARATOR);

	}

	/**
	 * Create a key using the client id and projection id as the first fields.
	 * 
	 * @param projection
	 */
	public KeyWriterReader(Projection projection) {
		this(projection, true);
	}

	/**
	 * The client id and projection id are assumed to be the first fields in the
	 * key.
	 * 
	 * @param key
	 * @param projection
	 */
	public KeyWriterReader(byte[] key, int from, int len, Projection projection) {
		this(key, from, len, projection, true);
	}

	public KeyWriterReader(byte[] key, Projection projection, boolean includeIds) {
		this(key, 0, key.length, projection, includeIds);
	}

	/**
	 * Creates an empty key byte array as per the Projection.<br/>
	 * Sets KeyColumnValue instances for each position in the byte key array as
	 * per the Projection columns.
	 * 
	 * @param projection
	 */
	public KeyWriterReader(Projection projection, boolean includeIds) {

		this(ProjectionKeyUtil.createByteKey(projection), projection,
				includeIds);

	}

	/**
	 * Make's a copy of the internal array
	 */
	public KeyWriterReader clone() {
		return new KeyWriterReader(Arrays.copyOf(key, key.length), projection,
				includeIds);
	}

	/**
	 * Fill the column specified with the given byte b.
	 * 
	 * @param columnName
	 * @param b
	 */
	public void fill(String columnName, byte b) {
		map.get(columnName).fill(b);
	}

	public void setHits(byte[] bytes, int from) {
		if (bytes.length == 8) {
			map.get(HITS_VARIABLE).setByteValue(bytes, from);
		} else {
			map.get(HITS_VARIABLE).write(Bytes.readLong(bytes, from));
		}
	}

	public void addDate(byte[] bytes, int from) {
		if (bytes.length == 8) {
			map.get(DATE_VARIABLE).setByteValue(bytes, from);
		} else {
			map.get(DATE_VARIABLE).write(Bytes.readLong(bytes, from));
		}
	}

	public void setDate(long timeInMills) {
		map.get(DATE_VARIABLE).write(timeInMills);
	}

	public long getDate() {
		return map.get(DATE_VARIABLE).readLong();
	}

	public Date dateDateObject() {
		return new Date(map.get(DATE_VARIABLE).readLong());
	}

	public int readProjectionId() {
		return (includeIds) ? projectionId.readInt() : -1;
	}

	public long readClientId() {
		return (includeIds) ? clientId.readLong() : -1;
	}

	public void writeId(Projection projection) {
		// will trim the table name down to its maximum width as defined in the
		clientId.write(projection.getClientId());
		projectionId.write(projection.getId());
	}

	/**
	 * Write to the correct column position as allocated from the Projection.
	 * 
	 * @param columnName
	 * @param value
	 */
	public void writeBytes(String columnName, byte[] value, int from) {

		map.get(columnName).setByteValue(value, from);

	}

	public int readInt(String columnName) {
		KeyColumnValue val = map.get(columnName);

		return val.readInt();
	}

	public long readLong(String columnName) {
		return map.get(columnName).readLong();
	}

	public double readDouble(String columnName) {
		return map.get(columnName).readDouble();
	}

	public String readString(String columnName) {
		return map.get(columnName).readString();
	}

	public byte[] readBytes(String columnName) {
		return map.get(columnName).readByteValue();
	}

	public byte[] getKey() {
		return key;
	}

	/**
	 * Get all of the values as Java Objects, The values are in the same order
	 * as specified by the Projection Columns added to this are the values:<br/>
	 * clientId:long, projectionId:int, date:long, hits:long<br/>
	 * 
	 * @return
	 */
	public Object[] getValues() {

		final int len = orderdValues.length;
		final Object[] javaValues = new Object[len];

		int i = 0;
		for (KeyColumnValue value : orderdValues) {
			javaValues[i++] = value.getObjectValue();
		}

		return javaValues;
	}

	/**
	 * Get only the values as Java Objects for those columns that are in the
	 * columns Set, The values are in the same order as specified by the
	 * Projection Columns
	 * 
	 * @param column
	 *            Set of String.
	 * @return
	 */
	public Object[] getValues(Set<String> columns) {
		return getValues(columns, EMPTY_SET);
	}

	/**
	 * Get only the values as Java Objects for those columns that are in the
	 * columns Set, The values are in the same order as specified by the
	 * Projection Columns
	 * 
	 * @param column
	 *            Set of String.
	 * @return
	 */
	public Object[] getValues(Set<String> columns, Set<String> ignoreColumns) {

		if (columns == null) {
			return new Object[0];
		}

		final int len = columns.size();
		if (len > orderdValues.length) {
			throw new RuntimeException("Columns[ " + columns
					+ "] is larger than the columns in projection: "
					+ map.keySet());
		}

		final Object[] javaValues = new Object[len];

		int i = 0;
		for (KeyColumnValue value : orderdValues) {
			String name = value.getName();
			if (ignoreColumns.contains(name)) {
				i++;
				continue;
			}

			if (columns.contains(value.getName())) {
				javaValues[i++] = value.getObjectValue();
			}
		}

		return javaValues;
	}

	/**
	 * The key must be of the same format as expected by the Projection
	 * 
	 * @param key
	 */
	public void setKey(byte[] key, int from, int len) {
		this.key = key;
		this.from = from;
		this.len = len;

		// dont reset the last two values
		for (int i = 0; i < orderdValues.length - 2; i++) {
			orderdValues[i].setKey(key, from, len);

		}
	}

	public void setKey(KeyWriterReader kwr) {
		setKey(kwr.key, kwr.from, kwr.len);
	}

	/**
	 * Write the string value to the correct type
	 * 
	 * @param columnName
	 * @param colValue
	 */
	public void write(String columnName, Number colValue) {
		try {
			map.get(columnName).write(colValue);
		} catch (NullPointerException npe) {
			NullPointerException npe2 = new NullPointerException(columnName
					+ " not found (" + map.keySet() + ")");
			npe2.setStackTrace(npe.getStackTrace());
			throw npe2;
		}
	}

	/**
	 * Write the string value to the correct type
	 * 
	 * @param columnName
	 * @param colValue
	 */
	public void write(String columnName, Object colValue) {
		try {
			map.get(columnName).write(colValue);
		} catch (NullPointerException npe) {
			NullPointerException npe2 = new NullPointerException(columnName
					+ " not found (" + map.keySet() + ")");
			npe2.setStackTrace(npe.getStackTrace());
			throw npe2;
		}
	}

	/**
	 * Write the string value to the correct type
	 * 
	 * @param columnName
	 * @param colValue
	 */
	public void write(String columnName, String colValue) {
		map.get(columnName).write(colValue);
	}

	/**
	 * Fill the byte array with (byte)0.
	 */
	public void reset() {
		Arrays.fill(key, Bytes.NULL_BYTE);
	}

	/**
	 * 0 if equal, < 0 if this is less than o, etc.
	 */
	@Override
	public int compareTo(KeyWriterReader o) {
		return Bytes.compareTo(key, from, len, o.key, o.from, o.len);
	}

}
