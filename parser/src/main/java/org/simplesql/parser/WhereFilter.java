package org.simplesql.parser;

public interface WhereFilter {

	/**
	 * @param data
	 * @return boolean true if the data should be included.
	 */
	boolean include(Object[] data);
	
}
