/* No es necesario modificar esta sección ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aquí --------------------------------------- */
%left '+' '-'
%left '*' '/'
%left ','
%left '<' '>' '&' '|' '=' '[' '.'
%left '!'

%%

/* Añadir las reglas en esta sección ----------------------- */

programa: elementos
		;

elementos: elemento
		| elementos elemento
		;

elemento: funcion
		| struct
		| atributo
		;
			
funcion: 'IDENT' '(' parametrosOpt ')' ':' tipo '{' sentencias '}'
		| 'IDENT' '(' parametrosOpt ')' '{' sentencias '}'
		;

parametrosOpt: parametros
			|
			;
	
parametros: 'IDENT' ':' tipo
		 | parametros ',' 'IDENT' ':' tipo

tipo: 'IDENT'
	|'INT'
	| 'REAL'
	| 'CHAR'
	| '[' 'LITERALINT' ']' tipo
	;
	 
struct: 'STRUCT' 'IDENT' '{' definiciones '}' ';'
	  ;

atributo: 'VAR' definicion
			;

definiciones: definicion
			| definiciones  definicion
			;
			
definicion: 'IDENT' ':' tipo ';'
			;
			
sentencias: sentencias sentencia
			|
			;
			
sentencia: 'VAR' definicion
		| 'READ' expresion ';'
		| 'PRINT' expresion ';'
		| 'WHILE' '(' expresion ')' '{' sentencias '}'
		| 'IF' '(' expresion ')' '{' sentencias '}' 
		| 'IF' '(' expresion ')' '{' sentencias '}' 'ELSE' '{' sentencias '}' 
		|  expresion '=' expresion ';'
		| 'RETURN' expresion ';'
		| 'RETURN' ';'
		| invocacionMetodo ';'
		;
		
expresion: 'LITERALINT'
		| 'LITERALREAL'
		| 'LITERALCHAR'
		| 'IDENT'
		| expresion '+' expresion
		| expresion '-' expresion
		| expresion '/' expresion
		| expresion '*' expresion
		| expresion '<' expresion
		| expresion '>' expresion
		| expresion '<' '=' expresion
		| expresion '>' '=' expresion
		| expresion '=' '=' expresion
		| expresion '!' '=' expresion
		| expresion '&' '&' expresion
		| expresion '|' '|' expresion
		| '!' expresion
		| 'CAST' '<' tipo '>' '(' expresion ')'
		| '(' expresion ')'
		| expresion '[' expresion ']'
		| expresion '.' 'IDENT'
		| invocacionMetodo
		;
		
invocacionMetodo: 'IDENT' '(' valoresOpt ')'


valoresOpt: valores
		|
		;
	
valores: expresion
	 | valores ',' expresion

				

%%
/* No es necesario modificar esta sección ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// Métodos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
