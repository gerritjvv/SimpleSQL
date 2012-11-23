package org.simplesql.om.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DataSource;
import org.simplesql.data.Key;
import org.simplesql.data.LongCell;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.om.aggregate.Continuous;

public class TestContinuous {

	@Test
	public void testRun() throws Throwable {

		Configuration conf = new PropertiesConfiguration();

		Continuous cn = new Continuous(conf,
				"CREATE TABLE my (name STRING,  hit INT )",
				"SELECT name, COUNT(hit) FROM my GROUP BY name");

		MySink sink = new MySink();
		long start = System.currentTimeMillis();
		cn.runAsync(new MyDataSource(), sink, 10000);

		Thread.sleep(10000);

		long iterations = cn.stopWait();

		long end = System.currentTimeMillis() - start;
		System.out.println("Made: " + iterations + " in " + end + "ms");
		
		Map<Key, Cell<?>[]> map = sink.map;
		
		for(Entry<Key, Cell<?>[]> entry : map.entrySet()){
			System.out.println("key: " + entry.getKey().asString()
					+ " : " + entry.getValue()[0] + ", " + entry.getValue()[1]);
		}
		
		List<Long> vals = sink.vals;
		long total = 0L;
		for(Long v : vals)
			total += v.longValue();
		
		long atotal = sink.map.get(new SimpleCellKey("a"))[1].getLongValue();
		
		//test that the total counted manually
		//and the total in the map is the same
		assertEquals(total, atotal);
		
		//test that the total is what was expected logically
		assertEquals(1000*iterations, total);
		
	}

	static class MySink implements DataSink {

		Map<Key, Cell<?>[]> map = new HashMap<Key, Cell<?>[]>();
		List<Long> vals = new ArrayList<Long>(1000);
		
		@Override
		public boolean fill(Key key, Cell<?>[] data) {
			Cell<?>[] existingData = map.get(key);

			vals.add(data[1].getLongValue());
			
			if(existingData == null){
				map.put(key, data);
			}else{
				
				LongCell lCell = (LongCell) existingData[1];
				lCell.inc(data[1].getLongValue());
				
			}
			
			
			return true;
		}

	}

	static class MyDataSource implements DataSource {

		List<Object[]> list = createArray(1000);

		@Override
		public Iterator<Object[]> iterator() {
			return list.iterator();
		}

		private List<Object[]> createArray(int len) {
			List<Object[]> l = new ArrayList<Object[]>(len);
			for (int i = 0; i < len; i++){
				l.add(new Object[] { "a", 1 });
//				l.add(new Object[] { "b", 1 });
			}
			
			return l;
		}

		@Override
		public long getEstimatedSize() {
			return 0;
		}

	}
}
