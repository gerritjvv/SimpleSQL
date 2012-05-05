package org.simplesql.test.data.util;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.simplesql.data.util.ByteArrayUtil;

public class TestByteArrayUtil {

	@Test
	public void testCombine() {
		byte[] bt1 = new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4 };
		byte[] bt2 = new byte[] { (byte) 5, (byte) 6, (byte) 7, (byte) 8 };

		byte[] combtest = new byte[] { (byte) 2, (byte) 3, (byte) 5, (byte) 6,
				(byte) 7 };

		byte[] comb = ByteArrayUtil.combine(bt1, 1, 2, bt2, 0, 3);

		assertTrue(Arrays.equals(combtest, comb));
	}

	@Test
	public void testAddGrow() {
		byte[] bt1 = new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4 };
		byte[] bt2 = new byte[] { (byte) 5, (byte) 6 };

		byte[] combtest = new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 4,
				(byte) 5, (byte) 6, (byte) 0, (byte) 0, (byte) 0 };

		byte[] comb = ByteArrayUtil.addTo(bt1, 3, bt2, 0, 2, 0.5F);

		assertTrue(Arrays.equals(combtest, comb));
	}

	@Test
	public void testAddNoGrow() {
		byte[] bt1 = new byte[] { (byte) 1, (byte) 0, (byte) 0, (byte) 0 };
		byte[] bt2 = new byte[] { (byte) 2 };

		byte[] combtest = new byte[] { (byte) 1, (byte) 2, (byte) 0, (byte) 0 };
		byte[] comb = ByteArrayUtil.addTo(bt1, 1, bt2, 0, 1, 0.5F);

		assertTrue(Arrays.equals(combtest, comb));
	}

	@Test
	public void testAddGrowThreshHold() {

		byte[] bt1 = new byte[] { (byte) 1, (byte) 2, (byte) 0, (byte) 0 };
		byte[] bt2 = new byte[] { (byte) 3 };

		byte[] combtest = new byte[] { (byte) 1, (byte) 2, (byte) 3, (byte) 0,
				(byte) 0, (byte) 0 };
		byte[] comb = ByteArrayUtil.addTo(bt1, 2, bt2, 0, 1, 0.5F);

		assertTrue(Arrays.equals(combtest, comb));

	}

}
