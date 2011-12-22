package org.simplesql.parser;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.simplesql.parser.SQLParser.statement_return;

public class QueryParserApp {

	public static void main(String[] args) throws RecognitionException {
		SQLLexer lexer = new SQLLexer(
				new ANTLRStringStream(
						"SELECT f(a,c), f(b), 'B', 1, d*(2+f(1*(3+2))) FROM tbl WHERE a=1 and b=3 GROUP BY a+1,b ORDER BY a,c"));
		SQLParser parser = new SQLParser(new CommonTokenStream(lexer));
		statement_return ret = parser.statement();
		CommonTree tree = (CommonTree) ret.getTree();

		System.out.println(tree.toStringTree());

		for (Object ch : tree.getChildren()) {
			CommonTree child = (CommonTree) ch;

			System.out.println("child: " + child);
			if (child.getChildCount() > 0) {
				printChildren("\t", child.getChildren());
			}

		}

	}

	private static void printChildren(String tab, List children) {

        for(Object ch : children){
                CommonTree tree = (CommonTree)ch;
                System.out.println(tab + ch);
                if(tree.getChildCount() > 0){
                        printChildren(tab  + "\t",tree.getChildren());
                }
   }
}


}
