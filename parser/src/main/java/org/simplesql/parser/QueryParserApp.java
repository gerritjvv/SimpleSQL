package org.simplesql.parser;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.simplesql.parser.SQLParser.statement_return;

public class QueryParserApp {

	public static void main(String[] args) throws RecognitionException {
		String str = "SELECT a FROM tbl WHERE b=1 AND c=1*-(6+8) OR b=3";
//		String str = "SELECT 'my', 12.2 FROM tbl";
		SQLLexer lexer = new SQLLexer(
				new ANTLRStringStream(
						str));
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
                if(ch.toString().equals("EXPRESSION")){
//                	System.out.println("ChildCount: " + tree.getChildCount());
//                	if(tree.getChildCount() == 0)
//                	{
//                		System.out.println("C");
//                	}
                	
                }
                if(tree.getChildCount() > 0){
                        printChildren(tab  + "\t",tree.getChildren());
                }
   }
}


}
