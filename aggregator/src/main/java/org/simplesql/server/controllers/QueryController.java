package org.simplesql.server.controllers;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.myrest.io.PlainText;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.schema.TableDef;
import org.simplesql.table.TableEngine;
import org.simplesql.table.TableEngine.SELECT_OUTPUT;
import org.simplesql.table.TableEngineManager;
import org.simplesql.table.TableRepo;
import org.simplesql.util.ParameterUtils;

public class QueryController {

	final TableEngineManager engineManager;
	final TableRepo repo;

	final ExecutorService threadPool = Executors.newCachedThreadPool();

	public QueryController(TableEngineManager engineManager, TableRepo repo) {
		super();
		this.engineManager = engineManager;
		this.repo = repo;
	}

	/**
	 * Select information from the table engine.
	 * 
	 * @param req
	 * @param table
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public HttpResponse select(HttpRequest req, String table)
			throws UnsupportedEncodingException {

		HttpResponse resp;

		final TableDef tableDef = repo.getTable(table);
		if (tableDef == null) {
			resp = new PlainText(table + " not found");
			resp.setStatus(HttpResponseStatus.NOT_FOUND);
		} else {

			QueryStringDecoder decoder = new QueryStringDecoder(req.getUri());
			String sqlSelect = ParameterUtils.getParameter(decoder, "sql");
			if (sqlSelect == null || sqlSelect.length() < 1) {
				resp = new PlainText(
						"Please provide a sql query string parameter");
				resp.setStatus(HttpResponseStatus.BAD_REQUEST);
			} else {

				final TableEngine engine = engineManager.getEngine(tableDef
						.getEngine());

				if (engine == null) {
					resp = new PlainText("Engine : " + tableDef.getEngine()
							+ " not found for table " + table);
					resp.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
				} else {

					// compile select
					final SQLCompiler compiler = new SimpleSQLCompiler(
							threadPool);

					final SELECT select = compiler.compileSelect(ParameterUtils
							.removeEnclosingQuotes(sqlSelect));
					final StringBuilder builder = new StringBuilder(1000);

					// ask engine to perform select
					engine.select(compiler, select, new SELECT_OUTPUT() {

						@Override
						public void write(Object[] values) {
							builder.append(StringUtils.join(values, ','))
									.append('\n');
						}

					});

					resp = new PlainText(builder.toString());
					resp.setStatus(HttpResponseStatus.OK);

				}
			}

		}

		return resp;
	}

}
