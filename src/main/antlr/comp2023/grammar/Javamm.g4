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
    : (importDeclaration)*classDeclaration EOF
    ;
importDeclaration : 'import'ID ('.'ID)*';';
classDeclaration : 'class'ID('extends'ID)?'{'( varDeclaration )*( methodDeclaration )*'}';
varDeclaration : type ID';';
methodDeclaration
    : ('public')? type ID'('(type ID(','type ID)*)?')''{'(varDeclaration)*(statement)*'return'expression';''}'
    | ('public')? 'static''void''main''(''String''['']'ID')''{'(varDeclaration)*(statement)*'}';
type
    : 'int' '['']'
    | 'boolean'
    | 'int'
    | ID;

statement
     : '{' ( statement )* '}'
     | 'if' '(' expression ')' statement 'else' statement
     | 'while' '(' expression ')' statement
     | expression ';'
     | ID '=' expression ';'
     | ID '[' expression ']' '=' expression ';'
     | ID '=' INTEGER ';'
    ;

expression
    : expression ('&&'|('<')|('+')|('-')|('*')|'/') expression
    | expression '['expression']'
    | expression '.''lenght'
    | expression '.'ID'('(expression(','expression)*)?')'
    | 'new' 'int' '[' expression ']'
    | 'new' ID '(' ')'
    | '!' expression
    | '(' expression ')'
    | INT
    | 'true'
    | 'false'
    | ID
    | 'this'
    ;
