package org.simplesql.wal;

import java.io.IOException;

/**
 * 
 * Increments a persistent counter.
 *
 */
public interface CheckPointService {

	
	long getCheckPoint(String name) throws IOException;
	long incCheckPoint(String name, long val) throws IOException;
	
	/**
	 * Forces the internal state to be persisted
	 * @param name
	 */
	void flush(String name);
	
	void close();
	
}
