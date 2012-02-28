package org.simplesql.test.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.simplesql.data.IntCell;
import org.simplesql.data.StringCell;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.parser.SimpleSQLCompiler.SimpleWhereFilter;
import org.simplesql.parser.WhereFilter;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

/**
 * 
 * Test the serializing methods for the SimpleWhereFilter
 * 
 */
public class TestWhereFilterSerialize {

	@Test
	public void testReadWriteEmpty() throws IOException {

		WhereFilter whereFilter = new SimpleWhereFilter();

		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(bytesOut);
		whereFilter.write(dataOut);
		dataOut.close();
		System.out.println(Arrays.toString(bytesOut.toByteArray()));
		whereFilter.read(new DataInputStream(new ByteArrayInputStream(bytesOut
				.toByteArray())));
	}

	@Test
	public void testReadWrite() throws IOException {

		String str = "SELECT 1, b, a*0.5/2, \"STR\",c , COUNT(1) FROM table WHERE a>=1 AND b<5 GROUP BY a, b";

		TableDef tableDef = new SimpleTableDef("mytbl", new SimpleColumnDef(
				Integer.class, "a", new IntCell()), new SimpleColumnDef(
				Integer.class, "b", new IntCell()), new SimpleColumnDef(
				String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);

		SQLExecutor exec = compiler.compile(tableDef, str);

		WhereFilter whereFilter = exec.getWhereFilter();
		
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(bytesOut);
		whereFilter.write(dataOut);
		dataOut.close();
		System.out.println(Arrays.toString(bytesOut.toByteArray()));
		whereFilter.read(new DataInputStream(new ByteArrayInputStream(bytesOut
				.toByteArray())));
		
		
	}
}
