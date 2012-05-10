package org.simplesql.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

public final class BooleanCell implements Cell<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean val = false;

	String name;

	public BooleanCell() {
	}

	public BooleanCell(boolean val) {
		super();
		this.val = val;
	}

	public BooleanCell(boolean val, String name) {
		super();
		this.val = val;
		this.name = name;
	}

	@Override
	public void inc() {
		val = !val;
	}

	@Override
	public void inc(int n) {
		inc();
	}

	@Override
	public void inc(long val) {
		inc();
	}

	@Override
	public void inc(double val) {
		inc();
	}

	@Override
	public double getDoubleValue() {
		return (val) ? 1 : 0;
	}

	public boolean getBooleanValue() {
		return this.val;
	}

	@Override
	public long getLongValue() {
		return (val) ? 1 : 0;
	}

	@Override
	public int getIntValue() {
		return (val) ? 1 : 0;
	}

	@Override
	public Boolean getData() {
		return val;
	}

	@Override
	public void inc(Counter counter) {
		inc();
	}

	@Override
	public Cell<Boolean> copy(boolean resetToDefaults) {
		return new BooleanCell((resetToDefaults) ? false : true);
	}

	public String toString() {
		return String.valueOf(val);
	}

	@Override
	public Object getMax() {
		return true;
	}

	@Override
	public Object getMin() {
		return false;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeBoolean(val);
	}

	private void readObject(ObjectInputStream in) throws IOException {
		val = in.readBoolean();
	}

	@Override
	public int compareTo(Cell<Boolean> cell) {
		return (val == cell.getData()) ? 0 : -1;
	}

	@Override
	public void setData(Boolean dat) {
		if (dat == null)
			val = false;
		else
			val = dat;
	}

	@Override
	public final void putHash(HashCodeBuilder builder) {
		builder.append(val);
	}

	@Override
	public int byteLength() {
		return 1;
	}

	@Override
	public int write(byte[] arr, int from) {
		Bytes.writeBytes(val, arr, from);
		return 1;
	}

	@Override
	public int read(byte[] arr, int from) {
		val = Bytes.readBoolean(arr, from);
		return 1;
	}

	@Override
	public org.simplesql.data.Cell.SCHEMA getSchema() {
		return SCHEMA.BOOLEAN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (val ? 1231 : 1237);
		return result;
	}
	

	public void readFields(DataInput in) throws IOException {
	
		val = (in.readByte() == 1);
	}

	public void write(DataOutput out) throws IOException {
		out.write((val) ? 1 : 0);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BooleanCell other = (BooleanCell) obj;
		if (val != other.val)
			return false;
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
