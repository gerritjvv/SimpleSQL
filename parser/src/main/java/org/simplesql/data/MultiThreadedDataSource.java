package org.simplesql.data;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * Adds support for data sources that present data as multiple iterators to be
 * consumed in parallel.
 * 
 */
public interface MultiThreadedDataSource extends DataSource {

	/**
	 * Iterator for reading the data one record at a time
	 */
	List<Iterator<Object[]>> iterators();

}
