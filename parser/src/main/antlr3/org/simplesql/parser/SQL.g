grammar SQL;

options {
  language = Java;
  k = 2;
  output=AST;
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
  
  import org.simplesql.parser.tree.*;
  
}

@lexer::header {
  package org.simplesql.parser;
  
  import org.simplesql.parser.tree.*;
  
}

@members {
  
  public SELECT select;
  public static final java.util.Set<String> variables = new java.util.HashSet<String>();
}

statement returns [SELECT ret = new SELECT(variables);]
           : SELECT (se1=expression { select = $ret;  $ret.select($se1.expr);}) 
                    (',' (se1=expression {$ret.select($se1.expr);}))* FROM IDENT {$ret.table($IDENT.text);} 
            (
            (WHERE w1=logical {$ret.where($logical.ret);})
                      |
            ('GROUP' 'BY' (gpe1=expression {$ret.groupBy($gpe1.expr);}) 
                     (',' (gpe1=expression {$ret.groupBy($gpe1.expr);}))*)
                     |
            ('ORDER' 'BY' (ope1=expression {$ret.orderBy($ope1.expr);}) 
                     (',' (ope1=expression {$ret.orderBy($ope1.expr);}))*)
                     |
            ('LIMIT' l=INTEGER {$ret.limit($l.text);})
            )*
            ';'*
//            -> ^(SELECT $se1*) 
//               ^(WHERE $w1*)?
//               ^(GROUP $gpe1*)?
//               ^(ORDER $ope1*)?
//               ^(LIMIT $l)?;
;

//negation : (t1+='NOT' | t1+='!')* (v1=funct|v2=term) 
//      -> ^(NEGATION_TOKEN $t1* $v1* $v2*);
unary returns [UNARY unary = new UNARY()]
 : 
      v1=funct {$unary.term($v1.f);}
      | v2=term {$unary.term($v2.term);}
      
//   -> ^(UNARY $v1* $v2*)
   ;
   
mult returns [MULT ret = new MULT()]
   :
    u1=unary {$ret.unary($u1.unary);}
    ((t='*' {$ret.mult();} | t='/' {$ret.divide();}
    | t='mod' {$ret.mod();}) 
    u2=unary {$ret.unary($u2.unary);})*
    
//   -> ^(MULT_TOKEN $u1 ($t $u2)* )
   ;

expression returns [EXPRESSION expr = new EXPRESSION()]
   : em1=mult
   {$expr.mult($em1.ret);} ((t+='-' {$expr.minus();}| t+='+' {$expr.plus();}) em2=mult {$expr.mult($em2.ret);})* 
   
//   -> ^(EXPRESSION $em1 ($t $em2)* )
   
   ;
   
relation returns [RELATION ret] 
        : 
          e1=expression RELATION e2=expression 
          {$ret = new RELATION($e1.expr, $RELATION.text, $e2.expr);}
//      -> ^(RELATION_TOKEN RELATION $e1 $e2);
  ;    
logical returns [LOGICAL ret = new LOGICAL()]
        : r1=relation {$ret.relation($r1.ret); select.rangeGroups.addRange($r1.ret.getRange());} 
          (lotoken=LOGICAL {
              $ret.logical($LOGICAL.text);
              org.simplesql.parser.tree.LOGICAL.OP lop = org.simplesql.parser.tree.LOGICAL.OP.parse($LOGICAL.text);
              if(lop == org.simplesql.parser.tree.LOGICAL.OP.JAVA_OR || lop == org.simplesql.parser.tree.LOGICAL.OP.OR)
                 select.rangeGroups.nextGroup();
              
            }  
           r2=relation {$ret.relation($r2.ret); select.rangeGroups.addRange($r2.ret.getRange());})*
//      -> ^(LOGICAL_TOKEN $r1 ($lotoken $r2)*);
 ;     
      

funct returns [FUNCTION f = new FUNCTION()] 
     : IDENT {$f.name($IDENT.text);}'(' fe1=expression {$f.expression($fe1.expr);} (',' fe2=expression {$f.expression($fe2.expr);})* ')'
//    -> ^(FUNC IDENT $fe1* $fe2*)
;

  
term returns [TERM term]: 
      ( v=IDENT  {variables.add($v.text.trim());}   
          -> ^(VARIABLE $v) {$term = new VARIABLE($v.text)}  
      | v=INTEGER -> ^(NUMBER $v) { $term = new INTEGER($v.text)}
      | v=BOOL    -> ^(BOOL $v) { $term = new BOOLEAN($v.text) }
      | v=DOUBLE -> ^(REAL_NUMBER $v) {$term = new DOUBLE($v.text)} 
      | '(' r=expression ')' -> ^(EXPRESSION $r)
          
      )
      | v=STRING_LITERAL -> ^(STRING $v) {$term = new STRING($v.text)};


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
BOOL : ('true') | ('TRUE')  | ('false') | ('FALSE') | '0' | '1';

STRING_LITERAL :
  '"' (~('"'|'\n'|'\r'))* '"' | '\'' (~('\''|'\n'|'\r'))* '\'';
  
IDENT : ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '_' | '-' | '$' | INTEGER)*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
