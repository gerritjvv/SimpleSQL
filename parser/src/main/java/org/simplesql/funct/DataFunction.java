package org.simplesql.funct;

import java.util.List;

public interface DataFunction {

	@SuppressWarnings("rawtypes")
	List apply(List list, Object... args);
	
	void setOrder(OrderDirection orderDir);
	
}
