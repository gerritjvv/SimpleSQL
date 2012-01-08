package org.simplesql.test.cache;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.junit.Test;
import org.simplesql.cache.FileCache;

public class FileCacheTest extends TestCase{

	
	@Test
	public void testFileSet() throws IOException, InterruptedException{
		
			final FileCache cache = new FileCache("testcache",  20000000 * 8);
			ExecutorService service = Executors.newCachedThreadPool();
			int threads = 100;
			final CountDownLatch latch = new CountDownLatch(threads);

			System.out.println(Runtime.getRuntime().freeMemory());
			service.submit(new Runnable(){
				public void run(){
					
					
					long max = 0L, min = Long.MAX_VALUE, avg = 0;
					
					for(int i = 0; i < 10000000; i++){
						byte[] bs = intToByteArray(i);
						try {
							long start = System.nanoTime();
							cache.put(bs, bs);
							long end = System.nanoTime();
							long diff = end - start;
							max= Math.max(diff, max);
							min = Math.min(diff, min);
							avg += diff;
						} catch (IOException e) {
							e.printStackTrace();
						}finally{
							latch.countDown();
						}
						
//						System.out.println("Max: " + max + " Min: " + min + " Avg: " + (avg/1000000));
					}
							
				}
			});
			
			latch.await();
			
			cache.remove();
			
	}
	
	
	private static final byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((value >>> offset) & 0xFF);
        }
        return b;
    }
	
	
}

