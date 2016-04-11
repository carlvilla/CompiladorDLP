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

elementos:   {$$ = new ArrayList<Elemento>();}
		| elementos elemento {$$ = $1; ((List)$1).add($2);}
		;

elemento: funcion {$$ = $1;}
		| struct {$$ = $1;}
		| atributo {$$ = $1;}
		;
			
funcion: 'IDENT' '(' parametrosOpt ')' ':' tipo '{'  atributos sentencias '}' {$$ = new Funcion($1,$3,$6,$8,$9);}
		| 'IDENT' '(' parametrosOpt ')' '{' atributos sentencias '}' {$$ = new Funcion($1,$3,null,$6,$7);}
		;
		
atributos:	{$$ = new ArrayList<Atributo>();}
		| atributos atributo {$$ = $1; ((List)$1).add($2);}
		;

parametrosOpt: parametros {$$ = $1;}
			|  {$$=null;}
			;
	
parametros: 'IDENT' ':' tipo {List<Parametro> lista = new ArrayList<Parametro>();lista.add(new Parametro($1,$3));$$ = lista;}
		 | parametros ',' 'IDENT' ':' tipo {$$ = $1; ((List<Parametro>)$1).add(new Parametro($3,$5));}

tipo: 'IDENT' {$$ = new Tipoident($1);}
	|'INT'	{$$ = new Tipoint();}
	| 'REAL'	{$$ = new Tiporeal();}
	| 'CHAR'	{$$ = new Tipochar();}
	| 'VOID'	{$$ = new Tipovoid();}
	| '[' 'LITERALINT' ']' tipo	{$$ = new Array(new Litent((Integer.valueOf(((Token)$2).getLexeme()))),$4);}
	;
	 
struct: 'STRUCT' 'IDENT' '{' definiciones '}' ';' {$$=new Struct($2,$4);}
	  ;

atributo: 'VAR' definicion {$$=new Atributo($2);}
			;

definiciones:  {$$ = new ArrayList();}
			| definiciones  definicion {$$ = $1; ((List)$1).add($2);}
			;
			
definicion: 'IDENT' ':' tipo ';' {$$ = new Definicion($1,$3);}
			;
			
sentencias: sentencias sentencia {$$ = $1; ((List)$1).add($2);}
			|					{$$ = new ArrayList<Sentencia>();}
			;
			
sentencia:'READ' expresion ';' {$$ = new Read($2);}
		| 'PRINT' expresion ';' {$$ = new Print($2);}
		| 'WHILE' '(' expresion ')' '{' sentencias '}' {$$ = new While($3,$6);}
		| 'IF' '(' expresion ')' '{' sentencias '}' {$$ = new If($3,$6,null);}
		| 'IF' '(' expresion ')' '{' sentencias '}' 'ELSE' '{' sentencias '}' {$$ = new If($3,$6,$10);}
		|  expresion '=' expresion ';' {$$ = new ExpresionBinaria($1,"=",$3);}
		| 'RETURN' expresion ';' {$$ = new Return($2);}
		| 'RETURN' ';' {$$ = new Return(null);}
		| 'IDENT' '(' valoresOpt ')' ';' {$$ = new InvocarSentencia($1,$3);}
		;
		
expresion:'LITERALINT' {$$ = new Litent($1);} 
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
		| expresion 'AND' expresion {$$ = new ExpresionLogica($1,"&&",$3);}
		| expresion 'OR' expresion {$$ = new ExpresionLogica($1,"||",$3);}
		| '!' expresion {$$ = new ExpresionUnaria($2);}
		| 'CAST' '<' tipo '>' '(' expresion ')' {$$=new Cast($3,$6);}
		| '(' expresion ')' {$$ = new EntreParentesis($2);}
		| expresion '[' expresion ']' {$$ = new AccesoArray($1,$3);}
		| expresion '.' 'IDENT' {$$ = new AccesoStruct($1,$3);}
		| 'IDENT' '(' valoresOpt ')' {$$ = new InvocarFuncion($1,$3);}
		;
		
valoresOpt: valores {$$ = $1;}
		|		{$$ = new ArrayList();}
		;
	
valores: expresion {List<Expresion> lista = new ArrayList<Expresion>();lista.add((Expresion)$1);$$ = lista;}
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
