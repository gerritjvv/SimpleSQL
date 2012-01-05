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


}
