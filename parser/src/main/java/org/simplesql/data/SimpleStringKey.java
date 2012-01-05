package org.simplesql.data;

/**
 * 
 * Wraps a Single String as the key object.
 * 
 */
public class SimpleStringKey implements Key {

	final String str;

	public SimpleStringKey(String key) {
		super();
		this.str = key;
	}

	@Override
	public String asString() {
		return str;
	}

	@Override
	public Cell[] getCells() {
		return new Cell[] { new StringCell(str) };
	}

	@Override
	public boolean equals(Object obj) {
		Key key = (Key)obj;
		return key.asString().equals(this.str);
	}

	
	@Override
	public int compareTo(Key key) {
		return str.compareTo(key.asString());
	}

	@Override
	public int hashCode() {
		return str.hashCode();
	}

	public String toString(){
		return "SimpleStringKey(" + str + ")";
	}
	
}
