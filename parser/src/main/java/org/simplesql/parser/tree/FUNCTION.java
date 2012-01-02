package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class FUNCTION extends TERM{

	String name;
	
	List<EXPRESSION> args = new ArrayList<EXPRESSION>();
	
	public void name(String name){
		this.name = name;
	}
	
	public void expression(EXPRESSION expr){
		args.add(expr);
	}
	
}
