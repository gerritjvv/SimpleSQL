package org.simplesql.kafka;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kafka.consumer.KafkaStream;

import org.simplesql.data.MultiThreadedDataSource;
import org.simplesql.data.util.Transform;

public class KafkaDataSource<T> implements MultiThreadedDataSource {

	final List<KafkaStream<T>> streams;
	final Transform<T> transform;

	public KafkaDataSource(List<KafkaStream<T>> streams, Transform<T> transform) {
		super();
		this.streams = streams;
		this.transform = transform;
	}

	@SuppressWarnings("unchecked")
	public Iterator<Object[]> iterator() {
		return new TransformedIterator<T>(((Iterator<T>) streams.get(0)
				.iterator()), transform);
	}

	public long getEstimatedSize() {
		return 0;
	}

	@SuppressWarnings("unchecked")
	public List<Iterator<Object[]>> iterators() {
		List<Iterator<Object[]>> its = new ArrayList<Iterator<Object[]>>();
		for (KafkaStream<T> stream : streams) {
			its.add(new TransformedIterator<T>(
					((Iterator<T>) stream.iterator()), transform));
		}

		return its;
	}

	static class TransformedIterator<T> implements Iterator<Object[]> {

		final Iterator<T> it;
		final Transform<T> transform;

		public TransformedIterator(Iterator<T> it, Transform<T> transform) {
			super();
			this.it = it;
			this.transform = transform;
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public Object[] next() {
			return transform.apply(it.next());
		}

		public void remove() {
			it.remove();
		}

	}

}
