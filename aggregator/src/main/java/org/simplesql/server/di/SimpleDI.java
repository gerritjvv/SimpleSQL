package org.simplesql.server.di;

import java.util.Arrays;

import javax.inject.Inject;

import org.simplesql.server.conf.SimpleConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.streams.commons.app.AppLifeCycleManager;
import org.streams.commons.app.impl.AppLifeCycleManagerImpl;

@Configuration
public class SimpleDI {

	@Inject
	SimpleConfig config;

	
	
	@Bean
	public AppLifeCycleManager getAppLifeCycleManager(){
		return new AppLifeCycleManagerImpl(
				null, /*checks*/
				Arrays.asList(), /* services */
				null /* post startup */
				);
	}
	
	
}
