
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	
	private void Greska(){
		if(greska != "") System.err.println("Leksicka greska (" + greska + ") u liniji " + line + " u koloni " + column);
		greska = ""; 
	}
	
	private String greska = "";
	private int line = 0;
	private int column = 0;

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ Greska();}
"\b" 	{ Greska();}
"\t" 	{ Greska();}
"\r\n" 	{ Greska();}
"\f" 	{ Greska();}

"program"   { Greska(); return new_symbol(sym.PROG, yytext());}
"break"   { Greska(); return new_symbol(sym.BREAK, yytext());}
"class"   { Greska(); return new_symbol(sym.CLASS, yytext());}
"enum"   { Greska(); return new_symbol(sym.ENUM, yytext());}
"else"   { Greska(); return new_symbol(sym.ELSE, yytext());}
"const"   { Greska(); return new_symbol(sym.CONST, yytext());}
"if"   { Greska(); return new_symbol(sym.IF, yytext());}
"do"   { Greska(); return new_symbol(sym.DO, yytext());}
"while"   { Greska(); return new_symbol(sym.WHILE, yytext());}
"new"   { Greska(); return new_symbol(sym.NEW, yytext());}
"print"   { Greska(); return new_symbol(sym.PRINT, yytext());}
"read"   { Greska(); return new_symbol(sym.READ, yytext());}
"return"   { Greska(); return new_symbol(sym.RETURN, yytext());}
"void"   { Greska(); return new_symbol(sym.VOID, yytext());}
"extends"   { Greska(); return new_symbol(sym.EXTENDS, yytext());}
"continue"   { Greska(); return new_symbol(sym.CONTINUE, yytext());}
"this" 	{ Greska(); return new_symbol(sym.THIS, yytext()); }
"super" 	{ Greska(); return new_symbol(sym.SUPER, yytext()); }
"goto" 		{ Greska(); return new_symbol(sym.GOTO, yytext()); }
"record"   { Greska(); return new_symbol(sym.RECORD, yytext());}
"+" 		{ Greska(); return new_symbol(sym.PLUS, yytext()); }
"-" 		{ Greska(); return new_symbol(sym.MINUS, yytext()); }
"*" 		{ Greska(); return new_symbol(sym.MULTIPLY, yytext()); }
"/" 		{ Greska(); return new_symbol(sym.DIVIDE, yytext()); }
"%" 		{ Greska(); return new_symbol(sym.MODULO, yytext()); }
"==" 		{ Greska(); return new_symbol(sym.IFEQUAL, yytext()); }
"!=" 		{ Greska(); return new_symbol(sym.IFNOTEQUAL, yytext()); }
">" 		{ Greska(); return new_symbol(sym.GREATER, yytext()); }
">=" 		{ Greska(); return new_symbol(sym.GREATEREQUAL, yytext()); }
"<" 		{ Greska(); return new_symbol(sym.LESS, yytext()); }
"<=" 		{ Greska(); return new_symbol(sym.LESSEQUAL, yytext()); }
"&&" 		{ Greska(); return new_symbol(sym.AND, yytext()); }
"||" 		{ Greska(); return new_symbol(sym.OR, yytext()); }
"=" 		{ Greska(); return new_symbol(sym.ASSIGN, yytext()); }
"++" 		{ Greska(); return new_symbol(sym.INCREMENT, yytext()); }
"--" 		{ Greska(); return new_symbol(sym.DECREMENT, yytext()); }
";" 		{ Greska(); return new_symbol(sym.SEMICOLON, yytext()); }
":" 		{ Greska(); return new_symbol(sym.COLON, yytext()); }
"," 		{ Greska(); return new_symbol(sym.COMMA, yytext()); }
"." 		{ Greska(); return new_symbol(sym.DOT, yytext()); }
"(" 		{ Greska(); return new_symbol(sym.LPAREN, yytext()); }
")" 		{ Greska(); return new_symbol(sym.RPAREN, yytext()); }
"[" 		{ Greska(); return new_symbol(sym.LBRACKET, yytext()); }
"]" 		{ Greska(); return new_symbol(sym.RBRACKET, yytext()); }
"{" 		{ Greska(); return new_symbol(sym.LBRACE, yytext()); }
"}"			{ Greska(); return new_symbol(sym.RBRACE, yytext()); }
"^"			{ Greska(); return new_symbol(sym.SQUARE, yytext()); }

"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

("true"|"false") { Greska(); return new_symbol(sym.BOOLCONST, new Boolean (yytext())); }
[0-9]+  { Greska(); return new_symbol(sym.NUMCONST, new Integer (yytext())); }
"'"."'" { Greska(); return new_symbol(sym.CHARCONST, new Character (yytext().charAt(1))); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ Greska(); return new_symbol (sym.IDENT, yytext()); }




. { if(greska == "") line = yyline + 1; if(greska == "") column = yycolumn; greska += yytext(); }










