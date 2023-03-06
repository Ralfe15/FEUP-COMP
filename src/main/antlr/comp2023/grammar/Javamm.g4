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
    : 'import' value+=ID ('.' value+=ID)* ';'
    ;

classDeclaration
    : 'class' name=ID ('extends' extension=ID)? '{' (varDeclaration)* (methodDeclaration)* '}'
    ;

varDeclaration
    : type varName=ID ';'
    ;

methodDeclaration
    : ('public')? returnType=type name=ID '(' args ')' '{' ( varDeclaration)* (statement)* 'return' expression ';' '}' #MethodDecl
    | ('public')? 'static' 'void' 'main' '(' ID '[' ']' ID ')' '{' (varDeclaration)* (statement)* '}' #MainMethodDecl
    ;

args
    : argums (',' argums)*
    ;

argums
    : type argName=ID
    ;

type
    : 'int' '[' ']' #IntArrayType
    | 'boolean' #BoolType
    | 'int' #IntType
    | ID #IdType
    ;

statement
    : '{' ( statement )* '}' #BlockStmt
    | 'if' '(' expression ')' statement 'else' statement #IfElseStmt
    | 'while' '(' expression ')' statement #WhileStmt
    | expression ';' #ExprStmt
    | ID '=' expression ';' #AssignStmt
    | ID '[' expression ']' '=' expression ';' #ArrayAssignStmt
    ;

expression
    : '!' expression #NotExpr
    | expression op=( '*' | '/' ) expression #MultDivExpr
    | expression op=( '+' | '-' ) expression #AddSubExpr
    | expression op=( '>' | '<' ) expression #RelExpr
    | expression op=('&&' | '||') expression #AndOrExpr
    | expression '[' expression ']' #ArrayAccessExpr
    | expression '.' 'length' #ArrayLengthExpr
    | expression '.' method=ID '(' ( expression ( ',' expression )* )? ')' #MethodCallExpr
    | 'new' 'int' '[' expression ']' #NewIntArrayExpr
    | 'new' object=ID '(' ')' #NewObjectExpr
    | '(' expression ')' #ParenExpr
    | bool=( 'true' | 'false' ) #BoolExpr
    | value=ID #IdExpr
    | value=INTEGER #IntExpr
    | 'this' #ThisExpr
    ;
