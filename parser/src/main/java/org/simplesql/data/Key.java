package org.simplesql.data;

import java.io.Serializable;


public interface Key extends Comparable<Key>, Serializable{

	String asString();

	Cell[] getCells();

	int hashCode();

	boolean equals(Object key);

}