package org.simplesql.test.funct;

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.StringCell;
import org.simplesql.data.impl.HashMapAggregateStore;
import org.simplesql.funct.COUNT;
import org.simplesql.funct.GroupBy;
import org.simplesql.funct.PassThroughTransform;
import org.simplesql.funct.SUM;

public class GroupByTest extends TestCase{

	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGroupBy(){
		
		HashMapAggregateStore store = new HashMapAggregateStore(
				new PassThroughTransform(0),
				new PassThroughTransform(1),
				new COUNT(2),
				new SUM(3));
		
		GroupBy groupBy = new GroupBy(new GroupBy.KeyParser() {
			
			@Override
			public String makeKey(Cell[] cells) {
				return new  StringBuilder().append(cells[0]).append(cells[1]).toString();
			}
		}, store);
		
		String[][] keys = new String[][]{ new String[]{"A", "1"}, new String[]{"B", "2"}, new String[]{"C", "3"} };
		
		
		int rows = 10000;
		long max = 0L, min = Long.MAX_VALUE, avg = 0L;
		long total = 0L;
		for(String[] k : keys){
			for(int i = 0; i < rows; i++){
				long start = System.nanoTime();
				groupBy.fill(getCells(k));
				long end = System.nanoTime() - start;
				max = Math.max(end, max);
				min = Math.min(end, min);
				avg += end;
			}
		}
	   
	   	groupBy.write(new DataSink() {
			
			@Override
			public boolean fill(Cell[] data) {
				
				System.out.println(Arrays.toString(data));
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
