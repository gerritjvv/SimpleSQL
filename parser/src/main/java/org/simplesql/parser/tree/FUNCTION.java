package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class FUNCTION extends TERM {

	enum AGGREGATE_FUNCTIONS {
		TOP, SUM, COUNT
	}

	String name;

	List<EXPRESSION> args = new ArrayList<EXPRESSION>();

	public FUNCTION() {
		super(TYPE.UKNOWN);
	}

	public void name(String name) {
		this.name = name;
		
		//if the name matches that of an aggregate function, set type to AGGREGATE
		final String nameUp = name.toUpperCase();
		if(AGGREGATE_FUNCTIONS.TOP.toString().equals(nameUp)){
			setType(TYPE.AGGREGATE_TOP);
		}else if(AGGREGATE_FUNCTIONS.SUM.toString().equals(nameUp)){
			setType(TYPE.AGGREGATE_SUM);
		}else if(AGGREGATE_FUNCTIONS.COUNT.toString().equals(nameUp)){
			setType(TYPE.AGGREGATE_COUNT);
		}
		
	}

	public boolean isAggregateFunction() {
		String nameUp = name.toUpperCase();
		return AGGREGATE_FUNCTIONS.TOP.toString().equals(nameUp)
				|| AGGREGATE_FUNCTIONS.SUM.toString().equals(nameUp)
				|| AGGREGATE_FUNCTIONS.COUNT.toString().equals(nameUp);
	}

	public void expression(EXPRESSION expr) {
		args.add(expr);
		
		//set the type based on the EXPRESSIONs passed
		setType(
				expr.type.max(getType())
				);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EXPRESSION> getArgs() {
		return args;
	}

	public void setArgs(List<EXPRESSION> args) {
		this.args = args;
	}

}
