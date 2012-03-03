package org.simplesql.wal.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class TestDBCheckPointService {

	File baseDir;
	
	@Test
	public void testInc() throws IOException{
	
//		EntityManager em = emf.createEntityManager();
//		DBCheckPointService service = new DBCheckPointService("test", em);
//		
//		long start = System.currentTimeMillis();
//		for(int i = 0; i < 100000; i++){
//			service.incCheckPoint("test", 1);
//			service.flush("test");	
//		}
//		
//		
//		System.out.println("Time taken: " + (System.currentTimeMillis() - start));
//		
//		CheckPoint chk = em.find(CheckPoint.class, "test_test");
//		
//		service.close();
//		emf.close();
		

//		assertNotNull(chk);
//		assertEquals(100000, chk.getCounter());
	}
	
	
	@Before
	public void setup() throws IOException{
		
		baseDir = new File("target/test/TestFileCheckPointService");
		
		if(baseDir.exists())
			FileUtils.deleteDirectory(baseDir);
		
		baseDir.mkdirs();
	}
}
