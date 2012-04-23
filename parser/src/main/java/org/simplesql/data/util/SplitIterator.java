package org.simplesql.data.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

public class SplitIterator implements Iterator<Object[]> {

	final Iterator<String> it;
	final SelectTransform transform;
	final String sep;

	public SplitIterator(SelectTransform transform, Iterator<String> it,
			String sep) {
		super();
		this.sep = sep;
		this.transform = transform;
		this.it = it;
	}

	public boolean hasNext() {
		return it.hasNext();
	}

	public Object[] next() {
		final String line = it.next();

		try {
			// commons-lang split is much faster than String.split
			return (line == null) ? null : transform.transform(StringUtils
					.split(line, sep));
		} catch (InvocationTargetException e) {
			RuntimeException rte = new RuntimeException(line + ", " +
		Arrays.toString(StringUtils.split(line,
					sep)) + ", sep: " + sep + ", " + e.toString(), e);
			rte.setStackTrace(e.getStackTrace());
			throw rte;
		}
	}

	public void remove() {

	}

}
