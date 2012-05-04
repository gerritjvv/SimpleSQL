package org.simplesql;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestRead {

	String file = "/home/gvanvuuren/checkouts/SimpleSQL/aggregator/dat";

	@Test
	public void readOne() throws Throwable {

		BufferedReader reader = new BufferedReader(new FileReader(
				new File(file)));

		long ts = System.currentTimeMillis();
		int count = 0;
		try {
			while (reader.readLine() != null)
				count++;

		} finally {
			reader.close();
		}

		System.out.println("Time One: " + (System.currentTimeMillis() - ts));
		System.out.println("Lines One : " + count);
	}
	
	/**
	 * Use this method to place the string on a queue, but rather than a string do the char arrray.
	 * @throws Throwable
	 */
	@Test
	public void readFour() throws Throwable {

		BufferedReader reader = new BufferedReader(new FileReader(
				new File(file)));

		long ts = System.currentTimeMillis();
		int count = 0;
		final char[] ch = new char[4096];
		int len = 0;
		int lines = 0;
		try {
			while (  ( len = reader.read(ch) ) > 0  ){
				
//				add the char array to an event in the distruptor
//				final String str = new String(ch, 0, len);
//				lines += StringUtils.split(str, "\n").length;
				
				count++;
			}

		} finally {
			reader.close();
		}

		System.out.println("Time Four: " + (System.currentTimeMillis() - ts));
		System.out.println("Lines Four : " + count);
	}

	@Test
	public void readThree() throws Throwable {
		BufferedReader reader = new BufferedReader(new FileReader(
				new File(file)));

		long ts = System.currentTimeMillis();
		int count = 0;
		try {

			do {
				final char[] ch = new char[1024];
				final int len = reader.read(ch);
				if (len > 0) {
					// process here async;
					
				} else {
					break;
				}

			} while (true);

		} finally {
			reader.close();
		}

		System.out.println("Three Time: " + (System.currentTimeMillis() - ts));
		System.out.println("Three Lines: " + count);
	}

	@Test
	public void readTwo() throws Throwable {

		BufferedInputStream reader = new BufferedInputStream(
				new FileInputStream(new File(file)));
		long ts = System.currentTimeMillis();
		int lines = 0;
		try {
			int count = 0;
			final int len = 1024;
			final byte buff[] = new byte[len];
			String line;
			int readLen = 0;

			ArrayList<byte[]> backChars = new ArrayList<byte[]>(10);

			int chpointer, revChpointer;

			ArrayList<String> lineArr = new ArrayList<String>(1000);

			int backCharsLen = 0;

			while ((readLen = reader.read(buff)) > 0) {

				if ((chpointer = Arrays.binarySearch(buff, 0, readLen,
						(byte) '\n')) > -1) {

					if (backChars.size() > 0) {

						byte[] backCharsStrBuff = new byte[backCharsLen];
						int destPos = 0;
						for (int i = 0; i < backChars.size(); i++) {
							byte[] _bytes = backChars.get(i);
							System.arraycopy(_bytes, 0, backCharsStrBuff,
									destPos, _bytes.length);
							destPos += _bytes.length;
						}
						
						lineArr.add(new String(backCharsStrBuff));
						backChars.clear();
						backCharsLen = 0;
					} else {
						lineArr.add(new String(buff, 0, chpointer));
					}
					revChpointer = chpointer + 1;
					lines++;

					while (revChpointer < readLen
							&& (chpointer = Arrays.binarySearch(buff,
									revChpointer, readLen, (byte) '\n')) > -1) {

						lineArr.add(new String(buff, revChpointer,
								(chpointer - revChpointer)));
						revChpointer = chpointer + 1;
						lines++;
					}

				} else {
					backCharsLen += readLen;
					backChars.add(Arrays.copyOf(buff, readLen));
				}

				lineArr.clear();

			}

		} finally {
			reader.close();
		}

		System.out.println("Read Two Time: "
				+ (System.currentTimeMillis() - ts));
		System.out.println("Two Lines: " + lines);
	}

	static class Event {
		int size;
		char[] buff;
	}

}
