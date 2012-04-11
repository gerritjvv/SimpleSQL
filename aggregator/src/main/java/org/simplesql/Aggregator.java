package org.simplesql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DataSource;
import org.simplesql.data.Key;
import org.simplesql.data.util.FileDataSource;
import org.simplesql.data.util.STDINDataSource;
import org.simplesql.data.util.SelectTransform;
import org.simplesql.om.ClientInfoTemplate.Projection;
import org.simplesql.om.data.StorageManager;
import org.simplesql.om.data.stores.BerkeleyStorageManager;
import org.simplesql.om.data.stores.CachedStoreManager;
import org.simplesql.om.data.stores.berkeley.DBManager;
import org.simplesql.om.projection.ProjectionLexer;
import org.simplesql.om.projection.ProjectionParser;
import org.simplesql.om.projection.ProjectionParser.projection_return;
import org.simplesql.om.util.ProjectionKeyUtil;
import org.simplesql.parser.SQLCompiler;
import org.simplesql.parser.SQLExecutor;
import org.simplesql.parser.SimpleSQLCompiler;
import org.simplesql.schema.TableDef;

/**
 * 
 * Reads input from STDIN, and takes as single argument a file name that
 * contains all of the projections to be made. Each projection comes in the form
 * of a SQL statement.
 * 
 */
public class Aggregator {

	static File workingDir;

	public static void main(String[] args) throws Throwable {

		if (!(args.length == 3 || args.length == 4)) {
			printUsage();
		}

		workingDir = new File("./" + System.currentTimeMillis());
		workingDir.mkdirs();
		try {
			List<String> lines = FileUtils.readLines(new File(args[2]));

			// only support one at the moment.

			Projection projection = createProjection(args[0]);
			final String sep = args[1];

			final TableDef tableDef = createSchema(projection);

			final ExecutorService execService = Executors.newCachedThreadPool();
			final SQLCompiler compiler = new SimpleSQLCompiler(execService);

			final SQLExecutor exec = compiler.compile(tableDef, lines.get(0));

			final SelectTransform transform = new SelectTransform(
					tableDef.getColumnDefs(), exec.getColumnsUsed());
			final DataSource dataSource = (args.length == 4) ? new FileDataSource(
					transform, new File(args[3]), sep) : new STDINDataSource(
					transform, sep);

			final StorageManager manager = getStorageManager(workingDir);
			AggregateStore storage = null;
			try {
				storage = manager.newAggregateStore(projection, exec);
				exec.pump(dataSource, storage, null);

				final BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(System.out));

				try {
					storage.write(new DataSink() {

						public boolean fill(Key key, Cell<?>[] data) {
							try {
								final int len = data.length;
								for (int i = 0; i < len; i++) {
									if (i != 0)
										out.append(sep);

									out.append(data[i].getData().toString());
								}
								out.append("\n");
							} catch (IOException exp) {
								throw new RuntimeException(exp.toString(), exp);
							}

							return true;

						}
					});
				} finally {
					out.close();
				}
			} finally {
				storage.close();
				manager.close();
			}

			execService.shutdown();
			execService.awaitTermination(2, TimeUnit.SECONDS);
			execService.shutdownNow();
		} finally {
			FileUtils.deleteDirectory(workingDir);
		}

	}

	private static StorageManager getStorageManager(File workingDir)
			throws Throwable {
		return new CachedStoreManager(new BerkeleyStorageManager(new DBManager(
				workingDir)), 500, null);
	}

	private static Projection createProjection(String line) throws Throwable {
		ProjectionLexer lexer = new ProjectionLexer(new ANTLRStringStream(line));
		ProjectionParser parser = new ProjectionParser(new CommonTokenStream(
				lexer));
		projection_return preturn = parser.projection();
		return preturn.builder.build();
	}

	private static TableDef createSchema(Projection projection) {
		return ProjectionKeyUtil.createTableDefNoIds(projection);
	}

	private static void printUsage() {
		System.out
				.println("<projecton schema> <projection sql file> [data file name]");
	}

}