package org.simplesql.wal.util;

import org.apache.commons.codec.binary.Base64;

import com.google.protobuf.Message;

/**
 * 
 * Common encoding deconding methods.<br/>
 * Base64:<br/>
 * Uses commons Base64 class.
 * 
 */
public class MessageUtil {

	public static final String toBase64(Message line) {
		return new String(Base64.encodeBase64(line.toByteArray()));
	}

	public static final byte[] fromBase64(String line) {
		return Base64.decodeBase64(line);
	}
	
	
}
