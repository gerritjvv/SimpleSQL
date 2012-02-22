package org.simplesql.om.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.simplesql.om.ClientInfoTemplate.Column;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.key.KeyWriterReader;
import org.simplesql.om.key.ProjectionColumnType;
import org.simplesql.om.util.Bytes;

public class TestKeyReaderWriter {

	/**
	 * Test that the projection key is correctly ordered
	 */
	@Test
	public void testReadWriteOrder() {

		Projection projection = buildProjection();

		KeyWriterReader kwr = new KeyWriterReader(projection);

		assertEquals(100, kwr.readProjectionId());
		kwr.writeBytes("a", Bytes.toBytes(10), 0);
		kwr.writeBytes("b", Bytes.toBytes(1L), 0);
		kwr.writeBytes("c", "1234".getBytes(), 0);
		kwr.writeBytes("d", Bytes.toBytes(0.234D), 0);

		Object[] values = kwr.getValues();

		assertEquals(10, ((Integer) values[2]).intValue());
		assertEquals(1L, ((Long) values[3]).longValue());
		assertEquals("1234", (String) values[4]);
		assertTrue(0.234D == ((Double) values[5]).doubleValue());

	}

	/**
	 * Test that the projection key is correctly ordered
	 */
	@Test
	public void testReadWrite() {

		Projection projection = buildProjection();

		KeyWriterReader kwr = new KeyWriterReader(projection);

		assertEquals(1, kwr.readClientId());
		assertEquals(100, kwr.readProjectionId());

		kwr.writeBytes("a", Bytes.toBytes(10), 0);
		kwr.writeBytes("b", Bytes.toBytes(1L), 0);
		kwr.writeBytes("c", "1234".getBytes(), 0);
		kwr.writeBytes("d", Bytes.toBytes(0.234D), 0);

		assertEquals(10, kwr.readInt("a"));
		assertEquals(1L, kwr.readLong("b"));
		assertEquals("1234", kwr.readString("c"));
		assertTrue(0.234D == kwr.readDouble("d"));

	}

	/**
	 * Test that strings are converted to correct type
	 */
	@Test
	public void testReadWriteStringToTyped() {

		Projection projection = buildProjection();

		KeyWriterReader kwr = new KeyWriterReader(projection);

		assertEquals(1, kwr.readClientId());
		assertEquals(100, kwr.readProjectionId());

		kwr.write("a", "10");
		kwr.write("b", "1");
		kwr.write("c", "1234");
		kwr.write("d", "0.234D");

		assertEquals(10, kwr.readInt("a"));
		assertEquals(1L, kwr.readLong("b"));
		assertEquals("1234", kwr.readString("c"));
		assertTrue(0.234D == kwr.readDouble("d"));

	}

	/**
	 * Build a Projection with a:int, b:long, c:string(4), d:double
	 * 
	 * @return
	 */
	private Projection buildProjection() {

		Projection.Builder pbuilder = Projection.newBuilder();

		pbuilder.setName("TestTable");
		pbuilder.setId(100);
		pbuilder.setClientId(1);

		pbuilder.addColumn(Column.newBuilder().setName("a").setOrder(0)
				.setType(ProjectionColumnType.INT.toString())
				.setWidth(ProjectionColumnType.INT.getWidth()));

		pbuilder.addColumn(Column.newBuilder().setName("b").setOrder(1)
				.setType(ProjectionColumnType.LONG.toString())
				.setWidth(ProjectionColumnType.LONG.getWidth()));

		pbuilder.addColumn(Column.newBuilder().setName("c").setOrder(2)
				.setType(ProjectionColumnType.STRING.toString()).setWidth(4));

		pbuilder.addColumn(Column.newBuilder().setName("d").setOrder(3)
				.setType(ProjectionColumnType.DOUBLE.toString())
				.setWidth(ProjectionColumnType.DOUBLE.getWidth()));

		return pbuilder.build();
	}

}
