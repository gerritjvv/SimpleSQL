package org.simplesql.data;

import java.util.Iterator;

public interface DataSource extends Iterable<Object[]> {

	/**
	 * Iterator for reading the data one record at a time
	 */
	Iterator<Object[]> iterator();

	/**
	 * 
	 * @return long estimated size in bytes
	 */
	long getEstimatedSize();

	/**
	 * For each record write to the dataSink, till the DataSink return false.
	 * 
	 * @param dataSink
	 */
	void each(DataSink dataSink);

}
