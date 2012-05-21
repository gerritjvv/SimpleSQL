package org.simplesql.test.data.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;
import org.simplesql.data.BooleanCell;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.StringCell;
import org.simplesql.data.util.SelectTransform;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.SimpleColumnDef;

public class TestSelectTransform {

	@Test
	public void testTransform() throws Throwable {

		ColumnDef[] defs = new ColumnDef[4];

		defs[0] = new SimpleColumnDef(String.class, "a", new StringCell());
		defs[1] = new SimpleColumnDef(Boolean.class, "b", new StringCell());
		defs[2] = new SimpleColumnDef(Integer.class, "c", new StringCell());
		defs[3] = new SimpleColumnDef(Double.class, "d", new StringCell());

		SelectTransform transform = new SelectTransform(defs,
				new HashSet<String>(Arrays.asList("a")));

		Object[] objs = transform.transform(new String[] { "Hi","true","1","2" });
		
		assertEquals(1, objs.length);
		assertEquals(String.class, objs[0].getClass());
		assertEquals("Hi", objs[0]);
		
	}

	@Test
	public void testTransform2() throws Throwable {

		ColumnDef[] defs = new ColumnDef[4];

		defs[0] = new SimpleColumnDef(String.class, "a", new StringCell());
		defs[1] = new SimpleColumnDef(Boolean.class, "b", new BooleanCell());
		defs[2] = new SimpleColumnDef(Integer.class, "c", new IntCell());
		defs[3] = new SimpleColumnDef(Double.class, "d", new DoubleCell());

		SelectTransform transform = new SelectTransform(defs,
				new HashSet<String>(Arrays.asList("a", "c")));

		Object[] objs = transform.transform(new String[] { "Hi","true","1","2" });
		
		assertEquals(2, objs.length);
		
		assertEquals(String.class, objs[0].getClass());
		assertEquals("Hi", objs[0]);
		
		assertEquals(Integer.class, objs[1].getClass());
		assertEquals(1, objs[1]);
		
	}
	

	@Test
	public void testTransform3() throws Throwable {

		ColumnDef[] defs = new ColumnDef[4];

		defs[0] = new SimpleColumnDef(String.class, "a", new StringCell());
		defs[1] = new SimpleColumnDef(Boolean.class, "b", new BooleanCell());
		defs[2] = new SimpleColumnDef(Integer.class, "c", new IntCell());
		defs[3] = new SimpleColumnDef(Double.class, "d", new DoubleCell());

		SelectTransform transform = new SelectTransform(defs,
				new HashSet<String>(Arrays.asList("a", "b", "c", "d")));

		Object[] objs = transform.transform(new String[] { "Hi","true","1","2" });
		
		assertEquals(4, objs.length);
		
		assertEquals(String.class, objs[0].getClass());
		assertEquals("Hi", objs[0]);
		
		assertEquals(Boolean.class, objs[1].getClass());
		assertEquals(true, objs[1]);
		
		assertEquals(Integer.class, objs[2].getClass());
		assertEquals(1, objs[2]);
		
		assertEquals(Double.class, objs[3].getClass());
		assertEquals(2D, objs[3]);
		
	}

}
