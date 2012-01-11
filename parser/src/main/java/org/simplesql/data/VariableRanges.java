package org.simplesql.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Group the variable Ranges for a Query
 *
 */
public class VariableRanges {

	Map<String, VariableRange> ranges = new HashMap<String, VariableRange>();
	
	public VariableRange getRange(String variableName){
		return ranges.get(variableName);
	}
	
	public void setVariableRange(String variableName, VariableRange range){
		VariableRange existingRange = ranges.get(variableName);
		if(existingRange == null){
			ranges.put(variableName, range);
		}else{
			existingRange.merge(range);
		}
	}
	
	public Set<String> getVariables(){
		return ranges.keySet();
	}

	public void setBiggerEq(String var, Object val) {
		VariableRange existingRange = ranges.get(var);
		if(existingRange == null){
			existingRange = new VariableRange(var, val instanceof Number, Integer.MAX_VALUE, val);
			ranges.put(var, existingRange);
		}else{
			existingRange.addBiggerEq(val);
		}
		
	}

	public void setSmallerEq(String var, Object val) {
		VariableRange existingRange = ranges.get(var);
		if(existingRange == null){
			existingRange = new VariableRange(var, val instanceof Number, val, Integer.MIN_VALUE);
			ranges.put(var, existingRange);
		}else{
			existingRange.addSmallerEq(val);
		}
		
	}
	
	public void setEq(String var, Object val) {
		VariableRange existingRange = ranges.get(var);
		if(existingRange == null){
			existingRange = new VariableRange(var, val instanceof Number, val, val);
			ranges.put(var, existingRange);
		}else{
			existingRange.addEq(val);
		}
		
	}
	
}
