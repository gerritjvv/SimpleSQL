// $ANTLR 3.4 /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g 2012-01-04 11:16:35

  package org.simplesql.parser;
  
  import org.simplesql.parser.tree.*;
  


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class SQLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "BY", "DOUBLE", "EXPRESSION", "FROM", "FUNC", "GROUP", "IDENT", "INTEGER", "LIMIT", "LOGICAL", "LOGICAL_TOKEN", "MINUS", "MULT_TOKEN", "NEGATION_TOKEN", "NUMBER", "ORDER", "PLUS", "REAL_NUMBER", "RELATION", "RELATION_TOKEN", "SELECT", "STRING", "STRING_LITERAL", "UNARY", "VARIABLE", "WHERE", "WS", "'('", "')'", "'*'", "'+'", "','", "'-'", "'/'", "';'", "'mod'"
    };

    public static final int EOF=-1;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int BY=4;
    public static final int DOUBLE=5;
    public static final int EXPRESSION=6;
    public static final int FROM=7;
    public static final int FUNC=8;
    public static final int GROUP=9;
    public static final int IDENT=10;
    public static final int INTEGER=11;
    public static final int LIMIT=12;
    public static final int LOGICAL=13;
    public static final int LOGICAL_TOKEN=14;
    public static final int MINUS=15;
    public static final int MULT_TOKEN=16;
    public static final int NEGATION_TOKEN=17;
    public static final int NUMBER=18;
    public static final int ORDER=19;
    public static final int PLUS=20;
    public static final int REAL_NUMBER=21;
    public static final int RELATION=22;
    public static final int RELATION_TOKEN=23;
    public static final int SELECT=24;
    public static final int STRING=25;
    public static final int STRING_LITERAL=26;
    public static final int UNARY=27;
    public static final int VARIABLE=28;
    public static final int WHERE=29;
    public static final int WS=30;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public SQLParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public SQLParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return SQLParser.tokenNames; }
    public String getGrammarFileName() { return "/home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g"; }


      
      public SELECT select;
      


    public static class statement_return extends ParserRuleReturnScope {
        public SELECT ret = new SELECT();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "statement"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:46:1: statement returns [SELECT ret = new SELECT()] : SELECT (se1= expression ) ( ',' (se1= expression ) )* FROM IDENT ( WHERE w1= logical )* ( 'GROUP' 'BY' (gpe1= expression ) ( ',' (gpe1= expression ) )* )* ( 'ORDER' 'BY' (ope1= expression ) ( ',' (ope1= expression ) )* )* ( 'LIMIT' l= INTEGER )* ( ';' )* ;
    public final SQLParser.statement_return statement() throws RecognitionException {
        SQLParser.statement_return retval = new SQLParser.statement_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token l=null;
        Token SELECT1=null;
        Token char_literal2=null;
        Token FROM3=null;
        Token IDENT4=null;
        Token WHERE5=null;
        Token string_literal6=null;
        Token string_literal7=null;
        Token char_literal8=null;
        Token string_literal9=null;
        Token string_literal10=null;
        Token char_literal11=null;
        Token string_literal12=null;
        Token char_literal13=null;
        SQLParser.expression_return se1 =null;

        SQLParser.logical_return w1 =null;

        SQLParser.expression_return gpe1 =null;

        SQLParser.expression_return ope1 =null;


        Object l_tree=null;
        Object SELECT1_tree=null;
        Object char_literal2_tree=null;
        Object FROM3_tree=null;
        Object IDENT4_tree=null;
        Object WHERE5_tree=null;
        Object string_literal6_tree=null;
        Object string_literal7_tree=null;
        Object char_literal8_tree=null;
        Object string_literal9_tree=null;
        Object string_literal10_tree=null;
        Object char_literal11_tree=null;
        Object string_literal12_tree=null;
        Object char_literal13_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:47:12: ( SELECT (se1= expression ) ( ',' (se1= expression ) )* FROM IDENT ( WHERE w1= logical )* ( 'GROUP' 'BY' (gpe1= expression ) ( ',' (gpe1= expression ) )* )* ( 'ORDER' 'BY' (ope1= expression ) ( ',' (ope1= expression ) )* )* ( 'LIMIT' l= INTEGER )* ( ';' )* )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:47:14: SELECT (se1= expression ) ( ',' (se1= expression ) )* FROM IDENT ( WHERE w1= logical )* ( 'GROUP' 'BY' (gpe1= expression ) ( ',' (gpe1= expression ) )* )* ( 'ORDER' 'BY' (ope1= expression ) ( ',' (ope1= expression ) )* )* ( 'LIMIT' l= INTEGER )* ( ';' )*
            {
            root_0 = (Object)adaptor.nil();


            SELECT1=(Token)match(input,SELECT,FOLLOW_SELECT_in_statement151); 
            SELECT1_tree = 
            (Object)adaptor.create(SELECT1)
            ;
            adaptor.addChild(root_0, SELECT1_tree);


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:47:21: (se1= expression )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:47:22: se1= expression
            {
            pushFollow(FOLLOW_expression_in_statement156);
            se1=expression();

            state._fsp--;

            adaptor.addChild(root_0, se1.getTree());

             select = retval.ret;  retval.ret.select((se1!=null?se1.expr:null));

            }


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:48:21: ( ',' (se1= expression ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==35) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:48:22: ',' (se1= expression )
            	    {
            	    char_literal2=(Token)match(input,35,FOLLOW_35_in_statement183); 
            	    char_literal2_tree = 
            	    (Object)adaptor.create(char_literal2)
            	    ;
            	    adaptor.addChild(root_0, char_literal2_tree);


            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:48:26: (se1= expression )
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:48:27: se1= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_statement188);
            	    se1=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, se1.getTree());

            	    retval.ret.select((se1!=null?se1.expr:null));

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            FROM3=(Token)match(input,FROM,FOLLOW_FROM_in_statement195); 
            FROM3_tree = 
            (Object)adaptor.create(FROM3)
            ;
            adaptor.addChild(root_0, FROM3_tree);


            IDENT4=(Token)match(input,IDENT,FOLLOW_IDENT_in_statement197); 
            IDENT4_tree = 
            (Object)adaptor.create(IDENT4)
            ;
            adaptor.addChild(root_0, IDENT4_tree);


            retval.ret.table((IDENT4!=null?IDENT4.getText():null));

            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:49:13: ( WHERE w1= logical )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==WHERE) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:49:14: WHERE w1= logical
            	    {
            	    WHERE5=(Token)match(input,WHERE,FOLLOW_WHERE_in_statement215); 
            	    WHERE5_tree = 
            	    (Object)adaptor.create(WHERE5)
            	    ;
            	    adaptor.addChild(root_0, WHERE5_tree);


            	    pushFollow(FOLLOW_logical_in_statement219);
            	    w1=logical();

            	    state._fsp--;

            	    adaptor.addChild(root_0, w1.getTree());

            	    retval.ret.where((w1!=null?w1.ret:null));

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:50:13: ( 'GROUP' 'BY' (gpe1= expression ) ( ',' (gpe1= expression ) )* )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==GROUP) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:50:14: 'GROUP' 'BY' (gpe1= expression ) ( ',' (gpe1= expression ) )*
            	    {
            	    string_literal6=(Token)match(input,GROUP,FOLLOW_GROUP_in_statement238); 
            	    string_literal6_tree = 
            	    (Object)adaptor.create(string_literal6)
            	    ;
            	    adaptor.addChild(root_0, string_literal6_tree);


            	    string_literal7=(Token)match(input,BY,FOLLOW_BY_in_statement240); 
            	    string_literal7_tree = 
            	    (Object)adaptor.create(string_literal7)
            	    ;
            	    adaptor.addChild(root_0, string_literal7_tree);


            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:50:27: (gpe1= expression )
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:50:28: gpe1= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_statement245);
            	    gpe1=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, gpe1.getTree());

            	    retval.ret.groupBy((gpe1!=null?gpe1.expr:null));

            	    }


            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:51:22: ( ',' (gpe1= expression ) )*
            	    loop3:
            	    do {
            	        int alt3=2;
            	        int LA3_0 = input.LA(1);

            	        if ( (LA3_0==35) ) {
            	            alt3=1;
            	        }


            	        switch (alt3) {
            	    	case 1 :
            	    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:51:23: ',' (gpe1= expression )
            	    	    {
            	    	    char_literal8=(Token)match(input,35,FOLLOW_35_in_statement273); 
            	    	    char_literal8_tree = 
            	    	    (Object)adaptor.create(char_literal8)
            	    	    ;
            	    	    adaptor.addChild(root_0, char_literal8_tree);


            	    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:51:27: (gpe1= expression )
            	    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:51:28: gpe1= expression
            	    	    {
            	    	    pushFollow(FOLLOW_expression_in_statement278);
            	    	    gpe1=expression();

            	    	    state._fsp--;

            	    	    adaptor.addChild(root_0, gpe1.getTree());

            	    	    retval.ret.groupBy((gpe1!=null?gpe1.expr:null));

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop3;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:52:13: ( 'ORDER' 'BY' (ope1= expression ) ( ',' (ope1= expression ) )* )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==ORDER) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:52:14: 'ORDER' 'BY' (ope1= expression ) ( ',' (ope1= expression ) )*
            	    {
            	    string_literal9=(Token)match(input,ORDER,FOLLOW_ORDER_in_statement300); 
            	    string_literal9_tree = 
            	    (Object)adaptor.create(string_literal9)
            	    ;
            	    adaptor.addChild(root_0, string_literal9_tree);


            	    string_literal10=(Token)match(input,BY,FOLLOW_BY_in_statement302); 
            	    string_literal10_tree = 
            	    (Object)adaptor.create(string_literal10)
            	    ;
            	    adaptor.addChild(root_0, string_literal10_tree);


            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:52:27: (ope1= expression )
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:52:28: ope1= expression
            	    {
            	    pushFollow(FOLLOW_expression_in_statement307);
            	    ope1=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, ope1.getTree());

            	    retval.ret.orderBy((ope1!=null?ope1.expr:null));

            	    }


            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:53:22: ( ',' (ope1= expression ) )*
            	    loop5:
            	    do {
            	        int alt5=2;
            	        int LA5_0 = input.LA(1);

            	        if ( (LA5_0==35) ) {
            	            alt5=1;
            	        }


            	        switch (alt5) {
            	    	case 1 :
            	    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:53:23: ',' (ope1= expression )
            	    	    {
            	    	    char_literal11=(Token)match(input,35,FOLLOW_35_in_statement335); 
            	    	    char_literal11_tree = 
            	    	    (Object)adaptor.create(char_literal11)
            	    	    ;
            	    	    adaptor.addChild(root_0, char_literal11_tree);


            	    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:53:27: (ope1= expression )
            	    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:53:28: ope1= expression
            	    	    {
            	    	    pushFollow(FOLLOW_expression_in_statement340);
            	    	    ope1=expression();

            	    	    state._fsp--;

            	    	    adaptor.addChild(root_0, ope1.getTree());

            	    	    retval.ret.orderBy((ope1!=null?ope1.expr:null));

            	    	    }


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop5;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:54:13: ( 'LIMIT' l= INTEGER )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==LIMIT) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:54:14: 'LIMIT' l= INTEGER
            	    {
            	    string_literal12=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_statement362); 
            	    string_literal12_tree = 
            	    (Object)adaptor.create(string_literal12)
            	    ;
            	    adaptor.addChild(root_0, string_literal12_tree);


            	    l=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_statement366); 
            	    l_tree = 
            	    (Object)adaptor.create(l)
            	    ;
            	    adaptor.addChild(root_0, l_tree);


            	    retval.ret.limit((l!=null?l.getText():null));

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:55:13: ( ';' )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==38) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:55:13: ';'
            	    {
            	    char_literal13=(Token)match(input,38,FOLLOW_38_in_statement384); 
            	    char_literal13_tree = 
            	    (Object)adaptor.create(char_literal13)
            	    ;
            	    adaptor.addChild(root_0, char_literal13_tree);


            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "statement"


    public static class unary_return extends ParserRuleReturnScope {
        public UNARY unary = new UNARY();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "unary"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:65:1: unary returns [UNARY unary = new UNARY()] : (v1= funct |v2= term );
    public final SQLParser.unary_return unary() throws RecognitionException {
        SQLParser.unary_return retval = new SQLParser.unary_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        SQLParser.funct_return v1 =null;

        SQLParser.term_return v2 =null;



        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:66:2: (v1= funct |v2= term )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==IDENT) ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==31) ) {
                    alt9=1;
                }
                else if ( (LA9_1==EOF||LA9_1==FROM||LA9_1==GROUP||(LA9_1 >= LIMIT && LA9_1 <= LOGICAL)||LA9_1==ORDER||LA9_1==RELATION||LA9_1==WHERE||(LA9_1 >= 32 && LA9_1 <= 39)) ) {
                    alt9=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA9_0==DOUBLE||LA9_0==INTEGER||LA9_0==STRING_LITERAL||LA9_0==31) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }
            switch (alt9) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:67:7: v1= funct
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_funct_in_unary415);
                    v1=funct();

                    state._fsp--;

                    adaptor.addChild(root_0, v1.getTree());

                    retval.unary.term((v1!=null?v1.f:null));

                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:68:9: v2= term
                    {
                    root_0 = (Object)adaptor.nil();


                    pushFollow(FOLLOW_term_in_unary429);
                    v2=term();

                    state._fsp--;

                    adaptor.addChild(root_0, v2.getTree());

                    retval.unary.term((v2!=null?v2.term:null));

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "unary"


    public static class mult_return extends ParserRuleReturnScope {
        public MULT ret = new MULT();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "mult"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:73:1: mult returns [MULT ret = new MULT()] : u1= unary ( (t= '*' |t= '/' |t= 'mod' ) u2= unary )* ;
    public final SQLParser.mult_return mult() throws RecognitionException {
        SQLParser.mult_return retval = new SQLParser.mult_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token t=null;
        SQLParser.unary_return u1 =null;

        SQLParser.unary_return u2 =null;


        Object t_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:74:4: (u1= unary ( (t= '*' |t= '/' |t= 'mod' ) u2= unary )* )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:75:5: u1= unary ( (t= '*' |t= '/' |t= 'mod' ) u2= unary )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_unary_in_mult467);
            u1=unary();

            state._fsp--;

            adaptor.addChild(root_0, u1.getTree());

            retval.ret.unary((u1!=null?u1.unary:null));

            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:76:5: ( (t= '*' |t= '/' |t= 'mod' ) u2= unary )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==33||LA11_0==37||LA11_0==39) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:76:6: (t= '*' |t= '/' |t= 'mod' ) u2= unary
            	    {
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:76:6: (t= '*' |t= '/' |t= 'mod' )
            	    int alt10=3;
            	    switch ( input.LA(1) ) {
            	    case 33:
            	        {
            	        alt10=1;
            	        }
            	        break;
            	    case 37:
            	        {
            	        alt10=2;
            	        }
            	        break;
            	    case 39:
            	        {
            	        alt10=3;
            	        }
            	        break;
            	    default:
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;

            	    }

            	    switch (alt10) {
            	        case 1 :
            	            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:76:7: t= '*'
            	            {
            	            t=(Token)match(input,33,FOLLOW_33_in_mult479); 
            	            t_tree = 
            	            (Object)adaptor.create(t)
            	            ;
            	            adaptor.addChild(root_0, t_tree);


            	            retval.ret.mult();

            	            }
            	            break;
            	        case 2 :
            	            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:76:30: t= '/'
            	            {
            	            t=(Token)match(input,37,FOLLOW_37_in_mult487); 
            	            t_tree = 
            	            (Object)adaptor.create(t)
            	            ;
            	            adaptor.addChild(root_0, t_tree);


            	            retval.ret.divide();

            	            }
            	            break;
            	        case 3 :
            	            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:77:7: t= 'mod'
            	            {
            	            t=(Token)match(input,39,FOLLOW_39_in_mult499); 
            	            t_tree = 
            	            (Object)adaptor.create(t)
            	            ;
            	            adaptor.addChild(root_0, t_tree);


            	            retval.ret.mod();

            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_unary_in_mult511);
            	    u2=unary();

            	    state._fsp--;

            	    adaptor.addChild(root_0, u2.getTree());

            	    retval.ret.unary((u2!=null?u2.unary:null));

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "mult"


    public static class expression_return extends ParserRuleReturnScope {
        public EXPRESSION expr = new EXPRESSION();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expression"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:83:1: expression returns [EXPRESSION expr = new EXPRESSION()] : em1= mult ( (t+= '-' |t+= '+' ) em2= mult )* ;
    public final SQLParser.expression_return expression() throws RecognitionException {
        SQLParser.expression_return retval = new SQLParser.expression_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token t=null;
        List list_t=null;
        SQLParser.mult_return em1 =null;

        SQLParser.mult_return em2 =null;


        Object t_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:84:4: (em1= mult ( (t+= '-' |t+= '+' ) em2= mult )* )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:84:6: em1= mult ( (t+= '-' |t+= '+' ) em2= mult )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_mult_in_expression542);
            em1=mult();

            state._fsp--;

            adaptor.addChild(root_0, em1.getTree());

            retval.expr.mult((em1!=null?em1.ret:null));

            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:85:28: ( (t+= '-' |t+= '+' ) em2= mult )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==34||LA13_0==36) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:85:29: (t+= '-' |t+= '+' ) em2= mult
            	    {
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:85:29: (t+= '-' |t+= '+' )
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==36) ) {
            	        alt12=1;
            	    }
            	    else if ( (LA12_0==34) ) {
            	        alt12=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 12, 0, input);

            	        throw nvae;

            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:85:30: t+= '-'
            	            {
            	            t=(Token)match(input,36,FOLLOW_36_in_expression553); 
            	            t_tree = 
            	            (Object)adaptor.create(t)
            	            ;
            	            adaptor.addChild(root_0, t_tree);

            	            if (list_t==null) list_t=new ArrayList();
            	            list_t.add(t);


            	            retval.expr.minus();

            	            }
            	            break;
            	        case 2 :
            	            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:85:55: t+= '+'
            	            {
            	            t=(Token)match(input,34,FOLLOW_34_in_expression560); 
            	            t_tree = 
            	            (Object)adaptor.create(t)
            	            ;
            	            adaptor.addChild(root_0, t_tree);

            	            if (list_t==null) list_t=new ArrayList();
            	            list_t.add(t);


            	            retval.expr.plus();

            	            }
            	            break;

            	    }


            	    pushFollow(FOLLOW_mult_in_expression567);
            	    em2=mult();

            	    state._fsp--;

            	    adaptor.addChild(root_0, em2.getTree());

            	    retval.expr.mult((em2!=null?em2.ret:null));

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expression"


    public static class relation_return extends ParserRuleReturnScope {
        public RELATION ret;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "relation"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:91:1: relation returns [RELATION ret] : e1= expression RELATION e2= expression ;
    public final SQLParser.relation_return relation() throws RecognitionException {
        SQLParser.relation_return retval = new SQLParser.relation_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token RELATION14=null;
        SQLParser.expression_return e1 =null;

        SQLParser.expression_return e2 =null;


        Object RELATION14_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:92:9: (e1= expression RELATION e2= expression )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:93:11: e1= expression RELATION e2= expression
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_expression_in_relation622);
            e1=expression();

            state._fsp--;

            adaptor.addChild(root_0, e1.getTree());

            RELATION14=(Token)match(input,RELATION,FOLLOW_RELATION_in_relation624); 
            RELATION14_tree = 
            (Object)adaptor.create(RELATION14)
            ;
            adaptor.addChild(root_0, RELATION14_tree);


            pushFollow(FOLLOW_expression_in_relation628);
            e2=expression();

            state._fsp--;

            adaptor.addChild(root_0, e2.getTree());

            retval.ret = new RELATION((e1!=null?e1.expr:null), (RELATION14!=null?RELATION14.getText():null), (e2!=null?e2.expr:null));

            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "relation"


    public static class logical_return extends ParserRuleReturnScope {
        public LOGICAL ret = new LOGICAL();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "logical"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:97:1: logical returns [LOGICAL ret = new LOGICAL()] : r1= relation (lotoken= LOGICAL r2= relation )* ;
    public final SQLParser.logical_return logical() throws RecognitionException {
        SQLParser.logical_return retval = new SQLParser.logical_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token lotoken=null;
        SQLParser.relation_return r1 =null;

        SQLParser.relation_return r2 =null;


        Object lotoken_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:98:9: (r1= relation (lotoken= LOGICAL r2= relation )* )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:98:11: r1= relation (lotoken= LOGICAL r2= relation )*
            {
            root_0 = (Object)adaptor.nil();


            pushFollow(FOLLOW_relation_in_logical670);
            r1=relation();

            state._fsp--;

            adaptor.addChild(root_0, r1.getTree());

            retval.ret.relation((r1!=null?r1.ret:null));

            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:99:11: (lotoken= LOGICAL r2= relation )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==LOGICAL) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:99:12: lotoken= LOGICAL r2= relation
            	    {
            	    lotoken=(Token)match(input,LOGICAL,FOLLOW_LOGICAL_in_logical688); 
            	    lotoken_tree = 
            	    (Object)adaptor.create(lotoken)
            	    ;
            	    adaptor.addChild(root_0, lotoken_tree);


            	    retval.ret.logical((lotoken!=null?lotoken.getText():null));

            	    pushFollow(FOLLOW_relation_in_logical695);
            	    r2=relation();

            	    state._fsp--;

            	    adaptor.addChild(root_0, r2.getTree());

            	    retval.ret.relation((r2!=null?r2.ret:null));

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "logical"


    public static class funct_return extends ParserRuleReturnScope {
        public FUNCTION f = new FUNCTION();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "funct"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:104:1: funct returns [FUNCTION f = new FUNCTION()] : IDENT '(' fe1= expression ( ',' fe2= expression )* ')' ;
    public final SQLParser.funct_return funct() throws RecognitionException {
        SQLParser.funct_return retval = new SQLParser.funct_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token IDENT15=null;
        Token char_literal16=null;
        Token char_literal17=null;
        Token char_literal18=null;
        SQLParser.expression_return fe1 =null;

        SQLParser.expression_return fe2 =null;


        Object IDENT15_tree=null;
        Object char_literal16_tree=null;
        Object char_literal17_tree=null;
        Object char_literal18_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:105:6: ( IDENT '(' fe1= expression ( ',' fe2= expression )* ')' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:105:8: IDENT '(' fe1= expression ( ',' fe2= expression )* ')'
            {
            root_0 = (Object)adaptor.nil();


            IDENT15=(Token)match(input,IDENT,FOLLOW_IDENT_in_funct732); 
            IDENT15_tree = 
            (Object)adaptor.create(IDENT15)
            ;
            adaptor.addChild(root_0, IDENT15_tree);


            retval.f.name((IDENT15!=null?IDENT15.getText():null));

            char_literal16=(Token)match(input,31,FOLLOW_31_in_funct735); 
            char_literal16_tree = 
            (Object)adaptor.create(char_literal16)
            ;
            adaptor.addChild(root_0, char_literal16_tree);


            pushFollow(FOLLOW_expression_in_funct739);
            fe1=expression();

            state._fsp--;

            adaptor.addChild(root_0, fe1.getTree());

            retval.f.expression((fe1!=null?fe1.expr:null));

            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:105:84: ( ',' fe2= expression )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==35) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:105:85: ',' fe2= expression
            	    {
            	    char_literal17=(Token)match(input,35,FOLLOW_35_in_funct744); 
            	    char_literal17_tree = 
            	    (Object)adaptor.create(char_literal17)
            	    ;
            	    adaptor.addChild(root_0, char_literal17_tree);


            	    pushFollow(FOLLOW_expression_in_funct748);
            	    fe2=expression();

            	    state._fsp--;

            	    adaptor.addChild(root_0, fe2.getTree());

            	    retval.f.expression((fe2!=null?fe2.expr:null));

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            char_literal18=(Token)match(input,32,FOLLOW_32_in_funct754); 
            char_literal18_tree = 
            (Object)adaptor.create(char_literal18)
            ;
            adaptor.addChild(root_0, char_literal18_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "funct"


    public static class term_return extends ParserRuleReturnScope {
        public TERM term;
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "term"
    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:110:1: term returns [TERM term] : ( (v= IDENT -> ^( VARIABLE $v) |v= INTEGER -> ^( NUMBER $v) |v= DOUBLE -> ^( REAL_NUMBER $v) | '(' r= expression ')' -> ^( EXPRESSION $r) ) |v= STRING_LITERAL -> ^( STRING $v) );
    public final SQLParser.term_return term() throws RecognitionException {
        SQLParser.term_return retval = new SQLParser.term_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token v=null;
        Token char_literal19=null;
        Token char_literal20=null;
        SQLParser.expression_return r =null;


        Object v_tree=null;
        Object char_literal19_tree=null;
        Object char_literal20_tree=null;
        RewriteRuleTokenStream stream_INTEGER=new RewriteRuleTokenStream(adaptor,"token INTEGER");
        RewriteRuleTokenStream stream_DOUBLE=new RewriteRuleTokenStream(adaptor,"token DOUBLE");
        RewriteRuleTokenStream stream_IDENT=new RewriteRuleTokenStream(adaptor,"token IDENT");
        RewriteRuleTokenStream stream_STRING_LITERAL=new RewriteRuleTokenStream(adaptor,"token STRING_LITERAL");
        RewriteRuleTokenStream stream_32=new RewriteRuleTokenStream(adaptor,"token 32");
        RewriteRuleTokenStream stream_31=new RewriteRuleTokenStream(adaptor,"token 31");
        RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");
        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:110:25: ( (v= IDENT -> ^( VARIABLE $v) |v= INTEGER -> ^( NUMBER $v) |v= DOUBLE -> ^( REAL_NUMBER $v) | '(' r= expression ')' -> ^( EXPRESSION $r) ) |v= STRING_LITERAL -> ^( STRING $v) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==DOUBLE||(LA17_0 >= IDENT && LA17_0 <= INTEGER)||LA17_0==31) ) {
                alt17=1;
            }
            else if ( (LA17_0==STRING_LITERAL) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }
            switch (alt17) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:111:7: (v= IDENT -> ^( VARIABLE $v) |v= INTEGER -> ^( NUMBER $v) |v= DOUBLE -> ^( REAL_NUMBER $v) | '(' r= expression ')' -> ^( EXPRESSION $r) )
                    {
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:111:7: (v= IDENT -> ^( VARIABLE $v) |v= INTEGER -> ^( NUMBER $v) |v= DOUBLE -> ^( REAL_NUMBER $v) | '(' r= expression ')' -> ^( EXPRESSION $r) )
                    int alt16=4;
                    switch ( input.LA(1) ) {
                    case IDENT:
                        {
                        alt16=1;
                        }
                        break;
                    case INTEGER:
                        {
                        alt16=2;
                        }
                        break;
                    case DOUBLE:
                        {
                        alt16=3;
                        }
                        break;
                    case 31:
                        {
                        alt16=4;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;

                    }

                    switch (alt16) {
                        case 1 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:111:9: v= IDENT
                            {
                            v=(Token)match(input,IDENT,FOLLOW_IDENT_in_term781);  
                            stream_IDENT.add(v);


                            // AST REWRITE
                            // elements: v
                            // token labels: v
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_v=new RewriteRuleTokenStream(adaptor,"token v",v);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 111:19: -> ^( VARIABLE $v)
                            {
                                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:111:22: ^( VARIABLE $v)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(
                                (Object)adaptor.create(VARIABLE, "VARIABLE")
                                , root_1);

                                adaptor.addChild(root_1, stream_v.nextNode());

                                adaptor.addChild(root_0, root_1);
                                }

                                adaptor.addChild(root_0, retval.term = new VARIABLE((v!=null?v.getText():null)));

                            }


                            retval.tree = root_0;

                            }
                            break;
                        case 2 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:112:9: v= INTEGER
                            {
                            v=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_term808);  
                            stream_INTEGER.add(v);


                            // AST REWRITE
                            // elements: v
                            // token labels: v
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_v=new RewriteRuleTokenStream(adaptor,"token v",v);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 112:19: -> ^( NUMBER $v)
                            {
                                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:112:22: ^( NUMBER $v)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(
                                (Object)adaptor.create(NUMBER, "NUMBER")
                                , root_1);

                                adaptor.addChild(root_1, stream_v.nextNode());

                                adaptor.addChild(root_0, root_1);
                                }

                                adaptor.addChild(root_0,  retval.term = new INTEGER((v!=null?v.getText():null)));

                            }


                            retval.tree = root_0;

                            }
                            break;
                        case 3 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:113:9: v= DOUBLE
                            {
                            v=(Token)match(input,DOUBLE,FOLLOW_DOUBLE_in_term831);  
                            stream_DOUBLE.add(v);


                            // AST REWRITE
                            // elements: v
                            // token labels: v
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleTokenStream stream_v=new RewriteRuleTokenStream(adaptor,"token v",v);
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 113:18: -> ^( REAL_NUMBER $v)
                            {
                                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:113:21: ^( REAL_NUMBER $v)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(
                                (Object)adaptor.create(REAL_NUMBER, "REAL_NUMBER")
                                , root_1);

                                adaptor.addChild(root_1, stream_v.nextNode());

                                adaptor.addChild(root_0, root_1);
                                }

                                adaptor.addChild(root_0, retval.term = new DOUBLE((v!=null?v.getText():null)));

                            }


                            retval.tree = root_0;

                            }
                            break;
                        case 4 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:114:9: '(' r= expression ')'
                            {
                            char_literal19=(Token)match(input,31,FOLLOW_31_in_term853);  
                            stream_31.add(char_literal19);


                            pushFollow(FOLLOW_expression_in_term857);
                            r=expression();

                            state._fsp--;

                            stream_expression.add(r.getTree());

                            char_literal20=(Token)match(input,32,FOLLOW_32_in_term859);  
                            stream_32.add(char_literal20);


                            // AST REWRITE
                            // elements: r
                            // token labels: 
                            // rule labels: retval, r
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
                            RewriteRuleSubtreeStream stream_r=new RewriteRuleSubtreeStream(adaptor,"rule r",r!=null?r.tree:null);

                            root_0 = (Object)adaptor.nil();
                            // 114:30: -> ^( EXPRESSION $r)
                            {
                                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:114:33: ^( EXPRESSION $r)
                                {
                                Object root_1 = (Object)adaptor.nil();
                                root_1 = (Object)adaptor.becomeRoot(
                                (Object)adaptor.create(EXPRESSION, "EXPRESSION")
                                , root_1);

                                adaptor.addChild(root_1, stream_r.nextTree());

                                adaptor.addChild(root_0, root_1);
                                }

                            }


                            retval.tree = root_0;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:117:9: v= STRING_LITERAL
                    {
                    v=(Token)match(input,STRING_LITERAL,FOLLOW_STRING_LITERAL_in_term899);  
                    stream_STRING_LITERAL.add(v);


                    // AST REWRITE
                    // elements: v
                    // token labels: v
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleTokenStream stream_v=new RewriteRuleTokenStream(adaptor,"token v",v);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 117:26: -> ^( STRING $v)
                    {
                        // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:117:29: ^( STRING $v)
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot(
                        (Object)adaptor.create(STRING, "STRING")
                        , root_1);

                        adaptor.addChild(root_1, stream_v.nextNode());

                        adaptor.addChild(root_0, root_1);
                        }

                        adaptor.addChild(root_0, retval.term = new STRING((v!=null?v.getText():null)));

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "term"

    // Delegated rules


 

    public static final BitSet FOLLOW_SELECT_in_statement151 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_statement156 = new BitSet(new long[]{0x0000000800000080L});
    public static final BitSet FOLLOW_35_in_statement183 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_statement188 = new BitSet(new long[]{0x0000000800000080L});
    public static final BitSet FOLLOW_FROM_in_statement195 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_statement197 = new BitSet(new long[]{0x0000004020081202L});
    public static final BitSet FOLLOW_WHERE_in_statement215 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_logical_in_statement219 = new BitSet(new long[]{0x0000004020081202L});
    public static final BitSet FOLLOW_GROUP_in_statement238 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_BY_in_statement240 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_statement245 = new BitSet(new long[]{0x0000004800081202L});
    public static final BitSet FOLLOW_35_in_statement273 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_statement278 = new BitSet(new long[]{0x0000004800081202L});
    public static final BitSet FOLLOW_ORDER_in_statement300 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_BY_in_statement302 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_statement307 = new BitSet(new long[]{0x0000004800081002L});
    public static final BitSet FOLLOW_35_in_statement335 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_statement340 = new BitSet(new long[]{0x0000004800081002L});
    public static final BitSet FOLLOW_LIMIT_in_statement362 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_INTEGER_in_statement366 = new BitSet(new long[]{0x0000004000001002L});
    public static final BitSet FOLLOW_38_in_statement384 = new BitSet(new long[]{0x0000004000000002L});
    public static final BitSet FOLLOW_funct_in_unary415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_unary429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_in_mult467 = new BitSet(new long[]{0x000000A200000002L});
    public static final BitSet FOLLOW_33_in_mult479 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_37_in_mult487 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_39_in_mult499 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_unary_in_mult511 = new BitSet(new long[]{0x000000A200000002L});
    public static final BitSet FOLLOW_mult_in_expression542 = new BitSet(new long[]{0x0000001400000002L});
    public static final BitSet FOLLOW_36_in_expression553 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_34_in_expression560 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_mult_in_expression567 = new BitSet(new long[]{0x0000001400000002L});
    public static final BitSet FOLLOW_expression_in_relation622 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_RELATION_in_relation624 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_relation628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relation_in_logical670 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_LOGICAL_in_logical688 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_relation_in_logical695 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_IDENT_in_funct732 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_31_in_funct735 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_funct739 = new BitSet(new long[]{0x0000000900000000L});
    public static final BitSet FOLLOW_35_in_funct744 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_funct748 = new BitSet(new long[]{0x0000000900000000L});
    public static final BitSet FOLLOW_32_in_funct754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_term781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_term808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_in_term831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_term853 = new BitSet(new long[]{0x0000000084000C20L});
    public static final BitSet FOLLOW_expression_in_term857 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_32_in_term859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_LITERAL_in_term899 = new BitSet(new long[]{0x0000000000000002L});

}