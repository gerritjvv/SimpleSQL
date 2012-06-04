package org.simplesql.server.conf;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class SimpleConfig {

	Configuration conf;

	public SimpleConfig(Configuration conf) {
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public int getInt(String name, int def) {
		return conf.getInt(name, def);
	}

	public long getLong(String name, long def) {
		return conf.getLong(name, def);
	}

	public String get(String name, String def) {
		return conf.getString(name, def);
	}

	public int getPort() {
		return conf.getInt("server.listener.port", 8383);
	}

	public static SimpleConfig getInstance(String path)
			throws ConfigurationException {
		return new SimpleConfig(new PropertiesConfiguration(path));
	}
}
