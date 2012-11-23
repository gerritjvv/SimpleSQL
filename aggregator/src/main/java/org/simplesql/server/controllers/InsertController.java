package org.simplesql.server.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.myrest.io.PlainText;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.INSERT;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.TableDef;
import org.simplesql.table.TableEngine;
import org.simplesql.table.TableEngineManager;
import org.simplesql.table.TableRepo;

public class InsertController {

	final TableRepo repo;
	final TableEngineManager engineManager;

	public InsertController(TableRepo repo, TableEngineManager engineManager) {
		super();
		this.repo = repo;
		this.engineManager = engineManager;
	}

	public HttpResponse create(HttpRequest req, String table)
			throws IOException, RecognitionException {

		HttpResponse resp;
		final TableDef tblDef = repo.getTable(table);
		if (tblDef != null) {

			final TableEngine engine = engineManager.getEngine(tblDef
					.getEngine());

			ChannelBuffer content = req.getContent();
			int len = (int) HttpHeaders.getContentLength(req, 0);

			if (len > 0) {
				final byte[] arr = new byte[len];
				content.readBytes(arr);

				BufferedReader reader = new BufferedReader(new StringReader(
						new String(arr, "UTF-8")));
				String line = null;

				while ((line = reader.readLine()) != null) {

					SQLLexer lexer = new SQLLexer(new ANTLRStringStream(line));
					SQLParser parser = new SQLParser(new CommonTokenStream(
							lexer));
					parser.setTreeAdaptor(new SELECTTreeAdaptor());
					INSERT insert = parser.insert().insert;

					engine.insert(insert);

				}

			}

			resp = new PlainText("DONE");
			resp.setStatus(HttpResponseStatus.OK);

		} else {

			resp = new PlainText("No table exist for " + table);
			resp.setStatus(HttpResponseStatus.BAD_REQUEST);

		}

		return resp;
	}

}
