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
    | rawType='int' #IntType
    | rawType='boolean' #BoolType
    | rawType='boolean' '[' ']' #BoolArrayType
    | rawType=ID #IdType
    | rawType=ID '[' ']' #IdArrayType
    ;

statement
    : '{' ( statement )* '}' #BlockStmt
    | 'if' '(' expression ')' statement 'else' statement #IfElseStmt
    | 'while' '(' expression ')' statement #WhileStmt
    | expression ';' #ExprStmt
    ;

returnExpression
    : 'return' expression ';'
    ;

params
    : expression ( ',' expression )*
    ;

expression
    : '(' expression ')' #ParenExpr
    | expression '[' expression ']' #ArrayAccessExpr
    | '!' expression #NotExpr
    | expression op=( '++' | '--' ) #PostIncrDecrExpr
    | expression '.' 'length' #ArrayLengthExpr
    | expression '.' method=ID '(' ( params )? ')' #MethodCallExpr
    | expression op=( '*' | '/' ) expression #MultDivExpr
    | expression op=( '+' | '-' ) expression #AddSubExpr
    | expression op=( '>' | '<' ) expression #RelExpr
    | expression op=('&&' | '||') expression #AndOrExpr
    | expression op='=' expression #AssignmentExpr
    | 'new' 'int' '[' expression ']' #NewIntArrayExpr
    | 'new' object=ID '(' ')' #NewObjectExpr
    | bool=( 'true' | 'false' ) #BoolExpr
    | value=ID ('[' expression ']')? #IdExpr
    | value=INTEGER #IntExpr
    | value='this' #ThisExpr
    ;
