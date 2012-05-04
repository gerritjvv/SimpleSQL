package org.simplesql;

import java.util.Iterator;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * 
 * Iterate over column values for a key value pair<br/>
 * Return long[]{name, value}<br/>
 * Both the column name and value must be of type long.
 */
public class ColumnCounterIterator implements Iterator<long[]> {

	final Result result;
	final KeyValue[] keyvalues;
	int index = 0;
	final int len;

	public ColumnCounterIterator(Result result) {
		this.result = result;
		keyvalues = result.raw();
		len = (keyvalues == null) ? 0 : keyvalues.length;
	}

	@Override
	public boolean hasNext() {
		return index < len;
	}

	@Override
	public long[] next() {
		if (index >= len)
			return null;

		final KeyValue kv = keyvalues[index++];

		return new long[] { Bytes.toLong(kv.getQualifier()),
				Bytes.toLong(kv.getValue()) };
	}

	@Override
	public void remove() {

	}

}
