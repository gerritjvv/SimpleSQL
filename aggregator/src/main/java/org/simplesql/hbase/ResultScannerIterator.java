package org.simplesql.hbase;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

public class ResultScannerIterator implements Iterator<Object[]> {

	final ResultScanner scanner;
	final HDataParser eval;

	Result result;

	int counter = 0;

	public ResultScannerIterator(HDataParser eval, ResultScanner scanner) {
		this.eval = eval;
		this.scanner = scanner;
	}

	@Override
	public boolean hasNext() {

		try {
			boolean ret = (result = scanner.next()) != null;
			if(!ret)
				scanner.close();
			
			return ret;
		} catch (IOException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			throw rte;
		}
	}

	@Override
	public Object[] next() {
		return eval.parse(result, result.getRow());
	}

	@Override
	public void remove() {
	}

}