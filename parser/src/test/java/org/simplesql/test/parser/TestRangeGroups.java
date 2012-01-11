package org.simplesql.test.parser;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.data.IntCell;
import org.simplesql.data.RangeGroups;
import org.simplesql.data.StringCell;
import org.simplesql.data.VariableRanges;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

public class TestRangeGroups extends TestCase{

	@Test
	public void testRange1(){
		
	String str = "SELECT 1, b, a*0.5/2, \"STR\",c , COUNT(1) FROM table WHERE a>=1 AND b<5 GROUP BY a, b";
		
		TableDef tableDef = new SimpleTableDef("mytbl", 
				new SimpleColumnDef(Integer.class, "a", new IntCell()),
				new SimpleColumnDef(Integer.class, "b", new IntCell()),
				new SimpleColumnDef(String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);
		
		SQLExecutor exec = compiler.compile(tableDef, str);
		
		RangeGroups groups = exec.getRangeGroups();
		System.out.println("Groups: " + groups.getRanges().size());
		
		List<VariableRanges> rangesList = groups.getRanges();
		assertNotNull(rangesList);
		assertEquals(1, rangesList.size());
		
		for(VariableRanges ranges : rangesList){
			
			System.out.println("A: " + ranges.getRange("a").getLower() + ", " + ranges.getRange("a").getUpper());
			System.out.println("B: " + ranges.getRange("b").getLower() + ", " + ranges.getRange("b").getUpper());
				Set<String> vars = ranges.getVariables();
				assertNotNull(vars);
				assertTrue(vars.contains("a"));
				assertTrue(vars.contains("b"));
				assertEquals(1, ((Integer)ranges.getRange("a").getLower()).intValue());
				assertEquals(Integer.MAX_VALUE, ((Integer)ranges.getRange("a").getUpper()).intValue());
				
				assertEquals(5, ((Integer)ranges.getRange("b").getUpper()).intValue());
				assertEquals(Integer.MIN_VALUE, ((Integer)ranges.getRange("b").getLower()).intValue());
				
		}
		
	}
	
}
