package org.simplesql.data.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

public class SplitIterator implements Iterator<Object[]> {

	final Iterator<String> it;
	final Pattern SPLIT;
	final SelectTransform transform;

	public SplitIterator(SelectTransform transform, Iterator<String> it,
			String sep) {
		super();
		this.transform = transform;
		this.it = it;
		SPLIT = Pattern.compile(sep);
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public Object[] next() {
		final String line = it.next();

		try {
			return (line == null) ? null : transform.transform(SPLIT
					.split(line));
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.toString(), e);
		}
	}

	public void remove() {

	}

}
