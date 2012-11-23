package org.simplesql.util;

import java.util.List;

import org.jboss.netty.handler.codec.http.QueryStringDecoder;

public class ParameterUtils {

	
	/**
	 * Remove any ' or " enclosing quotes
	 * @param str
	 * @return
	 */
	public static final String removeEnclosingQuotes(String str){
		if(str.startsWith("\"") || str.startsWith("'")){
			str = str.substring(1, str.length());
		}
		
		
		if(str.endsWith("\"") || str.endsWith("'")){
			str = str.substring(0, str.length()-1);
		}
	
		return str;
	}
	
	
	/**
	 * Utility method for retieving a single argument from the list values of a
	 * query string variable.
	 * 
	 * @param decoder
	 * @param name
	 * @return
	 */
	public static final String getParameter(QueryStringDecoder decoder,
			String name) {
		List<String> list = decoder.getParameters().get(name);
		return (list == null || list.size() < 1 || list.get(0) == null) ? null
				: list.get(0).trim();
	}
}
