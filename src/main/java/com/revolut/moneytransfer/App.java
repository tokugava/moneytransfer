package com.revolut.moneytransfer;

import javax.inject.Singleton;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.revolut.moneytransfer.dao.AccountDao;
import com.revolut.moneytransfer.dao.IAccountDao;
import com.revolut.moneytransfer.dao.ITransactionDao;
import com.revolut.moneytransfer.dao.TransactionDao;
import com.revolut.moneytransfer.resources.AccountResource;

/**
 * Hello world!
 *
 */
public class App {
	private static Server jettyServer;
	public static void main(String[] args) {

//		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//		context.setContextPath("/");
//
//		Server jettyServer = new Server(8080);
//		jettyServer.setHandler(context);
//
//		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
//		jerseyServlet.setInitOrder(0);
//
////		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", HelloWorld.class.getCanonicalName());
//		jerseyServlet.setInitParameter("jersey.config.server.provider.packages", "com.revolut.moneytransfer.resources");
//
//		
//		try {
//			jettyServer.start();
//			jettyServer.join();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		} finally {
//			jettyServer.destroy();
//		}

		ResourceConfig resourceConfig = new ResourceConfig();
		resourceConfig.packages(AccountResource.class.getPackage().getName());
		resourceConfig.register(JacksonJaxbJsonProvider.class);

		resourceConfig.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(AccountDao.class).to(IAccountDao.class).in(Singleton.class);
				bind(TransactionDao.class).to(ITransactionDao.class).in(Singleton.class);
			}
		});

		ServletContainer servletContainer = new ServletContainer(resourceConfig);

		ServletHolder sh = new ServletHolder(servletContainer);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.addServlet(sh, "/*");

		jettyServer = new Server(8080);

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { context });
		jettyServer.setHandler(contexts);
		
		try {
			jettyServer.start();
			jettyServer.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jettyServer.destroy();
		}
	}
	
	public static void finish() {
		jettyServer.destroy();
	}
}
