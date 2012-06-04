package org.simplesql.table;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

/**
 * 
 * Keeps a map of singleton TableEngine instances loaded from a properties field
 * with format:<br/>
 * engine.name=class of TableEngine type
 * 
 * 
 */
public class SimpleTableEngineManager implements TableEngineManager {

	private static final Logger LOG = Logger
			.getLogger(SimpleTableEngineManager.class);

	final Map<String, TableEngine> map = new HashMap<String, TableEngine>();

	public SimpleTableEngineManager(Configuration conf, TableRepo repo)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		final Configuration subconf = conf.subset("engine");

		final Iterator<String> it = subconf.getKeys();

		while (it.hasNext()) {

			final String key = it.next();
			final TableEngine engine = (TableEngine) Thread.currentThread()
					.getContextClassLoader().loadClass(subconf.getString(key))
					.newInstance();

			map.put(key, engine);
			engine.init(conf, repo);

			LOG.info(key + " : " + engine);
		}

	}

	@Override
	public TableEngine getEngine(String name) {
		return map.get(name);
	}

	@Override
	public void shutdown() {
		for (TableEngine engine : map.values())
			engine.close();
	}

}
