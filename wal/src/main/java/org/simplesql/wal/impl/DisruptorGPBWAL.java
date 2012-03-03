package org.simplesql.wal.impl;

import java.io.IOException;
import java.lang.ref.WeakReference;

import org.apache.log4j.Logger;
import org.simplesql.wal.Event;
import org.simplesql.wal.GPBWAL;

import com.google.protobuf.Message;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.LifecycleAware;

/**
 * 
 * Listens to a RingBuffer and writes the Request events to a GPBWAL.<br/>
 * The most important configuration is the ringSize. The parameter passed in<br/>
 * here must be exactly equal to that of the ring size.<br/>
 * An internal fixed Array is used to batch up the Requests, and its maximum<br/>
 * size is equal to that of the ringSize property.
 * <p/>
 * 
 * Exception Handler:<br/>
 * This class re-throws the exception as a RuntimeException.<p/>
 * 
 * Disk Flush:<br/>
 * On each batch the data is written to the local disk, flushed and synced.
 * 
 */
public class DisruptorGPBWAL<T extends Message> implements EventHandler<Event<T>>,
		LifecycleAware, ExceptionHandler {

	private static final Logger LOG = Logger.getLogger(DisruptorGPBWAL.class);

	final GPBWAL wal;
	final int ringSize;

	final WeakReference<T>[] requests;

	int index = 0;

	@SuppressWarnings("unchecked")
	public DisruptorGPBWAL(GPBWAL wal, int ringSize) {
		super();
		this.wal = wal;
		this.ringSize = ringSize;
		requests = new WeakReference[ringSize];
	}

	int total = 0;
	@Override
	public void onEvent(Event<T> event, long sequence, boolean endOfBatch)
			throws Exception {

		requests[index++] = new WeakReference<T>(event.getData());
		total++;
		if (endOfBatch) {
			wal.write(requests, 0, index);
			wal.flush();
			index = 0;

		}

	}

	@Override
	public void onStart() {

	}

	@Override
	public void onShutdown() {
		try {
			System.out.println("Shutdown: written : " + total);
			wal.close();
		} catch (IOException e) {
			LOG.error(e.toString(), e);
		}
	}

	@Override
	public void handle(Exception ex, long sequence, Object event) {
		LOG.error(ex.toString(), ex);
		throw new RuntimeException(ex);
	}

}
