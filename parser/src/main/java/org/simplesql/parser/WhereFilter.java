package org.simplesql.parser;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface WhereFilter {

	/**
	 * 
	 * @return String array
	 */
	String[] getColumnNames();

	/**
	 * @param data
	 * @return boolean true if the data should be included.
	 */
	boolean include(Object[] data);

	/**
	 * Used so that application architecture can save a WhereFilter for network
	 * transport or other
	 * 
	 * @param dataOut
	 * @throws IOException
	 */
	void write(DataOutput dataOut) throws IOException;

	/**
	 * Used so that application architecture can save a WhereFilter for network
	 * transport or other
	 * 
	 * @param dataIn
	 * @throws IOException
	 */
	void read(DataInput dataIn) throws IOException;

}
