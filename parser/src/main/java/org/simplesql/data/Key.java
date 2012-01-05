package org.simplesql.data;


public interface Key extends Comparable<Key>{

	String asString();

	Cell[] getCells();

	int hashCode();

	boolean equals(Object key);

}