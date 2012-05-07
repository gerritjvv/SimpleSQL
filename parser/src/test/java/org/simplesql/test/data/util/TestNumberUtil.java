package org.simplesql.test.data.util;

import org.junit.Test;
import org.simplesql.util.NumberUtil;

public class TestNumberUtil {

	@Test
	public void testConvert(){
	
		String numb = "123";
		int len = 10000;

		long start = System.currentTimeMillis();
		for(int i = 0; i < len; i++){
			new Integer(numb);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time3: " + (end-start) + "ms");
//		
//		start = System.currentTimeMillis();
//		for(int i = 0; i < len; i++){
//			Integer a = new Integer(numb);
//			a.intValue();
//		}
//		end = System.currentTimeMillis();
//		System.out.println("Time2: " + (end-start) + "ms");
//		
		
		start = System.currentTimeMillis();
		for(int i = 0; i < len; i++){
			NumberUtil.intValueOf(numb);
		}
		end = System.currentTimeMillis();
		System.out.println("Time1: " + (end-start) + "ms");		
		
		start = System.currentTimeMillis();
		for(int i = 0; i < len; i++){
			NumberUtil.intValueOf(numb);
		}
		end = System.currentTimeMillis();
		System.out.println("Time1: " + (end-start) + "ms");		
		
	}
	
}
