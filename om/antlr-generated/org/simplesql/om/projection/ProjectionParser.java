// $ANTLR 3.4 /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g 2012-02-22 15:50:54

  package org.simplesql.om.projection;
  
  import org.simplesql.om.*;
  


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class ProjectionParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DOUBLE", "IDENT", "INTEGER", "STRING_LITERAL", "WS", "'('", "')'", "','", "'='", "'BOOLEAN'", "'DAILY'", "'DOUBLE'", "'FLOAT'", "'HOURLY'", "'INT'", "'LONG'", "'PROJECTION'", "'SHORT'", "'STRING'", "'TABLE'", "'hitType'"
    };

    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__10=10;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int DOUBLE=4;
    public static final int IDENT=5;
    public static final int INTEGER=6;
    public static final int STRING_LITERAL=7;
    public static final int WS=8;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public ProjectionParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public ProjectionParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return ProjectionParser.tokenNames; }
    public String getGrammarFileName() { return "/home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g"; }


      int orderCounter = 0;


    public static class projection_return extends ParserRuleReturnScope {
        public org.simplesql.om.ClientInfoTemplate.Projection.Builder builder = org.simplesql.om.ClientInfoTemplate.Projection.newBuilder();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "projection"
    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:29:1: projection returns [org.simplesql.om.ClientInfoTemplate.Projection.Builder builder = org.simplesql.om.ClientInfoTemplate.Projection.newBuilder()] : ( 'PROJECTION' | 'TABLE' IDENT '(' c= column ( ',' c= column )* ( 'hitType' '=' ( 'DAILY' | 'HOURLY' ) )* ')' );
    public final ProjectionParser.projection_return projection() throws RecognitionException {
        ProjectionParser.projection_return retval = new ProjectionParser.projection_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token string_literal1=null;
        Token string_literal2=null;
        Token IDENT3=null;
        Token char_literal4=null;
        Token char_literal5=null;
        Token string_literal6=null;
        Token char_literal7=null;
        Token string_literal8=null;
        Token string_literal9=null;
        Token char_literal10=null;
        ProjectionParser.column_return c =null;


        Object string_literal1_tree=null;
        Object string_literal2_tree=null;
        Object IDENT3_tree=null;
        Object char_literal4_tree=null;
        Object char_literal5_tree=null;
        Object string_literal6_tree=null;
        Object char_literal7_tree=null;
        Object string_literal8_tree=null;
        Object string_literal9_tree=null;
        Object char_literal10_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:30:3: ( 'PROJECTION' | 'TABLE' IDENT '(' c= column ( ',' c= column )* ( 'hitType' '=' ( 'DAILY' | 'HOURLY' ) )* ')' )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==20) ) {
                alt4=1;
            }
            else if ( (LA4_0==23) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:30:3: 'PROJECTION'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal1=(Token)match(input,20,FOLLOW_20_in_projection75); 
                    string_literal1_tree = 
                    (Object)adaptor.create(string_literal1)
                    ;
                    adaptor.addChild(root_0, string_literal1_tree);


                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:30:16: 'TABLE' IDENT '(' c= column ( ',' c= column )* ( 'hitType' '=' ( 'DAILY' | 'HOURLY' ) )* ')'
                    {
                    root_0 = (Object)adaptor.nil();


                    string_literal2=(Token)match(input,23,FOLLOW_23_in_projection77); 
                    string_literal2_tree = 
                    (Object)adaptor.create(string_literal2)
                    ;
                    adaptor.addChild(root_0, string_literal2_tree);


                    IDENT3=(Token)match(input,IDENT,FOLLOW_IDENT_in_projection79); 
                    IDENT3_tree = 
                    (Object)adaptor.create(IDENT3)
                    ;
                    adaptor.addChild(root_0, IDENT3_tree);


                    retval.builder.setName((IDENT3!=null?IDENT3.getText():null));

                    char_literal4=(Token)match(input,9,FOLLOW_9_in_projection94); 
                    char_literal4_tree = 
                    (Object)adaptor.create(char_literal4)
                    ;
                    adaptor.addChild(root_0, char_literal4_tree);


                    pushFollow(FOLLOW_column_in_projection98);
                    c=column();

                    state._fsp--;

                    adaptor.addChild(root_0, c.getTree());

                    retval.builder.addColumn(c.col.build());

                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:32:16: ( ',' c= column )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==11) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:32:17: ',' c= column
                    	    {
                    	    char_literal5=(Token)match(input,11,FOLLOW_11_in_projection118); 
                    	    char_literal5_tree = 
                    	    (Object)adaptor.create(char_literal5)
                    	    ;
                    	    adaptor.addChild(root_0, char_literal5_tree);


                    	    pushFollow(FOLLOW_column_in_projection122);
                    	    c=column();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, c.getTree());

                    	    retval.builder.addColumn(c.col.build());

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:33:12: ( 'hitType' '=' ( 'DAILY' | 'HOURLY' ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==24) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:34:13: 'hitType' '=' ( 'DAILY' | 'HOURLY' )
                    	    {
                    	    string_literal6=(Token)match(input,24,FOLLOW_24_in_projection153); 
                    	    string_literal6_tree = 
                    	    (Object)adaptor.create(string_literal6)
                    	    ;
                    	    adaptor.addChild(root_0, string_literal6_tree);


                    	    char_literal7=(Token)match(input,12,FOLLOW_12_in_projection155); 
                    	    char_literal7_tree = 
                    	    (Object)adaptor.create(char_literal7)
                    	    ;
                    	    adaptor.addChild(root_0, char_literal7_tree);


                    	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:34:27: ( 'DAILY' | 'HOURLY' )
                    	    int alt2=2;
                    	    int LA2_0 = input.LA(1);

                    	    if ( (LA2_0==14) ) {
                    	        alt2=1;
                    	    }
                    	    else if ( (LA2_0==17) ) {
                    	        alt2=2;
                    	    }
                    	    else {
                    	        NoViableAltException nvae =
                    	            new NoViableAltException("", 2, 0, input);

                    	        throw nvae;

                    	    }
                    	    switch (alt2) {
                    	        case 1 :
                    	            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:34:28: 'DAILY'
                    	            {
                    	            string_literal8=(Token)match(input,14,FOLLOW_14_in_projection158); 
                    	            string_literal8_tree = 
                    	            (Object)adaptor.create(string_literal8)
                    	            ;
                    	            adaptor.addChild(root_0, string_literal8_tree);


                    	            retval.builder.setHitType(org.simplesql.om.ClientInfoTemplate.Projection.HIT_TYPE.DAILY);

                    	            }
                    	            break;
                    	        case 2 :
                    	            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:35:28: 'HOURLY'
                    	            {
                    	            string_literal9=(Token)match(input,17,FOLLOW_17_in_projection190); 
                    	            string_literal9_tree = 
                    	            (Object)adaptor.create(string_literal9)
                    	            ;
                    	            adaptor.addChild(root_0, string_literal9_tree);


                    	            retval.builder.setHitType(org.simplesql.om.ClientInfoTemplate.Projection.HIT_TYPE.HOURLY);

                    	            }
                    	            break;

                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    char_literal10=(Token)match(input,10,FOLLOW_10_in_projection209); 
                    char_literal10_tree = 
                    (Object)adaptor.create(char_literal10)
                    ;
                    adaptor.addChild(root_0, char_literal10_tree);


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
    // $ANTLR end "projection"


    public static class column_return extends ParserRuleReturnScope {
        public org.simplesql.om.ClientInfoTemplate.Column.Builder col = org.simplesql.om.ClientInfoTemplate.Column.newBuilder();
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "column"
    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:38:1: column returns [org.simplesql.om.ClientInfoTemplate.Column.Builder col = org.simplesql.om.ClientInfoTemplate.Column.newBuilder()] : IDENT (t= type |t= type width= INTEGER ) ;
    public final ProjectionParser.column_return column() throws RecognitionException {
        ProjectionParser.column_return retval = new ProjectionParser.column_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token width=null;
        Token IDENT11=null;
        ProjectionParser.type_return t =null;


        Object width_tree=null;
        Object IDENT11_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:39:2: ( IDENT (t= type |t= type width= INTEGER ) )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:39:4: IDENT (t= type |t= type width= INTEGER )
            {
            root_0 = (Object)adaptor.nil();


            IDENT11=(Token)match(input,IDENT,FOLLOW_IDENT_in_column233); 
            IDENT11_tree = 
            (Object)adaptor.create(IDENT11)
            ;
            adaptor.addChild(root_0, IDENT11_tree);


            retval.col.setName((IDENT11!=null?IDENT11.getText():null)); retval.col.setOrder(orderCounter++);

            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:40:9: (t= type |t= type width= INTEGER )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==13||(LA5_0 >= 15 && LA5_0 <= 16)||(LA5_0 >= 18 && LA5_0 <= 19)||(LA5_0 >= 21 && LA5_0 <= 22)) ) {
                int LA5_1 = input.LA(2);

                if ( ((LA5_1 >= 10 && LA5_1 <= 11)||LA5_1==24) ) {
                    alt5=1;
                }
                else if ( (LA5_1==INTEGER) ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:40:10: t= type
                    {
                    pushFollow(FOLLOW_type_in_column249);
                    t=type();

                    state._fsp--;

                    adaptor.addChild(root_0, t.getTree());

                    retval.col.setType((t!=null?input.toString(t.start,t.stop):null));

                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:42:10: t= type width= INTEGER
                    {
                    pushFollow(FOLLOW_type_in_column279);
                    t=type();

                    state._fsp--;

                    adaptor.addChild(root_0, t.getTree());

                    width=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_column283); 
                    width_tree = 
                    (Object)adaptor.create(width)
                    ;
                    adaptor.addChild(root_0, width_tree);


                    retval.col.setType((t!=null?input.toString(t.start,t.stop):null)); retval.col.setWidth(Integer.parseInt((width!=null?width.getText():null)));

                    }
                    break;

            }


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
    // $ANTLR end "column"


    public static class type_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "type"
    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:45:1: type : ( 'INT' | 'STRING' | 'DOUBLE' | 'LONG' | 'BOOLEAN' | 'FLOAT' | 'SHORT' ) ;
    public final ProjectionParser.type_return type() throws RecognitionException {
        ProjectionParser.type_return retval = new ProjectionParser.type_return();
        retval.start = input.LT(1);


        Object root_0 = null;

        Token set12=null;

        Object set12_tree=null;

        try {
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:45:6: ( ( 'INT' | 'STRING' | 'DOUBLE' | 'LONG' | 'BOOLEAN' | 'FLOAT' | 'SHORT' ) )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:
            {
            root_0 = (Object)adaptor.nil();


            set12=(Token)input.LT(1);

            if ( input.LA(1)==13||(input.LA(1) >= 15 && input.LA(1) <= 16)||(input.LA(1) >= 18 && input.LA(1) <= 19)||(input.LA(1) >= 21 && input.LA(1) <= 22) ) {
                input.consume();
                adaptor.addChild(root_0, 
                (Object)adaptor.create(set12)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


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
    // $ANTLR end "type"

    // Delegated rules


 

    public static final BitSet FOLLOW_20_in_projection75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_23_in_projection77 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_projection79 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_9_in_projection94 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_column_in_projection98 = new BitSet(new long[]{0x0000000001000C00L});
    public static final BitSet FOLLOW_11_in_projection118 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_column_in_projection122 = new BitSet(new long[]{0x0000000001000C00L});
    public static final BitSet FOLLOW_24_in_projection153 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_12_in_projection155 = new BitSet(new long[]{0x0000000000024000L});
    public static final BitSet FOLLOW_14_in_projection158 = new BitSet(new long[]{0x0000000001000400L});
    public static final BitSet FOLLOW_17_in_projection190 = new BitSet(new long[]{0x0000000001000400L});
    public static final BitSet FOLLOW_10_in_projection209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_column233 = new BitSet(new long[]{0x00000000006DA000L});
    public static final BitSet FOLLOW_type_in_column249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_column279 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_INTEGER_in_column283 = new BitSet(new long[]{0x0000000000000002L});

}