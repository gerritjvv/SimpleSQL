package org.simplesql.data.util;

/**
 * 
 * A basic interface to abstract transforming a message of some type to the
 * final Object[] expected by the simple sql interfaces.
 * 
 */
public interface Transform<T> {

	Object[] apply(T v);

}
