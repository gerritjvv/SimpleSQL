package org.simplesql.server.controllers;

import java.io.UnsupportedEncodingException;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.myrest.io.PlainText;
import org.simplesql.parser.SQLLexer;
import org.simplesql.parser.SQLParser;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.schema.ColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;
import org.simplesql.table.TableRepo;
import org.simplesql.util.ParameterUtils;

public class TableController {

	final TableRepo repo;

	public TableController(TableRepo repo) {
		super();
		this.repo = repo;
	}

	public HttpResponse create(HttpRequest req, String table)
			throws UnsupportedEncodingException {

		HttpResponse resp;

		final QueryStringDecoder decoder = new QueryStringDecoder(req.getUri());
		final String sql = ParameterUtils.getParameter(decoder, "sql");

		if (sql == null || sql.length() < 1) {
			resp = new PlainText("Please provide a sql query string parameter");
			resp.setStatus(HttpResponseStatus.BAD_REQUEST);
		} else {

			final SQLLexer lexer = new SQLLexer(new ANTLRStringStream(
					ParameterUtils.removeEnclosingQuotes(sql)));
			final SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
			parser.setTreeAdaptor(new SELECTTreeAdaptor());

			try {
				parser.create();
				if (parser.failed()) {
					resp = new PlainText("Failed to parse " + sql);
					resp.setStatus(HttpResponseStatus.BAD_REQUEST);
				} else {

					SimpleTableDef tbl = parser.tableDef;
					if (!checkForFamilyNames(tbl)) {
						resp = new PlainText(
								"All columns must have a family name included");
						resp.setStatus(HttpResponseStatus.BAD_REQUEST);
					} else {

						String engine = tbl.getEngine();
						if (engine == null || engine.trim().length() < 1) {
							resp = new PlainText(
									"Tables must include an engine definition");
							resp.setStatus(HttpResponseStatus.BAD_REQUEST);
						} else {
							tbl.setEngine(tbl.getEngine().toLowerCase());
							repo.setTable(tbl);

							resp = new PlainText("Created " + tbl.getName()
									+ " with Engine " + engine);
							resp.setStatus(HttpResponseStatus.CREATED);
						}
					}
				}
			} catch (RecognitionException e) {
				resp = new PlainText("Error while parsing table definition: "
						+ sql + ": " + e.getMessage());
				resp.setStatus(HttpResponseStatus.BAD_REQUEST);
			}

		}

		return resp;

	}

	private static final boolean checkForFamilyNames(TableDef tbl) {

		for (ColumnDef col : tbl.getColumnDefs()) {
			if (!col.isKey()) {
				String fm = col.getFamily();
				if (fm == null || fm.trim().length() < 1)
					return false;
			}
		}

		return true;
	}

	public HttpResponse delete(HttpRequest req, String table)
			throws UnsupportedEncodingException {
		repo.removeTable(table);
		return new PlainText("Deleted " + table);
	}

}
