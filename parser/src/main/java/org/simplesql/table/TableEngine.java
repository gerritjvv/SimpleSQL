package org.simplesql.table;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.tree.INSERT;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.SimpleTableDef;

/**
 * 
 * Describes the methods that a TableEngine should implement
 */
public interface TableEngine {

	void init(Configuration conf, TableRepo repo);
	void close();
	
	void select(SQLCompiler compiler, SELECT select, SELECT_OUTPUT output);

	void insert(INSERT insert);

	public static interface SELECT_OUTPUT {
		void write(Object[] values);
	}

}
