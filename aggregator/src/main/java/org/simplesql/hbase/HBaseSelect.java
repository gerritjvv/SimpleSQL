package org.simplesql.hbase;

import org.apache.hadoop.hbase.client.Result;

public interface HBaseSelect {

	
	Object[] read(Result res, byte[] key);
	
}
