package org.simplesql.parser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.antlr.runtime.tree.CommonTree;
import org.simplesql.data.AggregateStore;
import org.simplesql.data.Cell;
import org.simplesql.data.DataSink;
import org.simplesql.data.DataSource;
import org.simplesql.data.IntCell;
import org.simplesql.data.Key;
import org.simplesql.data.StringCell;
import org.simplesql.data.TransformFunction;
import org.simplesql.data.impl.HashMapAggregateStore;
import org.simplesql.schema.SimpleColumnDef;
import org.simplesql.schema.SimpleTableDef;
import org.simplesql.schema.TableDef;

public class QueryParserApp {

	public static void main(String[] args) throws Throwable {
		String str = "SELECT 1, b, a*0.5/2, \"STR\",c , COUNT(1) FROM table WHERE a>=1 AND b<5 GROUP BY a, b";
		
		TableDef tableDef = new SimpleTableDef("mytbl", 
				new SimpleColumnDef(Integer.class, "a", new IntCell()),
				new SimpleColumnDef(Integer.class, "b", new IntCell()),
				new SimpleColumnDef(String.class, "c", new StringCell()));

		ExecutorService execService = Executors.newCachedThreadPool();
		SQLCompiler compiler = new SimpleSQLCompiler(execService);
		
		SQLExecutor exec = compiler.compile(tableDef, str);
		
		Object[][] data = new Object[][]{
			
				new Object[]{ 1, 2, "hi"},
				new Object[]{ 1, 3, "hi there"},
				new Object[]{ 1, 3, "hi there"}
				
		};
		
		AggregateStore store = new HashMapAggregateStore(exec.getTransforms().toArray(new TransformFunction[0]));
		
		final List<Object[]> dataList = Arrays.asList(data);
		
		
		exec.pump(new DataSource() {
			
			@Override
			public Iterator<Object[]> iterator() {
					return dataList.iterator();
			}
			
			@Override
			public long getEstimatedSize() {
				return dataList.size();
			}
			
			
		}, store, null);
		
		execService.shutdown();
		
		store.write(new DataSink() {
			
			@Override
			public boolean fill(Key key, Cell<?>[] data) {
				System.out.println(key.asString().hashCode() + " : " + Arrays.toString(data));
				return true;
			}
		});
		
//		eval.setDefaultImports(new String[]{
//			"org.simplesql.data",
//			"org.simplesql.data.Cell",
//			"org.simplesql.data.IntCell",
//			"org.simplesql.data.DoubleCell",
//			"org.simplesql.data.LongCell",
//			"org.simplesql.data.StringCell",
//			"org.simplesql.data.DynamicCell"
//		});
		
//		Cell[] res = (Cell[]) eval.evaluate(new Object[] { new Integer(2) });

//		System.out.println(Arrays.toString(res));

		//
		//
		// for (Object ch : tree.getChildren()) {
		// CommonTree child = (CommonTree) ch;
		//
		// System.out.println("child: " + child);
		//
		// if(ch.toString().equals("EXPRESSION")){
		// parseExpression(child);
		// }
		//
		// if (child.getChildCount() > 0) {
		// printChildren("\t", child.getChildren());
		// }
		//
		// }

	}

	@SuppressWarnings("unchecked")
	public static void parseExpression(CommonTree tree) {

		List<CommonTree> children = tree.getChildren();
		StringBuilder buff = new StringBuilder(200);
		int len = children.size();
		for (int i = 0; i < len; i++) {
			CommonTree multToken = children.get(i);
			String name = multToken.toString();

			if (name.equals("MULT_TOKEN")) {
				System.out.println("MULT");
				parseUnary(buff, (CommonTree) multToken.getChild(0));

			} else {
				buff.append(name);
			}

			// for each multToken get

		}

		System.out.println("PARSED: " + buff.toString());
	}

	@SuppressWarnings("unchecked")
	private static void parseUnary(StringBuilder buff, CommonTree unary) {

		// unary operator, either size 1 or 2 applies - + to children.
		int len = unary.getChildCount();
		List<CommonTree> children = unary.getChildren();

		if (len == 1) {
			parseUnaryChild(buff, children.get(0));
		} else if (len == 2) {
			buff.append(children.get(0).toString());
			buff.append('(');
			parseUnaryChild(buff, children.get(1));
			buff.append(')');
		} else {
			throw new ParseException("UnExpected expression " + unary.getText());
		}

	}

	private static void parseUnaryChild(StringBuilder buff,
			CommonTree commonTree) {
		buff.append("expr");

	}

	private static void printChildren(String tab, List children) {

		for (Object ch : children) {
			CommonTree tree = (CommonTree) ch;
			System.out.println(tab + ch);

			if (ch.toString().equals("EXPRESSION")) {
				parseExpression(tree);
			}

			if (tree.getChildCount() > 0) {
				printChildren(tab + "\t", tree.getChildren());
			}
		}
	}

}
