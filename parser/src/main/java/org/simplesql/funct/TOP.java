package org.simplesql.funct;

import java.util.List;

public class TOP implements DataFunction {

	OrderDirection order = OrderDirection.ASC;

	@SuppressWarnings("rawtypes")
	@Override
	public List apply(List list, Object... args) {

		if (args.length != 1) {
			throw new RuntimeException("Invalid Arguments");
		}

		int n = (Integer) args[0];

		if(order.equals(OrderDirection.ASC)){
			return com.google.common.collect.Ordering.natural().greatestOf(list, n);
		}else{
			return com.google.common.collect.Ordering.natural().reverse().leastOf(list, n);
		}

	}

	public OrderDirection getOrder() {
		return order;
	}

	public void setOrder(OrderDirection order) {
		this.order = order;
	}

}
