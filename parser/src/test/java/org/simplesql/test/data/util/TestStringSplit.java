package org.simplesql.test.data.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class TestStringSplit {

	@Test
	public void testSplit() throws Throwable {

		String input = "g	0	563	454	0.8402282737561065	0.4908871459118874";

		String[] arr = StringUtils.split(input, "\t".charAt(0));

		System.out.println("Arr: " + Arrays.toString(arr));
	}

}
