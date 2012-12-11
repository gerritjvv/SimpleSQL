package org.simplesql.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.message.Message;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringDecoder;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink.DataSinkFactory;
import org.simplesql.data.DefaultDataSink;
import org.simplesql.data.Key;
import org.simplesql.data.LongCell;
import org.simplesql.data.util.Transform;
import org.simplesql.om.aggregate.ChunkedProcessor;

import com.google.common.collect.ImmutableMap;

public class TestKafkaDataSource extends ZookeeperTestSuite{

	@Test
	public void testSource() throws Throwable {
		ProducerConfig config = new ProducerConfig(ZookeeperTestSuite.props);
		Producer<String, String> producer = new Producer<String, String>(config);

		int messages = 1000;
		for (int i = 0; i < messages; i++) {
			producer.send(new ProducerData<String, String>("test", String
					.valueOf(i)));
		}

		Configuration conf = new PropertiesConfiguration();

		ChunkedProcessor cn = new ChunkedProcessor(conf,
				"CREATE TABLE my (name STRING,  hit INT )",
				"SELECT name, COUNT(hit) FROM my GROUP BY name");

		ConsumerConfig consumerConfig = new ConsumerConfig(
				ZookeeperTestSuite.props);
		kafka.javaapi.consumer.ConsumerConnector consumerConnector = Consumer
				.createJavaConsumerConnector(consumerConfig);

		// create 4 partitions of the stream for topic “test”, to allow 4
		// threads to consume
		Map<String, List<KafkaStream<Message>>> topicMessageStreams = consumerConnector
				.createMessageStreams(ImmutableMap.of("test", 4));
		List<KafkaStream<Message>> streams = topicMessageStreams.get("test");

		final StringDecoder decoder = new StringDecoder();

		KafkaDataSource<Message> dataSource = new KafkaDataSource<Message>(
				streams, new Transform<Message>() {
					public Object[] apply(Message m) {
						String v = decoder.toEvent(m);
						return new Object[] { "a", Integer.parseInt(v) };
					}
				});

		MySink sink = new MySink();

		long start = System.currentTimeMillis();
		cn.runAsync(dataSource, sink, 100);

		Thread.sleep(1000);
		long iterations = cn.stopWait();

		long end = System.currentTimeMillis() - start;
		System.out.println("Made: " + iterations + " in " + end + "ms");

	}

	static class MySink extends DefaultDataSink implements
			DataSinkFactory<MySink> {

		Map<Key, Cell<?>[]> map = new HashMap<Key, Cell<?>[]>();
		List<Long> vals = new ArrayList<Long>(1000);

		public boolean fill(Key key, Cell<?>[] data) {
			Cell<?>[] existingData = map.get(key);

			vals.add(data[1].getLongValue());

			if (existingData == null) {
				map.put(key, data);
			} else {

				LongCell lCell = (LongCell) existingData[1];
				lCell.inc(data[1].getLongValue());

			}

			return true;
		}

		public MySink create() {
			return new MySink();
		}

	}

}
