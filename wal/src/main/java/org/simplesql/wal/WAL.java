package org.simplesql.wal;

import java.io.IOException;

public interface WAL {

	/**
	 * Reads the WAL file to the current linePos, then reads each line including<br/>
	 * the line at linePos and sends it to the EachClosure. The line number is<br/>
	 * passed as the index<br/>
	 * 
	 * @param linePos
	 * @param closure
	 * @return int the next available empty line
	 */
	int eachLineFrom(int linePos, EachClosure<String> closure) throws IOException ;

	void write(String line) throws IOException;

	void write(String[] lines) throws IOException;

	public void flush() throws IOException;

	void close() throws IOException;

}
