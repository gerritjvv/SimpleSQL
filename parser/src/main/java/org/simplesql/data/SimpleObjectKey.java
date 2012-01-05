package org.simplesql.data;

public class SimpleObjectKey implements Key {

	final String str;

	public SimpleObjectKey(Object... data) {

		StringBuilder buff = new StringBuilder();
		for (Object obj : data) {
			buff.append(obj.toString());
		}

		str = buff.toString();

		System.out.println("Create key: " + str);

	}

	@Override
	public int compareTo(Key o) {
		return str.compareTo(o.asString());
	}

	@Override
	public String asString() {
		return str;
	}

	@Override
	public int hashCode() {
		return str.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return str.equals(((Key) obj).asString());
	}

	@Override
	public Cell[] getCells() {
		return null;
	}

}
