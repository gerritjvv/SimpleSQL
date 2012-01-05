package org.simplesql.parser;

import org.simplesql.schema.TableDef;

public interface SQLCompiler {

	/**
	 * Compiles the SQL and returns a SQLExecturo
	 * @param def TableDef
	 * @param sql String
	 * @return SQLExecutor
	 */
	SQLExecutor compile(TableDef def, String sql);
	
}
