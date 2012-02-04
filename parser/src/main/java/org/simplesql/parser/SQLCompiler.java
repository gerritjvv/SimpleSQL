package org.simplesql.parser;

import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.TableDef;

public interface SQLCompiler {

	/**
	 * Compiles the SQL and returns a SQLExecturo
	 * @param def TableDef
	 * @param sql String
	 * @return SQLExecutor
	 */
	SQLExecutor compile(TableDef def, String sql);
	SQLExecutor compile(TableDef tableDef, SELECT select);
	
	SQLExecutor compile(TableDefLoader loader, String sql);
	SELECT compileSelect(String sql);
	
	/**
	 * 
	 * Allows external processes to load the TableDef
	 *
	 */
	public static interface TableDefLoader{
		
		/**
		 * 
		 * @param tableName
		 * @return TableDef
		 */
		TableDef load(String tableName);
		
	}

	
}
