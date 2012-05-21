package org.simplesql.om.table.hbase;

import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.simplesql.parser.tree.INSERT;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.table.TableEngine;
import org.simplesql.table.TableRepo;

/**
 * 
 * HBase table engine.
 * 
 */
public class HBaseTableEngine implements TableEngine {

	
	final TableRepo repo;
	
	@Override
	public void init(Configuration conf, TableRepo repo) {
		this.repo = repo;
		
	}

	@Override
	public void close() {
		
	}

	@Override
	public void select(SELECT select, SELECT_OUTPUT output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTable(SimpleTableDef table) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTable(String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<String> listTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(INSERT insert) {
		// TODO Auto-generated method stub

	}

}
