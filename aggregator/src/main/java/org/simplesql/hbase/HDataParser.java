package org.simplesql.hbase;

import org.apache.hadoop.hbase.client.Result;

public interface HDataParser {
	Object[] parse(Result res, byte[] key);
}