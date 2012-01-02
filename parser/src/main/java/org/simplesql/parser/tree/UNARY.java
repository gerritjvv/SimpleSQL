package org.simplesql.parser.tree;

/**
 * 
 * An item can be a TERM or Function
 * 
 */
public class UNARY {

	
	TERM term; 
	

	public void term(TERM term) {
		this.term = term;
	}

}
