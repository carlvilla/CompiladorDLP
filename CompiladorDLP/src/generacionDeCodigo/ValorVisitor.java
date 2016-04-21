package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import ast.ExpresionBinaria;
import ast.Litchar;
import ast.Litent;
import ast.Litreal;
import ast.Tipo;
import ast.Var;
import visitor.DefaultVisitor;

public class ValorVisitor extends DefaultVisitor{
	
	private Map<String, String> instruccion = new HashMap<String, String>();
	
	public ValorVisitor(Writer writer, String sourceFile,Map<String,String> instruccion) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
		this.instruccion = instruccion;
	}

	
//	class Litent { int valor; }
	public Object visit(Litent node, Object param) {
		genera("push " + node.getValor());
		return null;
	}

	//	class Litchar { String valor; }
	public Object visit(Litchar node, Object param) {
		genera("pushb " + node.getValor());
		return null;
	}

	//	class Litreal { String valor; }
	public Object visit(Litreal node, Object param) {
		genera("pushf " + node.getValor());
		return null;
	}

	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		genera("pusha " + node.getDefinicion().getDireccion());
		genera("Load",node.getTipo());
		
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
			node.getLeft().accept(this, param);
			node.getRight().accept(this, param);
			genera(instruccion.get(node.getString()),node.getTipo());
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

		private PrintWriter writer;// = new PrintWriter(System.out);;
		private String sourceFile;



}
