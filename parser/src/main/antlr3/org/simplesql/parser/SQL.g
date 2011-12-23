grammar SQL;

options {
  language = Java;
  k = 2;
  output = AST;
}

tokens {
  FUNC;
  EXPRESSION;
  STRING;
  
  NUMBER;
  REAL_NUMBER;
  VARIABLE;
  RELATION_TOKEN;
  LOGICAL_TOKEN;
  MULT_TOKEN;
  NEGATION_TOKEN;
  UNARY;
  PLUS;
  MINUS;
  
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


//negation : (t1+='NOT' | t1+='!')* (v1=funct|v2=term) 
//      -> ^(NEGATION_TOKEN $t1* $v1* $v2*);
unary 
 : 
   (u1+='+' | u2+='-')*  (v1=funct|v2=term)
   -> ^(UNARY $u1* $u2* $v1* $v2*)
   ;
   
mult
   : u1=unary ((t+='*' | t+='/' | t+='mod') u2+=unary)*
   -> ^(MULT_TOKEN $u1 ($t $u2)* )
   ;

expression
   : m1=mult ((ad='+' | min='-') m2+=mult)*
   -> ^(EXPRESSION $m1* ($ad* $min* $m2)* )
   ;
   
relation : e1=expression RELATION e2=expression
      -> ^(RELATION_TOKEN RELATION $e1 $e2);
      
logical : r1=relation (lotoken+=LOGICAL r2+=relation)*
      -> ^(LOGICAL_TOKEN $r1 ($lotoken $r2)*);
      
      

funct : IDENT '(' fe1=expression (',' fe2+=expression)* ')'
    -> ^(FUNC IDENT $fe1* $fe2*);

  
term : 
      ( v=IDENT   -> ^(VARIABLE $v)   
      | v=INTEGER -> ^(NUMBER $v)
      | v=DOUBLE -> ^(REAL_NUMBER $v)  
      | '(' r=expression ')' -> ^(EXPRESSION $r) 
          
      )
      | v=STRING_LITERAL -> ^(STRING $v) ;

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

STRING_LITERAL :
  '"' (~('"'|'\n'|'\r'))* '"' | '\'' (~('\''|'\n'|'\r'))* '\'';
  
IDENT : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | INTEGER)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
