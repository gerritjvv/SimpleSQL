package org.simplesql.data.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.simplesql.data.DataSource;

public class STDINDataSource implements DataSource {

	final String sep;

	final Iterator<String> it;

	public STDINDataSource(String sep) {
		super();
		this.sep = sep;
		it = IOUtils.lineIterator(new BufferedReader(new InputStreamReader(
				System.in)));
	}

	public Iterator<Object[]> iterator() {
		return new SplitIterator(it, sep);
	}

	public long getEstimatedSize() {
		return 0;
	}

}
