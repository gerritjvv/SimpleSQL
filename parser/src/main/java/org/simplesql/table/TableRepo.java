package org.simplesql.table;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.simplesql.schema.TableDef;

/**
 * 
 * Store table definitions and retrieve them.
 *
 */
public interface TableRepo extends Iterable<String>{

	
	TableDef getTable(String name);
	void setTable(TableDef def);
	void removeTable(String name);
	
	Iterator<String> getTables();
	
	void close();
	void init(Configuration conf);
	
}
