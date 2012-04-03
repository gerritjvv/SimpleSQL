package org.simplesql.data.util;

import java.util.Iterator;
import java.util.regex.Pattern;

public class SplitIterator implements Iterator<Object[]> {

	final Iterator<String> it;
	final Pattern SPLIT;

	public SplitIterator(Iterator<String> it, String sep) {
		super();
		this.it = it;
		SPLIT = Pattern.compile(sep);
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public Object[] next() {
		final String line = it.next();
		final String[] split = SPLIT.split(line);
		return split;
	}

	public void remove() {

	}

}
