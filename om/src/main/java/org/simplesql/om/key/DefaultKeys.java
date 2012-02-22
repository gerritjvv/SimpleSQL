package org.simplesql.om.key;

import org.simplesql.om.key.typed.LongKeyColumnValueImpl;

/**
 * 
 * Contains the ordering of the ClientId, ProjectionId, Date and Hits Keys.<br/>
 * The defaults are: <br/>
 * In key:<br/>
 * "clientId":Long(order=-1),projectionId:Int(order=-2), other key values.<br/>
 * Not in key:<br/>
 * "date":Long(order=Integer.MAX_VALUE-1),"hits":Long(order=Integer.MAX_VALUE)
 */
public final class DefaultKeys {

	public static final String NAME_CLIENT_ID = "clientId";
	public static final String NAME_PROJECTION_ID = "projectionId";
	public static final String NAME_DATE = "date";
	public static final String NAME_HITS = "hits";

	/**
	 * Client id at position 0+bufferFrom of key, and takes up 8 bytes.
	 * 
	 * @param buffer
	 * @param bufferFrom
	 * @param bufferLen
	 * @return
	 */
	public static final KeyColumnValue createClientIdKeyColumnValue(
			byte[] buffer, int bufferFrom, int bufferLen) {
		return ProjectionColumnType.LONG.getColumnValue(NAME_CLIENT_ID, -2,
				buffer, 0, bufferFrom, bufferLen);
	}

	/**
	 * Projection id at position 8+bufferFrom of key, and takes up 4 bytes.
	 * 
	 * @param buffer
	 * @param bufferFrom
	 * @param bufferLen
	 * @return
	 */
	public static final KeyColumnValue createProjectionIdKeyColumnValue(
			byte[] buffer, int bufferFrom, int bufferLen) {
		return ProjectionColumnType.INT.getColumnValue(NAME_PROJECTION_ID, -1,
				buffer, ProjectionColumnType.LONG.getWidth(), bufferFrom,
				bufferLen);
	}

	/**
	 * Date Key, 8 bytes long.
	 * 
	 * @return
	 */
	public static final KeyColumnValue createDateKeyColumnValue() {
		return new LongKeyColumnValueImpl(NAME_DATE, Integer.MAX_VALUE - 1,
				new byte[8], 0, 8, 0, 8);
	}

	public static final KeyColumnValue createHitsKeyColumnValue() {
		return new LongKeyColumnValueImpl(NAME_HITS, Integer.MAX_VALUE,
				new byte[8], 0, 8, 0, 8);
	}

}
