package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import ast.ExpresionBinaria;
import ast.Print;
import ast.Programa;
import ast.Tipo;
import visitor.DefaultVisitor;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	private ValorVisitor valorVisitor;
	private DireccionVisitor direccionVisitor;
	
	private Map<String, String> instruccion = new HashMap<String, String>();
	
	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
		definirInstrucciones();
		valorVisitor = new ValorVisitor(writer,sourceFile,instruccion);
		direccionVisitor = new DireccionVisitor(writer, sourceFile);
	}

	private void definirInstrucciones() {
		instruccion.put("+", "add");
		instruccion.put("-", "sub");
		instruccion.put("*", "mul");
		instruccion.put("/", "div");
		instruccion.put("==", "eq");
		instruccion.put("!=", "ne");
		instruccion.put(">", "gt");
		instruccion.put(">=", "ge");
		instruccion.put("<", "lt");
		instruccion.put("<=", "le");
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

		if(node.getString()!="="){
			node.getLeft().accept(valorVisitor, param);
			node.getRight().accept(valorVisitor, param);
			genera(instruccion.get(node.getString()),node.getTipo());
		}else{
			node.getLeft().accept(direccionVisitor, param);
			node.getRight().accept(valorVisitor, param);
			genera("store",node.getTipo());
		}
		
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
