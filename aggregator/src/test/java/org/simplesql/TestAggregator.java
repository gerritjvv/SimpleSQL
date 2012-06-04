 package org.simplesql;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class TestAggregator {

	@Test
	public void test1() throws Throwable {

		
		Aggregator
				.main(new String[] { "", "TABLE mytable (name STRING, age DOUBLE)",
						",", "src/test/resources/test1.sql",
						"src/test/resources/data.txt" });

		
	}

	
	@Test
	public void test2() throws Throwable {

		
		Aggregator
				.main(new String[] { "TABLE mytable (name STRING, age DOUBLE)",
						",", "src/test/resources/test2.sql",
						"src/test/resources/data.txt" });

		
	}
	
	@Test
	public void test3() throws Throwable {

		
		Aggregator
				.main(new String[] { "TABLE mytable (name STRING, age DOUBLE)",
						",", "src/test/resources/test3.sql",
						"src/test/resources/data.txt" });

		
	}


	@Test
	public void test4() throws Throwable {

		
		Aggregator
				.main(new String[] { "TABLE mytable (name STRING, age DOUBLE)",
						",", "src/test/resources/test4.sql",
						"src/test/resources/data.txt" });

		
	}
	
}
