package main;

import generacionDeCodigo.*;

import java.io.*;

import semantico.*;
import sintactico.*;
import visitor.*;
import ast.*;


/**
 * Clase que inicia el compilador e invoca a todas sus fases.
 * 
 * No es necesario modificar este fichero. En su lugar hay que modificar:
 * - Para Análisis Sintáctico: 'sintactico/sintac.y' y 'sintactico/lexico.l'
 * - Para Análisis Semántico: 'semantico/Identificacion.java' y 'semantico/ComprobacionDeTipos.java'
 * - Para Generación de Código: 'generacionDeCodigo/GestionDeMemoria.java' y 'generacionDeCodigo/SeleccionDeInstrucciones.java'
 *
 * @author Raúl Izquierdo
 * @version 1.1
 */
public class Main {
	public static final String programa = "src/Hipoteca.txt";	// Entrada a usar durante el desarrollo

	public static void main(String[] args) throws Exception {
		GestorErrores gestor = new GestorErrores();

		AST raiz = compile(programa, gestor); // Poner args[0] en vez de "programa" en la versión final
		if (!gestor.hayErrores())
			System.out.println("El programa se ha compilado correctamente.");

		ASTPrinter.toHtml(programa, raiz, "Traza arbol"); // Utilidad generada por VGen (opcional)
	}

	/**
	 * Método que coordina todas las fases del compilador
	 */
	public static AST compile(String sourceName, GestorErrores gestor) throws Exception {

		// 1. Fases de Análisis Léxico y Sintáctico
		Yylex lexico = new Yylex(new FileReader(sourceName), gestor);
		
		
		Parser sintáctico = new Parser(lexico, gestor, false);
		sintáctico.parse();
		

		AST raiz = sintáctico.getAST();
		if (raiz == null) // Hay errores o el AST no se ha implementado aún
			return null;
		
		//Visitor
		Visitor visitor = new PrintVisitor();
		visitor.visit((Programa)raiz, null);

		// 2. Fase de Análisis Semántico
		AnalisisSemantico semántico = new AnalisisSemantico(gestor);
		semántico.analiza(raiz);
		if (gestor.hayErrores())
			return raiz;

		// 3. Fase de Generación de Código
		File sourceFile = new File(sourceName);
		Writer out = new FileWriter(new File(sourceFile.getParent(), "salida.txt"));

		GeneracionDeCodigo generador = new GeneracionDeCodigo();
		generador.genera(sourceFile.getName(), raiz, out);
		out.close();
	
		

		return raiz;
		
	
	   /* int token;
	    while ((token = lexico.yylex()) != 0)
		System.out.println("[" + lexico.line()+":"+lexico.column()+ "]" + "Token: " + token + " Lexema: " + lexico.lexeme());
	    
		
		return null;*/
		
	}
}
