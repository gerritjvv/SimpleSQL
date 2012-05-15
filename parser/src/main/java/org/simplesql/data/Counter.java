package org.simplesql.data;

public interface Counter {

	void inc();
	void inc(int val);
	void inc(long val);
	void inc(double val);
	void inc(float val);
	void inc(short val);
	void inc(byte val);
	
	byte getByteValue();
	double getDoubleValue();
	float getFloatValue();
	short getShortValue();
	long getLongValue();
	int getIntValue();
	
	void inc(Counter counter);
	
}
