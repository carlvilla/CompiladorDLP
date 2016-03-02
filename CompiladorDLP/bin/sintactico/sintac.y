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
%left ',' '<' '>' '[' '.'
%left 'MENORIGUAL' 'MAYORIGUAL' 'IGUAL' 'DISTINTO' 'AND' 'OR'
%left '!'

%%

/* Añadir las reglas en esta sección ----------------------- */

programa: elementos {raiz = new Programa($1);}
		;

elementos: elemento {List<Elemento> lista = new ArrayList<Elemento>();lista.add((Elemento)$1);$$ = lista;}
		| elementos elemento {$$ = $1; ((List)$1).add($2);}
		;

elemento: funcion {$$ = $1;}
		| struct {$$ = $1;}
		| atributo {$$ = $1;}
		;
			
funcion: 'IDENT' '(' parametrosOpt ')' ':' tipo '{' sentencias '}' {$$ = new Funcion($1,$2,$3,$4);}
		| 'IDENT' '(' parametrosOpt ')' '{' sentencias '}' {$$ = new Funcion($1,$2,null,$4);}
		;

parametrosOpt: parametros {$$ = $1;}
			|
			;
	
parametros: 'IDENT' ':' tipo {$$ = new Parametro($1,$3);}
		 | parametros ',' 'IDENT' ':' tipo {$$ = $1; ((List<Parametro>)$1).add(new Parametro($3,$5));}

tipo: 'IDENT' {$$ = new Tipoident();}
	|'INT'	{$$ = new Tipoint();}
	| 'REAL'	{$$ = new Tiporeal();}
	| 'CHAR'	{$$ = new Tipochar();}
	| '[' 'LITERALINT' ']' tipo	{$$ = new Array($2,$4);}
	;
	 
struct: 'STRUCT' 'IDENT' '{' definiciones '}' ';' {$$=new Struct($2,$4);}
	  ;

atributo: 'VAR' definicion {$$=new Atributo($2);}
			;

definiciones: definicion {$$ = $1;}
			| definiciones  definicion
			;
			
definicion: 'IDENT' ':' tipo ';' {$$ = new Definicion($1,$3);}
			;
			
sentencias: sentencias sentencia {$$ = $1; ((List)$1).add($2);}
			|					{$$ = new ArrayList();}
			;
			
sentencia: 'VAR' definicion  {$$ = $2;}
		| 'READ' expresion ';' {$$ = new Read($2);}
		| 'PRINT' expresion ';' {$$ = new Print($2);}
		| 'WHILE' '(' expresion ')' '{' sentencias '}' {$$ = new While($3,$6);}
		| 'IF' '(' expresion ')' '{' sentencias '}' {$$ = new If($3,$6,null);}
		| 'IF' '(' expresion ')' '{' sentencias '}' 'ELSE' '{' sentencias '}' {$$ = new If($3,$6,$10);}
		|  expresion '=' expresion ';' {$$ = new ExpresionBinaria($1,"=",$3);}
		| 'RETURN' expresion ';' {$$ = new Return($2);}
		| 'RETURN' ';' {$$ = new Return(null);}
		| invocacionMetodo ';' {$$ = $1;}
		;
		
expresion: 'LITERALINT' {$$ = new Litent($1);}
		| 'LITERALREAL' {$$ = new Litreal($1);}
		| 'LITERALCHAR' {$$ = new Litchar($1);}
		| 'IDENT' {$$ = new Var($1);}
		| expresion '+' expresion {$$ = new ExpresionBinaria($1,"+",$3);}
		| expresion '-' expresion {$$ = new ExpresionBinaria($1,"-",$3);}
		| expresion '/' expresion {$$ = new ExpresionBinaria($1,"/",$3);}
		| expresion '*' expresion {$$ = new ExpresionBinaria($1,"*",$3);}
		| expresion '<' expresion {$$ = new ExpresionBinaria($1,"<",$3);}
		| expresion '>' expresion {$$ = new ExpresionBinaria($1,">",$3);}
		| expresion 'MENORIGUAL' expresion {$$ = new ExpresionBinaria($1,"<=",$3);}
		| expresion 'MAYORIGUAL' expresion {$$ = new ExpresionBinaria($1,">=",$3);}
		| expresion 'IGUAL' expresion {$$ = new ExpresionBinaria($1,"==",$3);}
		| expresion 'DISTINTO' expresion {$$ = new ExpresionBinaria($1,"!=",$3);}
		| expresion 'AND' expresion {$$ = new ExpresionBinaria($1,"&&",$3);}
		| expresion 'OR' expresion {$$ = new ExpresionBinaria($1,"||",$3);}
		| '!' expresion {$$ = new ExpresionUnaria($2);}
		| 'CAST' '<' tipo '>' '(' expresion ')' {$$=new Cast($3,$6);}
		| '(' expresion ')' {$$ = $2;}
		| expresion '[' expresion ']' {$$ = new AccesoArray($1,$3);}
		| expresion '.' 'IDENT' {$$ = new AccesoStruct($1,$3);}
		| invocacionMetodo {$$ = $1;}
		;
		
invocacionMetodo: 'IDENT' '(' valoresOpt ')' {$$ = new Invocar($1,$3);}


valoresOpt: valores {$$ = $1;}
		|		{$$ = new ArrayList();}
		;
	
valores: expresion {$$ = $1;}
	 | valores ',' expresion {$$ = $1;((List<Expresion>)$1).add((Expresion)$3);}

				

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
