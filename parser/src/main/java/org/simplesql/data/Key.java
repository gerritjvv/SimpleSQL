package org.simplesql.data;

import java.io.Serializable;

public interface Key extends Comparable<Key>, Serializable {

	String asString();

	Cell[] getCells();

	Cell cellAt(int i);

	int hashCode();

	boolean equals(Object key);

	int compareAt(int i, Key key);

}