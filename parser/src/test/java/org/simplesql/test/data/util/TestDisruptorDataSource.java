package org.simplesql.test.data.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.simplesql.data.DoubleCell;
import org.simplesql.data.IntCell;
import org.simplesql.data.StringCell;
import org.simplesql.data.util.DisruptorDataSource;
import org.simplesql.data.util.SelectTransform;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.SimpleColumnDef;

public class TestDisruptorDataSource {

	static final String FILE = "src/test/resources/disruptorDataSource.txt";

	@Test
	public void test1() throws Throwable {

		
		Set<String> set = new HashSet<String>();
		set.add("a");
		ColumnDef[] defs = new ColumnDef[] {
				new SimpleColumnDef(String.class, "a", new StringCell()),
				new SimpleColumnDef(int.class, "b", new IntCell()),
				new SimpleColumnDef(int.class, "c", new IntCell()),
				new SimpleColumnDef(int.class, "d", new IntCell()),
				new SimpleColumnDef(Double.class, "e", new DoubleCell()),
				new SimpleColumnDef(Double.class, "f", new DoubleCell()) };

		InputStream in = getIn();
		try {
			DisruptorDataSource datasource = new DisruptorDataSource(
					new SelectTransform(defs, set), " ");

			Iterator<Object[]> it = datasource.iterator(in);
			List<String> list = new ArrayList<String>();

			int i = 0;
			while (it.hasNext()) {
				i++;
				Object[] arr = it.next();
				System.out.println("arr: " + Arrays.toString(arr));
					list.add((String) arr[0]);
				
			}

			List<String> lines = FileUtils.readLines(new File(FILE), "UTF-8");
			assertEquals(lines.size(), list.size());
			for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
				assertEquals(lines.get(lineIndex), list.get(lineIndex));
			}

		} finally {
			in.close();
		}

	}

	private static final InputStream getIn() throws FileNotFoundException {
		return new FileInputStream(FILE);
	}

}
