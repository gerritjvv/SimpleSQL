grammar SQL;

options {
  language = Java;
  k = 2;
  output = AST;
}

tokens {
  FUNC;
  EXPRESSION;
}
@header {
  package org.simplesql.parser;
}

@lexer::header {
  package org.simplesql.parser;
}


statement : SELECT (se1+=expression) (',' (se1+=expression))* FROM IDENT 
            (WHERE w1+=logical )*
            ('GROUP' 'BY' (gpe1+=expression) (',' (gpe1+=expression))*)*
            ('ORDER' 'BY' (ope1+=expression) (',' (ope1+=expression))*)*
            ('LIMIT' l=INTEGER)*
            ';'*
            -> ^(SELECT $se1*) 
               ^(WHERE $w1*)?
               ^(GROUP $gpe1*)?
               ^(ORDER $ope1*)?
               ^(LIMIT $l)?;


negation : ('not' | '!')* (funct|term);
unary 
 : 
   ('+' | '-')* negation
   ;
   
mult
   : unary (('*' | '/' | 'mod') unary)*
   ;

expression
   : m1=mult ((ad='+' | min='-') m2+=mult)*
   -> ^(EXPRESSION $m1 $ad* $min* $m2* )
   ;
   
relation : expression RELATION expression;
logical : relation (LOGICAL relation)*;

funct : IDENT '(' expression (',' expression)* ')';

string 
  : ('\'' (IDENT|INTEGER|'-'|'+'|'/'|'_'|'*') '\'' | '\"' (IDENT|INTEGER|'-'|'+'|'/'|'_'|'*') '\"')
  ;
   
  
term : 
      (IDENT
      | INTEGER 
      | DOUBLE  
      | '(' expression ')'
          
      )
      | string ;

RELATION : ('<' | '>' | '<=' | '>=' | '!=' | '=');
LOGICAL : ('&&' | '||' | ('A'|'a')('N'|'n')('D'|'d')) | ('O'|'o')('R'|'r');

SELECT : 'SELECT';
WHERE : 'WHERE';
ORDER: 'ORDER';
FROM : 'FROM';
GROUP : 'GROUP';
BY : 'BY';
LIMIT : 'LIMIT';

DOUBLE : INTEGER '.' INTEGER;
INTEGER : '0'..'9'+;


IDENT : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | INTEGER)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
