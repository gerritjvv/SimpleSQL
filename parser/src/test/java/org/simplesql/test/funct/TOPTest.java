package org.simplesql.test.funct;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.simplesql.funct.OrderDirection;
import org.simplesql.funct.TOP;

public class TOPTest {

	
	@Test
	public void testDesc(){
		
		TOP top = new TOP();
		top.setOrder(OrderDirection.ASC);
		List data = top.apply(getData(100), 10);
		
		System.out.println(Arrays.toString(data.toArray()));
		
	}
	
	
	private static final List<String> getData(int len){
		
		Random random = new Random();
		ArrayList<String> list = new ArrayList<String>(len);
		for(int i = 0; i < len; i++){
			list.add("" +random.nextInt(len));
		}
		return list;
	}
	
}
