package org.simplesql.om.key;

import java.util.Comparator;

import org.simplesql.om.ClientInfoTemplate.Column;

/**
 * 
 * Compare the projection columns by order
 *
 */
public class ProjectionColumnsComparator implements Comparator<Column>{

	public static final ProjectionColumnsComparator DEFAULT = new ProjectionColumnsComparator();
	
	@Override
	public int compare(Column c1, Column c2) {
		final int o1 = c1.getOrder();
		final int o2 = c2.getOrder();
		
		if(o1 < o2)
			return -1;
		else if(o2 > o1)
			return 1;
		else 
			return 0;
	}

}
