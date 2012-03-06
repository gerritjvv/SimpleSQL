package org.simplesql.wal.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;
import org.simplesql.wal.CheckPointService;

public class CheckPointServiceImpl implements CheckPointService {

	private static final Logger LOG = Logger
			.getLogger(CheckPointServiceImpl.class);

	final ExecutorService executorService = Executors.newCachedThreadPool();
	final ScheduledExecutorService scheduledService = Executors
			.newScheduledThreadPool(5);

	final File baseDir;

	final Map<String, PersistentCounter> counterMap = new ConcurrentHashMap<String, PersistentCounter>();

	public CheckPointServiceImpl(File baseDir) throws FileNotFoundException,
			IOException {
		super();
		if (!(baseDir.isDirectory() && baseDir.canWrite())) {
			throw new RuntimeException("The argument " + baseDir
					+ " must be a directory and must be writeable");
		}
		this.baseDir = baseDir;
	}

	private final PersistentCounter getCounter(String name)
			throws FileNotFoundException, IOException {

		PersistentCounter counter = counterMap.get(name);
		if (counter == null) {
			synchronized (counterMap) {
				counter = counterMap.get(name);
				if (counter == null) {
					counter = new PersistentCounter(executorService, new File(
							baseDir, name), false, scheduledService);
					counterMap.put(name, counter);
				}
			}
		}

		return counter;
	}

	public long getCheckPoint(String name) throws IOException {
		return getCounter(name).getValue();
	}

	public long incCheckPoint(String name, long val) throws IOException {
		return getCounter(name).inc(val);
	}

	public void flush(String name) {

	}

	public void close() {
		for (Entry<String, PersistentCounter> entry : counterMap.entrySet()) {
			try {
				entry.getValue().close();
			} catch (IOException e) {
				LOG.error(e.toString(), e);
			}
		}
	}

}
