package org.simplesql.data.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.simplesql.data.DataSource;

/**
 * 
 * Reads each line from the standard input, splits by the separator and passes
 * the split string through the SelectTransform. This processes is repeated via
 * the SplitIterator for each line in from the standard input.
 * 
 */
public class STDINDataSource implements DataSource {

	final String sep;

	final Iterator<String> it;

	final SelectTransform transform;
	
	public STDINDataSource(SelectTransform transform, String sep) {
		super();
		this.transform = transform;
		this.sep = sep;
		it = IOUtils.lineIterator(new BufferedReader(new InputStreamReader(
				System.in)));
		
//		tok = new StreamTokenizer(new BufferedReader(new InputStreamReader(
//				System.in)));
//		
		
	}

	public Iterator<Object[]> iterator() {
		return new SplitIterator(transform, it, sep);
	}

	public long getEstimatedSize() {
		return 0;
	}

}
