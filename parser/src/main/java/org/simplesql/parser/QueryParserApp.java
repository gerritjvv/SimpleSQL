package org.simplesql.parser;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.simplesql.parser.SQLParser.statement_return;

public class QueryParserApp {

	public static void main(String[] args) throws RecognitionException {
		String str = "SELECT 3+2*1/1 FROM table;";
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

            if(ch.toString().equals("EXPRESSION")){
              	parseExpression(child);
             }
            
			if (child.getChildCount() > 0) {
				printChildren("\t", child.getChildren());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public static void parseExpression(CommonTree tree){
		
		List<CommonTree> children = tree.getChildren();
		StringBuilder buff = new StringBuilder(200);
		int len = children.size();
		for(int i = 0; i < len; i++){
			CommonTree multToken = children.get(i);
			String name = multToken.toString();
			
			if(name.equals("MULT_TOKEN")){
				System.out.println("MULT");
				parseUnary(buff, (CommonTree)multToken.getChild(0));
				
			}else{
				buff.append(name);
			}
			
			//for each multToken get 
			
			
		}
		
		System.out.println("PARSED: " + buff.toString());
	}
	
	@SuppressWarnings("unchecked")
	private static void parseUnary(StringBuilder buff, CommonTree unary) {
		
		//unary operator, either size 1 or 2 applies - + to children.
		int len = unary.getChildCount();
		List<CommonTree> children = unary.getChildren();
		
		if(len == 1){
			parseUnaryChild(buff, children.get(0));
		}else if(len == 2){
			buff.append(children.get(0).toString());
			buff.append('(');
			parseUnaryChild(buff, children.get(1));
			buff.append(')');
		}else{
			throw new ParseException("UnExpected expression " + unary.getText());
		}
		
	}

	private static void parseUnaryChild(StringBuilder buff, CommonTree commonTree) {
		buff.append("expr");
		
	}

	private static void printChildren(String tab, List children) {
		   
        for(Object ch : children){
                CommonTree tree = (CommonTree)ch;
                System.out.println(tab + ch);
                
                if(ch.toString().equals("EXPRESSION")){
                  	parseExpression(tree);
                 }
        		
                
                if(tree.getChildCount() > 0){
                        printChildren(tab  + "\t",tree.getChildren());
                }
   }
}


}
