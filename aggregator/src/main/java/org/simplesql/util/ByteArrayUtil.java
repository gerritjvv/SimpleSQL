package org.simplesql.util;

/**
 * 
 * Provide a series of method that allow byte array parsing and growing.
 */
public class ByteArrayUtil {

	/**
	 * Combines the two arrays based on the from len values.
	 * 
	 * @param buff
	 * @param from
	 * @param len
	 * @param buff2
	 * @param from2
	 * @param len2
	 * @return byte array
	 */
	public static final byte[] combine(byte[] buff, int from, int len,
			byte[] buff2, int from2, int len2) {

		final int nlen = len + len2;
		final byte[] nbytes = new byte[nlen];

		System.arraycopy(buff, from, nbytes, 0, len);
		System.arraycopy(buff2, from2, nbytes, len, len2);

		return nbytes;
	}

	/**
	 * Adds buff2 to buff. The starting index is the from index.
	 * 
	 * @param buff
	 * @param from
	 * @param len
	 * @param buff2
	 * @param from2
	 * @param len2
	 * @param growRate
	 *            0.5 will result in a 1.5 grow rate.
	 * @return byte array
	 */
	public static final byte[] addTo(byte[] buff, int from, byte[] buff2,
			int from2, int len, float growRate) {

		final int buffLen = buff.length;
		final int addLen = from + len;
		final int f = (int) (buffLen * growRate);

		byte[] nbytes = buff;

		if (addLen >= buffLen) {
			final int lenTotal = buffLen + len;
			nbytes = new byte[(int) (lenTotal + (lenTotal * growRate))];
			System.arraycopy(buff, 0, nbytes, 0, buffLen);
			System.arraycopy(buff2, from2, nbytes, buffLen, len);

		} else if (addLen > f) {
			// grow the array by the growRate
			nbytes = new byte[(int) (buffLen + (buffLen * growRate))];
			System.arraycopy(buff, 0, nbytes, 0, from + 1);
			System.arraycopy(buff2, from2, nbytes, from, len);
		} else {
			System.arraycopy(buff2, from2, nbytes, from, len);
		}

		return nbytes;
	}

	/**
	 * Returns the index at which '\n' is found.
	 * 
	 * @param buff
	 * @param from index to start from
	 * @return -1
	 */
	public static final int findN(byte[] buff, int from) {
		final int len = buff.length;

		for (int i = from; i < len; i++) {
			if (buff[i] == '\n')
				return i;
		}

		return -1;
	}

}
