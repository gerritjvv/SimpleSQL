package org.simplesql.om.data;

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.Key;
import org.simplesql.data.SimpleCellKey;
import org.simplesql.data.StringCell;
import org.simplesql.funct.COUNT;
import org.simplesql.funct.GroupBy;
import org.simplesql.funct.PassThroughTransform;
import org.simplesql.funct.SUM;
import org.simplesql.om.data.stores.HashMapAggregateStore;

public class GroupByTest extends TestCase{

	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGroupBy(){
		
		HashMapAggregateStore store = new HashMapAggregateStore(
				new PassThroughTransform(0),
				new PassThroughTransform(1),
				new COUNT(2),
				new SUM(3));
		
		GroupBy groupBy = new GroupBy(store);
		
		String[][] keys = new String[][]{ new String[]{"A", "1"}, new String[]{"B", "2"}, new String[]{"C", "3"} };
		
		
		int rows = 10000;
		long max = 0L, min = Long.MAX_VALUE, avg = 0L;
		long total = 0L;
		for(String[] k : keys){
			for(int i = 0; i < rows; i++){
				long start = System.nanoTime();
				Cell[] cells = getCells(k);
				groupBy.fill(new SimpleCellKey(cells), cells);
				long end = System.nanoTime() - start;
				max = Math.max(end, max);
				min = Math.min(end, min);
				avg += end;
			}
		}
	   
	   	groupBy.write(new DataSink() {
			
			@Override
			public boolean fill(Key key, Cell[] data) {
				
				System.out.println(key + " : " + Arrays.toString(data));
				return true;
			}
		});
		
	}
	
	
	private Cell[] getCells(String... keys) {

		Cell[] cells = new Cell[keys.length + 2];

		int i = 0;
		for(String k : keys){
			cells[i++] = new StringCell(k);
		}

		cells[i++] = new IntCell(10);
		cells[i++] = new DoubleCell(0.02);

		return cells;
	}

	
}
