package org.simplesql.data.util;

import java.io.BufferedReader;
import java.io.IOException;
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

	// final Iterator<String> it;

	final SelectTransform transform;
	final BufferedReader reader;

	public STDINDataSource(SelectTransform transform, String sep) {
		super();
		this.transform = transform;
		this.sep = sep;
		// it = IOUtils.lineIterator(new BufferedReader(new InputStreamReader(
		// System.in)));

		reader = new BufferedReader(new InputStreamReader(System.in));

	}

	public Iterator<Object[]> iterator() {
		return new SplitIterator(transform, new Iterator<String>() {

			String val;

			@Override
			public boolean hasNext() {
				
				StringBuilder buff = new StringBuilder(100);
				int ch = -1;
				try {
					while ((ch = reader.read()) != '\n' && ch != -1) {
						buff.append(ch);
					}

					if (buff.length() > 0)
						val = buff.toString();
					else{
						val = null;
						IOUtils.closeQuietly(reader);
					}
				} catch (IOException e) {
					return false;
				}

				return (val != null);
			}

			@Override
			public String next() {
				return val;
			}

			@Override
			public void remove() {

			}

		}, sep);
	}

	public long getEstimatedSize() {
		return 0;
	}

}
