package org.simplesql.data;

/**
 * 
 * Data Sink's can receive data.
 * 
 */
public interface DataSink {

	/**
	 * 
	 * @param data
	 * @return true if more data is allowed, false to stop
	 */
	boolean fill(Cell[] data);

}
