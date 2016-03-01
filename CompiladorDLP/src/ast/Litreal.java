package ast;

import visitor.*;

//	litreal:expresion -> valor:String ;

public class Litreal extends AbstractExpresion {

	public Litreal(String valor) {
		this.valor = valor;
	}

	public Litreal(Object valor) {
		this.valor = (valor instanceof Token) ? ((Token)valor).getLexeme() : (String) valor;

		searchForPositions(valor);	// Obtener linea/columna a partir de los hijos
	}

	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String valor;
}

