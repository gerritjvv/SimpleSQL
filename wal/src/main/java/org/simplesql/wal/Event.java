package org.simplesql.wal;

import com.google.protobuf.Message;

/**
 * 
 * Event object used in the GPBWAL
 *
 * @param <T>
 */
public interface Event<T extends Message> {

	T getData();
	
}
