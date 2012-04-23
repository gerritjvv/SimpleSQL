package org.simplesql.data.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.simplesql.data.DataSource;

/**
 * 
 * Reads each line from the standard input, splits by the separator and passes
 * the split string through the SelectTransform. This processes is repeated via
 * the SplitIterator for each line in from the standard input.
 * 
 */
public class STDINDataSource implements DataSource {

	final String sep;

	final Iterator<String> it;

	final SelectTransform transform;
	final StreamTokenizer tok;

	public STDINDataSource(SelectTransform transform, String sep) {
		super();

		this.transform = transform;
		this.sep = sep;
		it = IOUtils.lineIterator(new BufferedReader(new InputStreamReader(
				System.in)));

		tok = new StreamTokenizer(new BufferedReader(new InputStreamReader(
				System.in)));

		tok.whitespaceChars(sep.charAt(0), sep.charAt(0));

	}

	public Iterator<Object[]> iterator() {

		return new Iterator<Object[]>() {

			Object[] arr;

			@Override
			public boolean hasNext() {

				int token;
				ArrayList<Object> list = new ArrayList<Object>(10);

				try {
					while ((token = tok.nextToken()) != StreamTokenizer.TT_EOF
							&& token == StreamTokenizer.TT_EOL) {
						list.add(
								(tok.ttype == StreamTokenizer.TT_NUMBER)? tok.nval : tok.sval);
					}

					if (token == StreamTokenizer.TT_EOF)
						arr = null;
					else
						arr = list.toArray();

				} catch (IOException e) {
					throw new RuntimeException(e.toString(), e);
				}

				return (arr != null);
			}

			@Override
			public Object[] next() {
				return arr;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub

			}

		};

		// return new SplitIterator(transform, it, sep);

	}

	public long getEstimatedSize() {
		return 0;
	}

}
