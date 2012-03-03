package org.simplesql.wal.impl;

import java.io.IOException;
import java.lang.ref.WeakReference;

import org.simplesql.wal.EachClosure;
import org.simplesql.wal.GPBWAL;
import org.simplesql.wal.WAL;
import org.simplesql.wal.util.MessageUtil;

import com.google.protobuf.Message;

/**
 * 
 * Supports sending the Request GPB object from the OM project.<br/>
 * Delegates the writing to a WAL implementation.
 * 
 */
public class GPBWALImpl implements GPBWAL {

	final WAL wal;

	public GPBWALImpl(WAL wal) {
		this.wal = wal;
	}

	@Override
	public void write(Message line) throws IOException {
		wal.write(MessageUtil.toBase64(line));
	}

	@Override
	public void write(Message[] msgLines) throws IOException {
		write(msgLines, 0, msgLines.length);
	}

	@Override
	public void flush() throws IOException {
		wal.flush();
	}

	@Override
	public void close() throws IOException {
		wal.close();
	}

	@Override
	public void write(Message[] msgLines, int from, int max) throws IOException {
		String[] lines = new String[max];
		for (int i = from; i < max; i++) {
			lines[i] = MessageUtil.toBase64(msgLines[i]);
		}
		wal.write(lines);
	}

	@Override
	public void write(WeakReference<? extends Message>[] msgLines, int from,
			int max) throws IOException {
		String[] lines = new String[max];
		for (int i = from; i < max; i++) {
			lines[i] = MessageUtil.toBase64(msgLines[i].get());
		}
		wal.write(lines);
	}

	@Override
	public int eachLineFrom(int linePos, final Message.Builder builder,
			final EachClosure<Message> closure) throws IOException {

		return wal.eachLineFrom(linePos, new EachClosure<String>() {

			@Override
			public void call(String t, int index) {
				try {
					Message protoValue = builder.clone()
							.mergeFrom(MessageUtil.fromBase64(t)).build();
					closure.call(protoValue, index);
				} catch (Throwable e) {
					RuntimeException rte = new RuntimeException(e);
					rte.setStackTrace(e.getStackTrace());
					throw rte;
				}
			}

		});

	}

}
