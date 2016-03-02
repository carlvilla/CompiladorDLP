package ast;

import visitor.*;

//	litent:expresion -> valor:int ;

public class Litent extends AbstractExpresion {

	public Litent(int valor) {
		this.valor = valor;
	}

	public Litent(Object valor) {
		this.valor = (valor instanceof Token) ? Integer.parseInt(((Token)valor).getLexeme()) : (Integer) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private int valor;
}

