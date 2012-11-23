package org.simplesql.data;

import java.util.Iterator;

/**
 * 
 * Takes another data source and wraps its Iterator returning only so many rows
 * as specified in this DataSource's limit parameter.
 * 
 */
public class LimitDataSource implements DataSource {

	final int limit;
	final DataSource src;

	public LimitDataSource(DataSource src, int limit) {
		super();
		this.src = src;
		this.limit = limit;
	}

	@Override
	public Iterator<Object[]> iterator() {
		return new LimitedIterator(src.iterator(), limit);
	}

	@Override
	public long getEstimatedSize() {
		return src.getEstimatedSize();
	}

	class LimitedIterator implements Iterator<Object[]> {

		final Iterator<Object[]> it;
		final int limit;
		int count = 0;

		public LimitedIterator(Iterator<Object[]> it, int limit) {
			super();
			this.it = it;
			this.limit = limit;
		}

		@Override
		public boolean hasNext() {
			return (count++ < limit) ? it.hasNext() : false;
		}

		@Override
		public Object[] next() {
			return (count < limit) ? it.next() : null;
		}

		@Override
		public void remove() {
			it.remove();
		}

	}

}
