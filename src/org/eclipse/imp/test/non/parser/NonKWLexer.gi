--
-- The Non Keyword Lexer
--
%options package=org.eclipse.imp.test.non.parser
%options template=KeywordTemplate.gi

%Include
    KWLexerLowerCaseMap.gi
%End

%Export
    -- List all the keywords the kwlexer will export to the lexer and parser
    non
%End

%Terminals
    a    b    c    d    e    f    g    h    i    j    k    l    m
    n    o    p    q    r    s    t    u    v    w    x    y    z
%End

%Start
    -- The Goal for the parser is a single Keyword
    Keyword
%End

%Rules
    Keyword ::= n o n          /.$setResult($_non);./
%End
