package org.simplesql.parser.tree;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class SELECTTreeAdaptor implements TreeAdaptor{

	@Override
	public Object create(Token payload) {
		return null;
	}

	@Override
	public Object dupNode(Object treeNode) {
		return null;
	}

	@Override
	public Object dupTree(Object tree) {
		return null;
	}

	@Override
	public Object nil() {
		return null;
	}

	@Override
	public Object errorNode(TokenStream input, Token start, Token stop,
			RecognitionException e) {
		return null;
	}

	@Override
	public boolean isNil(Object tree) {
		return tree == null;
	}

	@Override
	public void addChild(Object t, Object child) {
		
	}

	@Override
	public Object becomeRoot(Object newRoot, Object oldRoot) {
		return null;
	}

	@Override
	public Object rulePostProcessing(Object root) {
		return null;
	}

	@Override
	public int getUniqueID(Object node) {
		return 0;
	}

	@Override
	public Object becomeRoot(Token newRoot, Object oldRoot) {
		return null;
	}

	@Override
	public Object create(int tokenType, Token fromToken) {
		return null;
	}

	@Override
	public Object create(int tokenType, Token fromToken, String text) {
		return null;
	}

	@Override
	public Object create(int tokenType, String text) {
		return null;
	}

	@Override
	public int getType(Object t) {
		return 0;
	}

	@Override
	public void setType(Object t, int type) {
		
	}

	@Override
	public String getText(Object t) {
		return null;
	}

	@Override
	public void setText(Object t, String text) {
	}

	@Override
	public Token getToken(Object t) {
		return null;
	}

	@Override
	public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
	}

	@Override
	public int getTokenStartIndex(Object t) {
		return 0;
	}

	@Override
	public int getTokenStopIndex(Object t) {
		return 0;
	}

	@Override
	public Object getChild(Object t, int i) {
		return null;
	}

	@Override
	public void setChild(Object t, int i, Object child) {
	}

	@Override
	public Object deleteChild(Object t, int i) {
		return null;
	}

	@Override
	public int getChildCount(Object t) {
		return 0;
	}

	@Override
	public Object getParent(Object t) {
		return null;
	}

	@Override
	public void setParent(Object t, Object parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getChildIndex(Object t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setChildIndex(Object t, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceChildren(Object parent, int startChildIndex,
			int stopChildIndex, Object t) {
		// TODO Auto-generated method stub
		
	}

}
