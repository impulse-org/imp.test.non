%options package=org.eclipse.imp.test.non.parser
%options template=dtParserTemplateF.gi
%options import_terminals=NonLexer.gi
%options parent_saved,automatic_ast=toplevel,visitor=preorder,ast_directory=./Ast,ast_type=ASTNode

%Globals
    /.import org.eclipse.imp.parser.IParser;
    import java.util.Hashtable;
    import java.util.Stack;
    ./
%End

%Define
    $ast_class /.Object./
    $additional_interfaces /., IParser./
%End

%Terminals
    non
    IDENTIFIER 
    SEMICOLON ::= ';'
    LEFTBRACE ::= '{'
    RIGHTBRACE ::= '}'
%End

%Start
    compilationUnit
%End

%Rules
    compilationUnit ::= statementList

    statementList$$statement ::= %empty
                             | statementList statement

    statement ::= nonStmt
                | block

    nonStmt ::= non

    block ::= '{'$ statementList '}'$
%End
