package org.simplesql.parser;

import java.util.Arrays;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.codehaus.janino.ExpressionEvaluator;
import org.simplesql.data.Cell;
import org.simplesql.funct.GroupBy.KeyParser;
import org.simplesql.parser.tree.SELECT;
import org.simplesql.parser.tree.SELECTTreeAdaptor;
import org.simplesql.parser.tree.TreeJavaConvert;

public class QueryParserApp {

	public static void main(String[] args) throws Throwable {
		String str = "SELECT 1, a*0.5/2, \"STR\", SIZE(\"a\") FROM table WHERE a=2 AND b=3";
		// String str = "SELECT 'my', 12.2 FROM tbl";
		SQLLexer lexer = new SQLLexer(new ANTLRStringStream(str));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		parser.setTreeAdaptor(new SELECTTreeAdaptor());

		SELECT select = parser.statement().ret;

		TreeJavaConvert converter = new TreeJavaConvert(select);

		System.out.println(converter.getSelectExpressions());
		System.out.println(converter.getGroupByExpressions());
		System.out.println(converter.getOrderByExpressions());
		System.out.println(converter.getWhereExpressions());

		
		ExpressionEvaluator eval = new ExpressionEvaluator("new org.simplesql.data.Cell[]{"
				+ converter.getSelectExpressions() + "}", Object[].class,
				new String[] { "a" }, new Class[] { int.class },
				new Class[0], QueryParserApp.class.getClassLoader());
		
		String groupByExpressions = converter.getGroupByExpressions();
		KeyParser keyParser;
		
		if(groupByExpressions != null){
			
		}
		
		
//		eval.setDefaultImports(new String[]{
//			"org.simplesql.data",
//			"org.simplesql.data.Cell",
//			"org.simplesql.data.IntCell",
//			"org.simplesql.data.DoubleCell",
//			"org.simplesql.data.LongCell",
//			"org.simplesql.data.StringCell",
//			"org.simplesql.data.DynamicCell"
//		});
		
		Cell[] res = (Cell[]) eval.evaluate(new Object[] { new Integer(2) });

		System.out.println(Arrays.toString(res));

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
