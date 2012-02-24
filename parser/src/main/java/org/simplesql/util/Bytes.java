package org.simplesql.util;

import java.io.UnsupportedEncodingException;

public class Bytes {

	public static final byte NULL_BYTE = (byte) 0;
	public static final byte BYTE_ONE = (byte) 1;

	public static double readDouble(byte[] bytes, int from) {
		return Double.longBitsToDouble(readLong(bytes, from));
	}

	public static boolean readBoolean(byte[] bytes, int from) {
		return bytes[from] == BYTE_ONE;
	}

	public static long readLong(byte[] bytes, int from) {
		return (((long) (bytes[from] & 0xff) << 56)
				| ((long) (bytes[from + 1] & 0xff) << 48)
				| ((long) (bytes[from + 2] & 0xff) << 40)
				| ((long) (bytes[from + 3] & 0xff) << 32)
				| ((long) (bytes[from + 4] & 0xff) << 24)
				| ((long) (bytes[from + 5] & 0xff) << 16)
				| ((long) (bytes[from + 6] & 0xff) << 8) | ((long) (bytes[from + 7] & 0xff)));
	}

	public static final int readInt(byte[] bytes, int from) {
		return (((bytes[from] & 0xff) << 24) | ((bytes[from + 1] & 0xff) << 16)
				| ((bytes[from + 2] & 0xff) << 8) | (bytes[from + 3] & 0xff));
	}

	/**
	 * Reads until a zero byte is found then aborts
	 * 
	 * @param bytes
	 * @param from
	 * @param len
	 * @return
	 */
	public static String readString(byte[] bytes, int from, int len) {

		int slen = 0;
		final int total = from + len;

		// lets find the -1 byte position marker
		for (int i = from; i < total; i++) {
			if (bytes[i] == NULL_BYTE)
				break;

			slen++;
		}

		try {
			return new String(bytes, from, slen, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	public static final void writeBytes(boolean bool, byte[] bytes, int from) {
		bytes[from] = (bool) ? BYTE_ONE : NULL_BYTE;
	}

	public static final void writeBytes(String str, byte[] bytes, int from,
			int len) {
		try {
			byte[] strbytes = str.getBytes("UTF-8");
			System.arraycopy(strbytes, 0, bytes, from,
					(len < strbytes.length) ? len : strbytes.length);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static final void writeBytes(int i, byte[] bytes, int from) {
		bytes[from] = (byte) (i >> 24);
		bytes[from + 1] = (byte) ((i << 8) >> 24);
		bytes[from + 2] = (byte) ((i << 16) >> 24);
		bytes[from + 3] = (byte) ((i << 24) >> 24);
	}

	public static final void writeBytes(long i, byte[] bytes, int from) {
		bytes[from] = (byte) (0xff & (i >> 56));
		bytes[from + 1] = (byte) (0xff & (i >> 48));
		bytes[from + 2] = (byte) (0xff & (i >> 40));
		bytes[from + 3] = (byte) (0xff & (i >> 32));
		bytes[from + 4] = (byte) (0xff & (i >> 24));
		bytes[from + 5] = (byte) (0xff & (i >> 16));
		bytes[from + 6] = (byte) (0xff & (i >> 8));
		bytes[from + 7] = (byte) (0xff & i);
	}

	public static final void writeBytes(double i, byte[] bytes, int from) {
		writeBytes(Double.doubleToLongBits(i), bytes, from);
	}

	public static final byte[] toBytes(String str) {
		try {
			return str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static final byte[] toBytes(int i) {

		return new byte[] { (byte) (i >> 24), (byte) ((i << 8) >> 24),
				(byte) ((i << 16) >> 24), (byte) ((i << 24) >> 24) };
	}

	public static final byte[] toBytes(boolean i) {
		return (i) ? new byte[] { (byte) 1 } : new byte[] { (byte) 0 };
	}

	public static final byte[] toBytes(double i) {
		return toBytes(Double.doubleToLongBits(i));
	}

	public static final byte[] toBytes(long i) {

		return new byte[] { (byte) (0xff & (i >> 56)),
				(byte) (0xff & (i >> 48)), (byte) (0xff & (i >> 40)),
				(byte) (0xff & (i >> 32)), (byte) (0xff & (i >> 24)),
				(byte) (0xff & (i >> 16)), (byte) (0xff & (i >> 8)),
				(byte) (0xff & i) };
	}

	/**
	 * Lexographically compare two arrays.
	 * 
	 * @param buffer1
	 *            left operand
	 * @param buffer2
	 *            right operand
	 * @param offset1
	 *            Where to start comparing in the left buffer
	 * @param offset2
	 *            Where to start comparing in the right buffer
	 * @param length1
	 *            How much to compare from the left buffer
	 * @param length2
	 *            How much to compare from the right buffer
	 * @return 0 if equal, < 0 if left is less than right, etc.
	 */
	public static int compareTo(byte[] buffer1, int offset1, int length1,
			byte[] buffer2, int offset2, int length2) {
		// Bring WritableComparator code local
		int end1 = offset1 + length1;
		int end2 = offset2 + length2;
		for (int i = offset1, j = offset2; i < end1 && j < end2; i++, j++) {
			int a = (buffer1[i] & 0xff);
			int b = (buffer2[j] & 0xff);
			if (a != b) {
				return a - b;
			}
		}
		return length1 - length2;
	}

	public static int compareTo(byte[] buffer1, byte[] buffer2) {
		return compareTo(buffer1, 0, buffer1.length, buffer2, 0, buffer2.length);
	}

}
