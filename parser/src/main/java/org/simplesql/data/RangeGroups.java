package org.simplesql.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A range group can contain more than one VariableRanges object.<br/>
 * Ranges are split into as per the Relation Ship in the Where clause.<br/>
 * <p/>
 * 
 */
public class RangeGroups {

	List<VariableRanges> ranges = new ArrayList<VariableRanges>();
	VariableRanges range;

	public RangeGroups() {
		range = new VariableRanges();
		ranges.add(range);
	}

	public List<VariableRanges> getRanges() {
		return ranges;
	}

	public void nextGroup() {
		range = new VariableRanges();
		ranges.add(range);
	}

	public int size() {
		return ranges.size();
	}

	/**
	 * 
	 * @param varRange
	 *            null values are ignored
	 */
	public void addBiggerEq(String var, Object val) {
		if(val != null){
			range.setBiggerEq(var, val);
		}
	}
	
	/**
	 * 
	 * @param varRange
	 *            null values are ignored
	 */
	public void addSmallerEq(String var, Object val) {
		if(val != null){
			range.setSmallerEq(var, val);
		}
	}
	
	/**
	 * 
	 * @param varRange
	 *            null values are ignored
	 */
	public void addRange(VariableRange varRange) {
		if (varRange != null){
			range.setVariableRange(varRange.getVariable(), varRange);
		}
	}

}
