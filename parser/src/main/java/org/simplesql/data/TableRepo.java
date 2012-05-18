package org.simplesql.data;

import java.util.Iterator;

import org.simplesql.schema.TableDef;

/**
 * 
 * Store table definitions and retrieve them.
 *
 */
public interface TableRepo {

	
	TableDef getTable(String name);
	void setTable(TableDef def);
	void removeTable(String name);
	
	Iterator<String> getTables();
	
	
}
