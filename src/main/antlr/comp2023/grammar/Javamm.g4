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
    : importDeclaration* classDeclaration EOF
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
    : ('public')? type name=ID '(' args? ')' '{' ( varDeclaration)* (statement)* returnExpression '}' #MethodDecl
    | ('public')? 'static' 'void' 'main' '(' argName=ID '[' ']' ID ')' '{' (varDeclaration)* (statement)* '}' #MainMethodDecl
    ;

args
    : argument (',' argument)*
    ;

argument
    : type argName=ID
    ;

type
    : rawType='int' '[' ']' #IntArrayType
    | rawType='boolean' #BoolType
    | rawType='int' #IntType
    | rawType=ID #IdType
    ;

statement
    : '{' ( statement )* '}' #BlockStmt
    | 'if' '(' expression ')' statement 'else' statement #IfElseStmt
    | 'while' '(' expression ')' statement #WhileStmt
    | expression ';' #ExprStmt
    | varName=ID '=' expression ';' #AssignStmt
    | varName=ID '[' expression ']' '=' expression ';' #ArrayAssignStmt
    ;

returnExpression
    : 'return' expression ';'
    ;

expression
    : '(' expression ')' #ParenExpr
    | expression '[' expression ']' #ArrayAccessExpr
    | '!' expression #NotExpr
    | expression op=( '++' | '--' ) #PostIncrDecrExpr
    | expression op=( '*' | '/' ) expression #MultDivExpr
    | expression op=( '+' | '-' ) expression #AddSubExpr
    | expression op=( '>' | '<' ) expression #RelExpr
    | expression op=('&&' | '||') expression #AndOrExpr
    | expression op=('+=' | '*=' | '/=' | '-=') expression #AssignmentExpr
    | expression '.' 'length' #ArrayLengthExpr
    | expression '.' method=ID '(' ( expression ( ',' expression )* )? ')' #MethodCallExpr
    | 'new' 'int' '[' expression ']' #NewIntArrayExpr
    | 'new' object=ID '(' ')' #NewObjectExpr
    | bool=( 'true' | 'false' ) #BoolExpr
    | value=ID #IdExpr
    | value=INTEGER #IntExpr
    | 'this' #ThisExpr
    ;
