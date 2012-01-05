package org.simplesql.data;


public interface Key {

	String asString();

	Cell[] getCells();

	int hashCode();

	boolean equals(Key key);

}