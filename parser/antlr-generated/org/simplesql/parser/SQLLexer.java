// $ANTLR 3.4 /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g 2012-01-24 09:37:26

  package org.simplesql.parser;
  
  import org.simplesql.parser.tree.*;
  


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class SQLLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public SQLLexer() {} 
    public SQLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SQLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g"; }

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:14:7: ( '(' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:14:9: '('
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
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:15:7: ( ')' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:15:9: ')'
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
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:16:7: ( '*' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:16:9: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:17:7: ( '+' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:17:9: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:18:7: ( ',' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:18:9: ','
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
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:19:7: ( '-' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:19:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:20:7: ( '/' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:20:9: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:21:7: ( ';' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:21:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:22:7: ( 'mod' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:22:9: 'mod'
            {
            match("mod"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "RELATION"
    public final void mRELATION() throws RecognitionException {
        try {
            int _type = RELATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:10: ( ( '<' | '>' | '<=' | '>=' | '!=' | '=' ) )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:12: ( '<' | '>' | '<=' | '>=' | '!=' | '=' )
            {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:12: ( '<' | '>' | '<=' | '>=' | '!=' | '=' )
            int alt1=6;
            switch ( input.LA(1) ) {
            case '<':
                {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='=') ) {
                    alt1=3;
                }
                else {
                    alt1=1;
                }
                }
                break;
            case '>':
                {
                int LA1_2 = input.LA(2);

                if ( (LA1_2=='=') ) {
                    alt1=4;
                }
                else {
                    alt1=2;
                }
                }
                break;
            case '!':
                {
                alt1=5;
                }
                break;
            case '=':
                {
                alt1=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;

            }

            switch (alt1) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:13: '<'
                    {
                    match('<'); 

                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:19: '>'
                    {
                    match('>'); 

                    }
                    break;
                case 3 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:25: '<='
                    {
                    match("<="); 



                    }
                    break;
                case 4 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:32: '>='
                    {
                    match(">="); 



                    }
                    break;
                case 5 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:39: '!='
                    {
                    match("!="); 



                    }
                    break;
                case 6 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:128:46: '='
                    {
                    match('='); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "RELATION"

    // $ANTLR start "LOGICAL"
    public final void mLOGICAL() throws RecognitionException {
        try {
            int _type = LOGICAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:9: ( ( '&&' | '||' | ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) ) | ( 'O' | 'o' ) ( 'R' | 'r' ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='&'||LA3_0=='A'||LA3_0=='a'||LA3_0=='|') ) {
                alt3=1;
            }
            else if ( (LA3_0=='O'||LA3_0=='o') ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:11: ( '&&' | '||' | ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
                    {
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:11: ( '&&' | '||' | ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' ) )
                    int alt2=3;
                    switch ( input.LA(1) ) {
                    case '&':
                        {
                        alt2=1;
                        }
                        break;
                    case '|':
                        {
                        alt2=2;
                        }
                        break;
                    case 'A':
                    case 'a':
                        {
                        alt2=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);

                        throw nvae;

                    }

                    switch (alt2) {
                        case 1 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:12: '&&'
                            {
                            match("&&"); 



                            }
                            break;
                        case 2 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:19: '||'
                            {
                            match("||"); 



                            }
                            break;
                        case 3 :
                            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:26: ( 'A' | 'a' ) ( 'N' | 'n' ) ( 'D' | 'd' )
                            {
                            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:129:57: ( 'O' | 'o' ) ( 'R' | 'r' )
                    {
                    if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


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
    // $ANTLR end "LOGICAL"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:131:8: ( 'SELECT' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:131:10: 'SELECT'
            {
            match("SELECT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:132:7: ( 'WHERE' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:132:9: 'WHERE'
            {
            match("WHERE"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:133:6: ( 'ORDER' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:133:8: 'ORDER'
            {
            match("ORDER"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ORDER"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:134:6: ( 'FROM' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:134:8: 'FROM'
            {
            match("FROM"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "GROUP"
    public final void mGROUP() throws RecognitionException {
        try {
            int _type = GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:135:7: ( 'GROUP' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:135:9: 'GROUP'
            {
            match("GROUP"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "GROUP"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:136:4: ( 'BY' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:136:6: 'BY'
            {
            match("BY"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "LIMIT"
    public final void mLIMIT() throws RecognitionException {
        try {
            int _type = LIMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:137:7: ( 'LIMIT' )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:137:9: 'LIMIT'
            {
            match("LIMIT"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "LIMIT"

    // $ANTLR start "DOUBLE"
    public final void mDOUBLE() throws RecognitionException {
        try {
            int _type = DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:139:8: ( INTEGER '.' INTEGER )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:139:10: INTEGER '.' INTEGER
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
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:140:9: ( ( '0' .. '9' )+ )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:140:11: ( '0' .. '9' )+
            {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:140:11: ( '0' .. '9' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:
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
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
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
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:142:16: ( '\"' (~ ( '\"' | '\\n' | '\\r' ) )* '\"' | '\\'' (~ ( '\\'' | '\\n' | '\\r' ) )* '\\'' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\"') ) {
                alt7=1;
            }
            else if ( (LA7_0=='\'') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }
            switch (alt7) {
                case 1 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:143:3: '\"' (~ ( '\"' | '\\n' | '\\r' ) )* '\"'
                    {
                    match('\"'); 

                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:143:7: (~ ( '\"' | '\\n' | '\\r' ) )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0 >= '\u0000' && LA5_0 <= '\t')||(LA5_0 >= '\u000B' && LA5_0 <= '\f')||(LA5_0 >= '\u000E' && LA5_0 <= '!')||(LA5_0 >= '#' && LA5_0 <= '\uFFFF')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:
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
                    	    break loop5;
                        }
                    } while (true);


                    match('\"'); 

                    }
                    break;
                case 2 :
                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:143:33: '\\'' (~ ( '\\'' | '\\n' | '\\r' ) )* '\\''
                    {
                    match('\''); 

                    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:143:38: (~ ( '\\'' | '\\n' | '\\r' ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\t')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '&')||(LA6_0 >= '(' && LA6_0 <= '\uFFFF')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:
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
                    	    break loop6;
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
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:145:7: ( ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | INTEGER )* )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:145:9: ( 'a' .. 'z' | 'A' .. 'Z' ) ( 'a' .. 'z' | 'A' .. 'Z' | INTEGER )*
            {
            if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:145:30: ( 'a' .. 'z' | 'A' .. 'Z' | INTEGER )*
            loop8:
            do {
                int alt8=4;
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
                    alt8=1;
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
                    alt8=2;
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
                    alt8=3;
                    }
                    break;

                }

                switch (alt8) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:145:31: 'a' .. 'z'
            	    {
            	    matchRange('a','z'); 

            	    }
            	    break;
            	case 2 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:145:42: 'A' .. 'Z'
            	    {
            	    matchRange('A','Z'); 

            	    }
            	    break;
            	case 3 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:145:53: INTEGER
            	    {
            	    mINTEGER(); 


            	    }
            	    break;

            	default :
            	    break loop8;
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
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:147:4: ( ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+ )
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:147:6: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            {
            // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:147:6: ( ' ' | '\\t' | '\\n' | '\\r' | '\\f' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0 >= '\t' && LA9_0 <= '\n')||(LA9_0 >= '\f' && LA9_0 <= '\r')||LA9_0==' ') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:
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
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
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
        // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:8: ( T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | RELATION | LOGICAL | SELECT | WHERE | ORDER | FROM | GROUP | BY | LIMIT | DOUBLE | INTEGER | STRING_LITERAL | IDENT | WS )
        int alt10=23;
        alt10 = dfa10.predict(input);
        switch (alt10) {
            case 1 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:10: T__31
                {
                mT__31(); 


                }
                break;
            case 2 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:16: T__32
                {
                mT__32(); 


                }
                break;
            case 3 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:22: T__33
                {
                mT__33(); 


                }
                break;
            case 4 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:28: T__34
                {
                mT__34(); 


                }
                break;
            case 5 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:34: T__35
                {
                mT__35(); 


                }
                break;
            case 6 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:40: T__36
                {
                mT__36(); 


                }
                break;
            case 7 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:46: T__37
                {
                mT__37(); 


                }
                break;
            case 8 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:52: T__38
                {
                mT__38(); 


                }
                break;
            case 9 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:58: T__39
                {
                mT__39(); 


                }
                break;
            case 10 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:64: RELATION
                {
                mRELATION(); 


                }
                break;
            case 11 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:73: LOGICAL
                {
                mLOGICAL(); 


                }
                break;
            case 12 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:81: SELECT
                {
                mSELECT(); 


                }
                break;
            case 13 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:88: WHERE
                {
                mWHERE(); 


                }
                break;
            case 14 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:94: ORDER
                {
                mORDER(); 


                }
                break;
            case 15 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:100: FROM
                {
                mFROM(); 


                }
                break;
            case 16 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:105: GROUP
                {
                mGROUP(); 


                }
                break;
            case 17 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:111: BY
                {
                mBY(); 


                }
                break;
            case 18 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:114: LIMIT
                {
                mLIMIT(); 


                }
                break;
            case 19 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:120: DOUBLE
                {
                mDOUBLE(); 


                }
                break;
            case 20 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:127: INTEGER
                {
                mINTEGER(); 


                }
                break;
            case 21 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:135: STRING_LITERAL
                {
                mSTRING_LITERAL(); 


                }
                break;
            case 22 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:150: IDENT
                {
                mIDENT(); 


                }
                break;
            case 23 :
                // /home/gvanvuuren/checkouts/SimpleSQL/parser/src/main/antlr3/org/simplesql/parser/SQL.g:1:156: WS
                {
                mWS(); 


                }
                break;

        }

    }


    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA10_eotS =
        "\11\uffff\1\27\2\uffff\11\27\1\45\3\uffff\3\27\2\13\2\27\1\13\2"+
        "\27\1\57\1\27\2\uffff\1\61\2\13\5\27\1\uffff\1\27\1\uffff\3\27\1"+
        "\73\2\27\1\76\1\27\1\100\1\uffff\1\101\1\102\1\uffff\1\103\4\uffff";
    static final String DFA10_eofS =
        "\104\uffff";
    static final String DFA10_minS =
        "\1\11\10\uffff\1\157\2\uffff\1\116\1\122\1\105\1\110\3\122\1\131"+
        "\1\111\1\56\3\uffff\1\144\2\104\2\60\1\114\1\105\1\60\2\117\1\60"+
        "\1\115\2\uffff\3\60\2\105\1\122\1\115\1\125\1\uffff\1\111\1\uffff"+
        "\1\122\1\103\1\105\1\60\1\120\1\124\1\60\1\124\1\60\1\uffff\2\60"+
        "\1\uffff\1\60\4\uffff";
    static final String DFA10_maxS =
        "\1\174\10\uffff\1\157\2\uffff\1\156\1\162\1\105\1\110\1\162\2\122"+
        "\1\131\1\111\1\71\3\uffff\3\144\2\172\1\114\1\105\1\172\2\117\1"+
        "\172\1\115\2\uffff\3\172\2\105\1\122\1\115\1\125\1\uffff\1\111\1"+
        "\uffff\1\122\1\103\1\105\1\172\1\120\1\124\1\172\1\124\1\172\1\uffff"+
        "\2\172\1\uffff\1\172\4\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\uffff\1\12\1\13\12\uffff"+
        "\1\25\1\26\1\27\14\uffff\1\24\1\23\10\uffff\1\21\1\uffff\1\11\11"+
        "\uffff\1\17\2\uffff\1\16\1\uffff\1\15\1\20\1\22\1\14";
    static final String DFA10_specialS =
        "\104\uffff}>";
    static final String[] DFA10_transitionS = {
            "\2\30\1\uffff\2\30\22\uffff\1\30\1\12\1\26\3\uffff\1\13\1\26"+
            "\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\7\12\25\1\uffff\1\10\3\12"+
            "\2\uffff\1\14\1\23\3\27\1\21\1\22\4\27\1\24\2\27\1\15\3\27\1"+
            "\16\3\27\1\17\3\27\6\uffff\1\14\13\27\1\11\1\27\1\20\13\27\1"+
            "\uffff\1\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\31",
            "",
            "",
            "\1\33\37\uffff\1\32",
            "\1\34\37\uffff\1\35",
            "\1\36",
            "\1\37",
            "\1\40\37\uffff\1\35",
            "\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\1\46\1\uffff\12\25",
            "",
            "",
            "",
            "\1\47",
            "\1\51\37\uffff\1\50",
            "\1\51\37\uffff\1\50",
            "\12\27\7\uffff\3\27\1\52\26\27\6\uffff\32\27",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\1\53",
            "\1\54",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\1\55",
            "\1\56",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\1\60",
            "",
            "",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "",
            "\1\67",
            "",
            "\1\70",
            "\1\71",
            "\1\72",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\1\74",
            "\1\75",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\1\77",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\6\uffff\32\27",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | RELATION | LOGICAL | SELECT | WHERE | ORDER | FROM | GROUP | BY | LIMIT | DOUBLE | INTEGER | STRING_LITERAL | IDENT | WS );";
        }
    }
 

}