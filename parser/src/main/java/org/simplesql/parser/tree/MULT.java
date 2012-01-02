package org.simplesql.parser.tree;

import java.util.ArrayList;
import java.util.List;

public class MULT {

	List<Object> children = new ArrayList<Object>();
	public void unary(UNARY unary){
		children.add(unary);
	}
	public void mult(){
		children.add("*");
	}
	public void divide(){
		children.add("/");
	}
	public void mod(){
		children.add("mod");
	}
	
}
