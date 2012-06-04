package org.simplesql.table;

/**
 * Creates/Loads and manages TableEngine instances
 */
public interface TableEngineManager {

	
	TableEngine getEngine(String name);
	void shutdown();
	
}
