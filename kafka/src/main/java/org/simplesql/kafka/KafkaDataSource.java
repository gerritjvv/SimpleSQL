package org.simplesql.kafka;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

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

	public Iterator<Object[]> iterator() {
		return new TransformedIterator<T>(
				((Iterator<MessageAndMetadata<T>>) streams.get(0).iterator()),
				transform);
	}

	public long getEstimatedSize() {
		return 0;
	}

	public List<Iterator<Object[]>> iterators() {
		List<Iterator<Object[]>> its = new ArrayList<Iterator<Object[]>>();
		for (KafkaStream<T> stream : streams) {
			its.add(new TransformedIterator<T>(
					((Iterator<MessageAndMetadata<T>>) stream.iterator()),
					transform));
		}

		return its;
	}

	static class TransformedIterator<T> implements Iterator<Object[]> {

		final Iterator<MessageAndMetadata<T>> it;
		final Transform<T> transform;

		public TransformedIterator(Iterator<MessageAndMetadata<T>> it,
				Transform<T> transform) {
			super();
			this.it = it;
			this.transform = transform;
		}

		public boolean hasNext() {
			return it.hasNext();
		}

		public Object[] next() {
			MessageAndMetadata<T> obj = it.next();
			return transform.apply(obj.message());
		}

		public void remove() {
			it.remove();
		}

	}

}
