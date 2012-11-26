package org.simplesql.data;

import java.util.Iterator;

public class ChunkedIterator<T> implements Iterator<T> {

	final Iterator<T> it;
	final int chunkSize;

	int index = 0;

	public ChunkedIterator(Iterator<T> it, int chunkSize) {
		super();
		this.it = it;
		this.chunkSize = chunkSize;
	}

	@Override
	public boolean hasNext() {
		return it.hasNext() && (index < chunkSize);
	}

	@Override
	public T next() {
		index++;
		return it.next();
	}

	@Override
	public void remove() {
		it.remove();
	}

}
