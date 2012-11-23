package org.simplesql.server.di;

import java.util.Arrays;

import javax.inject.Inject;

import org.simplesql.om.data.KratiTableRepo;
import org.simplesql.server.conf.SimpleConfig;
import org.simplesql.server.controllers.QueryController;
import org.simplesql.server.controllers.TableController;
import org.simplesql.table.SimpleTableEngineManager;
import org.simplesql.table.TableEngineManager;
import org.simplesql.table.TableRepo;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.streams.commons.app.AppLifeCycleManager;
import org.streams.commons.app.ApplicationService;
import org.streams.commons.app.impl.AppLifeCycleManagerImpl;

@Configuration
public class SimpleDI {

	@Inject
	SimpleConfig config;

	@Inject
	BeanFactory factory;

	@Bean
	public QueryController queryController() {
		return new QueryController(factory.getBean(TableEngineManager.class),
				factory.getBean(TableRepo.class));
	}

	@Bean
	public TableController tableController() {
		return new TableController(factory.getBean(TableRepo.class));
	}

	@Bean
	public TableEngineManager tableEngineManager()
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return new SimpleTableEngineManager(config.getConf(),
				factory.getBean(TableRepo.class));
	}

	@Bean(destroyMethod = "close")
	public TableRepo tableRepo() throws Exception {
		KratiTableRepo repo = new KratiTableRepo();
		repo.init(config.getConf());
		return repo;
	}

	@Bean
	public AppLifeCycleManager getAppLifeCycleManager() {
		return new AppLifeCycleManagerImpl(null, /* checks */
		Arrays.asList(new TableEngineWrapper(factory
				.getBean(TableEngineManager.class))), /* services */
		null /* post startup */
		);
	}

	static class TableEngineWrapper implements ApplicationService {

		final TableEngineManager manager;

		public TableEngineWrapper(TableEngineManager manager) {
			super();
			this.manager = manager;
		}

		@Override
		public void shutdown() {
			manager.shutdown();
		}

		@Override
		public void start() throws Exception {
		}

	}

}
