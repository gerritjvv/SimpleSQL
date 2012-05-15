grammar Projection;

options {
  language = Java;
  k = 2;
  output=AST;
}

tokens {
 
  
}
@header {
  package org.simplesql.om.projection;
  
  import org.simplesql.om.*;
  
}

@lexer::header {
  package org.simplesql.om.projection;
  import org.simplesql.om.*;
}

@members {
  int orderCounter = 0;
} 

projection returns [org.simplesql.om.ClientInfoTemplate.Projection.Builder builder = org.simplesql.om.ClientInfoTemplate.Projection.newBuilder()]
: 'PROJECTION'|'TABLE' IDENT {$builder.setName($IDENT.text);} 
          '(' c=column {$builder.addColumn(c.col.build());}
               (',' c=column {$builder.addColumn(c.col.build());})*
           (
            'hitType' '=' ('DAILY' {$builder.setHitType(org.simplesql.om.ClientInfoTemplate.Projection.HIT_TYPE.DAILY);} 
                          |'HOURLY'{$builder.setHitType(org.simplesql.om.ClientInfoTemplate.Projection.HIT_TYPE.HOURLY);})
            )* ')';
          
column returns [org.simplesql.om.ClientInfoTemplate.Column.Builder col = org.simplesql.om.ClientInfoTemplate.Column.newBuilder()] 
 : IDENT {$col.setName($IDENT.text); $col.setOrder(orderCounter++);} 
        (t=type  {$col.setType($t.text);} 
          | 
         t=type width=INTEGER {$col.setType($t.text); $col.setWidth(Integer.parseInt($width.text));}
          | 
         t=type {$col.setType($t.text);} 'KEY' {$col.setKey(true);}
         |
         t=type width=INTEGER {$col.setType($t.text); $col.setWidth(Integer.parseInt($width.text));} 'KEY' {$col.setKey(true);}
        );

type : ('INT'|'STRING'|'DOUBLE'|'LONG'|'BOOLEAN'|'FLOAT'|'SHORT'|'BYTE');  

DOUBLE : INTEGER '.' INTEGER;
INTEGER : '0'..'9'+;

STRING_LITERAL :
  '"' (~('"'|'\n'|'\r'))* '"' | '\'' (~('\''|'\n'|'\r'))* '\'';
  
IDENT : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | INTEGER)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
