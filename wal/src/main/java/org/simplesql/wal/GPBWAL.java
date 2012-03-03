package org.simplesql.wal;

import java.io.IOException;
import java.io.SyncFailedException;
import java.lang.ref.WeakReference;

import com.google.protobuf.Message;

public interface GPBWAL {

	/**
	 * Reads the WAL file to the current linePos, then reads each line including<br/>
	 * the line at linePos and sends it to the EachClosure. The line number is<br/>
	 * passed as the index<br/>
	 * 
	 * @param linePos
	 * @param Builder
	 *            a message builder will be cloned for each message create
	 * @param closure
	 * @return int the next available empty line
	 */
	int eachLineFrom(int linePos, Message.Builder builder,
			EachClosure<Message> closure) throws IOException;

	void write(Message line) throws IOException;

	void write(Message[] lines) throws IOException;

	void write(Message[] lines, int from, int max) throws IOException;

	void write(WeakReference<? extends Message>[] lines, int from, int max)
			throws IOException;

	void flush() throws SyncFailedException, IOException;

	void close() throws IOException;

}
