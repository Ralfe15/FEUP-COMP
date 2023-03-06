grammar Javamm;

@header {
    package pt.up.fe.comp2023;
}

INTEGER :([0]|[1-9][0-9]*);
ID : [a-zA-Z_$][a-zA-Z_0-9$]* ;

WS : [ \t\n\r\f]+ -> skip ;

TRADICIONAL_COMMENT : '/*' .*? '*/' -> skip;
EOL_COMMENT: '//' ~[\r\n]* -> skip;
program
    : importDeclaration* methodDeclaration* classDeclaration* EOF
    ;

importDeclaration
    : 'import' ID ('.' ID)* ';'
    ;

classDeclaration
    : 'class' ID ('extends' ID)? '{' (varDeclaration)* (methodDeclaration)* '}'
    ;

varDeclaration
    : type ID ';'
    ;

methodDeclaration
    : ('public')? type ID '(' ( type ID ( ',' type ID )* )? ')' '{' ( varDeclaration)* (statement)* 'return' expression ';' '}'
    // Check if main arg is string in jmmAnalysis stage
    | ('public')? 'static' 'void' 'main' '(' ID '[' ']' ID ')' '{' (varDeclaration)* (statement)* '}'
    ;

type
    : 'int' '[' ']'
    | 'boolean'
    | 'int'
    | ID
    ;

statement
    : '{' ( statement )* '}'
    | 'if' '(' expression ')' statement 'else' statement | 'while' '(' expression ')' statement
    | expression ';'
    | ID '=' expression ';'
    | ID '[' expression ']' '=' expression ';'
    ;

expression
    : '!' expression
    | expression ( '*' | '/' ) expression
    | expression ( '+' | '-' ) expression
    | expression ( '>' | '<' ) expression
    | expression '&&' expression
    | expression '||' expression
    | expression '[' expression ']'
    | expression '.' 'length'
    | expression '.' ID '(' ( expression ( ',' expression )* )? ')' | 'new' 'int' '[' expression ']'
    | 'new' ID '(' ')'
    | '(' expression ')'
    | 'true'
    | 'false'
    | ID
    | INTEGER
    | 'this'
    ;
