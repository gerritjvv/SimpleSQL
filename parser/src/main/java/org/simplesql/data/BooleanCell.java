package org.simplesql.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.simplesql.util.Bytes;

import com.google.common.hash.Hasher;

public class BooleanCell implements Cell<Boolean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean val = false;

	public BooleanCell() {
	}

	public BooleanCell(boolean val) {
		super();
		this.val = val;
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
	public Hasher putHash(Hasher hasher) {
		return hasher.putBoolean(val);
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

}
