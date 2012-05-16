package org.simplesql.hbase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.codehaus.janino.ExpressionEvaluator;

public class ResultScannerIterator implements Iterator<Object[]> {

	final ResultScanner scanner;
	final ExpressionEvaluator eval;

	Result result;

	int counter = 0;
	int caching;

	public ResultScannerIterator(ExpressionEvaluator eval,
			ResultScanner scanner, int caching) {
		this.eval = eval;
		this.scanner = scanner;
		this.caching = caching;
	}

	@Override
	public boolean hasNext() {

		try {
			return (result = scanner.next()) != null;
		} catch (IOException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			throw rte;
		}
	}

	@Override
	public Object[] next() {
		try {
			counter++;
			if (counter % 10000 == 0)
				System.out.println(counter);

			return (Object[]) eval.evaluate(new Object[] { result });
		} catch (InvocationTargetException e) {
			RuntimeException rte = new RuntimeException(e.toString(), e);
			throw rte;
		}
	}

	@Override
	public void remove() {
	}

}