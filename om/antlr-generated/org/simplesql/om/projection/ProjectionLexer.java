// $ANTLR 3.4 /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g 2012-02-22 15:50:54

  package org.simplesql.om.projection;
  import org.simplesql.om.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class ProjectionLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public ProjectionLexer() {} 
    public ProjectionLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public ProjectionLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g"; }

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:12:6: ( '(' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:12:8: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:13:7: ( ')' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:13:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:14:7: ( ',' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:14:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:15:7: ( '=' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:15:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:16:7: ( 'BOOLEAN' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:16:9: 'BOOLEAN'
            {
            match("BOOLEAN"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:17:7: ( 'DAILY' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:17:9: 'DAILY'
            {
            match("DAILY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:18:7: ( 'DOUBLE' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:18:9: 'DOUBLE'
            {
            match("DOUBLE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:19:7: ( 'FLOAT' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:19:9: 'FLOAT'
            {
            match("FLOAT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:20:7: ( 'HOURLY' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:20:9: 'HOURLY'
            {
            match("HOURLY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:21:7: ( 'INT' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:21:9: 'INT'
            {
            match("INT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:22:7: ( 'LONG' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:22:9: 'LONG'
            {
            match("LONG"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:23:7: ( 'PROJECTION' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:23:9: 'PROJECTION'
            {
            match("PROJECTION"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:24:7: ( 'SHORT' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:24:9: 'SHORT'
            {
            match("SHORT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:25:7: ( 'STRING' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:25:9: 'STRING'
            {
            match("STRING"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:26:7: ( 'TABLE' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:26:9: 'TABLE'
            {
            match("TABLE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:27:7: ( 'hitType' )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:27:9: 'hitType'
            {
            match("hitType"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "DOUBLE"
    public final void mDOUBLE() throws RecognitionException {
        try {
            int _type = DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:47:8: ( INTEGER '.' INTEGER )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:47:10: INTEGER '.' INTEGER
            {
            mINTEGER(); 


            match('.'); 

            mINTEGER(); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOUBLE"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:48:9: ( ( '0' .. '9' )+ )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:48:11: ( '0' .. '9' )+
            {
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:48:11: ( '0' .. '9' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '0' && LA1_0 <= '9')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:50:16: ( '\"' (~ ( '\"' | '\\n' | '\\r' ) )* '\"' | '\\'' (~ ( '\\'' | '\\n' | '\\r' ) )* '\\'' )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\"') ) {
                alt4=1;
            }
            else if ( (LA4_0=='\'') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:51:3: '\"' (~ ( '\"' | '\\n' | '\\r' ) )* '\"'
                    {
                    match('\"'); 

                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:51:7: (~ ( '\"' | '\\n' | '\\r' ) )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0 >= '\u0000' && LA2_0 <= '\t')||(LA2_0 >= '\u000B' && LA2_0 <= '\f')||(LA2_0 >= '\u000E' && LA2_0 <= '!')||(LA2_0 >= '#' && LA2_0 <= '\uFFFF')) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);


                    match('\"'); 

                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:51:33: '\\'' (~ ( '\\'' | '\\n' | '\\r' ) )* '\\''
                    {
                    match('\''); 

                    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:51:38: (~ ( '\\'' | '\\n' | '\\r' ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0 >= '\u0000' && LA3_0 <= '\t')||(LA3_0 >= '\u000B' && LA3_0 <= '\f')||(LA3_0 >= '\u000E' && LA3_0 <= '&')||(LA3_0 >= '(' && LA3_0 <= '\uFFFF')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    match('\''); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:53:7: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | INTEGER )* )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:53:9: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | INTEGER )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:53:30: ( 'a' .. 'z' | 'A' .. 'Z' | INTEGER )*
            loop5:
            do {
                int alt5=4;
                switch ( input.LA(1) ) {
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt5=1;
                    }
                    break;
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                case 'H':
                case 'I':
                case 'J':
                case 'K':
                case 'L':
                case 'M':
                case 'N':
                case 'O':
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'U':
                case 'V':
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                    {
                    alt5=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                    alt5=3;
                    }
                    break;

                }

                switch (alt5) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:53:31: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); 

            	    }
            	    break;
            	case 2 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:53:42: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); 

            	    }
            	    break;
            	case 3 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:53:53: INTEGER
            	    {
            	    mINTEGER(); 


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:55:4: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:55:6: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:55:6: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0 >= '\t' && LA6_0 <= '\n')||(LA6_0 >= '\f' && LA6_0 <= '\r')||LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:
            	    {
            	    if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||(input.LA(1) >= '\f' && input.LA(1) <= '\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            _channel = HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:8: ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | DOUBLE | INTEGER | STRING_LITERAL | IDENT | WS )
        int alt7=21;
        alt7 = dfa7.predict(input);
        switch (alt7) {
            case 1 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:10: T__9
                {
                mT__9(); 


                }
                break;
            case 2 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:15: T__10
                {
                mT__10(); 


                }
                break;
            case 3 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:21: T__11
                {
                mT__11(); 


                }
                break;
            case 4 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:27: T__12
                {
                mT__12(); 


                }
                break;
            case 5 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:33: T__13
                {
                mT__13(); 


                }
                break;
            case 6 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:39: T__14
                {
                mT__14(); 


                }
                break;
            case 7 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:45: T__15
                {
                mT__15(); 


                }
                break;
            case 8 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:51: T__16
                {
                mT__16(); 


                }
                break;
            case 9 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:57: T__17
                {
                mT__17(); 


                }
                break;
            case 10 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:63: T__18
                {
                mT__18(); 


                }
                break;
            case 11 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:69: T__19
                {
                mT__19(); 


                }
                break;
            case 12 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:75: T__20
                {
                mT__20(); 


                }
                break;
            case 13 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:81: T__21
                {
                mT__21(); 


                }
                break;
            case 14 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:87: T__22
                {
                mT__22(); 


                }
                break;
            case 15 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:93: T__23
                {
                mT__23(); 


                }
                break;
            case 16 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:99: T__24
                {
                mT__24(); 


                }
                break;
            case 17 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:105: DOUBLE
                {
                mDOUBLE(); 


                }
                break;
            case 18 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:112: INTEGER
                {
                mINTEGER(); 


                }
                break;
            case 19 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:120: STRING_LITERAL
                {
                mSTRING_LITERAL(); 


                }
                break;
            case 20 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:135: IDENT
                {
                mIDENT(); 


                }
                break;
            case 21 :
                // /home/gvanvuuren/checkouts/SimpleSQL/om/src/main/antlr3/org.simplesql.om.projection/Projection.g:1:141: WS
                {
                mWS(); 


                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    static final String DFA7_eotS =
        "\5\uffff\12\21\1\37\3\uffff\14\21\2\uffff\5\21\1\62\13\21\1\uffff"+
        "\1\76\6\21\1\105\1\21\1\107\1\21\1\uffff\1\21\1\112\1\21\1\114\2"+
        "\21\1\uffff\1\117\1\uffff\1\120\1\21\1\uffff\1\122\1\uffff\1\21"+
        "\1\124\2\uffff\1\21\1\uffff\1\126\1\uffff\1\21\1\uffff\1\21\1\131"+
        "\1\uffff";
    static final String DFA7_eofS =
        "\132\uffff";
    static final String DFA7_minS =
        "\1\11\4\uffff\1\117\1\101\1\114\1\117\1\116\1\117\1\122\1\110\1"+
        "\101\1\151\1\56\3\uffff\1\117\1\111\1\125\1\117\1\125\1\124\1\116"+
        "\2\117\1\122\1\102\1\164\2\uffff\2\114\1\102\1\101\1\122\1\60\1"+
        "\107\1\112\1\122\1\111\1\114\1\124\1\105\1\131\1\114\1\124\1\114"+
        "\1\uffff\1\60\1\105\1\124\1\116\1\105\1\171\1\101\1\60\1\105\1\60"+
        "\1\131\1\uffff\1\103\1\60\1\107\1\60\1\160\1\116\1\uffff\1\60\1"+
        "\uffff\1\60\1\124\1\uffff\1\60\1\uffff\1\145\1\60\2\uffff\1\111"+
        "\1\uffff\1\60\1\uffff\1\117\1\uffff\1\116\1\60\1\uffff";
    static final String DFA7_maxS =
        "\1\172\4\uffff\2\117\1\114\1\117\1\116\1\117\1\122\1\124\1\101\1"+
        "\151\1\71\3\uffff\1\117\1\111\1\125\1\117\1\125\1\124\1\116\2\117"+
        "\1\122\1\102\1\164\2\uffff\2\114\1\102\1\101\1\122\1\172\1\107\1"+
        "\112\1\122\1\111\1\114\1\124\1\105\1\131\1\114\1\124\1\114\1\uffff"+
        "\1\172\1\105\1\124\1\116\1\105\1\171\1\101\1\172\1\105\1\172\1\131"+
        "\1\uffff\1\103\1\172\1\107\1\172\1\160\1\116\1\uffff\1\172\1\uffff"+
        "\1\172\1\124\1\uffff\1\172\1\uffff\1\145\1\172\2\uffff\1\111\1\uffff"+
        "\1\172\1\uffff\1\117\1\uffff\1\116\1\172\1\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\13\uffff\1\23\1\24\1\25\14\uffff\1\22\1"+
        "\21\21\uffff\1\12\13\uffff\1\13\6\uffff\1\6\1\uffff\1\10\2\uffff"+
        "\1\15\1\uffff\1\17\2\uffff\1\7\1\11\1\uffff\1\16\1\uffff\1\5\1\uffff"+
        "\1\20\2\uffff\1\14";
    static final String DFA7_specialS =
        "\132\uffff}>";
    static final String[] DFA7_transitionS = {
            "\2\22\1\uffff\2\22\22\uffff\1\22\1\uffff\1\20\4\uffff\1\20\1"+
            "\1\1\2\2\uffff\1\3\3\uffff\12\17\3\uffff\1\4\3\uffff\1\21\1"+
            "\5\1\21\1\6\1\21\1\7\1\21\1\10\1\11\2\21\1\12\3\21\1\13\2\21"+
            "\1\14\1\15\6\21\6\uffff\7\21\1\16\22\21",
            "",
            "",
            "",
            "",
            "\1\23",
            "\1\24\15\uffff\1\25",
            "\1\26",
            "\1\27",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\33\13\uffff\1\34",
            "\1\35",
            "\1\36",
            "\1\40\1\uffff\12\17",
            "",
            "",
            "",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "",
            "",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "\1\74",
            "\1\75",
            "",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\103",
            "\1\104",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\106",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\110",
            "",
            "\1\111",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\113",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\115",
            "\1\116",
            "",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "\1\121",
            "",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "",
            "\1\123",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "",
            "",
            "\1\125",
            "",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            "",
            "\1\127",
            "",
            "\1\130",
            "\12\21\7\uffff\32\21\6\uffff\32\21",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | DOUBLE | INTEGER | STRING_LITERAL | IDENT | WS );";
        }
    }
 

}