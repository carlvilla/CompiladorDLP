/* No es necesario modificar esta secci�n ------------------ */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* Precedencias aqu� --------------------------------------- */
%left '+' '-'
%left '*' '/'
%left ','
%right '<' '>' '&' '|' '='
%left '!'


%%

/* A�adir las reglas en esta secci�n ----------------------- */

programa: elementos
		;

elementos: elemento
		| elementos elemento
		;

elemento: funcion
		| struct
		| atributo
		;
			
funcion: 'IDENT' '(' parametros ')' ':' tipo '{' sentencias '}'
		| 'IDENT' '(' parametros ')' '{' sentencias '}'
		;

parametros: parametro
			|
			;
	
parametro: 'IDENT' ':' tipo
		 | parametro ',' parametro

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
		| 'IDENT' accesos
		| invocacionMetodo
		;
		
accesos: acceso
		| accesos acceso
		;
		
acceso:  '.' 'IDENT'
		| '[' expresion ']'

invocacionMetodo: 'IDENT' '(' valores ')'


valores: valor
			|
			;
	
valor: expresion
	 | valor ',' valor

				

%%
/* No es necesario modificar esta secci�n ------------------ */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

// M�todos de acceso para el main -------------
public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

// Funciones requeridas por Yacc --------------
void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sint�ctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
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
