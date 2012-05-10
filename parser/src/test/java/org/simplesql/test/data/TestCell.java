package org.simplesql.test.data;

import junit.framework.TestCase;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Test;
import org.simplesql.data.Cell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.LongCell;
import org.simplesql.data.StringCell;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class TestCell extends TestCase {

	@Test
	public void testHash(){
	
//		long t = System.currentTimeMillis();
//		final HashFunction hashFunct = Hashing.goodFastHash(10);
//		final Hasher hasher = hashFunct.newHasher();
//		
//		for(int i = 0; i < 1000; i++){
//			hasher.putInt(i);
//		}
//		long end = System.currentTimeMillis();
//		
//		System.out.println((end-t) + "ms");

		
		long t = System.currentTimeMillis();
		HashCodeBuilder builder = new HashCodeBuilder();
		for(int i = 0; i < 1000; i++){
			builder.append(i);
		}
		builder.toHashCode();
		long end = System.currentTimeMillis();
		
		System.out.println((end-t) + "ms");
		
		
	}
	
	@Test
	public void testInc() {

		Cell[] cells = new Cell[] { new IntCell(), new LongCell(),
				new DoubleCell() };

		for (Cell cell : cells) {

			int v = cell.getIntValue();
			cell.inc(10);

			assertEquals(v + 10, cell.getIntValue());

		}

	}

	public void testStringInc() {

		StringCell cell = new StringCell();
		cell.inc();

		assertEquals(0, cell.getIntValue());
	}

}
