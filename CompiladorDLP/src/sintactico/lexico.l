/* -- No es necesario modificar esta parte ----------------------------------------------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;

%%
%byaccj
%unicode
%line
%column
%public

%{
	public Yylex(Reader in, GestorErrores gestor) {
		this(in);
		this.gestor = gestor;
	}

	public int line() { return yyline + 1; }
	public int column() { return yycolumn + 1; }
	public String lexeme() { return yytext(); }

	// Traza para probar el lÈxico de manera independiente al sint·ctico
	public static void main(String[] args) throws Exception {
		Yylex lex = new Yylex(new FileReader(Main.programa), new GestorErrores());
		int token;
		while ((token = lex.yylex()) != 0)
			System.out.println("\t[" + lex.line() + ":" + lex.column() + "] Token: " + token + ". Lexema: " + lex.lexeme());
	}

	private GestorErrores gestor;
%}

%%

/* -- Modificar aquÌ. AÒadir reglas en esta secciÛn ----------------------------------------- */


[+\-*/;\()=&|!\-:{}\[\].,<>]	{ return yytext().charAt(0); }


print	{ return Parser.PRINT; }
read	{ return Parser.READ; }
int		{ return Parser.INT; }
void	{ return Parser.VOID; }
float	{ return Parser.REAL; }
char	{ return Parser.CHAR; }
return	{ return Parser.RETURN; }
struct	{ return Parser.STRUCT; }
while	{ return Parser.WHILE; }
var		{ return Parser.VAR; }
cast	{ return Parser.CAST; }
if		{ return Parser.IF;}
else	{ return Parser.ELSE;} 

"<=" 								{return Parser.MENORIGUAL;}
">=" 								{return Parser.MAYORIGUAL;}
"==" 								{return Parser.IGUAL;}
"!=" 								{return Parser.DISTINTO;}
"&&" 								{return Parser.AND;}
"||" 								{return Parser.OR;}

'[a-zA-Z0-9_Ò—]' | '\\n' 	{return Parser.LITERALCHAR;}
[a-zA-ZÒ—][a-zA-Z0-9_Ò—]*	{ return Parser.IDENT; }
[0-9]+						{ return Parser.LITERALINT; }
[0-9]+"."[0-9]+				{ return Parser.LITERALREAL; }


/* -- Se aconseja dejar estas reglas al final ----------------------------------------------- */

"/*"([^*]|\*+[^*/])*\*+"/"		{ }		/* Comentario de varias lineas como este */
"//".*							{ }		// Comentario de una linea como este

[ \n\r]		{ }
"\t"		{ yycolumn += 3; } // Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t == 4 caracteres. En Jflex: \t == 1 car√°cter.

.			{ gestor.error("LÈxico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column())); }
