// ANTLR grammar for expressions.


// Name the grammar.
grammar Expr;


// Define grammar options.
options {
    output=AST;
    ASTLabelType=CommonTree;
}


// Identify single character tokens.
tokens {
    PLUS='+';
    MINUS='-';
    MULT='*';
    DIV='/';
    LPAREN='(';
    RPAREN=')';
}


// Define packages for parser and lexer.
@header { package lein_antlr; }
@lexer::header { package lein_antlr; }


// The grammar is fairly straightforward.
calc_expr: expr EOF!;
expr: term ((PLUS | MINUS)^ term)*;
term: factor ((MULT | DIV)^ factor)*;
factor: INT | LPAREN! expr RPAREN!;

INT: '0'..'9'+;
WS: ( ' ' | '\t' | '\r' | '\n')+ { $channel=HIDDEN; };

