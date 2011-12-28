package org.simplesql.data;

public interface Counter {

	void inc();
	void inc(int val);
	void inc(long val);
	void inc(double val);
	
	double getDoubleValue();
	long getLongValue();
	int getIntValue();
	
	void inc(Counter counter);
	
}
