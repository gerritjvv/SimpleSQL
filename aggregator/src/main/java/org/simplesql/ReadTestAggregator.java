package org.simplesql;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
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
import org.simplesql.om.data.stores.KratiStoreManager;
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
public class ReadTestAggregator {

	static File workingDir;
	static Configuration conf;

	public static void main(String[] args) throws Throwable {

		File file = new File("/dev/stdin");
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String line = null;

		long start = System.currentTimeMillis();
		int lines = 0;
//		while ((line = reader.readLine()) != null) {
//			lines++;
//		}
			BufferedInputStream in = new BufferedInputStream(System.in);
			byte[] buff = new byte[1024];
			
			while( (in.read(buff)) > 0 ){
				lines++;
			}
				
		
		long end = System.currentTimeMillis() - start;
		System.out.println("Time: " + end + " lines: " + lines);

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
				.println("<config file> <projecton schema> <projection sql file> [data file name]");
	}

}
