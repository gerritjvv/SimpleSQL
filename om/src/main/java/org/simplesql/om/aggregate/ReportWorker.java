package org.simplesql.om.aggregate;

import java.util.concurrent.ExecutorService;

import org.apache.commons.configuration.Configuration;
import org.simplesql.data.DataSink;
import org.simplesql.data.DataSink.DataSinkFactory;
import org.simplesql.data.DataSource;
import org.simplesql.om.util.ExceptionUtil;

/**
 * 	
 * Helper class for reporting applications where each report is a class instance
 * 
 */
public abstract class ReportWorker {

	final Configuration conf;
	final int chunkSize;
	final ExecutorService service;
	ChunkedProcessor cn;

	public ReportWorker(ExecutorService service, Configuration conf,
			int chunkSize) {
		super();
		this.service = service;
		this.conf = conf;
		this.chunkSize = chunkSize;
	}

	abstract <T extends DataSink> DataSinkFactory<T> getDataSinkFactory();

	abstract DataSource getDataSource();

	abstract String getSchema();

	abstract String getSelect();

	public long stop() {
		return (cn == null) ? 0L : cn.stopWait();
	}

	public void runAsync() {

		try {
			cn = new ChunkedProcessor(conf, getSchema(), getSelect());
			cn.runAsync(getDataSource(), getDataSinkFactory(), chunkSize);
		} catch (Throwable e) {
			ExceptionUtil.throwRuntime(e);
		}
	}

	public void run() {
		try {

			// create a chunked processor run a block
			cn = new ChunkedProcessor(conf, getSchema(), getSelect());
			cn.run(getDataSource(), getDataSinkFactory(), chunkSize);

		} catch (Throwable e) {
			ExceptionUtil.throwRuntime(e);
		}

	}

}
