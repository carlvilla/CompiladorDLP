package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Elemento> elemento; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getElemento(), param);
		return null;
	}

	//	class Funcion { String string;  List<Parametro> parametro;  Tipo tipo;  List<Atributo> atributo;  List<Sentencia> sentencia; }
	public Object visit(Funcion node, Object param) {
		visitChildren(node.getParametro(), param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		visitChildren(node.getAtributo(), param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class Struct { String string;  List<Definicion> definicion; }
	public Object visit(Struct node, Object param) {
		visitChildren(node.getDefinicion(), param);
		return null;
	}

	//	class Atributo { Definicion definicion; }
	public Object visit(Atributo node, Object param) {
		if (node.getDefinicion() != null)
			node.getDefinicion().accept(this, param);
		return null;
	}

	//	class Definicion { String nombre;  Tipo tipo; }
	public Object visit(Definicion node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Parametro { String string;  Tipo tipo; }
	public Object visit(Parametro node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class If { Expresion condic;  List<Sentencia> verdadero;  List<Sentencia> falso; }
	public Object visit(If node, Object param) {
		if (node.getCondic() != null)
			node.getCondic().accept(this, param);
		visitChildren(node.getVerdadero(), param);
		visitChildren(node.getFalso(), param);
		return null;
	}

	//	class Read { Expresion expresion; }
	public Object visit(Read node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Return { Expresion expresion; }
	public Object visit(Return node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class Print { Expresion expresion; }
	public Object visit(Print node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class While { Expresion expresion;  List<Sentencia> sentencia; }
	public Object visit(While node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		visitChildren(node.getSentencia(), param);
		return null;
	}

	//	class InvocarSentencia { Invocar invocar; }
	public Object visit(InvocarSentencia node, Object param) {
		if (node.getInvocar() != null)
			node.getInvocar().accept(this, param);
		return null;
	}

	//	class ExpresionBinaria { Expresion left;  String string;  Expresion right; }
	public Object visit(ExpresionBinaria node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Litent { int valor; }
	public Object visit(Litent node, Object param) {
		return null;
	}

	//	class Litchar { String valor; }
	public Object visit(Litchar node, Object param) {
		return null;
	}

	//	class Litreal { String valor; }
	public Object visit(Litreal node, Object param) {
		return null;
	}

	//	class Var { String nombre; }
	public Object visit(Var node, Object param) {
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class ExpresionUnaria { Expresion expresion; }
	public Object visit(ExpresionUnaria node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class AccesoArray { Expresion contenedor;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getContenedor() != null)
			node.getContenedor().accept(this, param);
		if (node.getPosicion() != null)
			node.getPosicion().accept(this, param);
		return null;
	}

	//	class AccesoStruct { Expresion contenedor;  String atributo; }
	public Object visit(AccesoStruct node, Object param) {
		if (node.getContenedor() != null)
			node.getContenedor().accept(this, param);
		return null;
	}

	//	class EntreParentesis { Expresion contenido; }
	public Object visit(EntreParentesis node, Object param) {
		if (node.getContenido() != null)
			node.getContenido().accept(this, param);
		return null;
	}

	//	class Invocar { String string;  List<Expresion> expresion; }
	public Object visit(Invocar node, Object param) {
		visitChildren(node.getExpresion(), param);
		return null;
	}

	//	class Array { Litent litent;  Tipo tipo; }
	public Object visit(Array node, Object param) {
		if (node.getLitent() != null)
			node.getLitent().accept(this, param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Tipoint {  }
	public Object visit(Tipoint node, Object param) {
		return null;
	}

	//	class Tiporeal {  }
	public Object visit(Tiporeal node, Object param) {
		return null;
	}

	//	class Tipochar {  }
	public Object visit(Tipochar node, Object param) {
		return null;
	}

	//	class Tipoident { String tipo; }
	public Object visit(Tipoident node, Object param) {
		return null;
	}
	
	// Método auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}
}
