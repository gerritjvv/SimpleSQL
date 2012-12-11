package org.simplesql.om.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink.DataSinkFactory;
import org.simplesql.data.DataSource;
import org.simplesql.data.DefaultDataSink;
import org.simplesql.data.Key;
import org.simplesql.data.LongCell;
import org.simplesql.data.MultiThreadedDataSource;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.om.aggregate.ChunkedProcessor;

public class TestChunkedProcessor {

	@Test
	public void testMultiThreadedRun() throws Throwable {

		Configuration conf = new PropertiesConfiguration();

		ChunkedProcessor cn = new ChunkedProcessor(conf,
				"CREATE TABLE my (name STRING,  hit INT )",
				"SELECT name, COUNT(hit) FROM my GROUP BY name");

		int chunkSize = 10;
		MySink sink = new MySink();
		long start = System.currentTimeMillis();
		cn.runAsync(new MyMultiThreadedDataSource(), sink, chunkSize);

		Thread.sleep(1000);

		long iterations = cn.stopWait();

		long end = System.currentTimeMillis() - start;
		System.out.println("Made: " + iterations + " in " + end + "ms");

		Map<Key, Cell<?>[]> map = sink.map;

		for (Entry<Key, Cell<?>[]> entry : map.entrySet()) {
			System.out.println("key: " + entry.getKey().asString() + " : "
					+ entry.getValue()[0] + ", " + entry.getValue()[1]);
		}

		List<Long> vals = sink.vals;
		long total = 0L;
		for (Long v : vals)
			total += v.longValue();

		long atotal = sink.map.get(new SimpleCellKey("a"))[1].getLongValue();

		// test that the total counted manually
		// and the total in the map is the same
		assertEquals(total, atotal);

		// test that the total is what was expected logically
		assertEquals(iterations, total);

	}

	@Test
	public void testRun() throws Throwable {

		Configuration conf = new PropertiesConfiguration();

		ChunkedProcessor cn = new ChunkedProcessor(conf,
				"CREATE TABLE my (name STRING,  hit INT )",
				"SELECT name, COUNT(hit) FROM my GROUP BY name");

		int chunkSize = 1000;
		MySink sink = new MySink();
		long start = System.currentTimeMillis();
		cn.runAsync(new MyDataSource(), sink, chunkSize);

		Thread.sleep(1000);

		long iterations = cn.stopWait();

		long end = System.currentTimeMillis() - start;
		System.out.println("Made: " + iterations + " in " + end + "ms");

		Map<Key, Cell<?>[]> map = sink.map;

		for (Entry<Key, Cell<?>[]> entry : map.entrySet()) {
			System.out.println("key: " + entry.getKey().asString() + " : "
					+ entry.getValue()[0] + ", " + entry.getValue()[1]);
		}

		List<Long> vals = sink.vals;
		long total = 0L;
		for (Long v : vals)
			total += v.longValue();

		long atotal = sink.map.get(new SimpleCellKey("a"))[1].getLongValue();

		// test that the total counted manually
		// and the total in the map is the same
		assertEquals(total, atotal);

		// test that the total is what was expected logically
		assertEquals(iterations, total);

	}

	static class MySink extends DefaultDataSink implements
			DataSinkFactory<MySink> {

		Map<Key, Cell<?>[]> map = new ConcurrentHashMap<Key, Cell<?>[]>();
		List<Long> vals = new ArrayList<Long>(1000);

		@Override
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

		@Override
		public MySink create() {
			return this;
		}

	}

	static class MyMultiThreadedDataSource implements MultiThreadedDataSource {

		List<Object[]> list1 = createArray(10000);
		List<Object[]> list2 = createArray(10000);

		@Override
		public Iterator<Object[]> iterator() {
			return list1.iterator();
		}

		private List<Object[]> createArray(int len) {
			List<Object[]> l = new ArrayList<Object[]>(len);
			for (int i = 0; i < len; i++) {
				l.add(new Object[] { "a", 1 });
				// l.add(new Object[] { "b", 1 });
			}

			return l;
		}

		@Override
		public long getEstimatedSize() {
			return 0;
		}

		@Override
		public List<Iterator<Object[]>> iterators() {
			return Arrays.asList(list1.iterator(), list2.iterator());
		}

	}

	static class MyDataSource implements DataSource {

		List<Object[]> list = createArray(10000);

		@Override
		public Iterator<Object[]> iterator() {
			return list.iterator();
		}

		private List<Object[]> createArray(int len) {
			List<Object[]> l = new ArrayList<Object[]>(len);
			for (int i = 0; i < len; i++) {
				l.add(new Object[] { "a", 1 });
				// l.add(new Object[] { "b", 1 });
			}

			return l;
		}

		@Override
		public long getEstimatedSize() {
			return 0;
		}

	}
}
