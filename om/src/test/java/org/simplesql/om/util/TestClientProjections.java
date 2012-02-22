package org.simplesql.om.util;


public class TestClientProjections {

	
	/**
	 * Test that the projection key is correctly ordered
	 */
//	@Test
//	public void testProjectionKeyOrdered(){
//	
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d");
//		
//		String[] colsUsed = new String[]{"a", "b", "c", "d"};
//		
//		Projection projection = ClientProjections.getBestFitProjection(clientInfo.getProjectionList(), colsUsed);
//		assertNotNull(projection);
//		
//		String[] keys = ClientProjections.createProjectionKey(projection);
//		assertEquals("a", keys[0]);
//		assertEquals("b", keys[1]);
//		assertEquals("c", keys[2]);
//		assertEquals("d", keys[3]);
//		
//	}
//	
//	/**
//	 * Tries to find the best fit with exact
//	 */
//	@Test
//	public void testBestFit1(){
//	
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d");
//		
//		String[] colsUsed = new String[]{"a", "b", "c", "d"};
//		
//		Projection projection = ClientProjections.getBestFitProjection(clientInfo.getProjectionList(), colsUsed);
//		
//		
//		assertNotNull(projection);
//		
//		Set<String> pairs = new HashSet<String>(projection.getPairList());
//		
//		assertTrue(pairs.containsAll(Arrays.asList(colsUsed)));
//		
//	}
//	
//	/**
//	 * Tries to find the best fit with none exact
//	 */
//	@Test
//	public void testBestFit2(){
//	
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d", "a,b");
//		
//		String[] colsUsed = new String[]{"a", "b"};
//		
//		Projection projection = ClientProjections.getBestFitProjection(clientInfo.getProjectionList(), colsUsed);
//		
//		
//		assertNotNull(projection);
//		
//		Set<String> pairs = new HashSet<String>(projection.getPairList());
//		
//		assertTrue(pairs.containsAll(Arrays.asList(colsUsed)));
//		assertEquals(2, pairs.size());
//	}
//	
//	/**
//	 * Tries to find the best fit with none exact choosing smallest
//	 */
//	@Test
//	public void testBestFit3(){
//	
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d,e,f", "a", "a,b,c");
//		
//		String[] colsUsed = new String[]{"a", "b"};
//		
//		Projection projection = ClientProjections.getBestFitProjection(clientInfo.getProjectionList(), colsUsed);
//		
//		
//		assertNotNull(projection);
//		
//		Set<String> pairs = new HashSet<String>(projection.getPairList());
//		
//		assertTrue(pairs.containsAll(Arrays.asList(colsUsed)));
//		assertEquals(3, pairs.size());
//		System.out.println(pairs);
//	}
//	
//	
//	
//	@Test
//	public void testProjections(){
//		
//		Request request = buildRequest("a=1", "b=2", "c=3", "d=4");
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d");
//		
//		String[] keys = ClientProjections.createProjectionKeys(clientInfo, request, null, null);
//		
//		assertNotNull(keys);
//		assertEquals(1, keys.length);
//		assertEquals("a_b_c_d_1_2_3_4", keys[0]);
//		
//	}
//	
//
//
//	@Test
//	public void testProjectionsNoValue(){
//		
//		Request request = buildRequest("a=1", "b=2", "c=3");
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d");
//		
//		//we expect no keys to be returned here
//		String[] keys = ClientProjections.createProjectionKeys(clientInfo, request, null, null);
//		
//		assertNotNull(keys);
//		assertEquals(0, keys.length);
//		
//	}
//	
//	@Test
//	public void testProjectionsInAddedValue(){
//		
//		Request request = buildRequest("a=1", "b=2", "c=3");
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d");
//		
//		QueryParam queryParam = QueryParam.newBuilder().setKey("d").setValue("4").build();
//
//		String[] keys = ClientProjections.createProjectionKeys(clientInfo, request, new QueryParam[][]{{queryParam}}, null);
//		
//		assertNotNull(keys);
//		assertEquals(1, keys.length);
//		assertEquals("a_b_c_d_1_2_3_4", keys[0]);
//	}
//	
//
//	@Test
//	public void testProjections10(){
//		
//		Request request = buildRequest("a=1", "b=2", "c=3", "e=5", "f=6", "g=7");
//		ClientInfo clientInfo = buildClientInfo("a,b,c,d", "a", "b", "c", "d", "g", "f", "a,b", "a,b,c");
//		
//		
//		QueryParam queryParam = QueryParam.newBuilder().setKey("d").setValue("4").build();
//
//		String[] keys = ClientProjections.createProjectionKeys(clientInfo, request, new QueryParam[][]{{queryParam}}, null);
//		assertNotNull(keys);
//		Set<String> keySet = new HashSet<String>(Arrays.asList(keys));
//		System.out.println(keySet);
//		
//		assertEquals(9, keys.length);
//	
//		
//		assertTrue(keySet.contains("a_b_c_d_1_2_3_4"));
//		assertTrue(keySet.contains("a_b_c_1_2_3"));
//		assertTrue(keySet.contains("a_b_1_2"));
//		assertTrue(keySet.contains("a_1"));
//		assertTrue(keySet.contains("b_2"));
//		assertTrue(keySet.contains("c_3"));
//		assertTrue(keySet.contains("d_4"));
//		//we did not include e in the client projection so we do not expect it here.
//		assertFalse(keySet.contains("e_5"));
//		assertTrue(keySet.contains("f_6"));
//		assertTrue(keySet.contains("g_7"));
//	}
//	
//	private ClientInfo buildClientInfo(String... projections){
//		
//		ClientInfo.Builder builder = ClientInfo.newBuilder();
//		
//		for(String projection : projections){
//			String split[] = projection.split(",");
//			Projection.Builder pbuilder = Projection.newBuilder();
//			for(String p : split){
//				pbuilder.addPair(p);
//			}
//			builder.addProjection(pbuilder.build());
//		}
//		
//		return builder.build();
//	}
//	
//	
//	private Request buildRequest(String... params) {
//
//		Request.Builder builder = Request.newBuilder().setHost("localhost");
//
//		for (String param : params) {
//			String split[] = param.split("=");
//			builder.addParams(QueryParam.newBuilder().setKey(split[0])
//					.setValue(split[1]).build());
//		}
//
//		return builder.build();
//	}

}
