package generacionDeCodigo;

import java.io.*;
import java.util.*;

import ast.*;
import visitor.*;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	private ValorVisitor valorVisitor = new ValorVisitor();
	
	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}

	/*
	 * Poner aquí los visit necesarios.
	 * Si se ha usado VGen solo hay que copiarlos de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	//	Ejemplo:
	//
	//	public Object visit(Programa node, Object param) {
	//		genera("#source \"" + sourceFile + "\"");
	//		genera("call main");
	//		genera("halt");
	//		super.visit(node, param);	// Recorrer los hijos
	//		return null;
	//	}

	
//	class Programa { List<Elemento> elemento; }
	public Object visit(Programa node, Object param) {
		genera("#source \"" + sourceFile + "\"");
		visitChildren(node.getElemento(), param);
		genera("halt");
		return null;
	}
	
	
//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		genera("#line " + node.getEnd().getLine());
		node.getExpresion().accept(valorVisitor, param);
		genera("out",node.getExpresion().getTipo());
		
		return null;
	}
	
	
	
	
//	class ExpresionBinaria { Expresion left;  String string;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {

		// super.visit(node, param);

		if (node.getLeft() != null)
			node.getLeft().accept(this, param);

		if (node.getRight() != null)
			node.getRight().accept(this, param);

		return null;
	}
	
	
	
	


	
	
	// Método auxiliar recomendado -------------
		private void genera(String instruccion) {
			writer.println(instruccion);
		}

		private void genera(String instruccion, Tipo tipo) {
			genera(instruccion + tipo.getSufijo());
		}

		private PrintWriter writer;
		private String sourceFile;
}
