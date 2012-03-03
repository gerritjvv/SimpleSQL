package org.simplesql.wal;

public interface EachClosure<T> {

	void call(T t, int index);
	
}
