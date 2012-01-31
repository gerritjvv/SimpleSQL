package org.simplesql.funct;

import java.util.concurrent.TimeUnit;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class SQLFunctions {

	static final Cache<String, DateTimeFormatter> cache = CacheBuilder
			.newBuilder().concurrencyLevel(4)
			.expireAfterWrite(20, TimeUnit.MINUTES).maximumSize(1000).build();

	public static final int SIZE(String arg) {
		return (arg == null) ? 0 : arg.length();
	}

	/**
	 * Formats are based on
	 * http://joda-time.sourceforge.net/apidocs/org/joda/time
	 * /format/DateTimeFormat.html#forPattern(java.lang.String). yyyy-MM-dd
	 * HH:mm:ss
	 * 
	 * @param millis
	 * @return
	 */
	public static final String DATE_FORMAT(String format, long millis) {

		DateTimeFormatter df = cache.getIfPresent(format);
		if (df == null) {
			df = DateTimeFormat.forPattern(format);
			cache.put(format, df);
		}

		return df.print(millis);
	}

	public static final long DATE_TO_MILLIS(String format, String date) {
		DateTimeFormatter df = cache.getIfPresent(format);
		if (df == null) {
			df = DateTimeFormat.forPattern(format);
			cache.put(format, df);
		}

		return df.parseMillis(date);
	}

}
