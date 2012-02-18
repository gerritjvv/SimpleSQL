package org.simplesql.data;

import java.io.Serializable;

public interface Cell<T> extends Counter, Serializable {

	T getData();
	
	void setData(T dat);
	
	Cell<T> copy(boolean resetToDefaults);

	Object getMax();

	Object getMin();
	
}
