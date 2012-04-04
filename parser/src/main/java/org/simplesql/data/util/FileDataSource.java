package org.simplesql.data.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.simplesql.data.DataSource;

/**
 * 
 * Reads each line from a file, splits by the separator and passes the split
 * string through the SelectTransform. This processes is repeated via the
 * SplitIterator for each line in the file.
 * 
 */
public class FileDataSource implements DataSource {

	final String sep;
	final Iterator<String> it;
	final SelectTransform transform;

	public FileDataSource(SelectTransform transform, File file, String sep)
			throws FileNotFoundException {
		this.transform = transform;
		this.sep = sep;
		it = IOUtils.lineIterator(new BufferedReader(new InputStreamReader(
				new FileInputStream(file))));
	}

	public Iterator<Object[]> iterator() {
		return new SplitIterator(transform, it, sep);
	}

	public long getEstimatedSize() {
		return 0;
	}

}
