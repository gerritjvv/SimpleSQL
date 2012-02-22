package org.simplesql.om.util;

import java.util.concurrent.TimeUnit;

/**
 * 
 * Converts Date's to the byte columns value and back
 * 
 */
public class DateColumnUtil {

	/**
	 * Converts to milliseconds to a day date column value.
	 * 
	 * @param millis
	 * @return byte[] of length 4
	 */
	public static byte[] toDayDateColumn(long millis) {

		int d = Integer.MAX_VALUE - (int) TimeUnit.MILLISECONDS.toDays(millis);
		return Bytes.toBytes(d);
	}

	/**
	 * Convert the date column into a milliseconds time stamp.
	 * 
	 * @param dateColumn
	 * @return long
	 */
	public static long readDate(byte[] dateColumn) {
		int d = Bytes.readInt(dateColumn, 0);
		
		return TimeUnit.DAYS.toMillis((Integer.MAX_VALUE - d));
	}

	/**
	 * Convert the date column into a milliseconds time stamp.
	 * 
	 * @param dateColumn
	 * @return long
	 */
	public static long readDate(byte[] arr, int from) {
		int d = Bytes.readInt(arr, from);

		return TimeUnit.DAYS.toMillis((Integer.MAX_VALUE - d));
	}

	/**
	 * Converts to milliseconds to an hour day date column value.
	 * 
	 * @param millis
	 * @return byte[] of length 4
	 */
	public static byte[] toDayDateHourColumn(long millis) {

		int d = Integer.MAX_VALUE - (int) TimeUnit.MILLISECONDS.toHours(millis);

		return Bytes.toBytes(d);
	}

	/**
	 * Convert the date column for hours into a milliseconds time stamp.
	 * 
	 * @param dateColumn
	 * @return long
	 */
	public static long readDateHours(byte[] dateColumn) {
		int d = Bytes.readInt(dateColumn, 0);

		return TimeUnit.HOURS.toMillis((Integer.MAX_VALUE - d));
	}

	/**
	 * Convert the date column for hours into a milliseconds time stamp.
	 * 
	 * @param dateColumn
	 * @return long
	 */
	public static long readDateHours(byte[] arr, int from) {
		int d = Bytes.readInt(arr, from);

		return TimeUnit.HOURS.toMillis((Integer.MAX_VALUE - d));
	}

}
