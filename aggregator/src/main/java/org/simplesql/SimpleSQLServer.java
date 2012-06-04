package org.simplesql;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.myrest.http.HttpRestHandler;
import org.myrest.http.HttpRestServer;
import org.myrest.util.ControllerFactory;
import org.myrest.util.RestPathMappingContainer;
import org.simplesql.server.conf.SimpleConfig;
import org.simplesql.server.di.SimpleDI;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.streams.commons.app.AppLifeCycleManager;

public class SimpleSQLServer {

	private static final Logger LOG = Logger.getLogger(SimpleSQLServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {

		File configFile = new File(args[0]);
		if (!configFile.exists())
			throw new RuntimeException("Please type <config file>");

		// CREATE DI Configuration Instance that will be called inside the
		// spring DI
		// spring will us the method getInstance of AdGetConfigImpl passing it
		// he configFile.getAbsolutePath()
		ConstructorArgumentValues contructorArg = new ConstructorArgumentValues();
		contructorArg.addGenericArgumentValue(configFile.getAbsolutePath());

		final AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		AnnotatedGenericBeanDefinition configBeanDef = new AnnotatedGenericBeanDefinition(
				SimpleConfig.class);
		configBeanDef.setConstructorArgumentValues(contructorArg);
		configBeanDef.setFactoryMethodName("getInstance");

		// inject config
		appContext.registerBeanDefinition("config", configBeanDef);

		// register DI
		appContext.register(SimpleDI.class);
		appContext.refresh();

		final AppLifeCycleManager app = appContext
				.getBean(AppLifeCycleManager.class);
		app.init();

		RestFactory restFactory = new RestFactory(appContext);

		final RestPathMappingContainer select = new RestPathMappingContainer(
				"/query/select/${table} org.simplesql.server.controlers.QueryController.select");
		final RestPathMappingContainer insert = new RestPathMappingContainer(
				"/query/insert/${table} org.simplesql.server.controlers.QueryController.insert");
		final RestPathMappingContainer delete = new RestPathMappingContainer(
				"/query/delete/${table} org.simplesql.server.controlers.QueryController.delete");
		final RestPathMappingContainer create = new RestPathMappingContainer(
				"/table/create/${table} org.simplesql.server.controlers.TableController.create");
		final RestPathMappingContainer tblDelete = new RestPathMappingContainer(
				"/table/delete/${table} org.simplesql.server.controlers.TableController.delete");

		// add more mapping containers here for controller to method mappings
		final RestPathMappingContainer[] mappings = new RestPathMappingContainer[] {
				select, insert, delete, create, tblDelete };

		final ExecutorService service = Executors.newCachedThreadPool();

		final SimpleConfig config = appContext.getBean(SimpleConfig.class);

		final int port = config.getPort();

		final HttpRestHandler handler = new HttpRestHandler(mappings);
		final HttpRestServer server = new HttpRestServer(service, service,
				handler, port);

		server.start();
		LOG.info("Started server on port " + port);

		try {
			while (!Thread.currentThread().isInterrupted()) {
				Thread.sleep(500L);
			}
		} finally {
			app.shutdown();
			server.shutdown();
			service.shutdown();
		}

		LOG.info("Shutdown");

	}

	static class RestFactory implements ControllerFactory {

		final ApplicationContext ctx;

		public RestFactory(ApplicationContext ctx) {
			super();
			this.ctx = ctx;
		}

		@Override
		public final Object newInstance(String controllerName) {
			// controllerName is class name
			try {
				return ctx.getBean(Thread.currentThread()
						.getContextClassLoader().loadClass(controllerName));
			} catch (Throwable t) {
				RuntimeException rte = new RuntimeException(t.toString(), t);
				rte.setStackTrace(t.getStackTrace());
				throw rte;
			}
		}

	}

}
