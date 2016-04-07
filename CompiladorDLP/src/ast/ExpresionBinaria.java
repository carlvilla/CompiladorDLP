package ast;

import visitor.*;

//	expresionBinaria:expresion, sentencia -> left:expresion  string:String  right:expresion ;

public class ExpresionBinaria extends AbstractTraceable implements Expresion, Sentencia {

	public ExpresionBinaria(Expresion left, String string, Expresion right) {
		this.left = left;
		this.string = string;
		this.right = right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public ExpresionBinaria(Object left, Object string, Object right) {
		this.left = (Expresion) left;
		this.string = (string instanceof Token) ? ((Token)string).getLexeme() : (String) string;
		this.right = (Expresion) right;

		searchForPositions(left, string, right);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}

	public Expresion getRight() {
		return right;
	}
	public void setRight(Expresion right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion left;
	private String string;
	private Expresion right;
	
	private Tipo tipo;

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}

